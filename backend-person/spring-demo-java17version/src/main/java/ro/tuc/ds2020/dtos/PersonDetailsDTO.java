package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;
import ro.tuc.ds2020.enums.UserRole;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PersonDetailsDTO {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String address;
    @NotNull
    private String pass;
    @AgeLimit(limit = 18)
    private int age;
    @NotNull
    private UserRole userRole;

    public PersonDetailsDTO() {
    }

    public PersonDetailsDTO( String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public PersonDetailsDTO( String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public PersonDetailsDTO(UUID id, String name, String address, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public PersonDetailsDTO(String name, String username, String address, String pass, int age, UserRole userRole) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.pass = pass;
        this.age = age;
        this.userRole = userRole;
    }

    public PersonDetailsDTO(UUID id, String name, String username, String address, String pass, int age, UserRole userRole) {
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

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDetailsDTO that = (PersonDetailsDTO) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, age);
    }
}
