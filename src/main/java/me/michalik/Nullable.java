package me.michalik;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Nullable<T> {

    private static final Nullable<?> EMPTY = new Nullable<>();

    private final T value;

    private Nullable() {
        this.value = null;
    }

    private Nullable(T value) {
        this.value = value;
    }

    public static <T> Nullable<T> empty() {
        Nullable<T> nullable = (Nullable<T>) EMPTY;
        return nullable;
    }

    public static <T> Nullable<T> of(T value) {
        return value == null ? empty() : new Nullable<>(value);
    }

    public T get() {
        if(value == null) {
            throw new NullValueException("Value is not present");
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if(value != null) {
            consumer.accept(value);
        }
    }

    public Nullable<T> ifNotPresent(Runnable runnable) {
        if (value != null) {
            return this;
        }else {
            runnable.run();
            return empty();
        }
    }

    public Nullable<T> filter(Predicate<? super T> predicate) {
        return value == null ? this : predicate.test(value) ? this : empty();
    }

    public<U> Nullable<U> map(Function<? super T, ? extends U> mapper) {
        return value == null ? empty() : Nullable.of(mapper.apply(value));
    }

    public<U> Nullable<U> flatMap(Function<? super T, Nullable<U>> mapper) {
        return value == null ? empty() : mapper.apply(value);
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Nullable)) {
            return false;
        }
        Nullable<?> nullable = (Nullable<?>) o;
        return Objects.equals(value, nullable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? "Nullable{" +
                "value=" + value +
                '}' : "Nullable.empty";
    }
}
