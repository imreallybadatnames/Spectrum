package de.dafuqs.spectrum.helpers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// TODO: migrate to net.minecraft.world.tick ?
public final class TickLooper {

    public static final Codec<TickLooper> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("max").forGetter(TickLooper::getMaxTick),
            Codec.INT.fieldOf("current").forGetter(TickLooper::getTick)
    ).apply(i, TickLooper::new));

    private final int maxTick;
    private int currentTick;

    public TickLooper(int maxTick) {
        this(maxTick, 0);
    }

    private TickLooper(int maxTick, int currentTick) {
        this.maxTick = maxTick;
        this.currentTick = currentTick;
    }

    public void tick() {
        currentTick++;
    }

    public boolean reachedCap() {
        return currentTick >= maxTick;
    }

    public void reset() {
        currentTick = 0;
    }

    public int getTick() {
        return currentTick;
    }

    public int getMaxTick() {
        return maxTick;
    }

    public float getProgress() {
        return (float) currentTick / (float) maxTick;
    }
    
    @Override
    public String toString() {
        return "TickLooper (" + currentTick + "/" + maxTick + ")";
    }

}