package uk.co.codera.test.reporting;

import uk.co.codera.test.dto.TestClassReports;

@FunctionalInterface
public interface ReportGenerator {

    void generate(TestClassReports classReports);
}