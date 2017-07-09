package caffeine

import com.github.benmanes.caffeine.cache.AsyncCacheLoader
import com.github.benmanes.caffeine.cache.AsyncLoadingCache
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import spock.lang.Specification

import javax.annotation.Nonnull
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
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
    def "populate cache manually"() {
        given:
        Cache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(5)
                .build();

        when: "adding an entry direct"
        cache.put(1, "One")

        then: "entry must be present"
        cache.getIfPresent(1) == "One"

        when: "adding an entry via a loader"
        cache.get(2, { Integer key -> getStringFromExpensiveComputation(key) })

        then: "entry must be present"
        cache.getIfPresent(2) == "Two"

        when: "invalidation an entry"
        cache.invalidate(1)

        then: "entry must be null"
        cache.getIfPresent(1) == null
    }

    def "populate cache via loading"() {

    }

    def "populate cache asynchronously"() {
        given:
        AsyncLoadingCache<Integer, String> cache = Caffeine.newBuilder()
                .maximumSize(5)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .buildAsync(new AsyncCacheLoader() {
            @Override
            CompletableFuture asyncLoad(@Nonnull final Object key, @Nonnull final Executor executor) {
                return CompletableFuture.completedFuture(getStringFromExpensiveComputation(key));
            }
        })

        expect:
        cache.get(1).get() == "One";
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
