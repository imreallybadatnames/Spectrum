package de.dafuqs.spectrum.blocks.present;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;

import java.util.*;

public class PresentBlockEntity extends BlockEntity implements PlayerOwnedWithName {
	
	protected ItemStack presentStack = ItemStack.EMPTY;
	private UUID openerUUID;
	protected int openingTicks = 0;

	public PresentBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.PRESENT, pos, state);
	}
	
	public void triggerAdvancement() {
		UUID openerUUID = getOpenerUUID();
		if (openerUUID != null) {
			PlayerEntity opener = PlayerOwned.getPlayerEntityIfOnline(openerUUID);
			if (opener != null) {
				Support.grantAdvancementCriterion((ServerPlayerEntity) opener, "gift_or_open_present", "gifted_or_opened_present");
			}
		}
		
		UUID ownerUUID = getOwnerUUID();
		if (ownerUUID != null) {
			PlayerEntity wrapper = PlayerOwned.getPlayerEntityIfOnline(ownerUUID);
			if (wrapper != null) {
				Support.grantAdvancementCriterion((ServerPlayerEntity) wrapper, "gift_or_open_present", "gifted_or_opened_present");
			}
		}
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.presentStack = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound("Bundle"));
		if (nbt.contains("OpenerUUID")) {
			this.openerUUID = nbt.getUuid("OpenerUUID");
		} else {
			this.openerUUID = null;
		}
		if (nbt.contains("OpeningTick", NbtElement.NUMBER_TYPE)) {
			this.openingTicks = nbt.getInt("OpeningTick");
		}
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.put("Present", this.presentStack.encodeAllowEmpty(registryLookup));
		if (this.openerUUID != null) {
			nbt.putUuid("OpenerUUID", this.openerUUID);
		}
		if (this.openingTicks > 0) {
			nbt.putInt("OpeningTick", this.openingTicks);
		}
	}
	
	public int openingTick() {
		openingTicks++;
		markDirty();
		return this.openingTicks;
	}
	
	@Override
	public UUID getOwnerUUID() {
		return PresentItem.getOwner(this.presentStack).flatMap(ProfileComponent::id).orElse(null);
	}
	
	public ProfileComponent getOwner() {
		return PresentItem.getOwner(this.presentStack).orElse(null);
	}

	@Override
	public String getOwnerName() {
		return PresentItem.getOwner(this.presentStack).flatMap(ProfileComponent::name).orElse("???");
	}

	@Override
	public void setOwner(PlayerEntity playerEntity) {
		PresentItem.setOwner(this.presentStack, playerEntity);
		markDirty();
	}
	
	public void setOpenerUUID(PlayerEntity opener) {
		this.openerUUID = opener.getUuid();
		markDirty();
	}
	
	public UUID getOpenerUUID() {
		return this.openerUUID;
	}
	
	public ItemStack retrievePresent() {
		return this.presentStack.copy();
	}

	public Map<DyeColor, Integer> getColors() {
		return PresentItem.getWrapData(this.presentStack).colors();
	}

	public List<ItemStack> getStacks() {
		return PresentItem.getBundledStacks(this.presentStack).toList();
	}

	public DefaultedList<ItemStack> getDefaultedStacks() {
		var list = DefaultedList.copyOf(ItemStack.EMPTY);
		list.addAll(PresentItem.getBundledStacks(this.presentStack).toList());
		return list;
	}

	public void setPresent(ItemStack present) {
		this.presentStack = present;
		markDirty();
	}
	
	public boolean isEmpty() {
		return PresentItem.isEmpty(this.presentStack);
	}

}
