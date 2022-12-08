/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing.interfaces;

import com.mfvanek.image.resizing.enums.ResizeType;

import java.awt.image.BufferedImage;

public interface ImageResizer {

    BufferedImage resize(BufferedImage inputImage, ImageParams resizeParams);

    ResizeType getAlgorithm();
}
