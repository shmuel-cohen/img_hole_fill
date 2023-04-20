package weightingFunctions;

import java.util.Vector;

/**
 * this class implements WeightingFunctions.WeightingFunction and creates a new WeightingFunctions.WeightingFunction.
 */
public class DefaultDistanceFunction implements WeightingFunction {
    private final float z;
    private final float e;

    /**
     * constructor to create new instance of weighting function according to the following
     * formula: 1 / (norm(u - v) ** z + epsilon)
     * @param z z parameter.
     * @param e epsilon parameter.
     */
    public DefaultDistanceFunction(float z, float e){
        this.z = z;
        this.e = e;
    }
    @Override
    public float weight(Vector<Integer> u, Vector<Integer> v) {
        return (float) (1F /
                (Math.pow(Math.sqrt(Math.pow(u.get(0)-v.get(0), 2) + Math.pow(u.get(1)-v.get(1), 2) ), z)+e));
    }
}
