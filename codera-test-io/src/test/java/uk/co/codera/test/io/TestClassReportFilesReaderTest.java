package uk.co.codera.test.io;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import uk.co.codera.test.dto.TestClassReports;

public class TestClassReportFilesReaderTest {

    private static final String BASE_DIRECTORY = "target/test-reports";
    private static final int DEPTH = 3;

    private String rootDirectory;

    private TestClassReportFilesReader filesReader;

    @Before
    public void before() {
        this.rootDirectory = createSubdirectory(BASE_DIRECTORY);
        writeFilesUnderChildDirectories(DEPTH);

        this.filesReader = new TestClassReportFilesReader(this.rootDirectory);
    }

    @Test
    public void shouldRecurseDownAndFindAllReports() {
        assertThat(this.filesReader.read(), hasSize(DEPTH));
    }

    private void writeFilesUnderChildDirectories(int depth) {
        String path = this.rootDirectory;
        for (int i = 0; i < depth; i++) {
            path = createSubdirectory(path);
            TestClassReportFileWriter fileWriter = new TestClassReportFileWriter(path);
            fileWriter.write(TestClassReports.aValidTestClassReport().build());
        }
    }

    private String createSubdirectory(String path) {
        File subdirectory = new File(path, UUID.randomUUID().toString());
        try {
            FileUtils.forceMkdir(subdirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create subdirectory", e);
        }
        return subdirectory.getPath();
    }
}