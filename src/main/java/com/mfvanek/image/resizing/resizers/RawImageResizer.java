/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import org.springframework.stereotype.Component;

import java.awt.Image;

@Component
class RawImageResizer extends AbstractImageResizer {

    RawImageResizer() {
        super(Image.SCALE_SMOOTH);
    }

    @Override
    public ResizeType getAlgorithm() {
        return ResizeType.RAW;
    }
}
