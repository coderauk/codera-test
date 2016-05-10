package uk.co.codera.test.data;

import java.util.concurrent.atomic.AtomicLong;

public class LongSequenceGenerator implements DataGenerator<Long> {

    private final AtomicLong sequence;

    private LongSequenceGenerator(Builder builder) {
        this.sequence = new AtomicLong(builder.initialValue);
    }

    public static Builder aSequenceGenerator() {
        return new Builder();
    }

    @Override
    public Long create() {
        return Long.valueOf(this.sequence.getAndIncrement());
    }

    public static class Builder {

        private long initialValue = 0L;

        private Builder() {
            super();
        }

        public Builder startingAt(long initialValue) {
            this.initialValue = initialValue;
            return this;
        }

        public DataGenerator<Long> build() {
            return new LongSequenceGenerator(this);
        }
    }
}