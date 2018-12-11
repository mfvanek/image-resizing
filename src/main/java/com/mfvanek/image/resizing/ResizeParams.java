package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
import lombok.Getter;

@Getter
public class ResizeParams {

    private final String pathToFile;
    private final int width;
    private final int height;
    private final ResizeType algorithm;
    private final boolean isUrl;

    public ResizeParams(String pathToFile, int width, int height, ResizeType algorithm, boolean isUrl) {
        this.pathToFile = pathToFile;
        this.width = width;
        this.height = height;
        this.algorithm = algorithm;
        this.isUrl = isUrl;
    }
}
