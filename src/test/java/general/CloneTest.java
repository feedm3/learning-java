package general;

import general.util.Person;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class is used to test the java clone method.
 *
 * @author Fabian Dietenberger
 */
public class CloneTest {

    @Test
    public void testCloneObjectFlat() {
        final Person homer = new Person("Homer");
        final Person homerClone = homer.clone();

        assertThat(homer).isNotSameAs(homerClone);
        assertThat(homer.getName()).isEqualTo(homerClone.getName()); // same value
        assertThat(homer.getName()).isSameAs(homerClone.getName()); // same object reference
    }

    @Test
    public void testCloneObjectChangeFlatValues() {
        final Person homer = new Person("Homer");
        final Person homerClone = homer.clone();

        homerClone.setName("HomerClone");

        assertThat(homer.getName()).isEqualTo("Homer");
        assertThat(homerClone.getName()).isEqualTo("HomerClone");
    }

    @Test
    public void testCloneObjectAndChangeNestedValues() {
        final Person homer = new Person("Homer");
        homer.setChild(new Person("Bart"));
        final Person homerClone = homer.clone();

        // if we clone an object the nested objects still share the same objects!
        assertThat(homer).isNotSameAs(homerClone);
        assertThat(homer.getChild()).isSameAs(homerClone.getChild());

        homerClone.getChild().setName("Lisa");

        assertThat(homer.getChild().getName()).isEqualTo("Lisa");
        assertThat(homerClone.getChild().getName()).isEqualTo("Lisa");
    }
}
