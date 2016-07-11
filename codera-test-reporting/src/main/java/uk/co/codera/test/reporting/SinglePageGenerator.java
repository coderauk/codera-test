package uk.co.codera.test.reporting;

import java.util.HashMap;

import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.test.dto.TestClassReports;

public class SinglePageGenerator implements ReportGenerator {

    private final TemplateEngine templateEngine;
    private final String template;
    private final ReportWriter reportWriter;

    public SinglePageGenerator(TemplateEngine templateEngine, String template, ReportWriter reportWriter) {
        this.templateEngine = templateEngine;
        this.template = template;
        this.reportWriter = reportWriter;
    }

    @Override
    public void generate(TestClassReports classReports) {
        String report = this.templateEngine.merge(this.template, new HashMap<>());
        this.reportWriter.write(report);
    }
}