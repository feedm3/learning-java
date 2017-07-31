package jdk8;

import org.junit.Test;

import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.Locale;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class LocalizationTest {

    @Test
    public void testCountryIndiaAndLanguageFrench() {
        Locale locale = new Locale("FR", "IN");

        assertThat(locale.getLanguage()).isEqualTo("fr");
        assertThat(locale.getCountry()).isEqualTo("IN");
    }

    @Test(expected = ZoneRulesException.class)
    public void testGetUnknownZoneId() {
        ZoneId.of("test");
    }
}
