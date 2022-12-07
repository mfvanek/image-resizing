/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.pojos;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageParams;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Getter
@ToString
public class ResizeParams implements ImageParams {

    private final String pathToFile;
    private final String pathToFileLowercased;
    private final Dimension dimension;
    private final ResizeType algorithm;
    private final boolean convertToGrayscale;

    private ResizeParams(String pathToFile, Dimension dimension, ResizeType algorithm, boolean convertToGrayscale) {
        log.debug("Constructing ResizeParams with: pathToFile = {}, dimension = {}, algorithm = {}, convertToGrayscale = {}",
                pathToFile, dimension, algorithm, convertToGrayscale);
        validatePath(pathToFile);

        this.pathToFile = pathToFile;
        this.pathToFileLowercased = pathToFile.toLowerCase(Locale.ENGLISH);
        this.dimension = dimension;
        this.algorithm = algorithm;
        this.convertToGrayscale = convertToGrayscale;
    }

    public static ResizeParams newWithAlgorithm(String pathToFile, int width, int height, ResizeType algorithm) {
        return new ResizeParams(pathToFile, new Dimension(width, height), algorithm, true);
    }

    public static ResizeParams newWithRaw(String pathToFile, int width, int height) {
        return newWithAlgorithm(pathToFile, width, height, ResizeType.RAW);
    }

    public static ResizeParams newWithDefaultDimension(String pathToFile) {
        return newWithRaw(pathToFile, 500, 500);
    }

    @Override
    public String getPathToFile() {
        return pathToFile;
    }

    @Override
    public boolean isSimilarToURL() {
        return pathToFileLowercased.startsWith("http");
    }

    @Override
    public String getExtension() {
        return FilenameUtils.getExtension(pathToFileLowercased);
    }

    @Override
    public URI toURI() throws URISyntaxException {
        return new URI(pathToFile);
    }

    @Override
    public String getOutputName() {
        return "resized_" + FilenameUtils.getName(pathToFile);
    }

    @Override
    public boolean isConvertToGrayscale() {
        return convertToGrayscale;
    }

    @Override
    public int getWidth() {
        return dimension.getWidth();
    }

    @Override
    public int getHeight() {
        return dimension.getHeight();
    }

    @Override
    public ResizeType getAlgorithm() {
        return algorithm;
    }

    private static void validatePath(String pathToFile) {
        Objects.requireNonNull(pathToFile, "Path to image cannot be null");

        if (pathToFile.length() == 0) {
            throw new IllegalArgumentException("Path to image cannot be empty");
        }

        if (!(pathToFile.startsWith("file") || pathToFile.startsWith("http"))) {
            throw new IllegalArgumentException("Path to image should starts with 'file' or 'http'");
        }
    }

    public static ImageParams from(ImageParams oldParams, Dimension newDimension) {
        return new ResizeParams(oldParams.getPathToFile(), newDimension, oldParams.getAlgorithm(), oldParams.isConvertToGrayscale());
    }
}
