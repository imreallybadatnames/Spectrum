package de.dafuqs.spectrum.recipe.pedestal;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import io.wispforest.endec.*;
import io.wispforest.endec.impl.*;
import io.wispforest.owo.serialization.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public enum BuiltinGemstoneColor implements GemstoneColor, StringIdentifiable {
	CYAN(DyeColor.CYAN),
	MAGENTA(DyeColor.MAGENTA),
	YELLOW(DyeColor.YELLOW),
	BLACK(DyeColor.BLACK),
	WHITE(DyeColor.WHITE);

	private final DyeColor dyeColor;

	public static final Endec<BuiltinGemstoneColor> ENDEC = Endec.forEnum(BuiltinGemstoneColor.class);

	public static final StructEndec<BuiltinGemstoneColor> STRING_ENDEC = StructEndecBuilder.of(
		CodecUtils.toEndec(DyeColor.CODEC).fieldOf("color", BuiltinGemstoneColor::getDyeColor),
		BuiltinGemstoneColor::of
	);

	BuiltinGemstoneColor(DyeColor dyeColor) {
		this.dyeColor = dyeColor;
	}

	public static BuiltinGemstoneColor of(DyeColor color) {
		switch (color) {
			case CYAN -> {
				return BuiltinGemstoneColor.CYAN;
			}
			case MAGENTA -> {
				return BuiltinGemstoneColor.MAGENTA;
			}
			case YELLOW -> {
				return BuiltinGemstoneColor.YELLOW;
			}
			case BLACK -> {
				return BuiltinGemstoneColor.BLACK;
			}
			case WHITE -> {
				return BuiltinGemstoneColor.WHITE;
			}
			default -> throw new RuntimeException("Tried getting powder item for a color which does not have one");
	}
	}
	@Override
	public DyeColor getDyeColor() {
		return this.dyeColor;
	}

	@Override
	public Item getGemstonePowderItem() {
		switch (this) {
			case CYAN -> {
				return SpectrumItems.TOPAZ_POWDER;
			}
			case MAGENTA -> {
				return SpectrumItems.AMETHYST_POWDER;
			}
			case YELLOW -> {
				return SpectrumItems.CITRINE_POWDER;
			}
			case BLACK -> {
				return SpectrumItems.ONYX_POWDER;
			}
			case WHITE -> {
				return SpectrumItems.MOONSTONE_POWDER;
			}
			default -> throw new RuntimeException("Tried getting powder item for a color which does not have one");
		}
	}

	@Override
	public Codec<GemstoneColor> getCodec() {
		return StringIdentifiable.createCodec(BuiltinGemstoneColor::values).xmap(t -> t,
				g -> BuiltinGemstoneColor.valueOf(g.asString()));
	}

	@Override
	public String asString() {
		return name();
	}
}