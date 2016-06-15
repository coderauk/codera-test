package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestMetadataFactory.metadataFor;
import static uk.co.codera.test.dto.TestMethodReport.aTestMethodReport;

import org.junit.Test;

public class TestMethodReportTest {

    @Test
    public void shouldReturnSensibleToStringRatherThanObjectReference() {
        assertThat(aTestMethodReport().build().toString(), containsString("methodName="));
    }

    @Test
    public void shouldReturnMethodName() {
        assertThat(aTestMethodReport().methodName("shouldDoSomething").build().getMethodName(), is("shouldDoSomething"));
    }

    @Test
    public void shouldInheritClassLevelMetadataIfNoneSuppliedAtMethodLevel() {
        TestMethodReport methodReport = aTestMethodReport().defaultMetadata(classLevelMetadata()).build();
        assertThat(methodReport.getTestType(), is(TestType.INTEGRATION));
        assertThat(methodReport.getIssues(), contains("ISSUE-1"));
    }

    @Test
    public void shouldMergeClassAndMethodLevelMetadata() {
        TestMethodReport methodReport = aTestMethodReport().defaultMetadata(classLevelMetadata())
                .testMetadata(methodLevelMetadata()).build();
        assertThat(methodReport.getIssues(), hasItems("ISSUE-1", "ISSUE-2"));
    }

    private TestMetadata classLevelMetadata() {
        return metadataFor(ClassLevelMetadata.class);
    }

    private TestMetadata methodLevelMetadata() {
        return metadataFor(MethodLevelMetadata.class);
    }

    @TestMetadata(type = TestType.INTEGRATION, issues = { "ISSUE-1" })
    private static class ClassLevelMetadata {
    }

    @TestMetadata(issues = { "ISSUE-2" })
    private static class MethodLevelMetadata {
    }
}