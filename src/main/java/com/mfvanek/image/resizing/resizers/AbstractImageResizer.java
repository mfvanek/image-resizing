/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.ImageResizer;
import com.mfvanek.image.resizing.pojos.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

abstract class AbstractImageResizer implements ImageResizer {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractImageResizer.class);

    private final int hints;

    protected AbstractImageResizer(int hints) {
        this.hints = hints;
    }

    @Override
    public BufferedImage resize(BufferedImage inputImage, Dimension newDimension) {
        final Image tmp = inputImage.getScaledInstance(newDimension.getWidth(), newDimension.getHeight(), hints);
        final BufferedImage resized = new BufferedImage(newDimension.getWidth(), newDimension.getHeight(), inputImage.getType());
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
