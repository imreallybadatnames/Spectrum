package de.dafuqs.spectrum.blocks.deeper_down;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.block.*;
import net.minecraft.block.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class StrippingLootBlock extends Block implements StrippableDrop {
    
    private final Block sourceBlock;
    private final RegistryKey<LootTable> strippingLootTableKey;
    
    public StrippingLootBlock(Settings settings, Block sourceBlock, RegistryKey<LootTable> strippingLootTableKey) {
        super(settings);
        this.sourceBlock = sourceBlock;
        this.strippingLootTableKey = strippingLootTableKey;
    }

    @Override
    public MapCodec<? extends StrippingLootBlock> getCodec() {
        //TODO: Make the codec
        return null;
    }
    
    @Override
    public Block getStrippedBlock() {
        return sourceBlock;
    }
    
    @Override
    public RegistryKey<LootTable> getStrippingLootTableKey() {
        return strippingLootTableKey;
    }
    
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        checkAndDropStrippedLoot(state, world, pos, newState, moved);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    
}
