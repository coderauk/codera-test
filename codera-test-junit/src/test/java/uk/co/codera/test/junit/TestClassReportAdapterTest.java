package uk.co.codera.test.junit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TestClassReportAdapterTest {

    private TestClassReportAdapter adapter;

    @Before
    public void before() {
        this.adapter = new TestClassReportAdapter();
    }

    @Test
    public void shouldTransformToNonNullXmlString() {
        assertThat(toXml(aValidTestClassReport()), is(notNullValue()));
    }

    @Test
    public void shouldTransformToXmlWithDeclaration() {
        assertThat(toXml(aValidTestClassReport()),
                containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
    }

    @Test
    public void shouldHaveCorrectRootElement() {
        assertThat(toXml(aValidTestClassReport()), containsString("?><testClassReport>"));
    }

    private TestClassReport.Builder aValidTestClassReport() {
        return TestClassReport.aTestRunReport();
    }

    private String toXml(TestClassReport.Builder report) {
        return this.adapter.toXml(report.build());
    }
}