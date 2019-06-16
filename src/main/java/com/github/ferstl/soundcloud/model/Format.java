package com.github.ferstl.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Format {

  private final String protocol;
  private final String mimeType;

  @JsonCreator
  public Format(String protocol, String mimeType) {
    this.protocol = protocol;
    this.mimeType = mimeType;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public String getMimeType() {
    return this.mimeType;
  }
}
