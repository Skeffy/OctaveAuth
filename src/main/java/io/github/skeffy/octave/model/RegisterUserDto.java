package io.github.skeffy.octave.model;

import jakarta.validation.constraints.NotEmpty;

public class RegisterUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;

    public RegisterUserDto(String username, String email, String name) {
        this.username = username;
        this.email = email;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
