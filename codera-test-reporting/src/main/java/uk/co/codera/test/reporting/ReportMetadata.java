package uk.co.codera.test.reporting;

public class ReportMetadata {

    private final String projectName;

    private ReportMetadata(Builder builder) {
        this.projectName = builder.projectName;
    }

    public static Builder someReportMetadata() {
        return new Builder();
    }

    public String getProjectName() {
        return this.projectName;
    }

    public static class Builder {

        private String projectName;

        private Builder() {
            super();
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public ReportMetadata build() {
            return new ReportMetadata(this);
        }
    }
}