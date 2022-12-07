/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.interfaces;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public interface GraphicsProvider {

    Set<String> getSupportedFormats();

    boolean isFormatSupported(String imageFormat);

    default boolean isFormatNotSupported(String imageFormat) {
        return !isFormatSupported(imageFormat);
    }

    BufferedImage loadImage(URI uri);

    BufferedImage loadImage(URL url);

    default BufferedImage loadImage(ImageParams params) {
        final BufferedImage img;
        try {
            if (params.isSimilarToURL()) {
                img = loadImage(params.toURI().toURL());
            } else {
                img = loadImage(params.toURI());
            }
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}
