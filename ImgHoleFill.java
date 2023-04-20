import java.util.HashSet;
import java.util.Vector;

/**
 * this class implements static methods to fill hole in image.
 */
public class ImgHoleFill {


    public static void fillHole(GrayScale image, float z, float e, int n_connected) {
        fillHole(image, new DefaultDistanceFunction(z, e), n_connected);
    }

    /**
     * the method accept GrayScale image with hole and weighting function, and fill the hole
     * by the given function.
     * @param image the image to be filled.
     * @param function WeightingFunction that calculate distance between 2 vector.
     * @param n_connected the number of neighbors of each pixel.
     */
    public static void fillHole(GrayScale image, WeightingFunction function, int n_connected) {
        HashSet<Vector<Integer>> holes = new HashSet<>() ;
        HashSet<Vector<Integer>> boundary = new HashSet<>();
        Vector<Integer> pixel = findFirstHole(image);
//        holes.add(pixel);
        findHolesAndBoundary(image, holes, boundary, pixel, n_connected, true);
        setValueInHole(image, holes, boundary, function);
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



//    public static void main(String[] args) throws IOException {
//        System.out.println("k");
//        Vector<Integer> v1 = new Vector<>(2);
//        v1.add(19);
//        v1.add(20);
//        Vector<Integer> v2 = new Vector<Integer>();
//        v2.add(19);
//        v2.add(20);
//        System.out.print("v1"+ v1.hashCode()+ " v2: " + v2.hashCode());

//        loadImage("Mask.png");
//        HashSet<Vector<Integer>> holes = new HashSet<>();
//        Vector<Integer> pixel = new Vector<Integer>(2);
//        pixel.add(1);
//        pixel.add(2);
//        holes.add(pixel);
//        Vector<Integer> z = new Vector<Integer>(2);
//        z.add(1);
//        z.add(2);
//        holes.add(z);
//        System.out.println(holes);
//        System.out.println(holes.contains(z));


//        img.fill_hlo(z, 3)
//    }
}













//    HashSet<Vector<Integer>> holes = new HashSet<>();
//    Vector<Integer> pixel = new Vector<Integer>(2);
//        pixel.add(1);
//                pixel.add(2);
//                holes.add(pixel);
//                Vector<Integer> z = new Vector<Integer>(2);
//        pixel.add(1);
//        pixel.add(2);
//        System.out.println(holes.contains(z));