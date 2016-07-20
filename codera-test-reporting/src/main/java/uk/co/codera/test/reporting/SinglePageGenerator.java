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

    private SinglePageGenerator(Builder builder) {
        this.reportMetadata = builder.reportMetadata;
        this.templateEngine = builder.templateEngine;
        this.template = builder.template;
        this.reportWriter = builder.reportWriter;
        this.issueUrlFactory = builder.issueUrlFactory;
    }

    public static Builder aSinglePageGenerator() {
        return new Builder();
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

    public static class Builder {
        private ReportMetadata reportMetadata;
        private TemplateEngine templateEngine;
        private String template;
        private ReportWriter reportWriter;
        private IssueUrlFactory issueUrlFactory;

        private Builder() {
            super();
        }

        public Builder reportMetadata(ReportMetadata reportMetadata) {
            this.reportMetadata = reportMetadata;
            return this;
        }

        public Builder templateEngine(TemplateEngine templateEngine) {
            this.templateEngine = templateEngine;
            return this;
        }

        public Builder template(String template) {
            this.template = template;
            return this;
        }

        public Builder reportWriter(ReportWriter reportWriter) {
            this.reportWriter = reportWriter;
            return this;
        }

        public Builder issueUrlFactory(IssueUrlFactory issueUrlFactory) {
            this.issueUrlFactory = issueUrlFactory;
            return this;
        }

        public ReportGenerator build() {
            return new SinglePageGenerator(this);
        }
    }
}