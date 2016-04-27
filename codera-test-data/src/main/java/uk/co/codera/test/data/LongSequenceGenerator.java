package uk.co.codera.test.data;

import java.util.concurrent.atomic.AtomicLong;

public class LongSequenceGenerator implements NumberGenerator<Long> {

    private final AtomicLong sequence;

    private LongSequenceGenerator(Builder builder) {
        this.sequence = new AtomicLong(builder.initialValue);
    }

    public static Builder aSequenceGenerator() {
        return new Builder();
    }

    @Override
    public Long next() {
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

        public NumberGenerator<Long> build() {
            return new LongSequenceGenerator(this);
        }
    }
}