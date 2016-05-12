package uk.co.codera.test.junit;

public interface Adapter<I, O> {

    O adapt(I input);
}