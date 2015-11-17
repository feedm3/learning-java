package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used as test for the java 8 stream api.
 *
 * @author Fabian Dietenberger
 */
public class StreamsTest {

    @Test
    public void filterList() {
        final List<String> urls = new ArrayList<>();
        urls.add("https://google.de");
        urls.add("https://facebook.de");
        urls.add("https://unhypem.com");
        urls.add("http://uber.com");

        final List<String> httpsUrls = urls.stream()
                .filter(url -> url.startsWith("https"))
                .map(url -> url)
                .collect(Collectors.toList());

        assertThat(httpsUrls.size()).isEqualTo(3);
        assertThat(httpsUrls).doesNotContain("https://uber.com");
    }

    @Test
    public void filterMap() {
        final Map<String, Integer> characterLevels = new HashMap<>();
        characterLevels.put("Fabi", 10);
        characterLevels.put("Dave", 1);
        characterLevels.put("Raphy", 2);
        characterLevels.put("Done", 8);

        final List<String> bestCharacters = characterLevels
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertThat(bestCharacters.size()).isEqualTo(2);
        assertThat(bestCharacters).containsAllOf("Fabi", "Done");
    }
}
