package uk.co.codera.test.dto;

import java.util.UUID;

public class ExampleTestMethodReports {

    public static TestMethodReport.Builder aValidTestMethodReport() {
        return aValidTestMethodReportWithDefaultMetadata().testMetadata(
                TestMetadataFactory.metadataFor(MetadataWithIssue.class));
    }

    public static TestMethodReport.Builder aValidTestMethodReportWithDefaultMetadata() {
        return TestMethodReport.aTestMethodReport().methodName(UUID.randomUUID().toString());
    }

    @TestMetadata(issues = { "ISSUE-12" })
    private static class MetadataWithIssue {
    }
}