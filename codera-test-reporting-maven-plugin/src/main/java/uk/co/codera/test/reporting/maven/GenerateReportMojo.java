package uk.co.codera.test.reporting.maven;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import uk.co.codera.lang.io.ClasspathResource;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;
import uk.co.codera.test.dto.JiraIssueUrlFactory;
import uk.co.codera.test.dto.TestClassReports;
import uk.co.codera.test.io.TestClassReportFilesReader;
import uk.co.codera.test.reporting.ReportGenerator;
import uk.co.codera.test.reporting.ReportMetadata;
import uk.co.codera.test.reporting.SinglePageGenerator;

@Mojo(name = "generate")
public class GenerateReportMojo extends AbstractMojo {

    private static final String PACKAGING_POM = "pom";

    private static final String TEMPLATE_SINGLE_PAGE_HTML = "/templates/template-single-page-html.vm";

    private static final String DEFAULT_OUTPUT_FILENAME = "test-report.html";

    @Parameter(property = "project")
    private MavenProject project;

    @Parameter(property = "jiraBaseUrl")
    private String jiraBaseUrl;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (isPomModule()) {
            ReportMetadata reportMetadata = reportMetadata();
            String template = new ClasspathResource(TEMPLATE_SINGLE_PAGE_HTML).getAsString();

            ReportGenerator reportGenerator = reportGenerator(reportMetadata, template);

            TestClassReports reports = testClassReports();
            reportGenerator.generate(reports);
        }
    }

    private ReportGenerator reportGenerator(ReportMetadata reportMetadata, String template) {
        SinglePageGenerator.Builder singlePageGenerator = SinglePageGenerator.aSinglePageGenerator()
                .reportMetadata(reportMetadata).templateEngine(new VelocityTemplateEngine()).template(template)
                .reportWriter(this::writeReport);

        if (StringUtils.isNotBlank(this.jiraBaseUrl)) {
            singlePageGenerator.issueUrlFactory(new JiraIssueUrlFactory(this.jiraBaseUrl));
        }
        return singlePageGenerator.build();
    }

    private TestClassReports testClassReports() {
        TestClassReportFilesReader filesReader = new TestClassReportFilesReader(rootDirectory());
        return TestClassReports.over(filesReader.read());
    }

    private ReportMetadata reportMetadata() {
        return ReportMetadata.someReportMetadata().projectName(this.project.getArtifactId())
                .version(this.project.getVersion()).build();
    }

    private String rootDirectory() {
        return this.project.getBasedir().getPath();
    }

    private boolean isPomModule() {
        return PACKAGING_POM.equalsIgnoreCase(this.project.getPackaging());
    }

    private void writeReport(String report) {
        try {
            FileUtils.writeStringToFile(new File(this.project.getBuild().getDirectory(), DEFAULT_OUTPUT_FILENAME),
                    report);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}