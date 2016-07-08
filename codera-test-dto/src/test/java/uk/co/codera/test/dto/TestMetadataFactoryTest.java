package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestMetadataFactory.defaultTestMetadata;

import org.junit.Test;

import uk.co.codera.test.utility.ClassAssert;

public class TestMetadataFactoryTest {

    @Test
    public void shouldConformToStaticUtilityClass() {
        ClassAssert.assertStaticUtilityClass(TestMetadataFactory.class);
    }

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