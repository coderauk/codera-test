package uk.co.codera.test.junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import uk.co.codera.test.dto.TestClassReport;
import uk.co.codera.test.dto.TestMetadata;
import uk.co.codera.test.dto.TestMetadataFactory;
import uk.co.codera.test.dto.TestMethodReport;
import uk.co.codera.test.io.TestClassReportFileWriter;

public class TestMetadataRunListener extends RunListener {

    private final TestClassReportFileWriter fileWriter;

    private Map<String, TestClassReport.Builder> testClassReports;

    public TestMetadataRunListener() {
        this.fileWriter = new TestClassReportFileWriter("target/surefire-reports");
        resetListenerState();
    }

    @Override
    public void testStarted(Description description) {
        if (isTest(description)) {
            reportForClass(description).addTestMethodReport(aTestReport(description));
        }
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        this.testClassReports.values().stream().map(TestClassReport.Builder::build).forEach(this::writeOutReport);
        resetListenerState();
    }

    private boolean isTest(Description description) {
        return description != null && description.isTest();
    }

    private TestMethodReport.Builder aTestReport(Description description) {
        return TestMethodReport.aTestMethodReport().methodName(description.getMethodName())
                .testMetadata(description.getAnnotation(TestMetadata.class));
    }

    private void writeOutReport(TestClassReport report) {
        this.fileWriter.write(report);
    }

    private void resetListenerState() {
        resetTestClassReports();
    }

    private void resetTestClassReports() {
        this.testClassReports = new HashMap<>();
    }

    private TestClassReport.Builder reportForClass(Description testDescription) {
        return this.testClassReports.computeIfAbsent(
                testDescription.getClassName(),
                key -> TestClassReport.aTestClassReport().testClassName(key)
                        .defaultMetadata(metadataDefaultingIfNoAnnotationProvided(testDescription)));
    }

    private TestMetadata metadataDefaultingIfNoAnnotationProvided(Description description) {
        TestMetadata metadata = description.getTestClass().getAnnotation(TestMetadata.class);
        return metadata == null ? TestMetadataFactory.defaultTestMetadata() : metadata;
    }
}