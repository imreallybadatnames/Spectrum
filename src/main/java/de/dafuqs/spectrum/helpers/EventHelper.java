package de.dafuqs.spectrum.helpers;

import de.dafuqs.spectrum.blocks.redstone.RedstoneTransceiverBlock;
import de.dafuqs.spectrum.blocks.redstone.RedstoneTransceiverBlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EventHelper {

    public static DyeColor getRedstoneEventDyeColor(GameEvent.Message message) {
        return message.getEmitter().affectedState().getOrEmpty(RedstoneTransceiverBlock.CHANNEL).orElse(null);
    }

    public static int getRedstoneEventPower(World world, GameEvent.Message message) {
        var pos = message.getEmitterPos();
        var blockEntity = world.getBlockEntity(BlockPos.ofFloored(pos.x, pos.y, pos.z));
        if (blockEntity instanceof RedstoneTransceiverBlockEntity transceiver) {
            return transceiver.getCurrentSignalStrength();
        }
        return 0;
    }

}
