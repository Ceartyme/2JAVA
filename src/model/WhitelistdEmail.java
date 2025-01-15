package model;

public class WhitelistdEmail {
    private String email;

    public WhitelistdEmail() {}
    public WhitelistdEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "WhitelistdEmail{" +
                "email='" + email + '\'' +
                '}';
    }
}
