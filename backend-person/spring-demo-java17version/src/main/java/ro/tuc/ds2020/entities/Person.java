package ro.tuc.ds2020.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import ro.tuc.ds2020.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Person  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "password", nullable = false)
    private String pass;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    public Person() {
    }

    public Person(String name, String username, String address, String pass, int age, UserRole userRole) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.pass = pass;
        this.age = age;
        this.userRole = userRole;
    }

    public Person(UUID id, String name, String username, String address, String pass, int age, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.pass = pass;
        this.age = age;
        this.userRole = userRole;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
