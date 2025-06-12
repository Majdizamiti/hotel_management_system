public final class Client {
    public final String name;
    public final String email;

    public Client(String name, String email) {
        if (name == null || email == null) throw new NullPointerException();
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}