/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.resizers;

import io.github.mfvanek.image.resizing.enums.ResizeType;
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
