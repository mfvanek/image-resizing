/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.enums.ResizeType;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
public class ResizeParams {

    private static final Logger logger = LoggerFactory.getLogger(ResizeParams.class);

    private static class LazyHolder {

        static final Set<String> SUPPORTED_FORMATS = Arrays.stream(ImageIO.getWriterFormatNames())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private final String pathToFile;
    private final String pathToFileLowercased;
    private final int width;
    private final int height;
    private final ResizeType algorithm;

    public ResizeParams(String pathToFile, int width, int height, ResizeType algorithm) {
        logger.debug("Constructing ResizeParams with: pathToFile = {}, width = {}, height = {}, algorithm = {}", pathToFile, width, height, algorithm);
        validatePath(pathToFile);
        validateWidth(width);
        validateHeight(height);

        this.pathToFile = pathToFile;
        this.pathToFileLowercased = pathToFile.toLowerCase();
        this.width = width;
        this.height = height;
        this.algorithm = algorithm;
    }

    public static ResizeParams newWithRaw(String pathToFile, int width, int height) {
        return new ResizeParams(pathToFile, width, height, ResizeType.RAW);
    }

    public static ResizeParams newWithDefaultDimension(String pathToFile) {
        return newWithRaw(pathToFile, 500, 500);
    }

    public boolean isSimilarToURL() {
        return pathToFileLowercased.startsWith("http");
    }

    public String getExtension() {
        return FilenameUtils.getExtension(pathToFileLowercased);
    }

    public URI toURI() throws URISyntaxException {
        return new URI(pathToFile);
    }

    public String getOutputName() {
        return "resized_" + FilenameUtils.getName(pathToFile);
    }

    public boolean isFormatSupported() {
        return LazyHolder.SUPPORTED_FORMATS.contains(getExtension());
    }

    public boolean isFormatNotSupported() {
        return !isFormatSupported();
    }

    private static void validatePath(String pathToFile) {
        if (pathToFile == null) {
            throw new IllegalArgumentException("Path to image cannot be null");
        }

        if (pathToFile.length() == 0) {
            throw new IllegalArgumentException("Path to image cannot be empty");
        }

        if (!(pathToFile.startsWith("file") || pathToFile.startsWith("http"))) {
            throw new IllegalArgumentException("Path to image should starts with 'file' or 'http'");
        }
    }

    private static void validateWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("Width should be positive");
        }
    }

    private static void validateHeight(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("Height should be positive");
        }
    }
}
