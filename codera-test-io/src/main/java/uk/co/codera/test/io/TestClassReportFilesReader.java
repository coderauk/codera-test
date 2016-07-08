package uk.co.codera.test.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFilesReader {

    private final File directory;
    private final TestClassReportFileReader fileReader;

    public TestClassReportFilesReader(String directory) {
        this.directory = new File(directory);
        this.fileReader = new TestClassReportFileReader();
    }

    public List<TestClassReport> read() {
        List<TestClassReport> reports = new ArrayList<>();
        Iterator<File> files = reportFiles();
        while (files.hasNext()) {
            reports.add(this.fileReader.read(files.next().getPath()));
        }
        return reports;
    }

    private Iterator<File> reportFiles() {
        return FileUtils.iterateFiles(directory, new MetadataXmlFilter(), TrueFileFilter.INSTANCE);
    }
}