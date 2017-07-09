package jdk8.ocp;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class LocalDateTest {

    @Test
    public void testAddNegativeDurationAndPrintOut() {
        final Duration duration = Duration.ofDays(-4);
        final LocalDate date = LocalDate.of(2017, 7, 9);

        System.out.println(date);
        // -> 2017-07-09

        final LocalDate datePlus = date.plusDays(duration.toDays());

        System.out.println(datePlus);
        // -> 2017-07-05

        assertThat(datePlus.getDayOfMonth()).isEqualTo(5);
    }
}
