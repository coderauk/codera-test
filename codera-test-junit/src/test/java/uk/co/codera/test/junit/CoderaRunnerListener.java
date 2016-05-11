package uk.co.codera.test.junit;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class CoderaRunnerListener extends RunListener {

    @TestMetadata
    private final class DefaultTestMetadata {
    }

    private Map<String, TestRunReport.Builder> testClassReports;

    public CoderaRunnerListener() {
        resetListenerState();
    }

    @Override
    public void testStarted(Description description) {
        if (description != null && description.isTest()) {
            reportForClass(description).addTestReport(
                    TestReport.aTestReport().methodName(description.getMethodName())
                            .testMetadata(description.getAnnotation(TestMetadata.class)));
        }
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        Marshaller marshaller = JAXBContext.newInstance(TestRunReport.class).createMarshaller();

        this.testClassReports.values().forEach(it -> {
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                marshaller.marshal(it.build(), os);
                System.out.println(os.toString());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });

        resetListenerState();
    }

    private void resetListenerState() {
        resetTestClassReports();
    }

    private void resetTestClassReports() {
        this.testClassReports = new HashMap<>();
    }

    private TestRunReport.Builder reportForClass(Description testDescription) {
        return this.testClassReports.computeIfAbsent(
                testDescription.getClassName(),
                key -> TestRunReport.aTestRunReport().testClassName(key)
                        .defaultMetadata(metadataDefaultingIfNoAnnotationProvided(testDescription)));
    }

    private TestMetadata metadataDefaultingIfNoAnnotationProvided(Description description) {
        TestMetadata metadata = description.getTestClass().getAnnotation(TestMetadata.class);
        return metadata == null ? DefaultTestMetadata.class.getAnnotation(TestMetadata.class) : metadata;
    }
}