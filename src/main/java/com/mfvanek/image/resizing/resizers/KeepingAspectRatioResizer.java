/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageParams;
import com.mfvanek.image.resizing.pojos.Dimension;
import com.mfvanek.image.resizing.pojos.ResizeParams;
import org.springframework.stereotype.Component;

import java.awt.Image;
import java.awt.image.BufferedImage;

@Component
class KeepingAspectRatioResizer extends AbstractImageResizer {

    KeepingAspectRatioResizer() {
        super(Image.SCALE_SMOOTH);
    }

    @Override
    public BufferedImage resize(final BufferedImage inputImage, final ImageParams resizeParams) {
        // TODO More smart algorithm is needed for scaling
        final int newWidth = resizeParams.getWidth();
        final double aspectRatio = inputImage.getWidth() / (double) inputImage.getHeight();
        final int scaledHeight = (int) (newWidth / aspectRatio);
        final Dimension newDimension = new Dimension(newWidth, scaledHeight);
        return super.resize(inputImage, ResizeParams.from(resizeParams, newDimension));
    }

    @Override
    public ResizeType getAlgorithm() {
        return ResizeType.KEEP_ASPECT_RATIO;
    }
}
