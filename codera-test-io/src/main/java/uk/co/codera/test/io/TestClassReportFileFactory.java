package uk.co.codera.test.io;

import java.io.File;

import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFileFactory {

    private TestClassReportFileFactory() {
        super();
    }

    public static File create(String directory, TestClassReport report) {
        return new File(directory, filename(report));
    }

    public static String filename(TestClassReport report) {
        return String.format("METADATA-%s.xml", report.getTestClassName());
    }
}