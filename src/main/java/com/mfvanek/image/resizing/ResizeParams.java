package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
import lombok.Getter;

@Getter
public class ResizeParams {

    private final String pathToFile;
    private final int width;
    private final int height;
    private final ResizeType algorithm;

    public ResizeParams(String pathToFile, int width, int height, ResizeType algorithm) {
        this.pathToFile = pathToFile;
        this.width = width;
        this.height = height;
        this.algorithm = algorithm;
    }
}
