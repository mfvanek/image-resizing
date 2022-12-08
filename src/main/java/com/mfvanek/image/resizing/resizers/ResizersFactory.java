/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing.resizers;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public final class ResizersFactory {

    private static final ConcurrentMap<ResizeType, ImageResizer> RESIZERS_CACHE = new ConcurrentHashMap<>();

    private final List<ImageResizer> resizers;

    @PostConstruct
    public void init() {
        for (final ImageResizer resizer : resizers) {
            RESIZERS_CACHE.putIfAbsent(resizer.getAlgorithm(), resizer);
        }
    }

    public static ImageResizer getByAlgorithm(final ResizeType algorithm) {
        final ImageResizer resizer = RESIZERS_CACHE.get(algorithm);
        if (resizer == null) {
            throw new IllegalArgumentException("Unsupported resize algorithm " + algorithm);
        }
        return resizer;
    }
}
