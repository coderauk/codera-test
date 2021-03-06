package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
    public void shouldReturnMethodNameAsEnglish() {
        assertThat(aTestMethodReport().methodName("shouldDoSomething").build().getMethodNameAsEnglish(),
                is("Should Do Something"));
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

    @Test
    public void shouldNotOverrideTestTypeSpecifiedAtClassLevelAndNoAnnotationAtMethodLevel() {
        TestMethodReport methodReport = aTestMethodReport().defaultMetadata(classLevelMetadata()).build();
        assertThat(methodReport.getTestType(), is(TestType.INTEGRATION));
    }

    @Test
    public void shouldNotOverrideTestTypeSpecifiedAtClassLevelAndAnnotatedAtMethodLevelAsWell() {
        TestMethodReport methodReport = aTestMethodReport().defaultMetadata(classLevelMetadata())
                .testMetadata(methodLevelMetadata()).build();
        assertThat(methodReport.getTestType(), is(TestType.INTEGRATION));
    }

    @Test
    public void shouldOverrideTestTypeIfSpecifiedAtMethodLevelButNotClassLevel() {
        TestMethodReport methodReport = aTestMethodReport().testMetadata(methodLevelMetadataWithComponentType())
                .build();
        assertThat(methodReport.getTestType(), is(TestType.COMPONENT));
    }

    @Test
    public void shouldReturnIssueCount() {
        TestMethodReport methodReport = aTestMethodReport().defaultMetadata(classLevelMetadata())
                .testMetadata(methodLevelMetadata()).build();
        assertThat(methodReport.getIssueCount(), is(2));
    }

    @Test
    public void shouldDeclareIfItHasIssues() {
        assertThat(aTestMethodReport().defaultMetadata(classLevelMetadata()).build().hasIssues(), is(true));
    }

    @Test
    public void shouldDeclareIfItDoesNotHaveIssues() {
        assertThat(aTestMethodReport().build().hasIssues(), is(false));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(aTestMethodReport().build(), is(not(equalTo(null))));
    }

    @Test
    public void shouldNotBeEqualToDifferentType() {
        assertThat(aTestMethodReport().build(), is(not(equalTo("a String"))));
    }

    @Test
    public void shouldBeEqualIfForSameMethod() {
        assertEqual(aTestMethodReport().methodName("shouldDoSomething"),
                aTestMethodReport().methodName("shouldDoSomething"));
    }

    @Test
    public void shouldNotBeEqualIfForDifferentMethod() {
        assertNotEqual(aTestMethodReport().methodName("shouldDoSomething"),
                aTestMethodReport().methodName("shouldDoSomethingElse"));
    }

    private void assertEqual(TestMethodReport.Builder report1, TestMethodReport.Builder report2) {
        assertThat(report1.build(), is(equalTo(report2.build())));
    }

    private void assertNotEqual(TestMethodReport.Builder report1, TestMethodReport.Builder report2) {
        assertThat(report1.build(), is(not(equalTo(report2.build()))));
    }

    private TestMetadata classLevelMetadata() {
        return metadataFor(ClassLevelMetadata.class);
    }

    private TestMetadata methodLevelMetadata() {
        return metadataFor(MethodLevelMetadata.class);
    }

    private TestMetadata methodLevelMetadataWithComponentType() {
        return metadataFor(MethodLevelMetadataWithComponentType.class);
    }

    @TestMetadata(type = TestType.INTEGRATION, issues = { "ISSUE-1" })
    private static class ClassLevelMetadata {
    }

    @TestMetadata(issues = { "ISSUE-2" })
    private static class MethodLevelMetadata {
    }

    @TestMetadata(type = TestType.COMPONENT)
    private static class MethodLevelMetadataWithComponentType {
    }
}