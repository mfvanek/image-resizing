/*
 * Copyright (c) 2018-2022. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek/image-resizing
 */

package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.GraphicsProvider;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import com.mfvanek.image.resizing.pojos.ResizeParams;
import com.mfvanek.image.resizing.resizers.ResizersFactory;
import com.mfvanek.image.resizing.utils.ParamsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO Consider to use any third party library https://www.baeldung.com/java-images
@Slf4j
public class DemoApp {

    private static Path tmpDir;

    public static void main(final String[] args) {
        log.info("Hi there!%nThis is demo application for image resizing");
        log.info("Started with args[{}}] = {}", args.length, String.join("", args));

        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(ResizersConfig.class)) {
            final GraphicsProvider graphicsProvider = ctx.getBean(GraphicsProvider.class);
            log.info("Supported formats: {}", String.join(", ", graphicsProvider.getSupportedFormats()));

            tmpDir = Files.createTempDirectory("resized_images_");
            log.info("Output directory = {}", tmpDir);

            if (args.length == 0) { // when running from IDE
                final ResizeParams resizeParams = ParamsValidator.builder(args)
                        .useDefaultIfNeed()
                        .withAlgorithm(ResizeType.KEEP_ASPECT_RATIO)
                        .withPath("https://static.ngs.ru/news/99/preview/e88eba0dbd5cd0e30ee349a3a3c54dbd07d2b28f_712.jpg")
                        .validate();
                process(graphicsProvider, resizeParams);
            }

            final ResizeParams resizeParams = ParamsValidator.builder(args).useDefaultIfNeed().withAlgorithm(ResizeType.RAW).validate();
            process(graphicsProvider, resizeParams);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    private static void process(final GraphicsProvider graphicsProvider, final ResizeParams resizeParams) {
        try {
            if (graphicsProvider.isFormatNotSupported(resizeParams.getExtension())) {
                log.warn("File format '{}' is not supported", resizeParams.getExtension());
                return;
            }

            final BufferedImage img = graphicsProvider.loadImage(resizeParams);
            if (img != null) {
                final ImageResizer imageResizer = ResizersFactory.getByAlgorithm(resizeParams.getAlgorithm());
                final long startTime = System.nanoTime();
                final BufferedImage outputImage = imageResizer.resize(img, resizeParams);
                final long endTime = System.nanoTime();
                log.debug("Resize is completed. Elapsed time {} microseconds", (endTime - startTime) / 1_000_000);
                saveToFile(resizeParams, outputImage);
            } else {
                log.error("Unable to load given image");
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    private static void saveToFile(final ResizeParams resizeParams, final BufferedImage outputImage) throws IOException {
        final URI outputUri = tmpDir.resolve(resizeParams.getOutputName()).toUri();
        final File outputFile = new File(outputUri);
        ImageIO.write(outputImage, resizeParams.getExtension(), outputFile);
        log.info("Resized {}", outputUri);
    }
}
