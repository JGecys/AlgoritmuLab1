package search;

public class Student {

    private String name;
    private String lastname;

    public Student(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return lastname.equals(student.lastname);

    }

    @Override
    public int hashCode() {
        return lastname.hashCode();
    }

    @Override
    public String toString() {
        return name + " " + lastname;
    }
}
