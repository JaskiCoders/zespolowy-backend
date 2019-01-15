package pl.com.pollub.holo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class Base64Converter {
    // metoda zamieniajaca obrazek na Base64 K.Mazur
    public String convertImageToBase64(BufferedImage inputImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(inputImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            log.debug("Exception during converting image: {}", e.getMessage());
        }
        return DatatypeConverter.printBase64Binary(byteArrayOutputStream.toByteArray());
    }
}
