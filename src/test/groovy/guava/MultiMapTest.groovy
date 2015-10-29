package guava

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import spock.lang.Specification


/**
 * This class is used as test for the guava multimap.
 *
 * https://github.com/google/guava/wiki/NewCollectionTypesExplained#multimap
 *
 * @author Fabian Dietenberger
 */
class MultimapTest extends Specification {

    def "push elements to a list"() {
        setup: "an empty list"
        def ListMultimap<String, String> multimap = ArrayListMultimap.create()

        when: "we put 2 elements to the list"
        multimap.put("BMW", "4er")
        multimap.put("Audi", "A7")

        then: "there are 2 elements in the list"
        !multimap.isEmpty()
        multimap.size() == 2
        multimap.get("BMW").size() == 1
        multimap.get("Audi").size() == 1
    }

    def "push multiple values with the same key"() {
        setup: "an empty list"
        def ListMultimap<String, String> multimap = ArrayListMultimap.create()

        when: "we put 2 values with the same key"
        multimap.put("BMW", "5er")
        multimap.put("BMW", "6er")
        multimap.put("Audi", "A4")

        then: "we get 2 values by accessing this key"
        multimap.size() == 3
        multimap.get("BMW").size() ==  2
        multimap.get("Audi").size() == 1
        multimap.containsKey("BMW")
        multimap.containsValue("A4")
        multimap.containsEntry("BMW", "5er")
    }
}