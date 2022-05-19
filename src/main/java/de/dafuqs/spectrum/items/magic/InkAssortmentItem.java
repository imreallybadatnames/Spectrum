package de.dafuqs.spectrum.items.magic;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.energy.InkStorageItem;
import de.dafuqs.spectrum.energy.storage.IndividualCappedSimpleInkStorage;
import de.dafuqs.spectrum.items.trinkets.SpectrumTrinketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InkAssortmentItem extends SpectrumTrinketItem implements InkStorageItem<IndividualCappedSimpleInkStorage> {
	
	private final long maxEnergy;
	
	public InkAssortmentItem(Settings settings, long maxEnergy) {
		super(settings, new Identifier(SpectrumCommon.MOD_ID, "progression/unlock_ink_assortment"));
		this.maxEnergy = maxEnergy;
	}
	
	@Override
	public IndividualCappedSimpleInkStorage getEnergyStorage(ItemStack itemStack) {
		NbtCompound compound = itemStack.getNbt();
		if(compound != null && compound.contains("EnergyStore")) {
			return IndividualCappedSimpleInkStorage.fromNbt(compound.getCompound("EnergyStore"));
		}
		return new IndividualCappedSimpleInkStorage(this.maxEnergy);
	}
	
	@Override
	public void setEnergyStorage(ItemStack itemStack, IndividualCappedSimpleInkStorage storage) {
		NbtCompound compound = itemStack.getOrCreateNbt();
		compound.put("EnergyStore", storage.toNbt());
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		getEnergyStorage(stack).addTooltip(world, tooltip, context);
	}
	
}