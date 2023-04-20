import grayscaleImages.GrayScale;
import weightingFunctions.DefaultDistanceFunction;
import weightingFunctions.WeightingFunction;
import java.util.HashSet;
import java.util.Vector;

/**
 * this class implements static methods to fill hole in image.
 */
public abstract class ImgHoleFill {

    /**
     * the method accept grayscaleImages.GrayScale image with hole and weighting function, and fill the hole
     * by the given function.
     * @param image the image to be filled.
     * @param function WeightingFunctions.WeightingFunction that calculate distance between 2 vector.
     * @param n_connected the number of neighbors of each pixel.
     */
    public static void fillHole(GrayScale image, WeightingFunction function, int n_connected) {
        HashSet<Vector<Integer>> holes = new HashSet<>() ;
        HashSet<Vector<Integer>> boundary = new HashSet<>();
        Vector<Integer> pixel = findFirstHole(image);
        findHolesAndBoundary(image, holes, boundary, pixel, n_connected, true);
        setValueInHole(image, holes, boundary, function);
    }


    /**
     * overclouding method for default weighting function.
     * the default weighting function is: 1 / (norm(u - v) ** z + epsilon)
     * @param image the image.
     * @param z z parameter.
     * @param e epsilon parameter.
     * @param n_connected number of neighbors of each pixel.
     */
    public static void fillHole(GrayScale image, float z, float e, int n_connected) {
        fillHole(image, new DefaultDistanceFunction(z, e), n_connected);
    }

    /**
     * this method finds the first missed pixel.
     * @param image the image
     * @return vector of 2 dimensions of the first missed pixel.
     */
    private static Vector<Integer> findFirstHole(GrayScale image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getBright(x, y) == -1) {
                    Vector<Integer> pixel = new Vector<>(2);
                    pixel.add(x);
                    pixel.add(y);
                    return pixel;
                }
            }
        }// add empty pixel at end
        return new Vector<>(2);
    }


    /**
     * recursive function to find all the missed pixel and their neighbors.
     * @param image the image.
     * @param holes set of holes pixels
     * @param boundary set of boundary pixels
     * @param current the current position
     * @param n_connected the number of neighbors of each pixel.
     * @param _4connected flag to indicate if the pixel is 4 connect from the previous - true,
     *                    and false otherwise.
     */
    private static void findHolesAndBoundary(GrayScale image, HashSet<Vector<Integer>> holes,
                                             HashSet<Vector<Integer>> boundary,
                                             Vector<Integer> current,
                                             int n_connected, boolean _4connected)
    {
        if (image.getBright(current.get(0),current.get(1))!=-1)
        {
            if (_4connected || n_connected==8)
            {
                boundary.add(current);
            }
            return;
        }
        if (holes.contains(current)) return;
        holes.add(current);
        _4connected=true;
        for(int x = current.get(0) - 1; x <= current.get(0) + 1; x++){
            for(int y = current.get(1) - 1; y <= current.get(1) + 1; y++){
                _4connected = !_4connected;
                if ((x == current.get(0) && y == current.get(1))){
                    continue;
                }
                Vector<Integer> pixel = new Vector<>(2);
                pixel.add(x);
                pixel.add(y);
                findHolesAndBoundary(image,holes,boundary,pixel,n_connected,_4connected);
            }
        }
    }

    private static void setValueInHole(GrayScale image, HashSet<Vector<Integer>> holes,
                                       HashSet<Vector<Integer>> boundary,
                                       WeightingFunction function) {
        for (Vector<Integer> pixel : holes){
            image.setBright(pixel.get(0), pixel.get(1), valueInPixel(image, pixel, boundary, function));
        }
    }

    private static float valueInPixel(GrayScale image, Vector<Integer> u,
                                      HashSet<Vector<Integer>> boundary, WeightingFunction function) {
        float numerator = 0;
        float denominator = 0;
        for (Vector<Integer> v : boundary){
            float distance = function.weight(u, v);
            numerator += distance * image.getBright(v.get(0), v.get(1));
            denominator += distance;
        }
        return numerator / denominator;
    }
}