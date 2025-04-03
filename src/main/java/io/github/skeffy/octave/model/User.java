package io.github.skeffy.octave.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Validated
public class User {

    @NotNull
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String bio;
    @NotNull
    private Set<Authority> authorities = new HashSet<>();

    public User(String id, String username, String name, String email, String bio, String authorities) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.bio = bio;
        if(authorities != null) this.setAuthorities(authorities);
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public String getAuthoritiesString() {
        String authString = "";
        for (Authority auth : authorities) {
            if (authString.length() == 0) {
                authString += auth.getName();
            } else {
                authString += "," + auth.getName();
            }
        }
        return authString;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(String authority) {
        this.authorities.add(new Authority(authority));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(bio, user.bio) &&
                Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
