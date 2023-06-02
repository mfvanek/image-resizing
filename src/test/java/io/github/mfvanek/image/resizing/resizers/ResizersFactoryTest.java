/*
 * Copyright (c) 2018-2023. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package io.github.mfvanek.image.resizing.resizers;

import io.github.mfvanek.image.resizing.ResizersConfig;
import io.github.mfvanek.image.resizing.enums.ResizeType;
import io.github.mfvanek.image.resizing.interfaces.GraphicsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResizersConfig.class)
class ResizersFactoryTest {

    @Autowired
    private ApplicationContext ctx;

    @ParameterizedTest
    @EnumSource(ResizeType.class)
    void forEachResizeTypeShouldPresentImplementation(final ResizeType resizeType) {
        assertThat(ResizersFactory.getByAlgorithm(resizeType))
                .isNotNull()
                .isInstanceOf(AbstractImageResizer.class);
    }

    @Test
    void createGraphicsProvider() {
        final GraphicsProvider graphicsProvider = ctx.getBean(GraphicsProvider.class);
        assertThat(graphicsProvider)
                .isNotNull()
                .isInstanceOf(AwtGraphicsProvider.class);
    }

    @Test
    void correctness() {
        final Set<Class<?>> resizers = new HashSet<>();
        for (final ResizeType resizeType : ResizeType.values()) {
            resizers.add(ResizersFactory.getByAlgorithm(resizeType).getClass());
        }
        assertThat(resizers)
                .hasSameSizeAs(ResizeType.values());
    }
}
