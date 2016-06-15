package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestClassReport.aTestClassReport;
import static uk.co.codera.test.dto.TestMetadataFactory.metadataFor;
import static uk.co.codera.test.dto.TestMethodReport.aTestMethodReport;

import org.junit.Test;

public class TestClassReportTest {

    @Test
    public void shouldReturnEmptyListIfNoTestMethodsAdded() {
        assertThat(TestClassReport.aTestClassReport().build().getTestMethodReports(), empty());
    }

    @Test
    public void shouldReturnSensibleToStringAndNotObjectReference() {
        assertThat(TestClassReport.aTestClassReport().build().toString(), containsString("testReports="));
    }

    @Test
    public void shouldReturnTestClassName() {
        assertThat(TestClassReport.aTestClassReport().testClassName("aTestSuite").build().getTestClassName(),
                is("aTestSuite"));
    }

    @Test
    public void shouldMergeClassLevelMetadataIntoMethodIfSetWhenAdded() {
        TestClassReport classReport = aTestClassReport().defaultMetadata(classLevelMetadata())
                .addTestMethodReport(aTestMethodReport()).build();
        assertThat(classReport.getTestMethodReports().get(0).getTestType(), is(TestType.INTEGRATION));
    }

    @Test
    public void shouldNotMergeClassLevelMetadataIntoMethodIfSetAfterItWasAdded() {
        TestClassReport classReport = aTestClassReport().addTestMethodReport(aTestMethodReport())
                .defaultMetadata(classLevelMetadata()).build();
        assertThat(classReport.getTestMethodReports().get(0).getTestType(), is(TestType.UNIT));
    }

    private TestMetadata classLevelMetadata() {
        return metadataFor(ClassLevelMetadata.class);
    }

    @TestMetadata(type = TestType.INTEGRATION, issues = { "ISSUE-1" })
    private static class ClassLevelMetadata {
    }
}