package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.component_type.*;
import net.minecraft.component.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.codec.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.function.*;

public class SpectrumDataComponentTypes {
	
	private static final Deferrer DEFERRER = new Deferrer();
	
	public static ComponentType<Unit> ACTIVATED = register("activated", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<Integer> AOE = register("aoe", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
	public static ComponentType<BeverageComponent> BEVERAGE = register("beverage", builder -> builder.codec(BeverageComponent.CODEC).packetCodec(BeverageComponent.PACKET_CODEC));
	public static ComponentType<BottomlessBundleItem.BottomlessStack> BOTTOMLESS_STACK = register("bottomless_stack", builder -> builder.codec(BottomlessBundleItem.BottomlessStack.CODEC).packetCodec(BottomlessBundleItem.BottomlessStack.PACKET_CODEC));
	public static ComponentType<Identifier> BOUND_ITEM = register("bound_item", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
	public static ComponentType<Long> LAST_COOLDOWN_START = register("last_cooldown_start", builder -> builder.codec(Codec.LONG).packetCodec(PacketCodecs.VAR_LONG));
	public static ComponentType<Unit> HIDE_USAGE_TOOLTIP = register("hide_usage_tooltip", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<InertiaComponent> INERTIA = register("inertia", builder -> builder.codec(InertiaComponent.CODEC).packetCodec(InertiaComponent.PACKET_CODEC));
	public static ComponentType<InfusedBeverageComponent> INFUSED_BEVERAGE = register("infused_beverage", builder -> builder.codec(InfusedBeverageComponent.CODEC).packetCodec(InfusedBeverageComponent.PACKET_CODEC));
	public static ComponentType<InkStorageComponent> INK_STORAGE = register("ink_storage", builder -> builder.codec(InkStorageComponent.CODEC).packetCodec(InkStorageComponent.PACKET_CODEC));
	public static ComponentType<Unit> IS_PREVIEW_ITEM = register("is_preview_item", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<JadeWineComponent> JADE_WINE = register("jade_wine", builder -> builder.codec(JadeWineComponent.CODEC).packetCodec(JadeWineComponent.PACKET_CODEC));
	public static ComponentType<StatusEffectInstance> OIL_EFFECT = register("oil_effect", builder -> builder.codec(StatusEffectInstance.CODEC).packetCodec(StatusEffectInstance.PACKET_CODEC));
	public static ComponentType<OptionalInkColorComponent> OPTIONAL_INK_COLOR = register("optional_ink_color", builder -> builder.codec(OptionalInkColorComponent.CODEC).packetCodec(OptionalInkColorComponent.PACKET_CODEC));
	public static ComponentType<Float> OVERCHARGED = register("overcharged", builder -> builder.codec(Codec.FLOAT).packetCodec(PacketCodecs.FLOAT));
	public static ComponentType<ShootingStarComponent> SHOOTING_STAR = register("shooting_star", builder -> builder.codec(ShootingStarComponent.CODEC).packetCodec(ShootingStarComponent.PACKET_CODEC));
	public static ComponentType<Unit> SOCKETED = register("socketed", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<Unit> STABLE = register("stable", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<Integer> STORED_EXPERIENCE = register("stored_experiene", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));
	public static ComponentType<Identifier> STORED_RECIPE = register("stored_recipe", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
	public static ComponentType<PipeBombComponent> PIPE_BOMB = register("pipe_bomb", builder -> builder.codec(PipeBombComponent.CODEC).packetCodec(PipeBombComponent.PACKET_CODEC));
	public static ComponentType<Unit> UNIDENTIFIABLE = register("unidentifiable", builder -> builder.codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)));
	public static ComponentType<WorkstaffComponent> WORKSTAFF = register("workstaff", builder -> builder.codec(WorkstaffComponent.CODEC).packetCodec(WorkstaffComponent.PACKET_CODEC));
	public static ComponentType<WrappedPresentComponent> WRAPPED_PRESENT = register("wrapped_present", builder -> builder.codec(WrappedPresentComponent.CODEC).packetCodec(WrappedPresentComponent.PACKET_CODEC));
	
	public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return DEFERRER.defer(builderOperator.apply(ComponentType.builder()).build(),
				type -> Registry.register(Registries.DATA_COMPONENT_TYPE, id, type));
	}
	
	public static void register() {
		DEFERRER.flush();
	}
	
}
