package uk.co.codera.test.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.data.UuidRandomGenerator.aRandomUuidGenerator;

import org.junit.Test;

public class UuidRandomGeneratorTest {

    @Test
    public void shouldCreateNonNullUuid() {
        assertThat(aRandomUuidGenerator().build().create(), is(notNullValue()));
    }

    @Test
    public void shouldCreateUuidOfLength36() {
        assertThat(aRandomUuidGenerator().build().create().length(), is(36));
    }
}
