package pl.com.pollub.holo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static java.util.Objects.isNull;

@Component
@RestController // klasa jest kopia klasy ImageReader K.Mazur
public class ImageReader3 {

    @Autowired
    private FrameHandler3 frameHandler;

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    Mat matrix;
    Mat bgModel;
    VideoCapture cap = new VideoCapture();
    ImageConverter converter = new ImageConverter();
    BackgroundSubtractorMOG2 sub = BackgroundSubstractor.createSubstrator();

    @RequestMapping("/updateBgModel3")
    public void updateBgModel(){
        cap.open(0);
        double tres = sub.getVarThreshold();
        bgModel = matrix;
        sub = BackgroundSubstractor.createSubstrator();
        sub.setVarThreshold(tres);
        System.out.println("Model updated.");
    }

    @Scheduled(fixedDelay = 1)
    public BufferedImage getOneFrame() {
        matrix = converter.getMatrix();
        try {
            cap.grab();
            cap.retrieve(matrix);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(isNull(bgModel)){
            updateBgModel();
        }

        Mat fgMask = bgModel;
        sub.apply(matrix, fgMask,0);
        Mat output = new Mat();
        matrix.copyTo(output, fgMask);
        BufferedImage bufferdedImage = converter.getImage(output);
        frameHandler.frameCallback(bufferdedImage);
        return bufferdedImage;
    }

    @RequestMapping("/setThreshold3/{tres}")
    public void setThreshold(@PathVariable("tres") int value){
        System.out.println("Threshold is: "+value);
        BackgrundSubstractorConfigurator.setThreshold(sub, value);
    }
}
