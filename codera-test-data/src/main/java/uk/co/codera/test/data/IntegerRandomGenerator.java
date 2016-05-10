package uk.co.codera.test.data;

import java.util.Random;

public class IntegerRandomGenerator implements DataGenerator<Integer> {

    private final Random random;
    private final int bound;

    private IntegerRandomGenerator(Builder builder) {
        this.random = new Random(System.currentTimeMillis());
        this.bound = builder.maximumValue == null ? Integer.MAX_VALUE : builder.maximumValue;
    }

    public static Builder aRandomGenerator() {
        return new Builder();
    }

    @Override
    public Integer create() {
        return this.random.nextInt(this.bound);
    }

    public static class Builder {

        private Integer maximumValue;

        private Builder() {
            super();
        }

        public Builder maximumValue(Integer maximumValue) {
            this.maximumValue = maximumValue;
            return this;
        }

        public Builder noMaximumValue() {
            return maximumValue(null);
        }

        public IntegerRandomGenerator build() {
            return new IntegerRandomGenerator(this);
        }
    }
}