package guava

import com.google.common.collect.ImmutableMap
import spock.lang.Specification


/**
 * This class is used as test for the guava immutable map.
 *
 * https://github.com/google/guava/wiki/ImmutableCollectionsExplained
 *
 * @author Fabian Dietenberger
 */
class ImmutableMapTest extends Specification {

    def "create an immutable map"() {
        given:
        def englishToInt = ImmutableMap.<String, Integer> builder()
                .put("one", 1)
                .put("two", 2)
                .put("three", 3)
                .build()

        def germanToInt = ImmutableMap.of("Eins", 1, "Zwei", 2) // of() supports up to 5 key-value pairs

        when:
        englishToInt.put("four", 4)
        englishToInt.remove("three")
        englishToInt.clear()
        germanToInt.put("drei", 3)

        then:
        thrown UnsupportedOperationException
    }
}