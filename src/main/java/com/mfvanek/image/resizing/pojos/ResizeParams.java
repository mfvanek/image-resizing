/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.Dimensional;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
@ToString
public class ResizeParams implements Dimensional {

    private static final Logger logger = LoggerFactory.getLogger(ResizeParams.class);

    private final String pathToFile;
    private final String pathToFileLowercased;
    private final Dimension dimension;
    private final ResizeType algorithm;

    private ResizeParams(String pathToFile, Dimension dimension, ResizeType algorithm) {
        logger.debug("Constructing ResizeParams with: pathToFile = {}, dimension = {}, algorithm = {}", pathToFile, dimension, algorithm);
        validatePath(pathToFile);

        this.pathToFile = pathToFile;
        this.pathToFileLowercased = pathToFile.toLowerCase();
        this.dimension = dimension;
        this.algorithm = algorithm;
    }

    public static ResizeParams newWithAlgorithm(String pathToFile, int width, int height, ResizeType algorithm) {
        return new ResizeParams(pathToFile, new Dimension(width, height), algorithm);
    }

    public static ResizeParams newWithRaw(String pathToFile, int width, int height) {
        return newWithAlgorithm(pathToFile, width, height, ResizeType.RAW);
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

    @Override
    public int getWidth() {
        return dimension.getWidth();
    }

    @Override
    public int getHeight() {
        return dimension.getHeight();
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
}
