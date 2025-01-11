package de.dafuqs.spectrum.blocks.pastel_network.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ServerPastelNetwork extends PastelNetwork {

	public static final Codec<ServerPastelNetwork> CODEC = RecordCodecBuilder.create(i -> i.group(
			Uuids.CODEC.fieldOf("uuid").forGetter(ServerPastelNetwork::getUUID),
			World.CODEC.xmap(k -> (World) SpectrumCommon.minecraftServer.getWorld(k), World::getRegistryKey).fieldOf("world").forGetter(b -> b.world),
			TickLooper.CODEC.fieldOf("looper").forGetter(b -> b.transferLooper),
			SchedulerMap.getCodec(PastelTransmission.CODEC).fieldOf("transmissions").forGetter(b -> b.transmissions)
	).apply(i, ServerPastelNetwork::new));

	// new transfers are checked for every 10 ticks
	private final TickLooper transferLooper;
	protected final SchedulerMap<PastelTransmission> transmissions;
	protected final PastelTransmissionLogic transmissionLogic;

	public ServerPastelNetwork(World world, @Nullable UUID uuid) {
		this(uuid, world, new TickLooper(10), new SchedulerMap<>());
	}
	public ServerPastelNetwork(UUID uuid, World world, TickLooper transferLoop, SchedulerMap<PastelTransmission> transmissions) {
		super(world, uuid);
		this.transferLooper = transferLoop;
		this.transmissions = transmissions;
		this.transmissionLogic = new PastelTransmissionLogic(this);

		for (var entry : transmissions) {
			entry.getKey().setNetwork(this);
		}
	}

	@Override
	public void incorporate(PastelNetwork networkToIncorporate, PastelNodeBlockEntity node, PastelNodeBlockEntity otherNode) {
        super.incorporate(networkToIncorporate, node, otherNode);
		this.transmissionLogic.invalidateCache();
	}
	
	@Override
	public void addNodeAndLoadMemory(PastelNodeBlockEntity node) {
		super.addNodeAndLoadMemory(node);
		this.transmissionLogic.invalidateCache();
	}
	
	@Override
	public boolean removeNode(PastelNodeBlockEntity node, NodeRemovalReason reason) {
		boolean result = super.removeNode(node, reason);
		this.transmissionLogic.invalidateCache();
		return result;
	}

	@Override
	public void addAndRememberEdge(PastelNodeBlockEntity newNode, PastelNodeBlockEntity parent) {
		super.addAndRememberEdge(newNode, parent);
		this.transmissionLogic.invalidateCache();
	}

	@Override
	public void removeAndForgetEdge(PastelNodeBlockEntity node, PastelNodeBlockEntity parent) {
		super.removeAndForgetEdge(node, parent);
		this.transmissionLogic.invalidateCache();
	}

	@Override
	public void tick() {
		this.transmissions.tick();
		var priority = Priority.GENERIC;

		if (transferLooper.getTick() % 5 == 0) {
			priority = Priority.MODERATE;
		}
		else if (transferLooper.getTick() % 2 == 0) {
			priority = Priority.HIGH;
		}

		this.transferLooper.tick();
		var cap = transferLooper.reachedCap();
		if (cap || priority != Priority.GENERIC) {
			if (cap) {
				this.transferLooper.reset();
			}
			try {
				this.transmissionLogic.tick(priority);
			} catch (Exception e) {
				// hmmmmmm. Block getting unloaded / new one placed while logic is running?
			}
		}
		tickNodeEffects();
	}

	private void tickNodeEffects() {
		List<PastelNodeBlockEntity> nodeSync = new ArrayList<>();


		for (Map.Entry<PastelTransmission, Integer> transPair : transmissions) {
			var transmission = transPair.getKey();
			var remainingTravelTime = transPair.getValue();
			var nodes = transmission.getNodePositions();

			if (nodes.isEmpty())
				continue;

			var travelTime = transmission.getTransmissionDuration();
			double progress = travelTime - remainingTravelTime;

			if (progress != 0 && progress % transmission.getVertexTime() == 0) {
				var node = world.getBlockEntity(nodes.get((int) Math.round((nodes.size() - 1) * progress / travelTime)));

				if (!(node instanceof PastelNodeBlockEntity pastelNode))
					continue;

				nodeSync.add(pastelNode);
				if (pastelNode.isSensor())
					pastelNode.notifySensor();
			}
		}

		if (!nodeSync.isEmpty())
			PastelNodeStatusUpdatePayload.sendPastelNodeStatusUpdate(nodeSync, false);
	}
	
	@Override
	public void addTransmission(PastelTransmission transmission, int travelTime) {
		transmission.setNetwork(this);
		this.transmissions.put(transmission, travelTime);
	}

}
