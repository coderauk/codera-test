package uk.co.codera.test.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class MetadataXmlFilterTest {

    private static final String VALID_FILENAME = "METADATA-1234.xml";
    private static final String INVALID_FILENAME = "NOT-1234.json";

    private MetadataXmlFilter filter;

    @Before
    public void before() {
        this.filter = new MetadataXmlFilter();
    }

    @Test
    public void shouldAcceptFileWithValidFilename() {
        assertThat(this.filter.accept(new File(VALID_FILENAME)), is(true));
    }

    @Test
    public void shouldNotAcceptFileWithInvalidFormatFilename() {
        assertThat(this.filter.accept(new File(INVALID_FILENAME)), is(false));
    }

    @Test
    public void shouldAcceptDirectoryAndValidFilename() {
        assertThat(this.filter.accept(new File("/directory"), VALID_FILENAME), is(true));
    }

    @Test
    public void shouldNotAcceptDirectoryAndInvalidFilename() {
        assertThat(this.filter.accept(new File("/directory"), INVALID_FILENAME), is(false));
    }
}