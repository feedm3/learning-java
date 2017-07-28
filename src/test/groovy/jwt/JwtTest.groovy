package jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import spock.lang.Specification

import static com.google.common.truth.Truth.assertThat


/**
 * This class is used to test the auth0 java jwt implementation.
 *
 * Tip: When adding a custom payload it's best practice to use IANA keys first (https://www.iana.org/assignments/jwt/jwt.xhtml)
 */
class JwtTest extends Specification {

    final Algorithm algorithm = Algorithm.HMAC256("secret");

    def "create a token without payload"() {
        when:
        String token = JWT.create()
                .sign(algorithm)

        then:
        assertThat(token).isEqualTo(token)
    }

    def "verify a token"() {
        given:
        String token = JWT.create().sign(algorithm)

        when: "we use the correct algorythm with the correct secret"
        JWTVerifier verifier = JWT.require(algorithm).build()

        then: "verification succeeds as there is no exception"
        verifier.verify(token)

        when: "we use the wrong secret"
        JWT.require(Algorithm.HMAC256("daw")).build().verify(token)

        then: "an exception will be thrown"
        thrown JWTVerificationException
    }

    def "decode a token with empty payload"() {
        given:
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e30.DMCAvRgzrcf5w0Z879BsqzcrnDFKBY_GN6c3qKOUFtQ" // payload "e30" is "{}"

        when:
        DecodedJWT decodedToken = JWT.decode(token)
        Map claims = decodedToken.getClaims()

        then:
        assertThat(claims).isEmpty()
    }

    def "create and decode a token with payload"() {
        given: "a token with payload"
        String token = JWT.create()
                .withClaim("username","heinrich")
                .sign(algorithm)

        when: "decoding the token and extracting the payload/claims"
        DecodedJWT decodedToken = JWT.decode(token)
        String usernameClaim = decodedToken.getClaim("username").asString()
        Map<String, Claim> allClaims = decodedToken.getClaims();

        then: "we get all values we used for creating the token"
        assertThat(usernameClaim).isEqualTo("heinrich")
        assertThat(allClaims).hasSize(1)
        assertThat(allClaims.get("username").asString()).isEqualTo(usernameClaim)
    }

    def "access a token thats not existing"() {
        given:
        String token = JWT.create()
                .withClaim("username","heinrich")
                .sign(algorithm)

        when:
        DecodedJWT decodedToken = JWT.decode(token)
        String claim = decodedToken.getClaim("empty").asString()

        then:
        assertThat(claim).isNull()
    }
}
