/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.interfaces;

import java.net.URI;
import java.net.URISyntaxException;

public interface ImageParams extends Dimensional {

    boolean isSimilarToURL();

    String getExtension();

    URI toURI() throws URISyntaxException;

    String getOutputName();

    boolean isConvertToGrayscale();
}
