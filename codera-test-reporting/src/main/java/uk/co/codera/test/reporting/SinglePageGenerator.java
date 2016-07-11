package uk.co.codera.test.reporting;

import java.util.HashMap;
import java.util.Map;

import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.test.dto.TestClassReports;

public class SinglePageGenerator implements ReportGenerator {

    private static final String MODEL_ATTRIBUTE_TEST_CLASS_REPORTS = "testClassReports";

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
        Map<String, Object> model = model(classReports);
        String report = this.templateEngine.merge(this.template, model);
        this.reportWriter.write(report);
    }

    private Map<String, Object> model(TestClassReports classReports) {
        Map<String, Object> model = new HashMap<>();
        model.put(MODEL_ATTRIBUTE_TEST_CLASS_REPORTS, classReports);
        return model;
    }
}