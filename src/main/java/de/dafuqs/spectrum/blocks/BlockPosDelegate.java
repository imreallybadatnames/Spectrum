package de.dafuqs.spectrum.blocks;

import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;

public class BlockPosDelegate implements PropertyDelegate {

    private final BlockPos pos;

    public BlockPosDelegate(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> pos.getX();
            case 1 -> pos.getY();
            case 2 -> pos.getZ();
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
    }

    @Override
    public int size() {
        return 3;
    }

}
