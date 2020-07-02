import bagel.*;
import bagel.util.Point;

/**
 * Represents a Sprite in the Game
 */
public abstract class Sprite {
    private int x;
    private int y;
    private Image image;
    private Image cardImage;

    /**
     * Creates a Sprite in the Game
     * @param x witht the x coordinate
     * @param y coordinate
     * @param image image for the Sprite
     */
    public Sprite(int x, int y, Image image){
        this.x =x;
        this.y =y;
        this.image = image;
    }

    public Sprite(int x, int y, Image image, Image cardImage){
        this.x =x;
        this.y =y;
        this.image = image;
        this.cardImage = cardImage;
    }

    /**
     * gets the center of an object
     * @return the center of the object as a Point
     */
    public Point getCenter() { return new Point(x, y);}

    /**
     * Gets object image
     * @return Image representing object
     */
    public Image getImage() {
        return image;
    }

    public Image getCardImage() {
        return cardImage;
    }

    /**
     * Draws the object at the coordinate
     */
    protected void draw(){
        image.draw(this.x, this.y);

    }

    /**
     * get X coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * get Y coordinate
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * sets the x to be
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * sets the y to be
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }
}
