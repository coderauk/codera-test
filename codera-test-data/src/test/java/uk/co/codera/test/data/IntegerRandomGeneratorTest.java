package uk.co.codera.test.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static uk.co.codera.test.data.IntegerRandomGenerator.aRandomGenerator;

import org.hamcrest.Matcher;
import org.junit.Test;

public class IntegerRandomGeneratorTest {

    private static int DEFAULT_NUMBER_ITERATIONS = 10000;

    @Test
    public void shouldCreateNonNullNumber() {
        assertThat(IntegerRandomGenerator.aRandomGenerator().build().create(), is(notNullValue()));
    }

    @Test
    public void shouldNotGenerateNegativeNumberByDefault() {
        assertDefaultNumberTimes(aRandomGenerator(), greaterThanOrEqualTo(0));
    }

    @Test
    public void shouldNotGenerateNumberLargerThanMaximumValue() {
        assertDefaultNumberTimes(aRandomGenerator().maximumValue(5), lessThanOrEqualTo(5));
    }

    private void assertDefaultNumberTimes(IntegerRandomGenerator.Builder generator, Matcher<Integer> assertion) {
        assertDefaultNumberTimes(generator.build(), assertion);
    }

    private void assertDefaultNumberTimes(DataGenerator<Integer> generator, Matcher<Integer> assertion) {
        assertNTimes(DEFAULT_NUMBER_ITERATIONS, generator, assertion);
    }

    private void assertNTimes(int numberTimes, DataGenerator<Integer> generator, Matcher<Integer> assertion) {
        for (int i = 0; i < DEFAULT_NUMBER_ITERATIONS; i++) {
            assertThat(generator.create(), assertion);
        }
    }
}