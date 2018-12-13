/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.interfaces;


import com.mfvanek.image.resizing.pojos.Dimension;

import java.awt.image.BufferedImage;

public interface ImageResizer {

    BufferedImage resize(BufferedImage inputImage, Dimension newDimension);
}
