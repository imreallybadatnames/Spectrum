package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public interface Dragonjag {

    enum Variant implements StringIdentifiable {
        YELLOW,
        RED,
        PINK,
        PURPLE,
        BLACK;

        public static final Codec<Variant> CODEC = StringIdentifiable.createCodec(Variant::values);

        @Override
        public String asString() {
            return name();
        }
    }

    Dragonjag.Variant getVariant();

    static boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }

}
