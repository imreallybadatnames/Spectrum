package de.dafuqs.spectrum.blocks.pastel_network.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import de.dafuqs.spectrum.helpers.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.fabricmc.fabric.api.transfer.v1.storage.*;
import net.fabricmc.fabric.api.transfer.v1.transaction.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PastelTransmission implements SchedulerMap.Callback {

    public static final Codec<PastelTransmission> CODEC = RecordCodecBuilder.create(i -> i.group(
            BlockPos.CODEC.listOf().fieldOf("node_positions").forGetter(PastelTransmission::getNodePositions),
            ItemVariant.CODEC.fieldOf("variant").forGetter(PastelTransmission::getVariant),
            Codec.LONG.fieldOf("amount").forGetter(PastelTransmission::getAmount),
            Codec.INT.fieldOf("vertex_time").forGetter(PastelTransmission::getVertexTime)
    ).apply(i, PastelTransmission::new));

    public static final PacketCodec<RegistryByteBuf, PastelTransmission> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC.collect(PacketCodecs.toList()),
            PastelTransmission::getNodePositions,
            ItemVariant.PACKET_CODEC,
            PastelTransmission::getVariant,
            PacketCodecs.VAR_LONG,
            PastelTransmission::getAmount,
            PacketCodecs.VAR_INT,
            PastelTransmission::getVertexTime,
            PastelTransmission::new
    );

    private @Nullable PastelNetwork network;
    private final List<BlockPos> nodePositions;
    private final ItemVariant variant;
    private final long amount;
    private final int vertexTime;

    public PastelTransmission(List<BlockPos> nodePositions, ItemVariant variant, long amount, int vertexTime) {
        this.nodePositions = nodePositions;
        this.variant = variant;
        this.amount = amount;
        this.vertexTime = vertexTime;
    }

    public void setNetwork(@NotNull PastelNetwork network) {
        this.network = network;
    }

    public @Nullable PastelNetwork getNetwork() {
        return this.network;
    }

    public List<BlockPos> getNodePositions() {
        return nodePositions;
    }

    public int getVertexTime() {
        return vertexTime;
    }

    public int getTransmissionDuration() {
        return vertexTime * (nodePositions.size() - 1);
    }

    public ItemVariant getVariant() {
        return this.variant;
    }

    public long getAmount() {
        return this.amount;
    }

    public BlockPos getStartPos() {
        return this.nodePositions.getFirst();
    }

    @Override
    public void trigger() {
        arriveAtDestination();
    }

    private void arriveAtDestination() {
        if (nodePositions.isEmpty()) {
            return;
        }

        BlockPos destinationPos = nodePositions.getLast();
        PastelNodeBlockEntity destinationNode = this.network.getNodeAt(destinationPos);
        World world = this.network.getWorld();
        if (!world.isClient) {
            int inserted = 0;
            if (destinationNode != null) {
                Storage<ItemVariant> destinationStorage = destinationNode.getConnectedStorage();
                if (destinationStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        if (destinationStorage.supportsInsertion()) {
                            inserted = (int) destinationStorage.insert(variant, amount, transaction);
                            destinationNode.addItemCountUnderway(-inserted);
                            transaction.commit();
                        }
                    }
                }
            }
            if (inserted != amount) {
                InWorldInteractionHelper.scatter(world, destinationPos.getX() + 0.5, destinationPos.getY() + 0.5, destinationPos.getZ() + 0.5, variant, amount - inserted);
            }
        }
    }

}
