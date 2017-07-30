package jdk8;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.time.LocalDate;
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
}
