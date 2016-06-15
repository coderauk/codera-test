package uk.co.codera.test.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestClassReports.aValidTestClassReport;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.test.dto.TestClassReport;

public class TestClassReportFileWriterTest {

    private TestClassReportFileWriter fileWriter;

    @Before
    public void before() {
        this.fileWriter = new TestClassReportFileWriter("target/test-reports");
    }

    @Test
    public void shouldWriteFileSuccessfully() {
        TestClassReport testClassReport = aValidTestClassReport().build();
        this.fileWriter.write(testClassReport);
        File file = new File("target/test-reports/METADATA-" + testClassReport.getTestClassName() + ".xml");
        assertThat(file.exists(), is(true));
    }
}