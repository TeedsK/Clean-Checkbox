import java.awt.Color;
/**
 * The Circle Object used in the background animation of a checkbox
 * @author Teeds - Theo K
 */
public class Circle {
    int diameter;
    int wantedDiameter;
    int alpha;
    Color color;
    int maxAlpha;
    /**
     * Creates the circle with the width
     * @param width diameter of the circle / width of the checkbox
     */
    public Circle(int width) {
        this.diameter = width;
        this.wantedDiameter = width;
        this.alpha = 255;
        this.maxAlpha = 255;
    }
    /**
     * Creates the circle object with the color and width
     * @param width diameter of the circle / width of the checkbox
     * @param color the color of the circle
     */
    public Circle(int width, Color color) {
        this.diameter = width;
        this.wantedDiameter = width;
        this.alpha = 255;
        this.maxAlpha = 255;
        this.color = color;
    }

    /**
     * @return the maximum possible Alpha
     */
    public int getMaxAlpha() {
        return maxAlpha;
    }
    /**
     * Sets the maximum possible Alpha value
     * @param maxAlpha maximum alpha
     */
    public void setMaxAlpha(int maxAlpha) {
        this.maxAlpha = maxAlpha;
    }
    /**
     * @return the Diameter of the circle
     */
    public int getDiameter() {
        return diameter;
    }
    /**
     * @return the color of the circle
     */
    public Color getColor() {
        return color;
    }
    /**
     * @param color the desired color of the circle
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * @param diameter the diameter of the circle
     */
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
    /**
     * @param wanted the desired Diameter of the circle
     */
    public void setWanted(int wanted) {
        this.wantedDiameter = wanted;
    }
    /**
     * @return the desired diameter
     */
    public int getWanted() {
        return wantedDiameter;
    }
    /**
     * @param alpha set the alpha value
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
    /**
     * @return the current alpha value
     */
    public int getAlpha() {
        return alpha;
    }
}
