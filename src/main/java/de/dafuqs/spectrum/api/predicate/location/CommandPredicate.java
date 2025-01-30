package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.server.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;

import java.util.concurrent.atomic.*;

public record CommandPredicate(String command) implements CommandOutput {
	
	public static final Codec<CommandPredicate> CODEC = Codec.STRING.xmap(CommandPredicate::new, CommandPredicate::command);
	public static final PacketCodec<ByteBuf, CommandPredicate> PACKET_CODEC = PacketCodecs.STRING.xmap(CommandPredicate::new, CommandPredicate::command);
	
	public boolean test(ServerWorld world, BlockPos pos) {
		AtomicBoolean passed = new AtomicBoolean(false);
		MinecraftServer minecraftServer = world.getServer();
		ServerCommandSource serverCommandSource = new ServerCommandSource(this, Vec3d.ofCenter(pos), Vec2f.ZERO, world, 2, "SpectrumCommandWorldCondition", world.getBlockState(pos).getBlock().getName(), minecraftServer, null)
				.withReturnValueConsumer((successful, returnValue) -> passed.set(returnValue > 0));
		minecraftServer.getCommandManager().executeWithPrefix(serverCommandSource, command);
		return passed.get();
	}
	
	@Override
	public void sendMessage(Text message) {
	}
	
	@Override
	public boolean shouldReceiveFeedback() {
		return false;
	}
	
	@Override
	public boolean shouldTrackOutput() {
		return false;
	}
	
	@Override
	public boolean shouldBroadcastConsoleToOps() {
		return false;
	}
	
}
