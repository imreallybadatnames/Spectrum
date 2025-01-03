package de.dafuqs.spectrum.api.predicate.world;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.server.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.math.*;

import java.util.concurrent.atomic.*;

public class CommandType extends WorldConditionType<CommandType.Config> implements CommandOutput {
	
	public CommandType(Codec<CommandType.Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean test(Config config, ServerWorld world, BlockPos pos) {
		AtomicBoolean passed = new AtomicBoolean(false);
		MinecraftServer minecraftServer = world.getServer();
		ServerCommandSource serverCommandSource = new ServerCommandSource(this, Vec3d.ofCenter(pos), Vec2f.ZERO, world, 2, "SpectrumCommandWorldCondition", world.getBlockState(pos).getBlock().getName(), minecraftServer, null)
				.withReturnValueConsumer((successful, returnValue) -> {
					passed.set(returnValue > 0);
				});
		minecraftServer.getCommandManager().executeWithPrefix(serverCommandSource, config.command);
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
	
	public static class Config extends WorldConditionType.Config {
		
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				Codec.STRING.fieldOf("command").forGetter((config) -> config.command)
		).apply(instance, Config::new));
		
		public final String command;
		
		public Config(String command) {
			this.command = command;
		}
		
	}
	
}
