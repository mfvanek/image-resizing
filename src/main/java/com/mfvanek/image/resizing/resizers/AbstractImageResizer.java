/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.ImageParams;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import lombok.extern.slf4j.Slf4j;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

@Slf4j
abstract class AbstractImageResizer implements ImageResizer {

    private final int hints;

    AbstractImageResizer(final int hints) {
        this.hints = hints;
    }

    @Override
    public BufferedImage resize(final BufferedImage inputImage, final ImageParams resizeParams) {
        final Image tmp = inputImage.getScaledInstance(resizeParams.getWidth(), resizeParams.getHeight(), hints);
        final BufferedImage resized = new BufferedImage(resizeParams.getWidth(), resizeParams.getHeight(),
                resizeParams.isConvertToGrayscale() ? BufferedImage.TYPE_BYTE_GRAY : inputImage.getType());
        Graphics2D g2d = null;
        try {
            g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
        } finally {
            if (g2d != null) {
                g2d.dispose();
            }
        }
        return resized;
    }
}
