package com.mfvanek.image.resizing.interfaces;

import java.awt.image.BufferedImage;

public interface ImageResizer {

    BufferedImage resize(BufferedImage inputImage, int newWidth, int newHeight);
}
