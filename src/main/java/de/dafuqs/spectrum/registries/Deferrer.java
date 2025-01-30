package de.dafuqs.spectrum.registries;

import java.util.*;
import java.util.function.*;

public class Deferrer {
	
	private final ArrayList<Runnable> deferred = new ArrayList<>();
	
	public void flush() {
		deferred.forEach(Runnable::run);
		deferred.clear();
		deferred.trimToSize();
	}
	
	public <T> T defer(T value, Consumer<T> callback) {
		deferred.add(() -> callback.accept(value));
		return value;
	}
	
}
