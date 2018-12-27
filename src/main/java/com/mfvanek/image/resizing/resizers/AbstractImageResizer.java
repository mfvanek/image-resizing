/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.ImageParams;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

abstract class AbstractImageResizer implements ImageResizer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractImageResizer.class);

    private final int hints;

    AbstractImageResizer(int hints) {
        this.hints = hints;
    }

    @Override
    public BufferedImage resize(BufferedImage inputImage, ImageParams resizeParams) {
        final Image tmp = inputImage.getScaledInstance(resizeParams.getWidth(), resizeParams.getHeight(), hints);
        final BufferedImage resized = new BufferedImage(resizeParams.getWidth(), resizeParams.getHeight(),
                resizeParams.isConvertToGrayscale() ? BufferedImage.TYPE_BYTE_GRAY : inputImage.getType());
        Graphics2D g2d = null;
        try {
            g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            if (g2d != null) {
                g2d.dispose();
            }
        }
        return resized;
    }
}
