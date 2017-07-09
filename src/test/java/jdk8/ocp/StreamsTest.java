package jdk8.ocp;

import org.junit.Test;

import com.google.common.primitives.Ints;
import com.google.common.truth.Truth;

import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class StreamsTest {

    @Test
    public void testNumberOfCalls() {
        final boolean outputNoneMatch = IntStream.range(3, 8)
                .peek(System.out::print)
                .map(x -> x * 2)
                .noneMatch(x -> x % 2 != 0);
        // -> 34567

        System.out.println(outputNoneMatch);
        // -> true

        assertThat(outputNoneMatch).isTrue();

        final boolean outputMatch = IntStream.range(3, 8)
                .peek(System.out::print)
                .map(x -> x * 2)
                .noneMatch(x -> x % 2 == 0);
        // -> 3

        System.out.println(outputMatch);
        // -> false

        assertThat(outputMatch).isFalse();
    }

    @Test
    public void testForEachArguments() {
        // return type must always be null
        IntStream.range(3, 8)
                .forEach(System.out::println);

        IntStream.range(3, 8)
                .forEach(x -> x += x);

        // invalid operations
        /*
         * IntStream.range(3, 8)
         *       .forEach(x -> x * 2); // -> invalid return type
         *       .forEach(x -> return x); // -> invalid retrun statement
         */
    }

}
