package guava

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification


/**
 * This class is used as test for guava helper methods if you work with lists and sets.
 *
 * https://github.com/google/guava/wiki/CollectionUtilitiesExplained
 *
 * @author Fabian Dietenberger
 */
class CollectionUtilitiesTest extends Specification {

    def "Lists"() {
        given:
        List<String> stringsEmptyList = Lists.newArrayList()
        List<String> stringsList = Lists.newArrayList("BMW", "Audi", "VW")
        List<String> stringsCopyList = Lists.newArrayList(stringsList)

        expect:
        stringsEmptyList.size() == 0
        stringsList.size() == 3
        stringsCopyList.size() == 3

        when:
        stringsList.remove("BMW")

        then:
        stringsList.size() == 2
        stringsCopyList.size() == 3
    }

    def "Sets"() {
        given:
        Set<String> emptyStringsSet = Sets.newHashSet()
        Set<String> identityHashSet = Sets.newIdentityHashSet() // uses == as comparator

        expect:
        emptyStringsSet.size() == 0
        identityHashSet.size() == 0

        when:
        emptyStringsSet.addAll(new String("BMW"), new String("BMW"))
        identityHashSet.addAll(new String("BMW"), new String("BMW"))

        then:
        emptyStringsSet.size() == 1
        identityHashSet.size() == 2
    }
}