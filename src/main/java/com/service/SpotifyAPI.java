package com.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpotifyAPI {

    // Fetch artists
    // https://api.spotify.com/v1/search?q=artist%3A%22%22&type=artist&limit=1
    public void FetchArtist(String accessToken, String artistName){
        Fetch(accessToken, "https://api.spotify.com/v1/search?q=artist%3A%22%22&type=artist&limit=1", "Taylor Swift");
    }


    // Fetch albums
    // https://api.spotify.com/v1/artists/0OdUWJ0sBjDrqHygGUXeCF/albums?include_groups=album&market=ES&limit=1
    public void FetchAlbum(String accessToken, String albumName){

    }

    // Fetch tracks
    // https://api.spotify.com/v1/albums/6akEvsycLGftJxYudPjmqK/tracks?market=ES&limit=1

    public void FetchTrack(String accessToken, String trackName){

    }

    private void Fetch(String accessToken, String url, String thisIsAPlaceholder){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
