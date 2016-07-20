package uk.co.codera.test.reporting;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.tools.generic.DisplayTool;

import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.test.dto.IssueUrlFactory;
import uk.co.codera.test.dto.TestClassReports;

public class SinglePageGenerator implements ReportGenerator {

    private static final String MODEL_ATTRIBUTE_DISPLAY_TOOL = "displayTool";
    private static final String MODEL_ATTRIBUTE_PROJECT_NAME = "projectName";
    private static final String MODEL_ATTRIBUTE_VERSION = "version";
    private static final String MODEL_ATTRIBUTE_TEST_CLASS_REPORTS = "testClassReports";
    private static final String MODEL_ATTRIBUTE_ISSUE_URL_FACTORY = "issueUrlFactory";

    private final ReportMetadata reportMetadata;
    private final TemplateEngine templateEngine;
    private final String template;
    private final ReportWriter reportWriter;
    private final IssueUrlFactory issueUrlFactory;

    public SinglePageGenerator(ReportMetadata reportMetadata, TemplateEngine templateEngine, String template,
            ReportWriter reportWriter) {
        this(reportMetadata, templateEngine, template, reportWriter, null);
    }

    public SinglePageGenerator(ReportMetadata reportMetadata, TemplateEngine templateEngine, String template,
            ReportWriter reportWriter, IssueUrlFactory issueUrlFactory) {
        this.reportMetadata = reportMetadata;
        this.templateEngine = templateEngine;
        this.template = template;
        this.reportWriter = reportWriter;
        this.issueUrlFactory = issueUrlFactory;
    }

    @Override
    public void generate(TestClassReports classReports) {
        Map<String, Object> model = model(classReports);
        String report = this.templateEngine.merge(this.template, model);
        this.reportWriter.write(report);
    }

    private Map<String, Object> model(TestClassReports classReports) {
        Map<String, Object> model = new HashMap<>();
        model.put(MODEL_ATTRIBUTE_DISPLAY_TOOL, new DisplayTool());
        model.put(MODEL_ATTRIBUTE_PROJECT_NAME, this.reportMetadata.getProjectName());
        model.put(MODEL_ATTRIBUTE_VERSION, this.reportMetadata.getVersion());
        model.put(MODEL_ATTRIBUTE_TEST_CLASS_REPORTS, classReports);

        if (this.issueUrlFactory != null) {
            model.put(MODEL_ATTRIBUTE_ISSUE_URL_FACTORY, this.issueUrlFactory);
        }

        return model;
    }
}