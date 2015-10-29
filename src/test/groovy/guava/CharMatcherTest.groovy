package guava

import com.google.common.base.CharMatcher
import spock.lang.Specification


/**
 * This class is used as test for the guava char matcher.
 *
 * https://github.com/google/guava/wiki/StringsExplained#charmatcher
 *
 * @author Fabian Dietenberger
 */
class CharMatcherTest extends Specification {

    def "Remove special characters from a string"() {
        given:
        def input = "H*el'l_.o}12"

        when:
        def result = CharMatcher.JAVA_LETTER.retainFrom(input)

        then:
        result == "Hello"
    }

    def "Only get the numers of a string"() {
        given:
        def input = "He12l34_.*#@fr5321987654315786"

        expect:
        CharMatcher.DIGIT.retainFrom(input) == "12345321987654315786"
        CharMatcher.JAVA_DIGIT.retainFrom(input) == "12345321987654315786"
        CharMatcher.inRange('0' as char, '9' as char).retainFrom(input) == "12345321987654315786"
    }

    def "Remove everything except a given set"() {
        given:
        def input = "+483475/123897 80dfawuizfrg_.#"
        def validValues = "+0123456789"

        expect:
        CharMatcher.anyOf(validValues).retainFrom(input) == "+48347512389780"
    }

    def "Trim characters"() {
        given:
        def input = "---Hallo;;;"
        def inputWhitspace = "Hallo        Heinrich      "

        expect:
        CharMatcher.is('-' as char).trimLeadingFrom(input) == "Hallo;;;"
        CharMatcher.anyOf("-;").trimFrom(input) == "Hallo"
        CharMatcher.is(' ' as char).trimAndCollapseFrom(inputWhitspace, ' ' as char) == "Hallo Heinrich"
    }

    def "Replace characters"() {
        given:
        def input = "wie.geht das"
        def inputFruits = "apple.banana.cherry"

        expect:
        CharMatcher.anyOf(" .").replaceFrom(input, '-') == "wie-geht-das"
        CharMatcher.anyOf(".").replaceFrom(inputFruits, " and ") == "apple and banana and cherry"
    }

    def "Count characters"() {
        given:
        def input = "aabcdefa"

        expect:
        CharMatcher.is('a' as char).countIn(input) == 3
        CharMatcher.anyOf("ab").countIn(input) == 4
        CharMatcher.inRange('a' as char, 'e' as char).countIn(input) == 7
    }
}