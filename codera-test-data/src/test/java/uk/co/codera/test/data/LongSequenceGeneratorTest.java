package uk.co.codera.test.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LongSequenceGeneratorTest {

    @Test
    public void shouldStartFromZeroByDefault() {
        assertThat(LongSequenceGenerator.aSequenceGenerator().build().create(), is(Long.valueOf(0)));
    }

    @Test
    public void shouldBeAbleToStartGeneratorFromNumberOtherThanZero() {
        assertThat(LongSequenceGenerator.aSequenceGenerator().startingAt(5L).build().create(), is(Long.valueOf(5)));
    }

    @Test
    public void shouldBeAbleToStartGeneratorFromNegativeNumber() {
        assertThat(LongSequenceGenerator.aSequenceGenerator().startingAt(-7L).build().create(), is(Long.valueOf(-7)));
    }

    @Test
    public void shouldIncrementCorrectly() {
        DataGenerator<Long> generator = LongSequenceGenerator.aSequenceGenerator().startingAt(4L).build();
        List<Long> results = Arrays.asList(generator.create(), generator.create(), generator.create());
        assertThat(results, contains(Long.valueOf(4), Long.valueOf(5), Long.valueOf(6)));
    }
}