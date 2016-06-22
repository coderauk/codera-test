package uk.co.codera.test.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import uk.co.codera.lang.Adapter;
import uk.co.codera.lang.xml.JaxbToXmlAdapter;
import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFileWriter {

    private final String directory;
    private final Adapter<TestClassReport, String> adapter;

    public TestClassReportFileWriter(String directory) {
        this.directory = directory;
        this.adapter = new JaxbToXmlAdapter<>(TestClassReport.class);
    }

    public void write(TestClassReport report) {
        try {
            File file = file(report);
            FileUtils.writeStringToFile(file, toXml(report));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private File file(TestClassReport report) {
        return TestClassReportFileFactory.create(this.directory, report);
    }

    private String toXml(TestClassReport report) {
        return this.adapter.adapt(report);
    }
}