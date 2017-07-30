package jdk8.ocp;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;


/**
 *
 */
public class OptionalTest {

    @Test
    public void testFilterAndMapOnOptional() {
        final String result = Optional.of("test")
                .filter(string -> string.length() > 3)
                .map(String::toUpperCase)
                .orElseGet(() -> "nothing");

        assertThat(result).isEqualTo("TEST");
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        Optional.of(null);
    }

    @Test
    public void testOfNullable() {
        final String result = Optional.<String>ofNullable(null)
                .filter(string -> string.length() > 3)
                .map(String::toUpperCase)
                .orElse("test");

        assertThat(result).isEqualTo("test");
    }
}
