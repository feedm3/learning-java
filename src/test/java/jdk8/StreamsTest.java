package jdk8;

import jdk1.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used as test for the java 8 stream api.
 * <p>
 * Most of the stream examples are inspired by 2 great talks from
 * Dr. Venkat Subramaniam https://youtu.be/wk3WLaR2V2U and https://www.youtube.com/watch?v=HVID35J7h_I
 * and the streams tutorial by Benjamin Winterberg
 * http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 *
 * @author Fabian Dietenberger
 */
public class StreamsTest {

    /**
     * Stream objects can be created from various data sources. You can create a stream
     * from every List and Set object as well as from builder methods inside the different
     * stream classes.
     */
    @Test
    public void howToCreateAStream() {
        // create a stream from a list and set object
        Arrays.asList("a", "b", "c")
                .stream();
        new HashMap<>().entrySet().stream();

        // various builder methods
        Stream.of("a", "b", "c");
        Stream.generate(() -> "random value"); // inifit call of the supplier method
        IntStream.range(1, 4);
    }

    /**
     * There are two kinds of streams: Regular streams and primitive streams.
     * Primitive streams are for working with int, long and double. Both streams
     * are basically the same but primitive streams also support a sum() and
     * average() operation.
     * <p>
     * A typical scenario an IntStream can be used is for classic for-loops.
     */
    @Test
    public void primitiveStreams() {
        // for-loop replacement
        IntStream.range(0, 10)
                .forEach(index -> {
                    assertThat(index).isAtLeast(0);
                    assertThat(index).isLessThan(10);
                });

        // average calculation
        OptionalDouble average = DoubleStream.iterate(1, e -> e + 1)
                .limit(3) // 1, 2, 3
                .average();
        assertThat(average.getAsDouble()).isWithin(2.0);
    }

    /**
     * Every element gets passed down the intermediate functions one after another.
     * <p>
     * To reduce execution amounts it is recommended to put the filter methods at first
     * to only operate on relevant with the other intermediate functions.
     */
    @Test
    public void executionOrderOfIntermediateOperations() {
        int[] mapExecutionAmounts = new int[]{0, 0};
        int[] filterExecutionAmounts = new int[]{0, 0};

        // using the map operator first every element runs through it
        Stream.of("d1", "a1", "a2", "c1")
                .map(s -> {
                    mapExecutionAmounts[0]++;
                    return s.toUpperCase();
                })
                .filter(s -> {
                    filterExecutionAmounts[0]++;
                    return s.startsWith("A");
                })
                .forEach(s1 -> {
                });
        assertThat(mapExecutionAmounts[0]).isEqualTo(4);
        assertThat(filterExecutionAmounts[0]).isEqualTo(4);

        // if we first filter our elements only the filtered elements
        // get called in the map operation
        Stream.of("d1", "a1", "a2", "c1")
                .filter(s -> {
                    filterExecutionAmounts[1]++;
                    return s.startsWith("a");
                })
                .map(s -> {
                    mapExecutionAmounts[1]++;
                    return s.toUpperCase();
                })
                .forEach(s -> {
                });
        assertThat(filterExecutionAmounts[1]).isEqualTo(4);
        assertThat(mapExecutionAmounts[1]).isEqualTo(2);

        // the filter operator filters the element size down to 1
        // so the sort operation doesn't event get called
        int[] sortExecutionAmounts = new int[]{0};
        Stream.of("d1", "a1", "a2", "c1")
                .filter(s -> {
                    filterExecutionAmounts[1]++;
                    return s.startsWith("d");
                })
                .sorted((s1, s2) -> {
                    sortExecutionAmounts[0]++;
                    return s1.compareTo(s2);
                })
                .forEach(s -> {
                });
        assertThat(sortExecutionAmounts[0]).isEqualTo(0);
    }

    /**
     * A stream can only be executed once.
     */
    @Test(expected = IllegalStateException.class)
    public void executeStreamMoreThanOnce() {
        Stream<Integer> stream = Stream.iterate(1, e -> e + 1)
                .limit(10);
        stream.forEach(number -> {
        });
        stream.forEach(number -> {
        });
    }

    /**
     * A collector is a terminal operation which can be used to transform a stream into
     * a differnet kind of result. You can write you own collector or use one of the many
     * built-in collectors in the Collectors class.
     */
    @Test
    public void useCollectorToTransformToListMapSet() {
        final List<Person> persons = Person.createPersons(); // Max 18, Peter 23, Pamela 23, David 12

        // stream to list
        final List<Person> filteredPersonsMap = persons.stream()
                .filter(p -> p.getName().startsWith("P"))
                .collect(Collectors.toList());
        assertThat(filteredPersonsMap.size()).isEqualTo(2);

        // stream to set
        final Set<Person> filteredPersonsSet = persons.stream()
                .filter(p -> p.getName().startsWith("P"))
                .collect(Collectors.toSet());
        assertThat(filteredPersonsSet.size()).isEqualTo(2);

        // stream to map
        final Map<String, Person> mapByName = persons.stream()
                .collect(Collectors.toMap(Person::getName, p -> p)); // keys must be unique otherwise we get an exception
        assertThat(mapByName.size()).isEqualTo(4);
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
     * The stream gets manipulated inside lambdas and therefore we can use
     * (pseudo final) variables. if we would change the variable outside the
     * execution the java compiler would complain. but we can trick the compiler
     * by embedding the variable inside an array.
     * this should not be done in real projects!
     */
    @Test
    public void trickTheJavaCompilerWithChangingFinalVariable() {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final int[] factors = new int[]{2}; // we put a factor inside an array

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
     * ------------------------------------------------------
     * Examples
     * ------------------------------------------------------
     */
    @Test
    public void generateAStreamAndFilterTheValues() {
        // first we want to get a list with 100 numbers. we want to
        // have the square root of every number that is even, starting at 1
        Stream.iterate(1, e -> e + 1) // start at 1 and add 1 on every iteration
                .filter(StreamsTest::isEven) // only take the even numbers
                .map(e -> e * 2) // double number
                .limit(5) // only accept the first 5 results [2, 4, 6, 8, 10]
                .forEach(number -> { // execute the stream with a for each loop
                    System.out.println(number);
                    assertThat(number).isAtLeast(4);
                    assertThat(number).isAtMost(20);
                });
    }

    /**
     * We can change the starting parameter and the
     * applied increment after every loop
     */
    @Test
    public void useCustomStreamStartingAndIncrementValue() {
        Stream.iterate(10, e -> e + 3) // start at 10 and increment by 3
                .limit(10)
                .forEach(number -> {
                    assertThat(number).isAtLeast(10);
                    assertThat(number).isAtMost(37);
                });
    }

    /**
     * The Collectors provide a join method to simply concat multiple streams with a given separator.
     */
    @Test
    public void createACommaSeparatedStringFromAList() {
        final List<String> fileNames = Arrays.asList("File1.txt", "File2.txt");

        final String commaSeparatedFileNames = fileNames.stream()
                .collect(Collectors.joining(", "));

        assertThat(commaSeparatedFileNames).isEqualTo("File1.txt, File2.txt");
    }


    @Test
    public void aggregateAProperty() {
        final List<Person> persons = Person.createPersons();

        // calculate sum
        final Integer sum = persons.stream()
                .map(Person::getAge)
                .collect(Collectors.summingInt(Integer::intValue)); // we get a list with Integers

        assertThat(sum).isEqualTo(76);

        // calculate an average
        final Double averageAge = persons.stream()
                .collect(Collectors.averagingDouble(Person::getAge));

        assertThat(averageAge).isWithin(19.0);

        // calculate min
        final Optional<Integer> min = persons.stream()
                .map(Person::getAge)
                .min(Comparator.naturalOrder());

        assertThat(min.get()).isEqualTo(12);
    }

    @Test
    public void groupByProperty() {
        final List<Person> persons = Person.createPersons();

        final Map<Integer, List<Person>> groupByAge = persons.stream()
                .collect(Collectors.groupingBy(Person::getAge));

        assertThat(groupByAge.get(23).size()).isEqualTo(2);
    }

    @Test
    public void createAPhrase() {
        final List<Person> persons = Person.createPersons();

        final String phrase = persons.stream()
                .filter(p -> p.getAge() >= 18)
                .map(Person::getName)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age.")); // concat string, prefix, suffix

        assertThat(phrase).isEqualTo("In Germany Max and Peter and Pamela are of legal age.");
    }

    @Test
    public void simpleListFiltering() {
        final List<String> urls = new ArrayList<>();
        urls.add("https://google.de");
        urls.add("https://facebook.de");
        urls.add("https://unhypem.com");
        urls.add("http://uber.com");

        final List<String> httpsUrls = urls.stream()
                .filter(url -> url.startsWith("https"))
                .collect(Collectors.toList());

        assertThat(httpsUrls.size()).isEqualTo(3);
        assertThat(httpsUrls).doesNotContain("https://uber.com");
    }

    @Test
    public void filterMapByValueAndCreateAListOfKeys() {
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
