package uk.co.codera.test.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class TestMethodReport {

    @XmlElement(required = true)
    private String methodName;

    @XmlElement(required = true)
    private TestType testType;

    @XmlElementWrapper(name = "issues")
    @XmlElement(name = "issue")
    private Set<String> issues;

    TestMethodReport() {
        super();
    }

    public TestMethodReport(Builder builder) {
        this();
        this.methodName = builder.methodName;
        this.testType = builder.testType;
        this.issues = Collections.unmodifiableSet(new HashSet<>(builder.issues));
    }

    public static Builder aTestMethodReport() {
        return new Builder();
    }

    public String getMethodName() {
        return this.methodName;
    }

    public TestType getTestType() {
        return this.testType;
    }

    public Set<String> getIssues() {
        return this.issues;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {

        private String methodName;
        private TestMetadata defaultMetadata = TestMetadataFactory.defaultTestMetadata();
        private TestMetadata testMetadata;
        private TestType testType;
        private Set<String> issues;

        private Builder() {
            super();
        }

        public Builder methodName(String name) {
            this.methodName = name;
            return this;
        }

        public Builder defaultMetadata(TestMetadata metadata) {
            this.defaultMetadata = metadata;
            return this;
        }

        public Builder testMetadata(TestMetadata metadata) {
            this.testMetadata = metadata;
            return this;
        }

        public TestMethodReport build() {
            this.testType = mergeTestType();
            this.issues = mergeIssues();
            return new TestMethodReport(this);
        }

        private TestType mergeTestType() {
            return testMetadata == null ? defaultMetadata.type() : testMetadata.type();
        }

        private Set<String> mergeIssues() {
            @SuppressWarnings("squid:HiddenFieldCheck")
            Set<String> issues = new HashSet<>();
            addIssues(issues, defaultMetadata);
            addIssues(issues, testMetadata);
            return issues;
        }

        private void addIssues(Set<String> issues, TestMetadata metadata) {
            if (metadata != null) {
                issues.addAll(Arrays.asList(metadata.issues()));
            }
        }
    }
}