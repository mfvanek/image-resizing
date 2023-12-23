/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.resizers;

import io.github.mfvanek.image.resizing.interfaces.GraphicsProvider;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component("graphicsProvider")
class AwtGraphicsProvider implements GraphicsProvider {

    @Override
    public Set<String> getSupportedFormats() {
        return LazyHolder.SUPPORTED_FORMATS;
    }

    @Override
    public boolean isFormatSupported(final String imageFormat) {
        return LazyHolder.SUPPORTED_FORMATS.contains(imageFormat);
    }

    @Override
    public BufferedImage loadImage(final URI uri) {
        try {
            final File file = new File(uri);
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BufferedImage loadImage(final URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class LazyHolder {

        static final Set<String> SUPPORTED_FORMATS = Arrays.stream(ImageIO.getWriterFormatNames())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
