package uk.co.codera.test.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TestClassReports implements Iterable<TestClassReport> {

    private final List<TestClassReport> reports;

    private TestClassReports(List<TestClassReport> reports) {
        this.reports = reports;
    }

    public static TestClassReports over(TestClassReport... reports) {
        return over(Arrays.asList(reports));
    }

    public static TestClassReports over(List<TestClassReport> reports) {
        return new TestClassReports(Collections.unmodifiableList(reports));
    }

    @Override
    public Iterator<TestClassReport> iterator() {
        return this.reports.iterator();
    }

    public TestClassReports sortByTestClassNameAscending() {
        return sort((o1, o2) -> o1.getTestClassName().compareTo(o2.getTestClassName()));
    }

    public TestClassReports sortByTestClassNameDescending() {
        return sort((o1, o2) -> o2.getTestClassName().compareTo(o1.getTestClassName()));
    }

    private TestClassReports sort(Comparator<TestClassReport> comparator) {
        List<TestClassReport> toSort = new ArrayList<>(this.reports);
        Collections.sort(toSort, comparator);
        return over(toSort.toArray(new TestClassReport[] {}));
    }
}