package com.github.ferstl.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

  private final String url;

  // FIXME: 2019-06-16 Why do we need JsonProperty here?
  public Location(@JsonProperty("url") String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
