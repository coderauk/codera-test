package uk.co.codera.test.junit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.junit.TestClassReports.aValidTestClassReport;

import org.junit.Before;
import org.junit.Test;

public class JaxbXmlAdapterTest {

    private JaxbXmlAdapter<TestClassReport> adapter;

    @Before
    public void before() {
        this.adapter = new JaxbXmlAdapter<>(TestClassReport.class);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotBeAbleToMarshalNonAnnotatedObject() {
        new JaxbXmlAdapter<>(Object.class).adapt(new Object());
    }

    @Test
    public void shouldTransformToNonNullXmlString() {
        assertThat(adapt(aValidTestClassReport()), is(notNullValue()));
    }

    @Test
    public void shouldTransformToXmlWithDeclaration() {
        assertThat(adapt(aValidTestClassReport()),
                containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
    }

    private String adapt(TestClassReport.Builder report) {
        return this.adapter.adapt(report.build());
    }
}