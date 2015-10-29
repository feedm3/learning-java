package guava

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import spock.lang.Specification


/**
 * This class is used as test fot the guava bimap.
 *
 * https://github.com/google/guava/wiki/NewCollectionTypesExplained#bimap
 *
 * @author Fabian Dietenberger
 */
class BiMapTest extends Specification {

    def "add values to a bimap"() {
        given:
        BiMap<String, String> englishToGerman = HashBiMap.create()
        englishToGerman.put("one", "eins")
        englishToGerman.put("two", "zwei")
        englishToGerman.put("three", "drei")

        expect:
        englishToGerman.containsKey("one")
        englishToGerman.containsValue("eins")
        englishToGerman.size() == 3
    }

    def "inverse the bimap"() {
        given: "a bimap"
        BiMap<String, String> englishToGerman = HashBiMap.create()
        englishToGerman.put("one", "eins")
        englishToGerman.put("two", "zwei")
        englishToGerman.put("three", "three")

        when: "we inverse the bimap"
        englishToGerman = englishToGerman.inverse()

        then: "the key and values are interchanged"
        englishToGerman.containsKey("eins")
        englishToGerman.containsValue("one")

        when: "we inverse the bimap again"
        englishToGerman = englishToGerman.inverse()

        then: "we have the original bimap"
        englishToGerman.containsKey("one")
        englishToGerman.containsValue("eins")
    }

    def "override exisiting values"() {
        given: "a bimap"
        BiMap<String, String> englishToGerman = HashBiMap.create()
        englishToGerman.put("one", "uno")
        englishToGerman.put("uno", "zwei")
        englishToGerman.put("three", "three")

        when: "we put an exisiting key"
        englishToGerman.put("one", "eins")

        then: "the old one gets overriden"
        englishToGerman.containsKey("one")
        englishToGerman.containsValue("eins")

        when: "we put an existing value"
        englishToGerman.put("uno", "eins")

        then: "the bimap throws an error"
        thrown IllegalArgumentException

        when: "we force a put"
        englishToGerman.forcePut("one", "eins")
        englishToGerman.forcePut("two", "zwei")

        then: "we overridden the existing value"
        englishToGerman.containsKey("one")
        englishToGerman.containsValue("eins")
        englishToGerman.containsKey("two")
        englishToGerman.containsValue("zwei")
    }
}