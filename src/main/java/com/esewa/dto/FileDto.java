package com.esewa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class FileDto implements Serializable {

    @JsonProperty("content_type")
    private String contentType;
    private String name;
    private byte[] data;
}
