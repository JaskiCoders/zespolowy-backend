package pl.com.pollub.holo;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class ImageConverter {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private Mat matrix = new Mat();
    private BufferedImage image;

    public ImageConverter() {
    }

    public ImageConverter(Mat mat) {
        this.image = generateImageInMatSize(mat);
    }

    public BufferedImage generateImageInMatSize(Mat mat) {
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        this.matrix = mat;
        int width = mat.cols();
        int height = mat.rows();
        try {
            return new BufferedImage(width, height, type);
        }
        catch(Exception e){
            return new BufferedImage(1,1, type);
        }
    }

    BufferedImage getImage(Mat mat) {
        BufferedImage image = generateImageInMatSize(mat);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return image;
    }

    public Mat getMatrix() {
        return this.matrix;
    }
}
