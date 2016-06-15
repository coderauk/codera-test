package uk.co.codera.test.dto;

import static uk.co.codera.test.dto.TestClassReport.aTestClassReport;
import static uk.co.codera.test.dto.TestMethodReports.aValidTestMethodReport;

import java.util.UUID;

import uk.co.codera.test.dto.TestClassReport;

public class TestClassReports {

    public static TestClassReport.Builder aValidTestClassReport() {
        return aTestClassReport().testClassName(UUID.randomUUID().toString()).addTestMethodReport(
                aValidTestMethodReport());
    }
}