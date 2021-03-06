package guava

import com.google.common.base.Joiner
import spock.lang.Specification


/**
 * This class is used as test for the guava joiner.
 *
 * https://github.com/google/guava/wiki/StringsExplained#joiner
 *
 * @author Fabian Dietenberger
 */
class JoinerTest extends Specification {

    def "concatenate a string array"() {
        given:
        def strings = ["Fabi", "Moe", "Schleif", "Louis"]

        when:
        def concatedString = Joiner.on(", ").join(strings)

        then:
        concatedString == "Fabi, Moe, Schleif, Louis"
    }

    def "concatenate a string array with nulls"() {
        given:
        def strings = [null, "Moe", null, "Louis"]

        when:
        def concatedString = Joiner.on(", ").skipNulls().join(strings)

        then:
        concatedString == "Moe, Louis"
    }

    def "concatenate a string array and fill nulls with placeholder"() {
        given:
        def strings = [null, "Moe", null, "Louis"]

        when:
        def concatedString = Joiner.on(", ").useForNull("deleted").join(strings)

        then:
        concatedString == "deleted, Moe, deleted, Louis"
    }
}