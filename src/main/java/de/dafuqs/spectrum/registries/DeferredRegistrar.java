package de.dafuqs.spectrum.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class DeferredRegistrar {

    private static Class<?> currentClass = null;
    private static final Map<Class<?>, List<Deferred<?>>> DEFERRED_COMMON = new HashMap<>();
    private static final Map<Class<?>, List<Deferred<?>>> DEFERRED_CLIENT = new HashMap<>();

    private record Deferred<T>(T value, Consumer<T> callback) {
        public void apply() {
            callback.accept(value);
        }
    }

    public static void setClass(Class<?> newClass) {
        currentClass = newClass;
    }

    public static void registerCommon(Class<?> registrationClass) {
        if (DEFERRED_COMMON.containsKey(registrationClass)) {
            DEFERRED_COMMON.get(registrationClass).forEach(Deferred::apply);
        }
    }

    public static void registerClient(Class<?> registrationClass) {
        if (DEFERRED_CLIENT.containsKey(registrationClass)) {
            DEFERRED_CLIENT.get(registrationClass).forEach(Deferred::apply);
        }
    }

    public static <T> DeferredBuilder<T> defer(T value) {
        return new DeferredBuilder<>(value);
    }

    public static class DeferredBuilder<T> {

        private final T value;

        private DeferredBuilder(T value) {
            this.value = value;
        }

        public DeferredBuilder<T> withCommon(Consumer<T> callback) {
            DEFERRED_COMMON.computeIfAbsent(currentClass, key -> new ArrayList<>()).add(new Deferred<>(value, callback));
            return this;
        }

        public DeferredBuilder<T> withClient(Consumer<T> callback) {
            DEFERRED_CLIENT.computeIfAbsent(currentClass, key -> new ArrayList<>()).add(new Deferred<>(value, callback));
            return this;
        }

        public T value() {
            return value;
        }

    }

}
