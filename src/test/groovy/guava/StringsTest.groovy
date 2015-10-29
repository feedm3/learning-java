package guava

import com.google.common.base.Strings
import spock.lang.Specification


/**
 * This class is used as test for guava strings.
 *
 * https://github.com/google/guava/wiki/StringsExplained
 *
 * @author Fabian Dietenberger
 */
class StringsTest extends Specification {

    def "convert nulls to empty Strings"() {
        expect:
        Strings.nullToEmpty(null).equals("")
        Strings.nullToEmpty("test").equals("test")
    }

    def "check if String is null or empty" () {
        expect:
        Strings.isNullOrEmpty(null)
        Strings.isNullOrEmpty("")
        Strings.isNullOrEmpty(new String())
        !Strings.isNullOrEmpty("test")
    }
}