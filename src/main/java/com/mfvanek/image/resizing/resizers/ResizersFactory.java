/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.GraphicsProvider;
import com.mfvanek.image.resizing.interfaces.ImageResizer;

public final class ResizersFactory {

    private ResizersFactory() {}

    public static ImageResizer newImageResizer(ResizeType algorithm) {
        switch (algorithm) {
            case RAW:
                return new RawImageResizer();

            case KEEP_ASPECT_RATIO:
                return new KeepingAspectRatioResizer();
        }
        throw new IllegalArgumentException("Unsupported resize algorithm " + algorithm);
    }

    public static GraphicsProvider newGraphicsProvider() {
        return new AwtGraphicsProvider();
    }
}
