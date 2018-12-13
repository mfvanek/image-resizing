/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import java.awt.Image;

class RawImageResizer extends AbstractImageResizer {

    RawImageResizer() {
        super(Image.SCALE_SMOOTH);
    }
}
