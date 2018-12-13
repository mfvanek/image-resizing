/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.GraphicsProvider;

import javax.imageio.ImageIO;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// TODO tests
class AwtGraphicsProvider implements GraphicsProvider {

    AwtGraphicsProvider() {}

    private static class LazyHolder {

        static final Set<String> SUPPORTED_FORMATS = Arrays.stream(ImageIO.getWriterFormatNames())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSupportedFormats() {
        return LazyHolder.SUPPORTED_FORMATS;
    }

    @Override
    public boolean isFormatSupported(String imageFormat) {
        return LazyHolder.SUPPORTED_FORMATS.contains(imageFormat);
    }
}
