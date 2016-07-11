package uk.co.codera.test.dto;

import static uk.co.codera.test.dto.TestClassReport.aTestClassReport;
import static uk.co.codera.test.dto.ExampleTestMethodReports.aValidTestMethodReport;

import java.util.UUID;

import uk.co.codera.test.dto.TestClassReport;

public class ExampleTestClassReports {

    public static TestClassReport.Builder aValidTestClassReport() {
        return aTestClassReport().testClassName(UUID.randomUUID().toString()).addTestMethodReport(
                aValidTestMethodReport());
    }
}