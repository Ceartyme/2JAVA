package model;

public class User {
    private int idUser;
    private String email;
    private String password;
    private String username;
    private String Role;

    public User(){};

    public User(int idUser, String email, String password, String username, String role){
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.username = username;
        this.Role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}
