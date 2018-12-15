/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.GraphicsProvider;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import com.mfvanek.image.resizing.pojos.ResizeParams;
import com.mfvanek.image.resizing.resizers.ResizersFactory;
import com.mfvanek.image.resizing.utils.ParamsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * TODO Consider to use any third party library https://www.baeldung.com/java-images
 *
 * For instance:
 * file:/C:/src/image-resizing/target/classes/java-logo.jpeg
 * file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.jpeg
 */
public class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);
    private static Path tmpDir;

    public static void main(String[] args) {
        System.out.println("Hi there!\nThis is demo application for image resizing");
        System.out.print(String.format("Started with args[%d] = ", args.length));
        Arrays.stream(args).forEach(a -> System.out.print(a + " "));
        System.out.println();

        try {
            final GraphicsProvider graphicsProvider = ResizersFactory.newGraphicsProvider();
            System.out.println("Supported formats: " + String.join(", ", graphicsProvider.getSupportedFormats()));

            tmpDir = Files.createTempDirectory("resized_images_");
            System.out.println("Output directory = " + tmpDir);

            if (args.length == 0) { // when running from IDE
                ResizeParams resizeParams = ParamsValidator.getInstance(args).
                        useDefaultIfNeed().
                        withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).
                        withPath("https://static.ngs.ru/news/99/preview/e88eba0dbd5cd0e30ee349a3a3c54dbd07d2b28f_712.jpg").
                        validate();
                process(graphicsProvider, resizeParams);
            }

            ResizeParams resizeParams = ParamsValidator.getInstance(args).useDefaultIfNeed().withAlgorithm(ResizeType.RAW).validate();
            process(graphicsProvider, resizeParams);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    private static void process(final GraphicsProvider graphicsProvider, final ResizeParams resizeParams) {
        try {
            if (graphicsProvider.isFormatNotSupported(resizeParams.getExtension())) {
                System.out.println(String.format("File format '%s' is not supported", resizeParams.getExtension()));
                return;
            }

            BufferedImage img = graphicsProvider.loadImage(resizeParams);
            if (img != null) {
                final ImageResizer imageResizer = ResizersFactory.newImageResizer(resizeParams.getAlgorithm());
                final long startTime = System.currentTimeMillis();
                final BufferedImage outputImage = imageResizer.resize(img, resizeParams);
                final long endTime = System.currentTimeMillis();
                logger.debug(String.format("Resize is completed. Elapsed time %d ms", endTime - startTime));
                saveToFile(resizeParams, outputImage);
            } else {
                logger.error("Unable to load given image");
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    private static void saveToFile(final ResizeParams resizeParams, final BufferedImage outputImage)
            throws IOException {
        final URI outputUri = tmpDir.resolve(resizeParams.getOutputName()).toUri();
        final File outputFile = new File(outputUri);
        ImageIO.write(outputImage, resizeParams.getExtension(), outputFile);
        System.out.println("Resized " + outputUri);
    }
}
