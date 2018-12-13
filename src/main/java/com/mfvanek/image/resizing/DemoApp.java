/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
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

/**
 * TODO Consider to use any third party library https://www.baeldung.com/java-images
 *
 * For instance:
 * file:/C:/src/image-resizing/target/classes/java-logo.jpeg
 * file:///C:/Users/IVAN~1.VAK/AppData/Local/Temp/resized_images_7304718956539175727/resized_java-logo.jpeg
 */
class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);
    private static Path tmpDir;

    public static void main(String[] args) {
        System.out.println("Hi there! This is demo application for image resizing");

        try {
            tmpDir = Files.createTempDirectory("resized_images_");
            ResizeParams resizeParams = ParamsValidator.getInstance(args).
                    useDefaultIfNeed().
                    withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).
                    withPath("https://static.ngs.ru/news/99/preview/e88eba0dbd5cd0e30ee349a3a3c54dbd07d2b28f_712.jpg").
                    validate();
            process(resizeParams);

            resizeParams = ParamsValidator.getInstance(args).useDefaultIfNeed().withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).validate();
            process(resizeParams);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void process(final ResizeParams resizeParams) {
        try {
            if (resizeParams.isFormatNotSupported()) {
                System.out.println(String.format("File format '%s' is not supported", resizeParams.getExtension()));
                return;
            }

            BufferedImage img;
            if (resizeParams.isSimilarToURL()) {
                img = ImageIO.read(resizeParams.toURI().toURL());
            } else {
                File file = new File(resizeParams.toURI());
                img = ImageIO.read(file);
            }

            if (img != null) {
                final ImageResizer imageResizer = ResizersFactory.create(resizeParams.getAlgorithm());
                final BufferedImage outputImage = imageResizer.resize(img, resizeParams.getWidth(), resizeParams.getHeight());

                saveToFile(resizeParams, outputImage);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
