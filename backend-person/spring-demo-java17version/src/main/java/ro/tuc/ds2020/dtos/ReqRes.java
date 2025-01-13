package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.enums.UserRole;

import java.util.UUID;

public class ReqRes {

    private String token;
    private UUID id;
    private String name;
    private String username;
    private UserRole userRole;

    public ReqRes(String token, UUID id, String name, String username, UserRole userRole) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.username = username;
        this.userRole = userRole;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}