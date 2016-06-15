package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestMetadataFactory.defaultTestMetadata;

import org.junit.Test;

public class TestMetadataFactoryTest {

    @Test
    public void shouldNotReturnNullMetadata() {
        assertThat(defaultTestMetadata(), is(notNullValue()));
    }

    @Test
    public void shouldDefatulToHaveEmptyIssueList() {
        assertThat(defaultTestMetadata().issues(), is(arrayWithSize(0)));
    }

    @Test
    public void shouldDefaultToBeOfUnitTestType() {
        assertThat(defaultTestMetadata().type(), is(TestType.UNIT));
    }
}