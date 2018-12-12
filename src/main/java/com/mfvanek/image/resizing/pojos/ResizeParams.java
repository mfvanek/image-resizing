package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.enums.ResizeType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResizeParams {

    private final String pathToFile;
    private final String pathToFileLower;
    private final int width;
    private final int height;
    private final ResizeType algorithm;

    public ResizeParams(String pathToFile, int width, int height, ResizeType algorithm) {
        this.pathToFile = pathToFile;
        this.pathToFileLower = pathToFile.toLowerCase();
        this.width = width;
        this.height = height;
        this.algorithm = algorithm;
    }

    public boolean isUrl() {
        return pathToFileLower.startsWith("http");
    }

    public String getExtension() {
        return pathToFileLower.substring(pathToFileLower.lastIndexOf(".") + 1);
    }
}
