package guava

import spock.lang.Specification


/**
 * This class is used as test for guava preconditions.
 *
 * https://github.com/google/guava/wiki/PreconditionsExplained
 *
 * @author Fabian Dietenberger
 */
class PreconditionsTest extends Specification {

    def "check variables with preconditions"() {
        given:
        def list = Collections.emptyList()

        when:
        checkArgument(list != null)

        then:
        notThrown IllegalArgumentException

        when:
        checkArgument(list.size() == 1)

        then:
        thrown IllegalArgumentException

        when:
        checkArgument(list.size() == 1, "List must have 1 entry")

        then:
        IllegalArgumentException ex = thrown()
        ex.message == "List must have 1 entry"
    }
}