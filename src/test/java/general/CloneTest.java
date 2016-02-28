package general;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

        // if we clone an object the nested objects are still sharing the same objects!
        assertThat(homer).isNotSameAs(homerClone);
        assertThat(homer.getChild()).isSameAs(homerClone.getChild());

        homerClone.getChild().setName("Lisa");

        assertThat(homer.getChild().getName()).isEqualTo("Lisa");
        assertThat(homerClone.getChild().getName()).isEqualTo("Lisa");
    }
}

/**
 * Simple POJO which can be cloned.
 */
class Person implements Cloneable {

    private String name;
    private int age;
    private Person child;

    public Person(final String name) {
        this.name = name;
    }

    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(final Person child) {
        this.child = child;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Person> createPersons() {
        return Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));
    }
}
