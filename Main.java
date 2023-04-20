import java.io.IOException;

/**
 * this class implements the command to take 2 image, first is the image and second is the mask
 * that defines the hole.
 * the function marge them by converting them to grayscale, so fill the hole and save the filled image.
 * the arguments: [path_to_image] [path_to_mask] [z] [epsilon] [n_connectivity]
 */
public class Main {
    public static void main(String[] args)  {
        try {
            GrayScaleImage grayScaleImage = new GrayScaleImage(args[0], args[1]);
            ImgHoleFill.fillHole(grayScaleImage, Float.parseFloat(args[2]), Float.parseFloat(args[3]),
                    Integer.parseInt(args[4]));
            String output = args[0].substring(0, args[0].lastIndexOf("."));
            grayScaleImage.saveImage(output);

        }
        catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }
}
