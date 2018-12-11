package com.mfvanek.image.resizing;

import lombok.Getter;

@Getter
public class ResizeParams {

    private final String pathToFile;
    private final int width;
    private final int height;

    public ResizeParams(String pathToFile, int width, int height) {
        this.pathToFile = pathToFile;
        this.width = width;
        this.height = height;
    }
}
