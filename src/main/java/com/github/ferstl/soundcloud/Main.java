package com.github.ferstl.soundcloud;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.ferstl.soundcloud.model.Location;
import com.github.ferstl.soundcloud.model.TrackInfo;
import com.github.ferstl.soundcloud.model.Transcoding;
import static java.util.stream.Collectors.joining;

public class Main {

  // Hard coded client_id in https://a-v2.sndcdn.com/assets/app-8250257-9bad066-3.js
  private static final String CLIENT_ID = "aGG0HvvLMMohiFsNq85gvca9MB6wAxHP";

  public static void main(String[] args) {
    String oAuthToken = args[0];
    String scUrl = args[1];


    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = createObjectMapper();

    HttpMessageConverter<Object> jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
    restTemplate.getMessageConverters().add(jsonConverter);

    ScClient scClient = new ScClient(restTemplate, CLIENT_ID, oAuthToken);

    ResponseEntity<TrackInfo> result = scClient.resolve(scUrl);

    TrackInfo trackInfo = result.getBody();

    List<Transcoding> progressiveTranscodings = trackInfo.getMedia().getTranscodings().stream()
        .filter(transcoding -> "progressive".equals(transcoding.getFormat().getProtocol()))
        .collect(Collectors.toList());

    Transcoding transcoding;
    if (progressiveTranscodings.size() == 0) {
      throw new IllegalStateException("No progressive transcoding found");
    } else if (progressiveTranscodings.size() == 1) {
      transcoding = progressiveTranscodings.get(0);
    } else {
      transcoding = findHighQualityTranscoding(progressiveTranscodings)
          .orElseThrow(() -> new IllegalStateException("Found more than one standard quality progressive transcoding"));
    }

    String url = transcoding.getUrl();
    ResponseEntity<Location> downloadLocation = scClient.getAssetLocation(url);

    String downloadUrl = downloadLocation.getBody().getUrl();
    Path destination = Paths.get(".");

    System.out.println("Downloading: " + downloadUrl + " to " + destination.toAbsolutePath());

    Path file = scClient.download(downloadUrl, destination);

    System.out.println("Downloaded to " + file.toAbsolutePath());
  }

  private static Optional<Transcoding> findHighQualityTranscoding(List<Transcoding> progressiveTranscodings) {
    List<Transcoding> hqTranscodings = progressiveTranscodings.stream()
        .filter(transcoding -> "hq".equals(transcoding.getQuality()))
        .collect(Collectors.toList());

    if (hqTranscodings.size() == 0) {
      return Optional.empty();
    } else if (hqTranscodings.size() == 1) {
      return Optional.of(hqTranscodings.get(0));
    } else {
      String presets = hqTranscodings.stream()
          .map(Transcoding::getPreset)
          .collect(joining(", "));
      throw new IllegalStateException("Found more than one high quality progressive transcoding: " + presets);
    }

  }

  static ObjectMapper createObjectMapper() {
    return new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
