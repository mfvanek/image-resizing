/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.GraphicsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// TODO tests
@Component("graphicsProvider")
class AwtGraphicsProvider implements GraphicsProvider {

    private static final Logger logger = LoggerFactory.getLogger(AwtGraphicsProvider.class);

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

    @Override
    public BufferedImage loadImage(URI uri) {
        try {
            File file = new File(uri);
            return ImageIO.read(file);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public BufferedImage loadImage(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
