package uk.co.codera.test.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import uk.co.codera.lang.Adapter;
import uk.co.codera.lang.xml.XmlToJaxbAdapter;
import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFileReader {

    private final Adapter<String, TestClassReport> adapter;

    public TestClassReportFileReader() {
        this.adapter = new XmlToJaxbAdapter<>(TestClassReport.class);
    }

    public TestClassReport read(String filename) {
        String xml = readXml(filename);
        return this.adapter.adapt(xml);
    }

    private String readXml(String filename) {
        try {
            return FileUtils.readFileToString(new File(filename));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read file to string", e);
        }
    }
}