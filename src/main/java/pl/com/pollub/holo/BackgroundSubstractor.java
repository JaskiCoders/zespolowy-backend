package pl.com.pollub.holo;

import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

public class BackgroundSubstractor {

    static int  history = 1000;
    static float varThreshold = 100;
    static boolean bShadowDetection = false;

    public static BackgroundSubtractorMOG2 createSubstrator() {
        BackgroundSubtractorMOG2 substractor = Video.createBackgroundSubtractorMOG2();
        substractor.setVarThreshold(varThreshold);
        substractor.setHistory(history);
        substractor.setDetectShadows(bShadowDetection);
        return substractor;
    }
}
