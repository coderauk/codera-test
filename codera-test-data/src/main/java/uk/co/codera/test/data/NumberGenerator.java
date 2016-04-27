package uk.co.codera.test.data;

@FunctionalInterface
public interface NumberGenerator<T> {

    T next();
}