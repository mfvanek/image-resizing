/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResizersFactoryTest {

    @Test
    void create() {
        final ImageResizer imageResizerRaw = ResizersFactory.newImageResizer(ResizeType.RAW);
        assertNotNull(imageResizerRaw);

        final ImageResizer imageResizerAspectRatio = ResizersFactory.newImageResizer(ResizeType.KEEP_ASPECT_RATIO);
        assertNotNull(imageResizerAspectRatio);

        assertNotEquals(imageResizerRaw.getClass(), imageResizerAspectRatio.getClass());
    }
}