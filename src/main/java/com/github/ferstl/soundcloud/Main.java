package com.github.ferstl.soundcloud;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.ferstl.soundcloud.model.TrackInfo;

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
    System.out.println(result);
  }

  static ObjectMapper createObjectMapper() {
    return new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
