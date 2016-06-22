package uk.co.codera.test.dto;

import java.util.UUID;

public class TestMethodReports {

    public static TestMethodReport.Builder aValidTestMethodReport() {
        return TestMethodReport.aTestMethodReport().methodName(UUID.randomUUID().toString());
    }
}