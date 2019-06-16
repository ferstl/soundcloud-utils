package com.github.ferstl.soundcloud.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Media {

  private final Transcoding[] transcodings;

  @JsonCreator
  public Media(Transcoding[] transcodings) {
    this.transcodings = transcodings;
  }

  public List<Transcoding> getTranscodings() {
    return List.of(this.transcodings);
  }
}
