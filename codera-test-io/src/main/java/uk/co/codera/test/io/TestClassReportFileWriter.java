package uk.co.codera.test.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import uk.co.codera.lang.Adapter;
import uk.co.codera.lang.xml.JaxbToXmlAdapter;
import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFileWriter {

    private static final String FILENAME_TEMPLATE = "METADATA-%s.xml";

    private final File directory;
    private final Adapter<TestClassReport, String> adapter;

    public TestClassReportFileWriter(String directory) {
        this.directory = new File(directory);
        this.adapter = new JaxbToXmlAdapter<>(TestClassReport.class);
    }

    public void write(TestClassReport report) {
        try {
            File file = new File(this.directory, filename(report));
            FileUtils.writeStringToFile(file, toXml(report));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String filename(TestClassReport report) {
        return String.format(FILENAME_TEMPLATE, report.getTestClassName());
    }

    private String toXml(TestClassReport report) {
        return this.adapter.adapt(report);
    }
}