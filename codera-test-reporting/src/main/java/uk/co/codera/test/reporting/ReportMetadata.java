package uk.co.codera.test.reporting;

public class ReportMetadata {

    private final String projectName;
    private final String version;

    private ReportMetadata(Builder builder) {
        this.projectName = builder.projectName;
        this.version = builder.version;
    }

    public static Builder someReportMetadata() {
        return new Builder();
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getVersion() {
        return this.version;
    }

    public static class Builder {

        private String projectName;
        private String version;

        private Builder() {
            super();
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public ReportMetadata build() {
            return new ReportMetadata(this);
        }
    }
}