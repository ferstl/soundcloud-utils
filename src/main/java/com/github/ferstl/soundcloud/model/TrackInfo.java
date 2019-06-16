package com.github.ferstl.soundcloud.model;

import java.time.OffsetDateTime;

public class TrackInfo {

  private final String title;
  private final String id;
  private final OffsetDateTime createdAt;
  private final String description;
  private final long duration;
  private final Media media;

  public TrackInfo(String title, String id, OffsetDateTime createdAt, String description, long duration, Media media) {
    this.title = title;
    this.id = id;
    this.createdAt = createdAt;
    this.description = description;
    this.duration = duration;
    this.media = media;
  }

  public String getTitle() {
    return this.title;
  }

  public String getId() {
    return this.id;
  }

  public OffsetDateTime getCreatedAt() {
    return this.createdAt;
  }

  public String getDescription() {
    return this.description;
  }

  public long getDuration() {
    return this.duration;
  }

  public Media getMedia() {
    return this.media;
  }
}
