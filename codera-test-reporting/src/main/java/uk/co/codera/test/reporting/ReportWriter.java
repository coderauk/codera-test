package uk.co.codera.test.reporting;

@FunctionalInterface
public interface ReportWriter {

    void write(String report);
}