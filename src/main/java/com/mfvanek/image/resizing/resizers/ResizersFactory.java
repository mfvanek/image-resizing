package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageResizer;

public final class ResizersFactory {

    private ResizersFactory() {}

    public static ImageResizer create(ResizeType algorithm) {
        switch (algorithm) {
            case RAW:
                return new RawImageResizer();

            case KEEP_ASPECT_RATIO:
                return new KeepingAspectRatioResizer();
        }
        throw new IllegalArgumentException("Unsupported resize algorithm " + algorithm);
    }
}
