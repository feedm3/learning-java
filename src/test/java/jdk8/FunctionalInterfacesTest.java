package jdk8;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class FunctionalInterfacesTest {

    @Test
    public void testFunctionInstantiation() {
        final DoubleFunction<String> doubleFunction = String::valueOf;

        final double value = 2;
        final String valueOf = doubleFunction.apply(value);

        assertThat(valueOf).isEqualTo("2.0");
    }

    /**
     * There are 4 different default base functional interfaces.
     *
     * - Function
     * - Consumer
     * - Supplier
     * - Predicate
     */
    @Test
    public void testDifferentFunctionalInterfaces() {
        // Function: Has an input parameter and a return value
        final Function<String, Integer> function = (value) -> { return 1; };
        final Integer functionRestult = function.apply("test");
        assertThat(functionRestult).isEqualTo(1);

        // Consumer: Only has an input parameter
        final Consumer<String> consumer = (value) -> {};
        consumer.accept("test");

        // Supplier: Only has an return value
        final Supplier<Integer> supplier = () -> { return 1; };
        final Integer supplierResult = supplier.get();
        assertThat(supplierResult).isEqualTo(1);

        // Predicate: Has an input paramter and a boolean return value
        final Predicate<String> predicate = (value) -> { return true; };
        final boolean predicateResult = predicate.test("test");
        assertThat(predicateResult).isTrue();
    }
}
