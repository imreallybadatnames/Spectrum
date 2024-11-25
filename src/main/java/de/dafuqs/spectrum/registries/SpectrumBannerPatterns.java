package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.block.entity.*;
import net.minecraft.registry.*;

@SuppressWarnings("unused")
public class SpectrumBannerPatterns {
	
	public static RegistryKey<BannerPattern> SPECTRUM_LOGO = of("spectrum_logo");
	public static RegistryKey<BannerPattern> AMETHYST_CLUSTER = of("amethyst_cluster");
	public static RegistryKey<BannerPattern> AMETHYST_SHARD = of("amethyst_shard");
	public static RegistryKey<BannerPattern> CRAFTING_TABLET = of("crafting_tablet");
	public static RegistryKey<BannerPattern> FOUR_LEAF_CLOVER = of("four_leaf_clover");
	public static RegistryKey<BannerPattern> INK_FLASK = of("ink_flask");
	public static RegistryKey<BannerPattern> KNOWLEDGE_GEM = of("knowledge_gem");
	public static RegistryKey<BannerPattern> GUIDEBOOK = of("guidebook");
	public static RegistryKey<BannerPattern> MULTITOOL = of("multitool");
	public static RegistryKey<BannerPattern> NEOLITH = of("neolith");
	public static RegistryKey<BannerPattern> PALETTE = of("palette");
	public static RegistryKey<BannerPattern> PIGMENT = of("pigment");
	public static RegistryKey<BannerPattern> RAW_AZURITE = of("raw_azurite");
	public static RegistryKey<BannerPattern> SHIMMER = of("shimmer");
	public static RegistryKey<BannerPattern> VEGETAL = of("vegetal");
	public static RegistryKey<BannerPattern> BEDROCK_DUST = of("bedrock_dust");
	public static RegistryKey<BannerPattern> SHIMMERSTONE = of("shimmerstone");
	public static RegistryKey<BannerPattern> JADE_VINE = of("jade_vine");
	public static RegistryKey<BannerPattern> ASTROLOGER = of("astrologer");
	public static RegistryKey<BannerPattern> VELVET_ASTROLOGER = of("velvet_astrologer");
	public static RegistryKey<BannerPattern> POISONBLOOM = of("poisonbloom");
	public static RegistryKey<BannerPattern> DEEP_LIGHT = of("deep_light");

	private static RegistryKey<BannerPattern> of(String id) {
		return RegistryKey.of(RegistryKeys.BANNER_PATTERN, SpectrumCommon.locate(id));
	}

}
