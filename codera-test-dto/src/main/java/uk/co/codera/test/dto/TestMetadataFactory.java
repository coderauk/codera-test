package uk.co.codera.test.dto;

@TestMetadata
public class TestMetadataFactory {

    private TestMetadataFactory() {
        super();
    }

    public static TestMetadata defaultTestMetadata() {
        return metadataFor(TestMetadataFactory.class);
    }

    public static TestMetadata metadataFor(Class<?> clazz) {
        return clazz.getAnnotation(TestMetadata.class);
    }
}