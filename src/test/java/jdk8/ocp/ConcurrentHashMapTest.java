package jdk8.ocp;

import org.junit.Test;

import com.google.common.truth.Truth;

import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.truth.Truth.assertThat;

/**
 *
 */
public class ConcurrentHashMapTest {

    @Test(expected = NullPointerException.class)
    public void testAddNull() {
        final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put(null, 1);
    }
}
