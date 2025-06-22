package io.github.skeffy.octave.security.googleoauth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class GoogleUserService {

    public static String API = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleResponseDto get(String token) {
        GoogleResponseDto user = null;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<GoogleResponseDto> response = restTemplate.exchange(
                API+token,
                HttpMethod.GET,
                entity,
                GoogleResponseDto.class
        );
        user = response.getBody();
        return user;
    }
}
