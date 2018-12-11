package com.mfvanek.image.resizing.resizers;

import java.awt.*;
import java.awt.image.BufferedImage;

class KeepingAspectRatioResizer extends AbstractImageResizer {

    KeepingAspectRatioResizer() {
        super(Image.SCALE_SMOOTH);
    }

    @Override
    public BufferedImage resize(BufferedImage inputImage, int newWidth, int newHeight) {
        double aspectRatio = inputImage.getWidth() / (double) inputImage.getHeight();
        int scaledHeight = (int) (newWidth / aspectRatio);
        return super.resize(inputImage, newWidth, scaledHeight);
    }
}
