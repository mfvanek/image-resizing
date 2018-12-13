/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.utils;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.pojos.ResizeParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;

public class ParamsValidator {

    private static final Logger logger = LoggerFactory.getLogger(ParamsValidator.class);

    private static final int EXPECTED_COUNT = 3;
    private static final URL DEFAULT_URL = getDefaultImage();

    private final String[] args;
    private boolean canUseDefault;
    private String pathToFile;
    private int width = 200;
    private int height = 100;
    private ResizeType algorithm = ResizeType.RAW;

    private ParamsValidator(String[] args) {
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
        final URL defaultURL = ParamsValidator.class.getClassLoader().getResource("java-logo.jpeg");
        if (defaultURL != null) {
            logger.debug("Default image URL = {}", defaultURL);
        } else {
            logger.error("Default image is not found!");
        }
        return defaultURL;
    }

    public static ParamsValidator getInstance(String[] args) {
        return new ParamsValidator(args);
    }

    public ParamsValidator useDefaultIfNeed() {
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
            width = toDimensionValue(args[1], "width");
            height = toDimensionValue(args[2], "height");
        } else {
            if (!(args.length == 0 && this.canUseDefault)) {
                final String error = String.format("Invalid number of arguments; should be %d arguments: path-to-image, width and height", EXPECTED_COUNT);
                throw new IllegalArgumentException(error);
            }
        }

        return new ResizeParams(pathToFile, width, height, algorithm);
    }

    private static int toDimensionValue(String value, String paramName) {
        try {
            return Integer.parseInt(value, 10);
        } catch (NumberFormatException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new IllegalArgumentException(String.format("The %s has an invalid format or value", paramName), e);
        }
    }
}
