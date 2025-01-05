package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.enums.UserRole;

import java.util.Objects;
import java.util.UUID;

public class PersonDTO {
    private UUID id;
    private String name;
    private String username;
    private String address;
    private int age;
    private UserRole userRole;

    public PersonDTO() {
    }

    public PersonDTO(UUID id, String name, String username, String address, int age, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        PersonDTO personDTO = (PersonDTO) o;
        return age == personDTO.age &&
                Objects.equals(name, personDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
