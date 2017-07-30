package jdk8;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used to test the new date and time api.
 * <p>
 * There are 3 ways of saving a time:
 *  - Only the date (without the time)
 *  - Only the time (without a date)
 *  - Date and time
 * <p>
 *  You can also choose between an {@link Instant} and a Local* class. The
 *  difference is that the instant has a timezone (UTC) and therefore the timestamp can
 *  be seen as a point on a timeline where else the Local* classes dont have a timezone
 *  and therefore act like a time on a wall clock.
 *  <p>
 *  Best way to create dates is by using one of their builder methods.
 *
 * @author Fabian Dietenberger
 */
public class DateTimeTest {

    /**
     * Only save the date.
     */
    @Test
    public void testCreateDate() {
        final LocalDate nowDate = LocalDate.now();
        final LocalDate specificDate = LocalDate.of(2016, Month.FEBRUARY, 28);

        assertThat(specificDate.getYear()).isEqualTo(2016);
        assertThat(specificDate.getMonth()).isEqualTo(Month.FEBRUARY);
        assertThat(specificDate.getDayOfMonth()).isEqualTo(28);
    }

    /**
     * Only save the time.
     */
    @Test
    public void testCreateTime() {
        final LocalTime nowTime = LocalTime.now();
        final LocalTime specificTime = LocalTime.of(12, 45, 50);

        assertThat(specificTime.getHour()).isEqualTo(12);
        assertThat(specificTime.getMinute()).isEqualTo(45);
        assertThat(specificTime.getSecond()).isEqualTo(50);
    }

    /**
     * Save the date and time.
     */
    @Test
    public void testCreateDateAndTime() {
        final LocalDateTime nowDateTime = LocalDateTime.now();
        final LocalDateTime specificDateTime = LocalDateTime.of(2016, Month.FEBRUARY, 28, 12, 45, 50);

        assertThat(specificDateTime.getYear()).isEqualTo(2016);
        assertThat(specificDateTime.getMonth()).isEqualTo(Month.FEBRUARY);
        assertThat(specificDateTime.getDayOfMonth()).isEqualTo(28);
        assertThat(specificDateTime.getHour()).isEqualTo(12);
        assertThat(specificDateTime.getMinute()).isEqualTo(45);
        assertThat(specificDateTime.getSecond()).isEqualTo(50);
    }

    /**
     * Add some time on a timestamp.
     */
    @Test
    public void testCalculationWithTimes() {
        // all time classes are immutable so if you add some time
        // you get the new time as return type!
        LocalDateTime dateTime = LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0, 0);
        dateTime = dateTime.plus(2, ChronoUnit.DAYS);
        dateTime = dateTime.plusHours(12);
        dateTime = dateTime.plusMonths(1);

        assertThat(dateTime.getYear()).isEqualTo(2016);
        assertThat(dateTime.getMonth()).isEqualTo(Month.FEBRUARY);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(3);
        assertThat(dateTime.getHour()).isEqualTo(12);
        assertThat(dateTime.getMinute()).isEqualTo(0);
        assertThat(dateTime.getSecond()).isEqualTo(0);
    }

    /**
     * Add a periode on a timestamp.
     */
    @Test
    public void testAddingPeriodToLocalDate() {
        final Period period = Period.ofDays(2);
        LocalDate date = LocalDate.of(2016, Month.JANUARY, 28); // January = 31 days

        // add 2 days
        date = date.plus(period);

        assertThat(date.getYear()).isEqualTo(2016);
        assertThat(date.getMonth()).isEqualTo(Month.JANUARY);
        assertThat(date.getDayOfMonth()).isEqualTo(30);

        // add 2 more days
        date = date.plus(period);

        assertThat(date.getYear()).isEqualTo(2016);
        assertThat(date.getMonth()).isEqualTo(Month.FEBRUARY);
        assertThat(date.getDayOfMonth()).isEqualTo(1);
    }

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
