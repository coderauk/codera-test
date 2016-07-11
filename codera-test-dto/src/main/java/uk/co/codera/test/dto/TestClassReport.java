package uk.co.codera.test.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class TestClassReport {

    @XmlElement(required = true)
    private String testClassName;

    @XmlElementWrapper(name = "tests")
    @XmlElement(name = "test", required = true)
    private List<TestMethodReport> testMethodReports;

    TestClassReport() {
        super();
    }

    public TestClassReport(Builder builder) {
        this();
        this.testClassName = builder.testClassName;
        this.testMethodReports = Collections.unmodifiableList(new ArrayList<>(builder.testReports));
    }

    public static Builder aTestClassReport() {
        return new Builder();
    }

    public String getTestClassName() {
        return this.testClassName;
    }

    public List<TestMethodReport> getTestMethodReports() {
        return this.testMethodReports;
    }

    public int getTestMethodReportCount() {
        return this.testMethodReports.size();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.testClassName).append(this.testMethodReports.hashCode()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!obj.getClass().isAssignableFrom(getClass())) {
            return false;
        }
        TestClassReport other = (TestClassReport) obj;
        return new EqualsBuilder().append(this.testClassName, other.testClassName)
                .append(this.testMethodReports, other.testMethodReports).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String testClassName;
        private TestMetadata defaultMetadata = TestMetadataFactory.defaultTestMetadata();
        private final List<TestMethodReport> testReports;

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

        public Builder addTestMethodReport(TestMethodReport.Builder report) {
            this.testReports.add(report.defaultMetadata(this.defaultMetadata).build());
            return this;
        }

        public TestClassReport build() {
            return new TestClassReport(this);
        }
    }
}