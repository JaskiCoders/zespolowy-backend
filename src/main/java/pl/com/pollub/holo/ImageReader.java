package pl.com.pollub.holo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Component
@RestController
public class ImageReader {

    @Autowired
    private FrameHandler frameHandler;
    @Autowired
    private Base64Converter converter;
    private BufferedImage img;

    @PostConstruct
    private void setImage() throws IOException {
        img = ImageIO.read(new File("C:\\Mgr\\zespolowy-backend\\src\\main\\resources\\500kb.png"));
    }

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Scheduled(fixedDelay = 1)
    public BufferedImage getOneFrame() throws IOException {
        frameHandler.frameCallback(img);
        return img;
        
    }

    @RequestMapping(value = "/getFrameByRest")
    public String getFrameByRest(){
        return "{\"value\": \"" + converter.convertImageToBase64(img) +
                "\" " + ",\"timestamp\": \"" + System.currentTimeMillis() + "\"}";
    }

}
