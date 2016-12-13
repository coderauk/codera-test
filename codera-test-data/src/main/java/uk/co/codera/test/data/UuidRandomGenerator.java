package uk.co.codera.test.data;

import java.util.UUID;

public class UuidRandomGenerator implements DataGenerator<String> {

    private UuidRandomGenerator(Builder builder) {
        super();
    }

    public static Builder aRandomUuidGenerator() {
        return new Builder();
    }

    @Override
    public String create() {
        return UUID.randomUUID().toString();
    }

    public static class Builder {

        private Builder() {
            super();
        }

        public UuidRandomGenerator build() {
            return new UuidRandomGenerator(this);
        }
    }

}
