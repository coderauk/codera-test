package uk.co.codera.test.reporting;

import uk.co.codera.lang.io.ClasspathResource;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;
import uk.co.codera.test.dto.TestClassReports;
import uk.co.codera.test.io.TestClassReportFilesReader;

public class SinglePageGeneratorApplication {

    public static void main(String[] args) {
        ReportMetadata reportMetadata = ReportMetadata.someReportMetadata().projectName("Example project").build();
        String template = new ClasspathResource("/templates/template-single-page-html.vm").getAsString();
        String rootDirectory = args[0];
        // String targetFile = args[1];

        TestClassReportFilesReader filesReader = new TestClassReportFilesReader(rootDirectory);
        TestClassReports reports = TestClassReports.over(filesReader.read());

        ReportGenerator reportGenerator = SinglePageGenerator.aSinglePageGenerator().reportMetadata(reportMetadata)
                .templateEngine(new VelocityTemplateEngine()).template(template)
                .reportWriter(SinglePageGeneratorApplication::writeReport).build();

        reportGenerator.generate(reports);
    }

    private static void writeReport(String report) {
        System.out.println(report);
    }
}