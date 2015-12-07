package java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used to test the new date and time api.
 *
 * @author Fabian Dietenberger
 */
public class DateTimeTest {

    @Test
    public void testCalculationWithTimes() {
        final Instant now = Instant.now();
        final Instant later = now.plus(5, ChronoUnit.HOURS);
        final Instant eventLater = later.plus(Duration.ofMinutes(7));

        assertThat(eventLater).isEqualTo(now.plus(5, ChronoUnit.HOURS).plus(7, ChronoUnit.MINUTES));
    }
}
