package pl.com.pollub.holo;

import org.opencv.video.BackgroundSubtractorMOG2;


public class BackgrundSubstractorConfigurator {

    public static BackgroundSubtractorMOG2 setThreshold (BackgroundSubtractorMOG2 bgs, int threshold) {
        bgs.setVarThreshold(threshold);
        return bgs;
    }

    public static void setShadowDetection(BackgroundSubtractorMOG2 bgs, boolean detectShadows) {
        bgs.setDetectShadows(detectShadows);
    }

    public static void setBackgroundRatio(BackgroundSubtractorMOG2 bgs, double ratio) {
        bgs.setBackgroundRatio(ratio);

    }

    public static void setMinValue(BackgroundSubtractorMOG2 bgs, double minValue) {
        bgs.setVarMin(minValue);
    }

    public static void setMaxValue(BackgroundSubtractorMOG2 bgs, double maxValue) {
        bgs.setVarMax(maxValue);
    }

}
