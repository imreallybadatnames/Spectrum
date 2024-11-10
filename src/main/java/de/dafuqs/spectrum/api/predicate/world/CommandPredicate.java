package de.dafuqs.spectrum.api.predicate.world;

import com.google.gson.*;
import net.minecraft.server.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class CommandPredicate implements WorldConditionPredicate, CommandOutput {
	public static final CommandPredicate ANY = new CommandPredicate(null);
	
	public final String command;
	
	public CommandPredicate(String command) {
		this.command = command;
	}
	
	public static CommandPredicate fromJson(JsonObject json) {
		if (json == null || json.isJsonNull()) return ANY;
		return new CommandPredicate(json.get("command").getAsString());
	}
	
	@Override
	public boolean test(ServerWorld world, BlockPos pos) {
		if (this == ANY) return true;
		AtomicBoolean passed = new AtomicBoolean(false);
		MinecraftServer minecraftServer = world.getServer();
		ServerCommandSource serverCommandSource = new ServerCommandSource(this, Vec3d.ofCenter(pos), Vec2f.ZERO, world, 2, "FusionShrine", world.getBlockState(pos).getBlock().getName(), minecraftServer, null)
				.withReturnValueConsumer((successful, returnValue) -> {
					passed.set(returnValue > 0);
				});
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
