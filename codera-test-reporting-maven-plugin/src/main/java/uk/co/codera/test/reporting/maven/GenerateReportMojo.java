package uk.co.codera.test.reporting.maven;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import uk.co.codera.lang.io.ClasspathResource;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;
import uk.co.codera.test.dto.TestClassReports;
import uk.co.codera.test.io.TestClassReportFilesReader;
import uk.co.codera.test.reporting.ReportGenerator;
import uk.co.codera.test.reporting.ReportMetadata;
import uk.co.codera.test.reporting.SinglePageGenerator;

@Mojo(name = "generate")
public class GenerateReportMojo extends AbstractMojo {

    @Parameter(property = "project")
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (isPomModule()) {
            ReportMetadata reportMetadata = ReportMetadata.someReportMetadata()
                    .projectName(this.project.getArtifactId()).build();
            String template = new ClasspathResource("/templates/template-single-page-html.vm").getAsString();

            TestClassReportFilesReader filesReader = new TestClassReportFilesReader(rootDirectory());
            TestClassReports reports = TestClassReports.over(filesReader.read());

            ReportGenerator reportGenerator = new SinglePageGenerator(reportMetadata, new VelocityTemplateEngine(),
                    template, this::writeReport);

            reportGenerator.generate(reports);
        }
    }

    private String rootDirectory() {
        return this.project.getBasedir().getPath();
    }

    private boolean isPomModule() {
        return "pom".equalsIgnoreCase(this.project.getPackaging());
    }

    private void writeReport(String report) {
        try {
            FileUtils.writeStringToFile(new File(this.project.getBuild().getDirectory(), "test-report.html"), report);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}