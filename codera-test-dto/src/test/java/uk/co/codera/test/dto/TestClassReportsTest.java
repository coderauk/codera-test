package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.ExampleTestClassReports.aValidTestClassReport;

import java.util.Iterator;

import org.junit.Test;

public class TestClassReportsTest {

    @Test
    public void shouldBeAbleToIterateOverCollection() {
        TestClassReport report1 = aTestClassReport();
        TestClassReport report2 = aTestClassReport();

        TestClassReports reports = TestClassReports.over(report1, report2);

        Iterator<TestClassReport> iterator = reports.iterator();
        assertThat(iterator.next(), is(report1));
        assertThat(iterator.next(), is(report2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToAlterCollectionViaIterator() {
        Iterator<TestClassReport> iterator = TestClassReports.over(aTestClassReport(), aTestClassReport()).iterator();
        iterator.next();
        iterator.remove();
    }

    private TestClassReport aTestClassReport() {
        return aValidTestClassReport().build();
    }
}