package uk.co.codera.test.io;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Objects;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import uk.co.codera.test.dto.TestClassReport;
import uk.co.codera.test.dto.ExampleTestClassReports;

public class TestClassReportFileReaderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String REPORTS_DIRECTORY = "target/test-reports";

    private TestClassReportFileReader fileReader;
    private TestClassReportFileWriter fileWriter;

    @Before
    public void before() {
        this.fileReader = new TestClassReportFileReader();
        this.fileWriter = new TestClassReportFileWriter(REPORTS_DIRECTORY);
    }

    @Test
    public void shouldBeAbleToReadReportFromFile() {
        String filename = writeReportAndReturnFilename();
        assertThat(this.fileReader.read(filename), is(notNullValue()));
    }

    @Test
    public void shouldThrowExceptionForInvalidFilename() {
        this.expectedException.expect(IllegalStateException.class);
        this.expectedException.expectMessage("Unable to read file to string");
        this.fileReader.read("invalid-name");
    }

    @Test
    public void shouldBeEqualWhenReadBackToWhatWasWritten() {
        TestClassReport originalReport = ExampleTestClassReports.aValidTestClassReport().build();
        String filename = writeReportAndReturnFilename(originalReport);

        TestClassReport retrievedReport = this.fileReader.read(filename);
        assertThat(Objects.deepEquals(originalReport, retrievedReport), is(true));
    }

    private String writeReportAndReturnFilename() {
        return writeReportAndReturnFile().getPath();
    }

    private String writeReportAndReturnFilename(TestClassReport report) {
        return writeReportAndReturnFile(report).getPath();
    }

    private File writeReportAndReturnFile() {
        return writeReportAndReturnFile(ExampleTestClassReports.aValidTestClassReport().build());
    }

    private File writeReportAndReturnFile(TestClassReport report) {
        this.fileWriter.write(report);
        return TestClassReportFileFactory.create(REPORTS_DIRECTORY, report);
    }
}