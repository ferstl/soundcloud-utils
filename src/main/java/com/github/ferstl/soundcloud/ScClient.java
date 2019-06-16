package com.github.ferstl.soundcloud;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

public class ScClient {

  private static final String SC_API_V2 = "https://api-v2.soundcloud.com";

  private final RestOperations restOperations;
  private final String clientId;
  private final String oAuthToken;


  public ScClient(RestOperations restOperations, String clientId, String oAuthToken) {
    this.restOperations = restOperations;
    this.clientId = clientId;
    this.oAuthToken = oAuthToken;
  }

  public ResponseEntity<String> resolve(String scUrl) {
    URI uri = apiV2Builder()
        .path("/resolve")
        .queryParam("url", scUrl)
        .queryParam("client_id", this.clientId)
        .build()
        .toUri();

    return this.restOperations.getForEntity(uri, String.class);
  }

  private static UriComponentsBuilder apiV2Builder() {
    return UriComponentsBuilder.fromHttpUrl(SC_API_V2);
  }
}
