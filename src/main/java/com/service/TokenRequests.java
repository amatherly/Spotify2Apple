package com.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenRequests {
    private String tokenID;
    private long tokenExpTime;

    public TokenRequests() {
        this.tokenID = "";
        this.tokenExpTime = 0;
    }

    public String getTokenID() {
        if(tokenID == null){
            getRequest();
        }
          return tokenID;
    }



    private void getRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://accounts.spotify.com/api/token", null, String.class);
        System.out.println(response);
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
