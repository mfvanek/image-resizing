/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.ResizersConfig;
import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.GraphicsProvider;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ResizersConfig.class})
class ResizersFactoryTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void createImageResizer() {
        final ImageResizer imageResizerRaw = ResizersFactory.getByAlgorithm(ResizeType.RAW);
        assertNotNull(imageResizerRaw);

        final ImageResizer imageResizerAspectRatio = ResizersFactory.getByAlgorithm(ResizeType.KEEP_ASPECT_RATIO);
        assertNotNull(imageResizerAspectRatio);

        assertNotEquals(imageResizerRaw.getClass(), imageResizerAspectRatio.getClass());
    }

    @Test
    void createGraphicsProvider() {
        final GraphicsProvider graphicsProvider = ctx.getBean(GraphicsProvider.class);
        assertNotNull(graphicsProvider);
    }
}