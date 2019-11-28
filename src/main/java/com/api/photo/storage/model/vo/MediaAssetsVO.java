package com.api.photo.storage.model.vo;

import lombok.Data;

import java.io.InputStream;

@Data
public class MediaAssetsVO {

    private InputStream stream;
    private int width;
    private int height;
    private String contentType;
    private long size;
}
