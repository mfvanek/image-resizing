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
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * TODO Consider to use any third party library https://www.baeldung.com/java-images
 */
public class DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);
    private static Path tmpDir;

    public static void main(String[] args) {
        System.out.println("Hi there! This is demo application for image resizing");

        try {
            System.out.println("Supported formats:");
            Arrays.stream(ImageIO.getWriterFormatNames()).forEach(System.out::println);

            tmpDir = Files.createTempDirectory("resized_images_");
            ResizeParams resizeParams = ParamsValidator.getInstance(args).
                    useDefault().
                    withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).
                    withPath("https://static.ngs.ru/news/99/preview/e88eba0dbd5cd0e30ee349a3a3c54dbd07d2b28f_712.jpg").
                    validate();
            processURI(resizeParams);

            resizeParams = ParamsValidator.getInstance(args).useDefault().withAlgorithm(ResizeType.KEEP_ASPECT_RATIO).validate();
            processURI(resizeParams);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void processURI(final ResizeParams resizeParams) {
        try {
            BufferedImage img;
            if (resizeParams.isUrl()) {
                img = ImageIO.read(new URL(resizeParams.getPathToFile()));
            } else {
                File file = new File(new URI(resizeParams.getPathToFile()));
                img = ImageIO.read(file);
            }

            if (img != null) {
                final ImageResizer imageResizer = ResizersFactory.create(resizeParams.getAlgorithm());
                BufferedImage outputImage = imageResizer.resize(img, resizeParams.getWidth(), resizeParams.getHeight());

                // TODO save file in the same file-type as a source image
                final URI outputUri = tmpDir.resolve("resized_file.png").toUri();
                File outputFile = new File(outputUri);
                ImageIO.write(outputImage, "png", outputFile);
                System.out.println("Resized " + outputUri);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
