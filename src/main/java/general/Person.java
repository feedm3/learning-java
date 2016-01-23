package general;

/**
 * This class is used as POJO which can be cloned.
 *
 * @author Fabian Dietenberger
 */
public class Person implements Cloneable {

    private String name;
    private Person child;

    public Person(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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
}
