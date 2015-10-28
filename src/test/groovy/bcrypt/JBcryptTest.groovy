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

    def "password and hash must be validated true"() {
        given: "a password"
        def password = 'testpassword'

        when: "we hash the password"
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))

        then: "we can validate the password against this hash"
        BCrypt.checkpw(password, hash)
        !BCrypt.checkpw("wrong password", hash)
    }
}