package uk.co.codera.test.junit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class TestMetadataRunListener extends RunListener {

    @TestMetadata
    private final class DefaultTestMetadata {
    }

    private final TestClassReportAdapter adapter;

    private Map<String, TestClassReport.Builder> testClassReports;

    public TestMetadataRunListener() {
        this.adapter = new TestClassReportAdapter();
        resetListenerState();
    }

    @Override
    public void testStarted(Description description) {
        if (isTest(description)) {
            reportForClass(description).addTestReport(aTestReport(description));
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
        return TestMethodReport.aTestReport().methodName(description.getMethodName())
                .testMetadata(description.getAnnotation(TestMetadata.class));
    }

    private void writeOutReport(TestClassReport report) {
        try {
            FileUtils.writeStringToFile(new File("target/surefire-reports/METADATA-" + report.getTestClassName()
                    + ".xml"), toXml(report));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toXml(TestClassReport report) {
        return this.adapter.toXml(report);
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
                key -> TestClassReport.aTestRunReport().testClassName(key)
                        .defaultMetadata(metadataDefaultingIfNoAnnotationProvided(testDescription)));
    }

    private TestMetadata metadataDefaultingIfNoAnnotationProvided(Description description) {
        TestMetadata metadata = description.getTestClass().getAnnotation(TestMetadata.class);
        return metadata == null ? DefaultTestMetadata.class.getAnnotation(TestMetadata.class) : metadata;
    }
}