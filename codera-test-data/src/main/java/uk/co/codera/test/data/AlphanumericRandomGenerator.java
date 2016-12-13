package uk.co.codera.test.data;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class AlphanumericRandomGenerator implements DataGenerator<String> {

    private final Integer length;

    private AlphanumericRandomGenerator(Builder builder) {
        this.length = builder.length == null ? 10 : builder.length;
    }

    public static Builder aRandomAlphanumericGenerator() {
        return new Builder();
    }

    @Override
    public String create() {
        return randomAlphanumeric(length);
    }

    public static class Builder {

        private Integer length;

        private Builder() {
            super();
        }

        public Builder length(Integer length) {
            this.length = length;
            return this;
        }

        public AlphanumericRandomGenerator build() {
            return new AlphanumericRandomGenerator(this);
        }
    }
}
