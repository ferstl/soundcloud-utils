package com.github.ferstl.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Transcoding {

  private final String url;
  private final long duration;
  private final String quality;
  private final String preset;
  private final Format format;

  @JsonCreator
  public Transcoding(String url, long duration, String quality, String preset, Format format) {
    this.url = url;
    this.duration = duration;
    this.quality = quality;
    this.preset = preset;
    this.format = format;
  }

  public String getUrl() {
    return this.url;
  }

  public long getDuration() {
    return this.duration;
  }

  public String getQuality() {
    return this.quality;
  }

  public String getPreset() {
    return this.preset;
  }

  public Format getFormat() {
    return this.format;
  }
}
