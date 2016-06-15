package uk.co.codera.test.dto;

import uk.co.codera.lang.xml.JaxbXmlAdapter;

public class TestClassReportAdapter extends JaxbXmlAdapter<TestClassReport> {

    public TestClassReportAdapter() {
        super(TestClassReport.class);
    }
}