package jdk8.ocp;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.util.function.DoubleFunction;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class FunctionTest {

    @Test
    public void testFunctionInstantiation() {
        final DoubleFunction<String> doubleFunction = String::valueOf;

        final double value = 2;
        final String valueOf = doubleFunction.apply(value);

        assertThat(valueOf).isEqualTo("2.0");
    }
}
