package com.MoodMusicApplicaiton.java;

import com.moodmusic.config.SpotifyConfig;
import com.service.SpotifyAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@SpringBootApplication
@ComponentScan(basePackages = {"com.moodmusic.config", "com.service"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RestController
    class SpotifyController {
        @Autowired
        private SpotifyAuth spotifyAuth;
        @Autowired
        private SpotifyConfig spotifyConfig;

        @GetMapping("/")
        public String Entry(){
            try{
                spotifyAuth.getRequest();
            }
            catch(Exception e){
                System.out.println("Error: " + e);
            }
            return "Success!";
        }

        @GetMapping("/logout")
        public String LogOut(){
            spotifyAuth.setTokenID(null);
            return "Logged out!";
        }
        @GetMapping("/testing")
        public String testClientCredentials(){
            String token = spotifyAuth.getTokenID();
            String authResponse = spotifyAuth.getAuth(token);
            return "Access Token : " +  token + "\n" + " ClientID: " + spotifyConfig.getClientId() + "\n" +  " ClientSecret: " + spotifyConfig.getClientSecret() + "\n" + "token: " + authResponse;
        }

        @GetMapping("/user")
        public Map<String, Object> user(OAuth2AuthenticationToken token) {
            return token.getPrincipal().getAttributes();
        }

        @GetMapping("/revoke")
        public void revoke() {
            spotifyAuth.revokeToken(spotifyAuth.getTokenID());
        }

    }
}
