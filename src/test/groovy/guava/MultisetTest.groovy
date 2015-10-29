package guava

import com.google.common.collect.HashMultiset
import com.google.common.collect.Multiset
import spock.lang.Specification


/**
 * This class is used as test for the guava multiset.
 *
 * https://github.com/google/guava/wiki/NewCollectionTypesExplained#multiset
 *
 * @author Fabian Dietenberger
 */
class MultisetTest extends Specification {

    def "create a multiset"() {
        given:
        Multiset<String> tags = HashMultiset.create()
        tags.add("BMW")
        tags.add("BMW")
        tags.add("Audi")
        tags.add("VW", 3)
        tags.add(null)

        expect:
        tags.size() == 7
        tags.count("BMW") == 2
        tags.count("bmw") == 0
        tags.count("VW") == 3
        tags.count(null) == 1
    }

}