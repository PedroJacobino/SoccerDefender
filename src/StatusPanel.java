import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;

/**
 * Represents a Status Panel
 */
public class StatusPanel extends Panel{

    /**
     * Create a status panel in the game
     * @param xp upper left coordinate
     * @param yp upper left coordinat
     * @param backg background image
     */
    public StatusPanel(int xp, int yp, Image backg) {
        super(xp, (int) (yp - backg.getHeight()), backg);
    }

    /**
     * renders theStatus Panel with
     * @param wavenumber int representing current wave in the level
     * @param timescale double representing the timescale set
     * @param status status of the Player
     * @param lives lives the player still has
     */
    public void render(int wavenumber, double timescale, String status, int lives){
        draw();
        textfont.drawString("Wave: "+wavenumber, 10, y + 16);
        if (timescale > 1.0) {
            textfont.drawString("Time Scale: " + timescale, 210, y + 16, greenOption);
        } else {
            textfont.drawString("Time Scale: " + timescale, 210, y + 16);
        }
        textfont.drawString("Status: "+status,  450, y + 16);
        textfont.drawString("Lives: "+lives,  950, y + 16);
    }

}
