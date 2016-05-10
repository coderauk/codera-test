package uk.co.codera.test.data;

@FunctionalInterface
public interface DataGenerator<T> {

    T create();
}