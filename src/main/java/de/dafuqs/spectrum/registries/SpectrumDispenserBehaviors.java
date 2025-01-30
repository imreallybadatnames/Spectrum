package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.blocks.mob_head.*;
import de.dafuqs.spectrum.blocks.shooting_star.*;
import de.dafuqs.spectrum.items.tools.*;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.*;
import net.minecraft.item.*;

public class SpectrumDispenserBehaviors {
	
	public static void register() {
		DispenserBlock.registerBehavior(SpectrumBlocks.BOTTOMLESS_BUNDLE, new BottomlessBundleItem.BottomlessBundlePlacementDispenserBehavior());
		DispenserBlock.registerBehavior(SpectrumItems.BEDROCK_SHEARS, new ShearsDispenserBehavior());
		
		// Shooting Stars
		DispenserBlock.registerBehavior(SpectrumBlocks.COLORFUL_SHOOTING_STAR, new ShootingStarDispenserBehavior());
		DispenserBlock.registerBehavior(SpectrumBlocks.FIERY_SHOOTING_STAR, new ShootingStarDispenserBehavior());
		DispenserBlock.registerBehavior(SpectrumBlocks.GEMSTONE_SHOOTING_STAR, new ShootingStarDispenserBehavior());
		DispenserBlock.registerBehavior(SpectrumBlocks.GLISTERING_SHOOTING_STAR, new ShootingStarDispenserBehavior());
		DispenserBlock.registerBehavior(SpectrumBlocks.PRISTINE_SHOOTING_STAR, new ShootingStarDispenserBehavior());
		
		// Fluid Buckets
		DispenserBehavior fluidBucketBehavior = DispenserBlock.BEHAVIORS.get(Items.WATER_BUCKET);
		DispenserBlock.registerBehavior(SpectrumItems.GOO_BUCKET, fluidBucketBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.LIQUID_CRYSTAL_BUCKET, fluidBucketBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.MIDNIGHT_SOLUTION_BUCKET, fluidBucketBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.DRAGONROT_BUCKET, fluidBucketBehavior);
		
		// Arrows
		for (GlassArrowVariant variant : SpectrumRegistries.GLASS_ARROW_VARIANT) {
			DispenserBlock.registerProjectileBehavior(variant.getArrow());
		}
		
		// Spawn Eggs
		DispenserBehavior spawnEggBehavior = DispenserBlock.BEHAVIORS.get(Items.SHEEP_SPAWN_EGG);
		DispenserBlock.registerBehavior(SpectrumItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG, spawnEggBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.KINDLING_SPAWN_EGG, spawnEggBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.LIZARD_SPAWN_EGG, spawnEggBehavior);
        DispenserBlock.registerBehavior(SpectrumItems.PRESERVATION_TURRET_SPAWN_EGG, spawnEggBehavior);
        DispenserBlock.registerBehavior(SpectrumItems.ERASER_SPAWN_EGG, spawnEggBehavior);
		
		// Equipping Mob Heads
		DispenserBehavior armorEquipBehavior = DispenserBlock.BEHAVIORS.get(Items.PLAYER_HEAD);
		for (Block skullBlock : SpectrumSkullBlock.MOB_HEADS.values()) {
			DispenserBlock.registerBehavior(skullBlock, armorEquipBehavior);
		}
		
		// Decay
		DispenserBehavior blockPlacementDispenserBehavior = new BlockPlacementDispenserBehavior();
		
		DispenserBlock.registerBehavior(SpectrumItems.BOTTLE_OF_FADING, blockPlacementDispenserBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.BOTTLE_OF_FAILING, blockPlacementDispenserBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.BOTTLE_OF_RUIN, blockPlacementDispenserBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.BOTTLE_OF_FORFEITURE, blockPlacementDispenserBehavior);
		DispenserBlock.registerBehavior(SpectrumItems.BOTTLE_OF_DECAY_AWAY, blockPlacementDispenserBehavior);
		
		DispenserBlock.registerBehavior(SpectrumItems.PRIMORDIAL_LIGHTER, PrimordialLighterItem.DISPENSER_BEHAVIOR);
	}
	
}
