package jdk8;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

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

    /**
     * Duration should be used for units of time a day and smaller,
     * Period should be used for units of time a day and larger.
     */
    @Test
    public void testDifferenceDurationPeriod() {
        final Duration duration = Duration.ofDays(1);
        final Period period = Period.ofDays(1);

        assertThat(duration.getUnits()).containsExactly(ChronoUnit.SECONDS, ChronoUnit.NANOS);
        assertThat(period.getUnits()).containsExactly(ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS);

        assertThat(duration.toString()).isEqualTo("PT24H");
        assertThat(period.toString()).isEqualTo("P1D");
    }

    /**
     * Duration is used for objects that contain seconds, as duration itself is saved in seconds.
     */
    @Test(expected = UnsupportedTemporalTypeException.class)
    public void testAddDurationToDate() {
        LocalDate.now().plus(Duration.ofDays(1));
    }

    @Test
    public void testDaylightSavings() {
        // clock moves 1 hour forward on 12th march 2017 in US
        final LocalDate date = LocalDate.of(2017, 3, 12);
        final LocalTime time = LocalTime.of(1, 0);
        final ZoneId zoneId = ZoneId.of("America/New_York");

        final ZonedDateTime zoneDateTime = ZonedDateTime.of(date, time, zoneId);
        final Duration duration = Duration.ofHours(3);
        final ZonedDateTime dateTimeAfterDaylightSaving = zoneDateTime.plus(duration);

        // 1:00 + daylight saving + 3:00
        assertThat(dateTimeAfterDaylightSaving.getHour()).isEqualTo(5);
    }
}
