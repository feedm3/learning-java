package guava

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.common.cache.Weigher
import spock.lang.Specification

import java.util.concurrent.TimeUnit


/**
 * This class is used as test for the guavas cache.
 *
 * https://github.com/google/guava/wiki/CachesExplained
 *
 * @author Fabian Dietenberger
 */
class CacheTest extends Specification {

    def "Save values by a key in the cache"() {
        given:
        CacheLoader<String, String> loader = new KeyToUpperCaseCacheLoader()
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader)

        expect:
        cache.size() == 0
        cache.getUnchecked("hallo") == "HALLO"
        cache.size() == 1
        cache.getUnchecked("hallo") == "HALLO"
        cache.size() == 1
        cache.getUnchecked("HalLo") == "HALLO"
        cache.size() == 2
        cache.getIfPresent("hallo") == "HALLO"
        cache.getIfPresent("notCache") == null
        cache.size() == 2
    }

    def "Cache with maximum size"() {
        given:
        CacheLoader<String, String> loader = new KeyToUpperCaseCacheLoader()
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).build(loader)

        expect:
        cache.size() == 0
        cache.getUnchecked("first")
        cache.getUnchecked("second")
        cache.getUnchecked("third")
        cache.getUnchecked("fourth")
        cache.size() == 3
        cache.getIfPresent("first") == null
        cache.getIfPresent("second") == "SECOND"
        cache.getIfPresent("third") == "THIRD"
        cache.getIfPresent("fourth") == "FOURTH"
    }

    def "Cache with maximum weight"() {
        given:
        CacheLoader<String, String> loader = new KeyToUpperCaseCacheLoader()
        Weigher<String, String> weigherByStringLength = new WeigherByStringLength()
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumWeight(10).weigher(weigherByStringLength).build(loader)

        expect:
        // if a weight is set, the cache deletes entries if the weight size gets reached
        // the cache may delete entries before the limit gets reached
        // you do not know what entries get deleted. the cache selects the entries which are less likely to be used again
        cache.getUnchecked("vw")
        cache.getUnchecked("bmw")
        cache.getUnchecked("audi") // weight = 9
        cache.size() == 3
        cache.getUnchecked("xyz") // weight = 10
        cache.size() == 3
    }

    def "Cache with time eviction"() {
        given:
        CacheLoader<String, String> loader = new KeyToUpperCaseCacheLoader()
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(100, TimeUnit.MILLISECONDS).build(loader)

        when:
        cache.getUnchecked("first")
        cache.getUnchecked("second")
        Thread.sleep(60)
        cache.getUnchecked("first") // reset 'first' expiration time
        Thread.sleep(80)

        then:
        cache.getIfPresent("first") == "FIRST"
        cache.getIfPresent("second") == null

        when:
        Thread.sleep(110)

        then:
        cache.getIfPresent("first") == null
    }

    /**
     * The cache loader makes calculations with the key and returns a value.
     */
    class KeyToUpperCaseCacheLoader extends CacheLoader<String, String> {

        @Override
        String load(final String key) throws Exception {
            return key.toUpperCase()
        }
    }

    /**
     * A weigher calculates the weight from a key and/or a value.
     * The weight is used by the cache for expiration.
     */
    class WeigherByStringLength implements Weigher<String, String> {

        @Override
        int weigh(final String key, final String value) {
            // example: key = vw; value = VW
            return value.length()
        }
    }
}