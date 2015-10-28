package bcrypt

import org.mindrot.jbcrypt.BCrypt
import spock.lang.Specification


/**
 * @author Fabian Dietenberger
 */
class JBcryptTest extends Specification {

    def "create a new hash"() {
        setup:
        def password = 'testpassword'

        when:
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))

        then:
        !hash.isEmpty()
        !hash.equals(password)
        (hash.length() == 59 || hash.length() == 60)
    }

    def "password and hash must be validated true"() {
        setup:
        def password = 'testpassword'
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))

        when:
        def isPasswordCorrect = BCrypt.checkpw(password, hash)

        then:
        isPasswordCorrect
    }

    def "different password and hash must be validated false"() {
        setup:
        def password = 'testpassword'
        def hash = BCrypt.hashpw(password, BCrypt.gensalt(9))
        def differentPassword = 'wrong'

        when:
        def isPasswordCorrect = BCrypt.checkpw(differentPassword, hash)

        then:
        !isPasswordCorrect
    }
}