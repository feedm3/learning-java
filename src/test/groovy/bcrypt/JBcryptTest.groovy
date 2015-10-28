package bcrypt

import org.mindrot.jbcrypt.BCrypt
import spock.lang.Specification


/**
 * This class is used as test for the jBcrypt library.
 *
 * @author Fabian Dietenberger
 */
class JBcryptTest extends Specification {

    def "create a new hash"() {
        given: "a password"
        def password = 'testpassword'

        when: "we hash the password"
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))

        then: "we get a hash"
        !hash.isEmpty()
        !hash.equals(password)
        (hash.length() == 59 || hash.length() == 60)
    }

    def "validate password and hash"() {
        given: "a password"
        def password = 'testpassword'

        when: "we hash the password"
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))

        then: "we get 'true' for the correct password"
        BCrypt.checkpw(password, hash)

        and: "we get 'false' for the incorrect password"
        !BCrypt.checkpw("wrong password", hash)
    }
}