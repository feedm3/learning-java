package general;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used as POJO which can be cloned.
 *
 * @author Fabian Dietenberger
 */
public class Person implements Cloneable {

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

    public static List<Person> buildPersons() {
        return Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));
    }
}
