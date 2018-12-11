package com.mfvanek.image.resizing;

import com.mfvanek.image.resizing.enums.ResizeType;
import com.mfvanek.image.resizing.interfaces.ImageResizer;
import com.mfvanek.image.resizing.resizers.ResizersFactory;
import com.mfvanek.image.resizing.utils.ParamsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * TODO Consider to use any third party library https://www.baeldung.com/java-images
 */
public class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);

    private static final ResizeType algorithm = ResizeType.RAW;

    public static void main(String[] args) {
        System.out.println("Hi there! This is demo application for image resizing");

        try {
            final ResizeParams resizeParams = ParamsValidator.getInstance(args).useDefault().validate();

            // TODO not working!
            // URI netUri = new URI("https://en.wikipedia.org/wiki/File:Java_programming_language_logo.svg");
            // processURI(netUri, resizeParams);

            URI localUri = new URI(resizeParams.getPathToFile());
            processURI(localUri, resizeParams);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void processURI(final URI uri, final ResizeParams resizeParams) {
        try {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(uri));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }

            if (img != null) {
                try {
                    final ImageResizer imageResizer = ResizersFactory.create(algorithm);
                    BufferedImage outputImage = imageResizer.resize(img, resizeParams.getWidth(), resizeParams.getHeight());

                    // TODO save file in the same file-type as a source image
                    File outputfile = new File("saved.png");
                    ImageIO.write(outputImage, "png", outputfile);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
