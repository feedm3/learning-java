package guava

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification


/**
 * This class is used as test for guava predicates.
 *
 * https://github.com/google/guava/wiki/FunctionalExplained#predicates
 *
 * @author Fabian Dietenberger
 */
class PredicatesTest extends Specification {

    def "use a custom predicate"() {
        given:
        Set<Integer> numbers = Sets.newHashSet(1, 3, 5, 6, 7, 8)

        when:
        Iterable<Integer> evenNumbers = Iterables.filter(numbers, new IsEvenNumber())
        Iterables.filter(numbers, Predicates.isNull())

        then:
        evenNumbers.containsAll(6, 8)
        Iterables.all(evenNumbers, new IsEvenNumber())
    }

    def "use a build in predicate"() {
        given:
        def strings = Lists.asList("Hello", null, "World")

        when:
        def listNullsOnly = Iterables.filter(strings, Predicates.isNull())
        def stringsOnly = Iterables.filter(strings, Predicates.instanceOf(String.class))

        then:
        listNullsOnly.size() == 1
        stringsOnly.size() == 2
    }

    class IsEvenNumber implements Predicate<Integer> {

        @Override
        boolean apply(final Integer input) {
            return (input % 2) == 0;
        }
    }
}