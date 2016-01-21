package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used as test for the java 8 stream api.
 *
 * @author Fabian Dietenberger
 */
public class StreamsTest {

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

    @Test
    public void looping() {
        // instead of a classic for loops we can use an IntStream
        // the pros are the better readability and a final index variable
        // which we therefore can use in a lambda
        IntStream.range(0, 10)
                .forEach(index -> {
                    assertThat(index).isAtLeast(0);
                    assertThat(index).isLessThan(10);
                });
    }

    @Test
    public void loopingInfiniteStreams() {
        // the stream api can loop infinite times
        final List<Double> sqrtOfFirst100EvenNumbers = Stream.iterate(1, e -> e + 1)
                .filter(StreamsTest::isEven)
                .map(Math::sqrt)
                .limit(100)
                .collect(Collectors.toList());

        sqrtOfFirst100EvenNumbers.forEach(number -> {
            assertThat(number * number).isAtLeast(2.0);
            assertThat(number * number).isAtMost(201.0);
        });

        final List<Integer> first100EvenNumbers = Stream.iterate(1, e -> e + 1)
                .filter(StreamsTest::isEven)
                .limit(100)
                .collect(Collectors.toList());

        first100EvenNumbers.forEach(number -> {
            assertThat(number).isAtLeast(2);
            assertThat(number).isAtMost(200);
        });

        Stream.iterate(10, e -> e + 3)
                .limit(100)
                .forEach(System.out::println);
    }

    static boolean isEven(final int number) {
        return number % 2 == 0;
    }
}
