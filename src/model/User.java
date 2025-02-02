package model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private int idUser;
    private String email;
    private String password;
    private String username;
    private Role role;

    public User(){}

    public User(int idUser, String email, String password, String username, int roleId){
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.username = username;
        switch (roleId){
            case 1:
                this.role=Role.ADMIN;
                break;
            case 2:
                this.role=Role.EMPLOYEE;
                break;
            case 3:
                this.role=Role.USER;
                break;
        }
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

    public void setPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public int getRoleId() {
        switch (role) {
            case ADMIN:
                return 1;
            case EMPLOYEE:
                return 2;
            case USER:
                return 3;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }


    public void setRole(int role) {
        switch (role){
            case 1:
                this.role=Role.ADMIN;
                break;
            case 2:
                this.role=Role.EMPLOYEE;
                break;
            case 3:
                this.role=Role.USER;
                break;
        }
    }

    public void setRole(Role role){
        this.role=role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", Role='" + role + '\'' +
                '}';
    }
}
