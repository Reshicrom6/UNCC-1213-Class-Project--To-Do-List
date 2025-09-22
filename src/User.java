public class User {
    private String name;

    public User() {
        name = "";
    }

    public User(String name) {
        this.name = name;
    }

    public User(User user) {
        this.name = user.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
