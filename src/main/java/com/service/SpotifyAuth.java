package com.service;

import com.moodmusic.config.SpotifyConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class SpotifyAuth {
    private String tokenID;
    private long tokenExpTime;

    @Autowired
    private SpotifyConfig spotifyConfig;
    JSONObject accessToken = new JSONObject();


    public String getTokenID() {
        if (tokenID == null) {
            tokenID = getRequest();
        }
        return tokenID;
    }


    public String getAuth(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(spotifyConfig.getClientId(), spotifyConfig.getClientSecret());

        // Set body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("redirect_uri", "http://localhost:8080/login/oauth2/code/spotify");  // Replace with your actual redirect URI

        // Create HttpEntity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://accounts.spotify.com/api/token", requestEntity, String.class);

        // Parse the response
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        this.tokenID = jsonObject.getString("access_token");
        this.tokenExpTime = jsonObject.getLong("expires_in");
        return responseEntity.getBody();
    }

    public String getRequest() {
        RestTemplate restTemplate = new RestTemplate();

        // Define the request headers according to the Spotify API documentation
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(spotifyConfig.getClientId(), spotifyConfig.getClientSecret());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://accounts.spotify.com/api/token", requestEntity, String.class);

        // Parse the response to get the token ID and expiration time
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        tokenID = jsonObject.getString("access_token");
        tokenExpTime = jsonObject.getLong("expires_in");

        System.out.println(responseEntity.getBody());
        return tokenID;
    }

    public void revokeToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String spotifyRevokeUrl = "https://accounts.spotify.com/api/revoke";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(spotifyConfig.getClientId(), spotifyConfig.getClientSecret());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        restTemplate.postForEntity(spotifyRevokeUrl, request, String.class);
    }

    public void setTokenID(String TokenID) {
        this.tokenID = TokenID;
    }


    public long getTokenExpTime() {
        return tokenExpTime;
    }

    public long setTokenExpirationTime(long TokenExpirationTime) {
        return this.tokenExpTime = TokenExpirationTime;
    }
}
