package grayscaleImages;

/**
 * An interface designed to set the required API  in order to activate the "fill hole" function.
 */
public interface GrayScale {
    float getBright(int x, int y);
    void setBright(int x, int y, float value);
    int getWidth();
    int getHeight();
}

