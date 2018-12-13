/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.interfaces;

import java.util.Set;

public interface GraphicsProvider {

    Set<String> getSupportedFormats();

    boolean isFormatSupported(String imageFormat);

    default boolean isFormatNotSupported(String imageFormat) {
        return !isFormatSupported(imageFormat);
    }
}
