package uk.co.codera.test.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class TestRunReport {

    @XmlElement(required = true)
    private String testClassName;

    @XmlElementWrapper(name = "tests")
    @XmlElement(name = "test", required = true)
    private List<TestReport> testReports;

    TestRunReport() {
        super();
    }

    public TestRunReport(Builder builder) {
        this();
        this.testClassName = builder.testClassName;
        this.testReports = Collections.unmodifiableList(new ArrayList<>(builder.testReports));
    }

    public static Builder aTestRunReport() {
        return new Builder();
    }

    public String getTestClassName() {
        return this.testClassName;
    }

    public List<TestReport> getTestReports() {
        return this.testReports;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String testClassName;
        private TestMetadata defaultMetadata;
        private final List<TestReport> testReports;

        private Builder() {
            this.testReports = new ArrayList<>();
        }

        public Builder defaultMetadata(TestMetadata metadata) {
            this.defaultMetadata = metadata;
            return this;
        }

        public Builder testClassName(String name) {
            this.testClassName = name;
            return this;
        }

        public Builder addTestReport(TestReport.Builder report) {
            this.testReports.add(report.defaultMetadata(this.defaultMetadata).build());
            return this;
        }

        public TestRunReport build() {
            return new TestRunReport(this);
        }
    }
}