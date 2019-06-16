package com.github.ferstl.soundcloud;

import java.net.URI;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import com.github.ferstl.soundcloud.model.Location;
import com.github.ferstl.soundcloud.model.TrackInfo;

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

  public ResponseEntity<TrackInfo> resolve(String scUrl) {
    URI uri = apiV2Builder()
        .path("/resolve")
        .queryParam("url", scUrl)
        .queryParam("client_id", this.clientId)
        .build()
        .toUri();

    HeadersBuilder<?> builder = RequestEntity.get(uri);
    if (this.oAuthToken != null) {
      builder.header("Authorization", "OAuth " + this.oAuthToken);
    }

    return this.restOperations.exchange(builder.build(), TrackInfo.class);
  }

  public ResponseEntity<Location> getAssetLocation(String url) {
    URI requestUri = UriComponentsBuilder.fromHttpUrl(url)
        .build()
        .toUri();

    HeadersBuilder<?> builder = RequestEntity.get(requestUri);
    if (this.oAuthToken != null) {
      builder.header("Authorization", "OAuth " + this.oAuthToken);
    }

    return this.restOperations.exchange(builder.build(), Location.class);
  }

  private static UriComponentsBuilder apiV2Builder() {
    return UriComponentsBuilder.fromHttpUrl(SC_API_V2);
  }
}
