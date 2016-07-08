package uk.co.codera.test.io;

import static uk.co.codera.test.utility.ClassAssert.assertStaticUtilityClass;

import org.junit.Test;

public class TestClassReportFileFactoryTest {

    @Test
    public void shouldConformToStaticUtilityClass() {
        assertStaticUtilityClass(TestClassReportFileFactory.class);
    }
}