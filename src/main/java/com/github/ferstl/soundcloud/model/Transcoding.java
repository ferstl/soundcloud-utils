package com.github.ferstl.soundcloud.model;

import java.net.URL;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Transcoding {

  private final URL url;
  private final long duration;
  private final String quality;
  private final String preset;
  private final Format format;

  @JsonCreator
  public Transcoding(URL url, long duration, String quality, String preset, Format format) {
    this.url = url;
    this.duration = duration;
    this.quality = quality;
    this.preset = preset;
    this.format = format;
  }

  public URL getUrl() {
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
