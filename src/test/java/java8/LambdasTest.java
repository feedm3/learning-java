package java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used to make some tests with lambdas.
 *
 * @author Fabian Dietenberger
 */
public class LambdasTest {

    private String myName = "Fabi";

    @Test
    public void testLambdasFromCollections() {
        final List<String> names = Arrays.asList("Done", "Fab", "Klemens");
        Collections.sort(names, (o1, o2) -> Integer.compare(o1.length(), o2.length()));

        assertThat(names.get(0)).isEqualTo("Fab");
    }

    @Test
    public void testLambdaWithVariableReferences() {
        String nonFinalString = "I am not final";

        final Runnable runnable = () -> {
            // it's possible to create new variables inside a lambda
            final String myName = "Peter";
            assertThat(myName).isEqualTo("Peter");

            // you can also access non-final variables
            assertThat(nonFinalString).isNotEmpty();

            // 'this' references to the outer object
            assertThat(this.myName).isEqualTo("Fabi");
        };
        runnable.run();
    }

    @Test
    public void testOwnLambda() {
        // we can implement interfaces with one method as lambda
        final UrlPrefixer httpPrefixer = url -> "http://" + url;

        final String prefixedUrl = httpPrefixer.prefix("google.de");

        assertThat(prefixedUrl).isEqualTo("http://google.de");
    }

    @Test
    public void testOwnLambdaWithDefaultMethod() {
        final UrlPrefixer httpPrefixer = url -> "http://" +  url;

        final String prefixedUrl = httpPrefixer.prefix("google.de");
        final String unPrefixedUrl = httpPrefixer.removeHttpPrefix(prefixedUrl);

        assertThat(unPrefixedUrl).isEqualTo("google.de");
    }

    /**
     * This interface has only one abstract method so it can be used as lambda.
     *
     * The "FunctionalInterface" annotation is optional.
     */
    @FunctionalInterface
    interface UrlPrefixer {

        String prefix(final String url);

        default String removeHttpPrefix(final String url) {
            return url.substring(7, url.length());
        }
    }
}
