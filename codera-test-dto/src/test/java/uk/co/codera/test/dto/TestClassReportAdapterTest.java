package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.TestClassReports.aValidTestClassReport;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.test.dto.TestClassReport;
import uk.co.codera.test.dto.TestClassReportAdapter;

public class TestClassReportAdapterTest {

    private TestClassReportAdapter adapter;

    @Before
    public void before() {
        this.adapter = new TestClassReportAdapter();
    }

    @Test
    public void shouldHaveCorrectRootElement() {
        assertThat(adapt(aValidTestClassReport()), containsString("?><testClassReport>"));
    }

    private String adapt(TestClassReport.Builder report) {
        return this.adapter.adapt(report.build());
    }
}