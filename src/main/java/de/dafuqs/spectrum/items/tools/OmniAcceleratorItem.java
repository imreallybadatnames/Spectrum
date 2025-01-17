package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.*;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class OmniAcceleratorItem extends BundleItem implements InkPowered, ExtendedItemBarProvider, SlotBackgroundEffectProvider {
	
	protected static final InkCost COST = new InkCost(InkColors.YELLOW, 20);
	protected static final int CHARGE_TIME = 10;
	
	public OmniAcceleratorItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return CHARGE_TIME;
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!(user instanceof ServerPlayerEntity player)) return stack;
		
		Optional<ItemStack> shootStackOptional = getFirstStack(world.getRegistryManager(), stack);
		if (shootStackOptional.isEmpty()) {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			return stack;
		}
		
		if (!InkPowered.tryDrainEnergy(player, COST)) {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			return stack;
		}
		
		ItemStack shootStack = shootStackOptional.get();
		OmniAcceleratorProjectile projectile = OmniAcceleratorProjectile.get(shootStack);
		if (projectile.createProjectile(shootStack, user, world, stack) != null) {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), projectile.getSoundEffect(), SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
			if (!player.isCreative()) {
				decrementFirstItem(stack);
			}
		}
		
		return stack;
	}
	
	public static void decrementFirstItem(ItemStack acceleratorStack) {
		acceleratorStack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> {
			if (comp.contains("Items")) {
				comp.apply(nbt -> {
					NbtList itemsList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
					if (!itemsList.isEmpty()) {
						NbtCompound stackNbt = itemsList.getCompound(0);
						int count = stackNbt.getByte("Count");
						if (count > 1) {
							stackNbt.putByte("Count", (byte) (count - 1));
						} else {
							itemsList.removeFirst();
							if (itemsList.isEmpty()) {
								nbt.remove("Items");
							}
						}
					}
				});
			}
		});
	}
	
	public static Optional<ItemStack> getFirstStack(RegistryWrapper.WrapperLookup wrapperLookup, ItemStack stack) {
		var nbtComp = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
		if (!nbtComp.contains("Items")) {
			return Optional.empty();
		} else {
			NbtList itemsList = nbtComp.copyNbt().getList("Items", NbtElement.COMPOUND_TYPE);
			if (itemsList.isEmpty()) {
				return Optional.empty();
			} else {
				NbtCompound stackNbt = itemsList.getCompound(0);
				return ItemStack.fromNbt(wrapperLookup, stackNbt);
			}
		}
	}
	
	@Override
	public List<InkColor> getUsedColors() {
		return List.of(COST.color());
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		addInkPoweredTooltip(tooltip);
	}
	
	@Environment(EnvType.CLIENT)
	public static class Renderer implements DynamicItemRenderer {
		public Renderer() {
		}
		
		@Override
		public void render(ItemRenderer renderer, ItemStack stack, ModelTransformationMode mode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {
			renderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, model);
			MinecraftClient client = MinecraftClient.getInstance();
			if (mode != ModelTransformationMode.GUI || client.world == null) return;

			Optional<ItemStack> optionalStack = getFirstStack(client.world.getRegistryManager(), stack);
			if (optionalStack.isEmpty()) {
				return;
			}
			ItemStack bundledStack = optionalStack.get();

			BakedModel bundledModel = renderer.getModel(bundledStack, client.world, client.player, 0);
			
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0.5F, 0.5F, 0.5F);
			renderer.renderItem(bundledStack, mode, leftHanded, matrices, vertexConsumers, light, overlay, bundledModel);
			matrices.pop();
		}
	}
	
	@Override
	public SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
		var usable = InkPowered.hasAvailableInk(player, COST);
		return usable ? SlotEffect.BORDER_FADE : SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		return 0xFFFFFF;
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public boolean allowVanillaDurabilityBarRendering(@Nullable PlayerEntity player, ItemStack stack) {
		if (player == null || player.getStackInHand(player.getActiveHand()) != stack)
			return true;
		
		return !player.isUsingItem();
	}
	
	@Override
	public ExtendedItemBarProvider.BarSignature getSignature(@Nullable PlayerEntity player, @NotNull ItemStack stack, int index) {
		if (player == null || !player.isUsingItem())
			return ExtendedItemBarProvider.PASS;
		
		var activeStack = player.getStackInHand(player.getActiveHand());
		if (activeStack != stack)
			return ExtendedItemBarProvider.PASS;
		
		
		var progress = Math.round(MathHelper.clampedLerp(0, 13, ((float) player.getItemUseTime() / CHARGE_TIME)));
		return new ExtendedItemBarProvider.BarSignature(2, 13, 13, progress, 1, 0xFFFFFFFF, 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
}
