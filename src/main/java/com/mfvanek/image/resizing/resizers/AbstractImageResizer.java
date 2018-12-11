package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.ImageResizer;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class AbstractImageResizer implements ImageResizer {

    private final int hints;

    protected AbstractImageResizer(int hints) {
        this.hints = hints;
    }

    @Override
    public BufferedImage resize(BufferedImage inputImage, int newWidth, int newHeight) {
        Image tmp = inputImage.getScaledInstance(newWidth, newHeight, hints);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, inputImage.getType());
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
