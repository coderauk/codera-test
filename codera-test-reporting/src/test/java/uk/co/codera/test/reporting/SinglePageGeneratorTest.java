package uk.co.codera.test.reporting;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.test.dto.ExampleTestClassReports.aValidTestClassReport;

import java.util.Map;
import java.util.UUID;

import org.apache.velocity.tools.generic.DisplayTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.test.dto.IssueUrlFactory;
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

    private ReportMetadata reportMetadata;

    private ReportGenerator generator;

    @Before
    public void before() {
        this.reportMetadata = TestReportMetadata.validReportMetadata().build();
        this.generator = aSinglePageGenerator().build();
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

    @Test
    public void shouldPassProjectNameFromMetadataWithModel() {
        generateReport();
        assertThat(capturedModel().get("projectName"), is(this.reportMetadata.getProjectName()));
    }

    @Test
    public void shouldPassVersionFromMetadataWithModel() {
        generateReport();
        assertThat(capturedModel().get("version"), is(this.reportMetadata.getVersion()));
    }

    @Test
    public void shouldPassDisplayToolWithModel() {
        generateReport();
        assertThat(capturedModel().get("displayTool"), is(instanceOf(DisplayTool.class)));
    }

    @Test
    public void shouldNotPassIssueUrlFactoryToModelIfNotProvided() {
        generateReport();
        assertThat(capturedModel().containsKey("issueUrlFactory"), is(false));
    }

    @Test
    public void shouldPassIssueUrlFactoryToModelIfProvided() {
        IssueUrlFactory issueUrlFactory = mock(IssueUrlFactory.class);
        this.generator = aSinglePageGenerator().issueUrlFactory(issueUrlFactory).build();
        generateReport();
        assertThat(capturedModel().containsKey("issueUrlFactory"), is(true));
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

    private SinglePageGenerator.Builder aSinglePageGenerator() {
        return SinglePageGenerator.aSinglePageGenerator().reportMetadata(this.reportMetadata)
                .templateEngine(this.mockTemplateEngine).template(this.template).reportWriter(this.mockReportWriter);
    }
}