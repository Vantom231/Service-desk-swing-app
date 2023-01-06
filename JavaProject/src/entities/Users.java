package entities;

import javax.persistence.*;

@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "account_lvl", nullable = false)
    private int accountLvl;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "status", nullable = false)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountLvl() {
        return accountLvl;
    }

    public void setAccountLvl(int accountLvl) {
        this.accountLvl = accountLvl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountLvl=" + accountLvl +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
