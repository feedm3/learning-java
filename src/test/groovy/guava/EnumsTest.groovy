package guava

import com.google.common.base.Enums
import spock.lang.Specification



/**
 * Tests for the Enums helper class.
 *
 * http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/base/Enums.html
 *
 * @author Fabian Dietenberger
 */
class EnumsTest extends Specification {

    enum State {
        OK,
        ERROR
    }

    def "Convert strings to enums"() {
        expect:
        Enums.getIfPresent(State.class, "OK").orNull() == State.OK
        Enums.getIfPresent(State.class, "Nope").orNull() == null
        Enums.getIfPresent(State.class, "Nope").or(State.ERROR) == State.ERROR
    }
}