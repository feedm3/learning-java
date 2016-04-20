package caffeine

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import spock.lang.Specification

import java.util.concurrent.TimeUnit
/**
 * This class is used as test for the caffeine library.
 *
 * @author Fabian Dietenberger
 */
class CaffeineTest extends Specification {

    /**
     * Caffeine allows explicit control of retrieving, updating and invalidation entries.
     */
    def "populate a cache manual"() {
        given:
        Cache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(5)
                .build();

        when: // adding an entry direct
        cache.put(1, "One")

        then:
        cache.getIfPresent(1) == "One"

        when: // adding an entry via a loader
        cache.get(2, { Integer key -> getStringFromExpensiveComputation(key) })

        then:
        cache.getIfPresent(2) == "Two"

        when: // invalidation an entry
        cache.invalidate(1)

        then:
        cache.getIfPresent(1) == null
    }

    def getStringFromExpensiveComputation = { int number ->
        switch (number) {
            case 1:
                return "One"
            case 2:
                return "Two"
        }
    }
}