package uk.co.codera.test.dto;

import uk.co.codera.test.dto.TestClassReport;

public class TestClassReports {

    public static TestClassReport.Builder aValidTestClassReport() {
        return TestClassReport.aTestClassReport().addTestMethodReport(TestMethodReports.aValidTestMethodReport());
    }
}