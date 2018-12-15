/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.interfaces.ImageParams;

import java.awt.Image;
import java.awt.image.BufferedImage;

class KeepingAspectRatioResizer extends AbstractImageResizer {

    KeepingAspectRatioResizer() {
        super(Image.SCALE_SMOOTH);
    }

    @Override
    public BufferedImage resize(BufferedImage inputImage, ImageParams resizeParams) {
        // TODO More smart algorithm is needed for scaling
        final int newWidth = resizeParams.getWidth();
        final double aspectRatio = inputImage.getWidth() / (double) inputImage.getHeight();
        final int scaledHeight = (int) (newWidth / aspectRatio);
        return super.resize(inputImage, resizeParams);
    }
}
