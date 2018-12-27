/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class ResizersFactory {

    @Autowired
    private List<ImageResizer> resizers;

    private static final Map<ResizeType, ImageResizer> resizersCache = new HashMap<>();

    private ResizersFactory() {}

    @PostConstruct
    public void init() {
        for(ImageResizer resizer : resizers) {
            resizersCache.put(resizer.getAlgorithm(), resizer);
        }
    }

    public static ImageResizer getByAlgorithm(ResizeType algorithm) {
        final ImageResizer resizer = resizersCache.get(algorithm);
        if (resizer == null) {
            throw new IllegalArgumentException("Unsupported resize algorithm " + algorithm);
        }
        return resizer;
    }
}
