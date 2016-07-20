package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.dto.ExampleTestClassReports.aValidTestClassReport;
import static uk.co.codera.test.dto.ExampleTestMethodReports.aValidTestMethodReport;

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

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToAlterSortedCollectionViaIterator() {
        Iterator<TestClassReport> iterator = TestClassReports.over(aTestClassReport(), aTestClassReport())
                .sortByTestClassNameAscending().iterator();
        iterator.next();
        iterator.remove();
    }

    @Test
    public void shouldBeAbleToSortByTestClassNameAscending() {
        TestClassReport report1 = aValidTestClassReport().testClassName("shouldComeSecond").build();
        TestClassReport report2 = aValidTestClassReport().testClassName("comesFirst").build();

        Iterator<TestClassReport> iterator = TestClassReports.over(report1, report2).sortByTestClassNameAscending()
                .iterator();
        assertThat(iterator.next(), is(report2));
        assertThat(iterator.next(), is(report1));
    }

    @Test
    public void shouldBeAbleToSortByTestClassNameDescending() {
        TestClassReport report1 = aValidTestClassReport().testClassName("comesLast").build();
        TestClassReport report2 = aValidTestClassReport().testClassName("shouldComeFirst").build();

        Iterator<TestClassReport> iterator = TestClassReports.over(report1, report2).sortByTestClassNameDescending()
                .iterator();
        assertThat(iterator.next(), is(report2));
        assertThat(iterator.next(), is(report1));
    }

    @Test
    public void shouldReportTotalNumberOfTestMethodsCorrectly() {
        TestClassReport report1 = aValidTestClassReport().noTestMethodReports()
                .addTestMethodReport(aValidTestMethodReport()).build();
        TestClassReport report2 = aValidTestClassReport().noTestMethodReports()
                .addTestMethodReport(aValidTestMethodReport()).addTestMethodReport(aValidTestMethodReport()).build();
        assertThat(TestClassReports.over(report1, report2).getTotalTestMethodReportCount(), is(3));
    }

    @Test
    public void shouldReportNumberOfClassReportsCorrectly() {
        assertThat(TestClassReports.over(aValidTestClassReport().build(), aValidTestClassReport().build()).size(),
                is(2));
    }

    private TestClassReport aTestClassReport() {
        return aValidTestClassReport().build();
    }
}