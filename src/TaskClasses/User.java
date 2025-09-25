package TaskClasses;
public class User {
    private String name;

    //constructors
    public User() { //default
        name = "";
    }

    public User(String name) {
        this.name = name;
    }

    public User(User user) { //copy
        this.name = user.name;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    //getter
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
