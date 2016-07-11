package uk.co.codera.test.reporting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.test.dto.ExampleTestClassReports.aValidTestClassReport;

import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.test.dto.TestClassReports;

@RunWith(MockitoJUnitRunner.class)
public class SinglePageGeneratorTest {

    @Mock
    private TemplateEngine mockTemplateEngine;

    private String template = UUID.randomUUID().toString();

    @Mock
    private ReportWriter mockReportWriter;

    @Captor
    private ArgumentCaptor<Map<String, Object>> modelCaptor;

    private ReportGenerator generator;

    @Before
    public void before() {
        this.generator = new SinglePageGenerator(this.mockTemplateEngine, this.template, this.mockReportWriter);
    }

    @Test
    public void shouldGenerateSingleFile() {
        generateReport();
        verify(this.mockReportWriter).write(anyString());
    }

    @Test
    public void shouldMergeModelWithTemplate() {
        generateReport();
        verify(this.mockTemplateEngine).merge(eq(this.template), anyMapOf(String.class, Object.class));
    }

    @Test
    public void shouldPassOutputFromTemplateMergeToReportWriter() {
        String mergeResult = UUID.randomUUID().toString();
        when(this.mockTemplateEngine.merge(anyString(), anyMapOf(String.class, Object.class))).thenReturn(mergeResult);
        generateReport();
        verify(this.mockReportWriter).write(mergeResult);
    }

    @Test
    public void shouldPassTestClassReportsWithModel() {
        TestClassReports reports = testClassReports();
        generateReport(reports);
        assertThat(capturedModel().get("testClassReports"), is(reports));
    }

    private void generateReport() {
        generateReport(testClassReports());
    }

    private void generateReport(TestClassReports reports) {
        this.generator.generate(reports);
    }

    private Map<String, Object> capturedModel() {
        verify(this.mockTemplateEngine).merge(anyString(), this.modelCaptor.capture());
        return this.modelCaptor.getValue();
    }

    private TestClassReports testClassReports() {
        return TestClassReports.over(aValidTestClassReport().build(), aValidTestClassReport().build());
    }
}