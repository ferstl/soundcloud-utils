package com.github.ferstl.soundcloud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {

  // Hard coded client_id in https://a-v2.sndcdn.com/assets/app-8250257-9bad066-3.js
  private static final String CLIENT_ID = "aGG0HvvLMMohiFsNq85gvca9MB6wAxHP";

  public static void main(String[] args) {
    String oAuthToken = args[0];
    String scUrl = args[1];


    RestTemplate restTemplate = new RestTemplate();
    ScClient scClient = new ScClient(restTemplate, CLIENT_ID, oAuthToken);

    ResponseEntity<String> result = scClient.resolve(scUrl);
    System.out.println(result);
  }
}
