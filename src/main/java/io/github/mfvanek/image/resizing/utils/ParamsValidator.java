/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.utils;

import io.github.mfvanek.image.resizing.enums.ResizeType;
import io.github.mfvanek.image.resizing.pojos.ResizeParams;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Slf4j
public final class ParamsValidator {

    private static final int EXPECTED_COUNT = 3;
    private static final URL DEFAULT_URL = getDefaultImage();

    private final String[] args;
    private boolean canUseDefault;
    private String pathToFile;
    private int width = 200;
    private int height = 100;
    private ResizeType algorithm = ResizeType.RAW;

    private ParamsValidator(final String... args) {
        this.args = args;
        this.canUseDefault = false;
        try {
            if (DEFAULT_URL != null) {
                this.pathToFile = DEFAULT_URL.toURI().toString();
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static URL getDefaultImage() {
        final URL defaultURL = Thread.currentThread().getContextClassLoader().getResource("java-logo.jpeg");
        if (defaultURL != null) {
            log.debug("Default image URL = {}", defaultURL);
        } else {
            log.error("Default image is not found!");
        }
        return defaultURL;
    }

    public static ParamsValidator builder(final String... args) {
        return new ParamsValidator(args);
    }

    public ParamsValidator useDefaultIfNeed() {
        this.canUseDefault = true;
        return this;
    }

    public ParamsValidator withAlgorithm(final ResizeType algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public ParamsValidator withPath(final String pathToFile) {
        this.pathToFile = pathToFile;
        return this;
    }

    public ResizeParams validate() {
        Objects.requireNonNull(args, "Parameter 'args' cannot be null");

        if (args.length == EXPECTED_COUNT) {
            pathToFile = args[0];
            width = toDimensionValue(args[1], "width");
            height = toDimensionValue(args[2], "height");
        } else {
            if (!(args.length == 0 && this.canUseDefault)) {
                final String error = String.format("Invalid number of arguments; should be %d arguments: path-to-image, width and height", EXPECTED_COUNT);
                throw new IllegalArgumentException(error);
            }
        }

        return ResizeParams.newWithAlgorithm(pathToFile, width, height, algorithm);
    }

    private static int toDimensionValue(final String value, final String paramName) {
        try {
            return Integer.parseInt(value, 10);
        } catch (NumberFormatException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new IllegalArgumentException(String.format("The %s has an invalid format or value", paramName), e);
        }
    }
}
