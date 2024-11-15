package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public class PastelNetworkCreationCriterion extends AbstractCriterion<PastelNetworkCreationCriterion.Conditions> {
	
	public static final Identifier ID = SpectrumCommon.locate("pastel_network_creation");
	
	public void trigger(ServerPlayerEntity player, ServerPastelNetwork network) {
		this.trigger(player, (conditions) -> conditions.matches(network.getNodes(PastelNodeType.CONNECTION).size(), network.getNodes(PastelNodeType.PROVIDER).size(),
			network.getNodes(PastelNodeType.STORAGE).size(), network.getNodes(PastelNodeType.SENDER).size(), network.getNodes(PastelNodeType.GATHER).size(), network.getNodes(PastelNodeType.BUFFER).size()));
	}
	
	@Override
	public Codec<Conditions> getConditionsCodec() {
		return Conditions.CODEC;
	}
	
	public record Conditions(
		Optional<LootContextPredicate> player,
		NumberRange.IntRange totalNodes,
		NumberRange.IntRange connectionNodes,
		NumberRange.IntRange providerNodes,
		NumberRange.IntRange storageNodes,
		NumberRange.IntRange senderNodes,
		NumberRange.IntRange gatherNodes,
		NumberRange.IntRange bufferNodes
	) implements AbstractCriterion.Conditions {
		
		public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			LootContextPredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
			NumberRange.IntRange.CODEC.fieldOf("total_nodes").forGetter(Conditions::totalNodes),
			NumberRange.IntRange.CODEC.fieldOf("connection_nodes").forGetter(Conditions::connectionNodes),
			NumberRange.IntRange.CODEC.fieldOf("provider_nodes").forGetter(Conditions::providerNodes),
			NumberRange.IntRange.CODEC.fieldOf("storage_nodes").forGetter(Conditions::storageNodes),
			NumberRange.IntRange.CODEC.fieldOf("sender_nodes").forGetter(Conditions::senderNodes),
			NumberRange.IntRange.CODEC.fieldOf("gather_nodes").forGetter(Conditions::gatherNodes),
			NumberRange.IntRange.CODEC.fieldOf("buffer_nodes").forGetter(Conditions::bufferNodes)
			).apply(instance, Conditions::new));
		
		
		public boolean matches(int connectionNodes, int providerNodes, int storageNodes, int senderNodes, int gatherNodes, int bufferNodes) {
			return this.totalNodes.test(connectionNodes + providerNodes + storageNodes + senderNodes + gatherNodes) && this.connectionNodes.test(connectionNodes) && this.providerNodes.test(providerNodes) && this.storageNodes.test(storageNodes) && this.senderNodes.test(senderNodes) && this.gatherNodes.test(gatherNodes) && this.bufferNodes.test(bufferNodes);
		}
		
	}
	
}
