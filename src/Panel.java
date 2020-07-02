import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * Represents a Panel in General
 */
public abstract class Panel extends Sprite{
    protected final double x;
    protected final double y;
    protected final Image panel;
    protected final Font font = new Font("res/fonts/DejaVuSans-Bold.ttf", 22);
    protected final Font moneyfont = new Font("res/fonts/DejaVuSans-Bold.ttf", 50);
    protected final Font textfont = new Font("res/fonts/DejaVuSans-Bold.ttf", 16);
    protected final DrawOptions greenOption = new DrawOptions();
    protected final DrawOptions redOption = new DrawOptions();

    /**
     * Creates a Panel
     * @param xp coordinate Upper Left
     * @param yp coordinate Upper Left
     * @param panelimage Image of the Panel
     */
    protected Panel(int xp, int yp, Image panelimage){
        super(xp, yp, panelimage);
        panel = panelimage;
        x = xp;
        y = yp;
        greenOption.setBlendColour(Colour.GREEN);
        redOption.setBlendColour(Colour.RED);
    }

    /**
     * Drawing the Panel in the screen
     */
    @Override
    protected void draw(){
        panel.drawFromTopLeft(x, y);
    }

    /**
     * gets the center of a panel which was defined with the x and y as the top left coordinate
     * @return Point representing the center
     */
    @Override
    public Point getCenter(){
        return new Point(this.getImage().getWidth()/2 + x, this.getImage().getHeight()/2 + y);
    }

}
