package guava

import com.google.common.collect.Ordering
import com.google.common.primitives.Ints
import spock.lang.Specification


/**
 * This class is used as test for the guava ordering.
 *
 * https://github.com/google/guava/wiki/OrderingExplained
 *
 * @author Fabian Dietenberger
 */
class OrderingTest extends Specification {

    def "simple order a list of ints"() {
        given:
        def intsToSort = Arrays.asList(5, 1, 3, null, 2, null, 10)

        when:
        Collections.sort(intsToSort, Ordering.natural().nullsFirst())

        then:
        intsToSort.get(0) == null
        intsToSort.get(2) == 1
        intsToSort.last() == 10

        when:
        Collections.sort(intsToSort, Ordering.natural().nullsFirst().reverse())

        then:
        intsToSort.get(0) == 10
        intsToSort.last() == null

        when: "we dont specify a null ordering in a list with nulls"
        Collections.sort(intsToSort, Ordering.natural())

        then: "an exception will be thrown"
        thrown NullPointerException
    }

    def "order strings by length with custom order"() {
        given: "a custom order and a strings array"
        def stringsToSort = Arrays.asList("Johanna", "Stefan", "Sankt Martin", "Fabi")
        def stringOrderingByLength = new StringOrderingByLength()

        when: "we sort the array"
        Collections.sort(stringsToSort, stringOrderingByLength)

        then: "the array is sorted by the strings length"
        stringsToSort.get(0) == "Fabi"
        stringsToSort.last() == "Sankt Martin"
    }

    def "order strings by length with custom and secondary order"() {
        given:
        def stringsToSort = Arrays.asList("John", "Stefan", "Sankt Martin", "Fabian") // some have the same length
        def stringOrderingByLength = new StringOrderingByLength()

        when:
        Collections.sort(stringsToSort, stringOrderingByLength.compound(Ordering.natural()))

        then:
        stringsToSort.get(0) == "John"
        stringsToSort.get(1) == "Fabian"
        stringsToSort.get(2) == "Stefan"
        stringsToSort.get(3) == "Sankt Martin"

        when:
        Collections.sort(stringsToSort, stringOrderingByLength.reverse().compound(Ordering.natural().reverse()))

        then:
        stringsToSort.get(0) == "Sankt Martin"
        stringsToSort.get(1) == "Stefan"
        stringsToSort.get(2) == "Fabian"
        stringsToSort.get(3) == "John"
    }

    def "ordering list with nulls"() {
        given:
        def stringsToSort = Arrays.asList("Mo", "Jonny", "Steff", null)
        def stringOrderingByLength = new StringOrderingByLength()

        when:
        Collections.sort(stringsToSort, stringOrderingByLength.nullsFirst().compound(Ordering.natural()))

        then:
        stringsToSort.get(0) == null
        stringsToSort.get(1) == "Mo"
        stringsToSort.get(2) == "Jonny"
    }

    def "find min, max and x greatest values"() {
        given:
        def stringsToSort = Arrays.asList("Mo", "Jonny", "Steff", "Anna", "Heinrich")
        def stringOrderingByLength = new StringOrderingByLength()

        when:
        def maxString = stringOrderingByLength.compound(Ordering.natural()).max(stringsToSort)
        def minString = stringOrderingByLength.compound(Ordering.natural()).min(stringsToSort)
        def longestStrings = stringOrderingByLength.compound(Ordering.natural()).greatestOf(stringsToSort, 3)

        then:
        maxString == "Heinrich"
        minString == "Mo"
        longestStrings == Arrays.asList("Heinrich", "Steff", "Jonny")
    }

    /**
     * Custom order class to order strings by length
     */
    class StringOrderingByLength extends Ordering<String> {

        @Override
        int compare(final String left, final String right) {
            return Ints.compare(left.length(), right.length())
        }
    }
}