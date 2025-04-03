package io.github.skeffy.octave.security.googleoauth;

public class GoogleResponseDto {

    private final String azp;
    private final String aud;
    private final String sub;
    private final String scope;
    private final String exp;
    private final String expiresIn;
    private final String email;
    private final boolean emailVerified;
    private final String accessType;

    public GoogleResponseDto(String azp, String aud, String sub, String scope, String exp, String expiresIn, String email, boolean emailVerified, String accessType) {
        this.azp = azp;
        this.aud = aud;
        this.sub = sub;
        this.scope = scope;
        this.exp = exp;
        this.expiresIn = expiresIn;
        this.email = email;
        this.emailVerified = emailVerified;
        this.accessType = accessType;
    }

    public String getAzp() {
        return azp;
    }

    public String getAud() {
        return aud;
    }

    public String getSub() {
        return sub;
    }

    public String getScope() {
        return scope;
    }

    public String getExp() {
        return exp;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getAccessType() {
        return accessType;
    }
}
