package com.mfvanek.image.resizing.utils;

import com.mfvanek.image.resizing.pojos.ResizeParams;
import com.mfvanek.image.resizing.enums.ResizeType;

public class ParamsValidator {

    private static final int EXPECTED_COUNT = 3;

    private final String[] args;
    private boolean canUseDefault;
    private String pathToFile = "file:///C:/src/image-resizing/src/main/resources/java-logo.jpeg"; // default value TODO relative path
    private int width;
    private int height;
    private ResizeType algorithm = ResizeType.RAW;

    private ParamsValidator(String[] args) {
        this.args = args;
        this.canUseDefault = false;
    }

    public static ParamsValidator getInstance(String[] args) {
        return new ParamsValidator(args);
    }

    public ParamsValidator useDefault() {
        this.canUseDefault = true;
        return this;
    }

    public ParamsValidator withAlgorithm(ResizeType algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public ParamsValidator withPath(String pathToFile) {
        this.pathToFile = pathToFile;
        return this;
    }

    public ResizeParams validate() {
        if (args == null) {
            throw new IllegalArgumentException("Parameter 'args' cannot be null");
        }

        if (args.length == EXPECTED_COUNT) {
            pathToFile = args[0];
            validatePath();
            width = Integer.parseInt(args[1], 10);
            validateWidth();
            height = Integer.parseInt(args[2], 10);
            validateHeight();
        } else {
            if (args.length == 0 && this.canUseDefault) {
                width = 200;
                height = 100;
            } else {
                final String error = String.format("Invalid number of arguments; should be %d arguments: path-to-image, width and height", EXPECTED_COUNT);
                throw new IllegalArgumentException(error);
            }
        }

        return new ResizeParams(pathToFile, width, height, algorithm);
    }

    private void validatePath() {
        if (pathToFile.length() == 0) {
            throw new IllegalArgumentException("Path to image cannot be empty");
        }

        if (!(pathToFile.startsWith("file") || pathToFile.startsWith("http"))) {
            throw new IllegalArgumentException("Path to image should starts with 'file' or 'http'");
        }
    }

    private void validateWidth() {
        if (width < 1) {
            throw new IllegalArgumentException("Width should be positive");
        }
    }

    private void validateHeight() {
        if (height < 1) {
            throw new IllegalArgumentException("Height should be positive");
        }
    }
}
