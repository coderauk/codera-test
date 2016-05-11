package uk.co.codera.test.junit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class TestClassReportAdapter {

    private final Marshaller marshaller;

    public TestClassReportAdapter() {
        try {
            this.marshaller = JAXBContext.newInstance(TestClassReport.class).createMarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public String toXml(TestClassReport report) {
        try {
            return toXmlExceptionally(report);
        } catch (JAXBException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toXmlExceptionally(TestClassReport report) throws JAXBException, IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            this.marshaller.marshal(report, stream);
            return stream.toString();
        }
    }
}