package uk.co.codera.test.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.data.AlphanumericRandomGenerator.aRandomAlphanumericGenerator;

import org.junit.Test;

public class AlphanumericRandomGeneratorTest {

    @Test
    public void shouldCreateNonNullAlphanumeric() {
        assertThat(aRandomAlphanumericGenerator().build().create(), is(notNullValue()));
    }

    @Test
    public void shouldCreateAnAlphanumericOfLength10IfNoLengthSpecified() {
        assertThat(aRandomAlphanumericGenerator().build().create().length(), is(10));
    }

    @Test
    public void shouldCreateAlphanumericOfSpecifiedLength() {
        assertThat(aRandomAlphanumericGenerator().length(5).build().create().length(), is(5));
    }
}
