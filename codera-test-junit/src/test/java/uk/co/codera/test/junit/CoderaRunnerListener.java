package uk.co.codera.test.junit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class CoderaRunnerListener extends RunListener {

    @TestMetadata
    private final class DefaultTestMetadata {
    }

    private final TestClassReportAdapter adapter;

    private Map<String, TestClassReport.Builder> testClassReports;

    public CoderaRunnerListener() {
        this.adapter = new TestClassReportAdapter();
        resetListenerState();
    }

    @Override
    public void testStarted(Description description) {
        if (description != null && description.isTest()) {
            reportForClass(description).addTestReport(
                    TestMethodReport.aTestReport().methodName(description.getMethodName())
                            .testMetadata(description.getAnnotation(TestMetadata.class)));
        }
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        this.testClassReports.values().forEach(this::writeOutReport);
        resetListenerState();
    }

    private void writeOutReport(TestClassReport.Builder report) {
        System.out.println(toXml(report));
    }

    private String toXml(TestClassReport.Builder report) {
        return this.adapter.toXml(report.build());
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