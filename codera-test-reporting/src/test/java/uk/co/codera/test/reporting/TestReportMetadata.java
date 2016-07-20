package uk.co.codera.test.reporting;

import java.util.UUID;

public class TestReportMetadata {

    public static ReportMetadata.Builder validReportMetadata() {
        return ReportMetadata.someReportMetadata().projectName(randomString()).version(randomString());
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}