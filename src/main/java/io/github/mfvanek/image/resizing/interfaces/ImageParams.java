/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.interfaces;

import io.github.mfvanek.image.resizing.enums.ResizeType;

import java.net.URI;
import java.net.URISyntaxException;

public interface ImageParams extends Dimensional {

    boolean isSimilarToURL();

    String getExtension();

    URI toURI() throws URISyntaxException;

    String getOutputName();

    boolean isConvertToGrayscale();

    ResizeType getAlgorithm();

    String getPathToFile();
}
