package java8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used as test for the java 8 stream api.
 * <p>
 * Most of the stream examples are inspired by a great talk from
 * Dr. Venkat Subramaniam https://youtu.be/wk3WLaR2V2U
 *
 * @author Fabian Dietenberger
 */
public class StreamsTest {

    /**
     * Instead of a classic for loops we can use an IntStream
     * the pros are the better readability and a final index variable
     * which we therefore can use in a lambda
     */
    @Test
    public void basicIntStreamLoop() {
        IntStream.range(0, 10)
                .forEach(index -> {
                    assertThat(index).isAtLeast(0);
                    assertThat(index).isLessThan(10);
                });
    }

    /**
     * The basic stream can be used to stream over generated values. The values
     * can than be filtered and manipulated.
     */
    @Test
    public void createAStreamAndFilterTheValues() {
        // first we want to get a list with 100 numbers. we want to
        // have the square root of every number that is even, starting at 1
        Stream.iterate(1, e -> e + 1) // start at 1 and add 1 on every iteration
                .filter(StreamsTest::isEven) // only take the even numbers
                .map(Math::sqrt) // calculate the square root of every even number
                .limit(100) // only accept the first 100 results
                .forEach(number -> { // execute the stream with a for each loop
                    assertThat(number * number).isAtLeast(2.0);
                    assertThat(number * number).isAtMost(201.0); // rounding issues make the last number little higher 200
                });
    }

    /**
     * All applied functions to a stream get executed when the terminal operation gets called.
     * Terminal operations are either void or return a non-stream result.
     */
    @Test
    public void executeAStreamLater() {
        final Stream<Integer> first100EvenNumbersStream = Stream.iterate(1, e -> e + 1)
                .filter(StreamsTest::isEven)
                .limit(100);

        // the stream gets executed when the terminal opation gets called
        first100EvenNumbersStream.forEach(number -> {
            assertThat(number).isAtLeast(2);
            assertThat(number).isAtMost(200);
        });
    }

    /**
     * We can change the starting parameter and the
     * applied increment after every loop
     */
    @Test
    public void customStreamStartingAndIncrementValue() {
        Stream.iterate(10, e -> e + 3) // start at 10 and increment by 3
                .limit(10)
                .forEach(number -> {
                    assertThat(number).isAtLeast(10);
                    assertThat(number).isAtMost(37);
                });
    }

    /**
     * The stream gets manipulated inside lambdas and therefore we can use
     * (pseudo final) variables. if we would change the variable outside the
     * execution the java compiler would complain. but we can trick the compiler
     * by embedding the variable inside an array.
     * this should not be done in real projects!
     */
    @Test
    public void trickTheJavaCompilerWithChangingFinalVariable() {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final int[] factors = new int[]{ 2 }; // we put a factor inside an array

        final Stream<Integer> numbersStream = numbers.stream()
                .map(e -> e * factors[0]); // the stream uses the factor to multiply every entry

        factors[0] = 44; // we then change the factor

        // at this time in code the stream was not executed because no terminal operation was called

        numbersStream.forEach(number -> { // we call the terminal operation and the stream starts calculation
            // because we change the factor before the terminal
            // function the value 44 was used and not 2
            assertThat(number).isAtLeast(44);
            assertThat(number).isAtMost(440);
        });
    }

    /**
     * The Collectors provide a join method to simply concat multiple streams with a given separator.
     */
    @Test
    public void createACommaSeparatedStringFromAList() {
        final List<String> fileNames = Arrays.asList("File1.txt", "File2.txt");

        String commaSeparatedFileNames = fileNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));

        assertThat(commaSeparatedFileNames).isEqualTo("File1.txt, File2.txt");
    }

    @Test
    public void sortAList() {

    }


    @Test
    public void filterList() {
        final List<String> urls = new ArrayList<>();
        urls.add("https://google.de");
        urls.add("https://facebook.de");
        urls.add("https://unhypem.com");
        urls.add("http://uber.com");

        final List<String> httpsUrls = urls.stream()
                .filter(url -> url.startsWith("https"))
                .map(url -> url)
                .collect(Collectors.toList());

        assertThat(httpsUrls.size()).isEqualTo(3);
        assertThat(httpsUrls).doesNotContain("https://uber.com");
    }

    @Test
    public void filterMap() {
        final Map<String, Integer> characterLevels = new HashMap<>();
        characterLevels.put("Fabi", 10);
        characterLevels.put("Dave", 1);
        characterLevels.put("Raphy", 2);
        characterLevels.put("Done", 8);

        final List<String> bestCharacters = characterLevels
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(bestCharacters.size()).isEqualTo(2);
        assertThat(bestCharacters).containsAllOf("Fabi", "Done");
    }

    /**
     * Static helper method to check if a number is even.
     *
     * @param number the number to check
     * @return true if number is even
     */
    static boolean isEven(final int number) {
        return number % 2 == 0;
    }
}
