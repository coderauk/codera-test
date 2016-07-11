package uk.co.codera.test.dto;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestClassReports implements Iterable<TestClassReport> {

    private final List<TestClassReport> reports;

    private TestClassReports(List<TestClassReport> reports) {
        this.reports = reports;
    }

    public static TestClassReports over(TestClassReport... reports) {
        return new TestClassReports(Arrays.asList(reports));
    }

    @Override
    public Iterator<TestClassReport> iterator() {
        return this.reports.iterator();
    }
}