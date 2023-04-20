import java.util.Vector;

/**
 * An interface for distance calculations between two points
 */
public interface WeightingFunction {
    float weight(Vector<Integer> u, Vector<Integer> v);
}
