package guava

import com.google.common.collect.ImmutableSet
import spock.lang.Specification


/**
 * This class is used as test for the guava immutable list.
 *
 * https://github.com/google/guava/wiki/ImmutableCollectionsExplained
 *
 * @author Fabian Dietenberger
 */
class ImmutableListTest extends Specification {

    def "add elements to an immutable set"() {
        given: "an immutable set"
        def colors = ImmutableSet.of(
                "black",
                "red",
                "gold"
        )

        when: "adding or removing elements"
        colors.add("white")
        colors.remove("black")

        then: "an exception should be thrown and the size should remain the same"
        thrown UnsupportedOperationException
        colors.size() == 3
    }
}