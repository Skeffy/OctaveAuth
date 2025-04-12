package io.github.skeffy.octave.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDto {

    private String token;
    private String refresh;
    private User user;

    public LoginResponseDto(String token, String refresh, User user) {
        this.token = token;
        this.refresh = refresh;
        this.user = user;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("refresh")
    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
