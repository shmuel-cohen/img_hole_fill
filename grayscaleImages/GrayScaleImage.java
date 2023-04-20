package grayscaleImages;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * this class implements grayscaleImages.GrayScale and create gray scale images from images.
 */
public class GrayScaleImage implements GrayScale {
    private final float[][] pixelImage ;

    /**
     * constructor that creates gray scale images from 2 images one is the picture and the other is the
     * hole.
     * @param imageFieldPath the path to the picture image.
     * @param holeFilePath  the path to the hole image.
     * @throws IOException in case of an I/O error  while opening the image files.
     */
    public GrayScaleImage(String imageFieldPath, String holeFilePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imageFieldPath));
        pixelImage = new float[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                int grayscale = (int) (rgb.getRed() * 0.299 + rgb.getGreen() * 0.587 + rgb.getBlue() * 0.114);
                pixelImage[x][y] = (grayscale / 255F);
            }
        }
        //save the hole
        image = ImageIO.read(new File(holeFilePath));
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                int grayscale = (int) (rgb.getRed() * 0.299 + rgb.getGreen() * 0.587 + rgb.getBlue() * 0.114);
                if ((grayscale / 255F) < 0.5){
                    pixelImage[x][y] = -1;
                }
            }
        }
    }

    /**
     * method to save the gray scale image in RBG image.
     * @param imagePath the path to save to.
     * @throws IOException in case of an I/O error  while creating the image files.
     */
    public void saveImage(String imagePath) throws IOException{
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int color = (int)(pixelImage[x][y] * 255);
                Color gray = new Color(color, color, color);
                image.setRGB(x, y, gray.getRGB());
            }
        }
        File output = new File(imagePath + " filled hole.png");
        ImageIO.write(image, "png", output);
    }


    @Override
    public float getBright(int x, int y) {
        return pixelImage[x][y];
    }

    @Override
    public int getWidth() {
        return pixelImage.length;
    }

    @Override
    public int getHeight() {
        return pixelImage[0].length;
    }

    @Override
    public void setBright(int x, int y, float value) {
        pixelImage[x][y] = value;
    }
}
