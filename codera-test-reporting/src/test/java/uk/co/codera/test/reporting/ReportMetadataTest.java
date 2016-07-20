package uk.co.codera.test.reporting;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.reporting.TestReportMetadata.validReportMetadata;

import org.junit.Test;

public class ReportMetadataTest {

    @Test
    public void shouldBeAbleToSetProjectName() {
        assertThat(validReportMetadata().projectName("proj").build().getProjectName(), is("proj"));
    }

    @Test
    public void shouldBeAbleToSetVersion() {
        assertThat(validReportMetadata().version("0.0.4-SNAPSHOT").build().getVersion(), is("0.0.4-SNAPSHOT"));
    }
}