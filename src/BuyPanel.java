import bagel.DrawOptions;
import bagel.*;
import bagel.util.*;

import java.util.*;

/**
 * Represents a BuyPanel
 */
public class BuyPanel extends Panel{

    private static LinkedHashMap<ItemRegister, String> prod = new LinkedHashMap<ItemRegister, String>();// preserves the insertion order
    private Rectangle Blue;
    private Rectangle Red;
    private Rectangle Green;
    private Rectangle Pink;
    private Rectangle White;

    // for the images of the classes
    private int coordx = 64;
    private int coordy = 50;
    private int distancehor = 100;
    private int scale = 2; // how much smaller

    private Image at = new Image("res/images/BuyPanel/Aim.png");
    private Image mf = new Image("res/images/BuyPanel/Maestro.png");
    private Image df = new Image("res/images/BuyPanel/Wall.png");
    private Image dfm = new Image("res/images/BuyPanel/Tower.png");
    private Image gk =  new Image("res/images/BuyPanel/Glove.png");

    private boolean RedSet = false;
    private boolean GreenSet = false;
    private boolean BlueSet = false;
    private boolean PinkSet = false;
    private boolean WhiteSet = false;
    private int slidingPanelHeight;
    private int slidingPanelWidth;
    private int yposition = (int) (this.panel.getHeight());
    private static boolean slidingPanelIsActivated;
    private DrawOptions colorOption;
    private Font cardFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 23);
    private int level = 0;
    private String Country;

    /**
     * Returns a BuyPanel
     * @param xp Upper x coordinate
     * @param yp Upper y coordinate
     * @param backg background image
     */
    public BuyPanel(int xp, int yp, Image backg){
        super(xp, yp, backg);
        Country = "Brazil";

        colorOption = new DrawOptions();
        for (ItemRegister r : ItemRegister.values()){
            if (r.getCountry().equals(Country) && r.getDifficulty() == 0)  {
                prod.put(r, r.getPosition());
                r.setCardImage();
                //r.setImage();
            }
        }

        Red = new Rectangle(new Point(coordx-at.getWidth()/(scale*2), coordy-at.getHeight()/(scale*2)), at.getWidth()/scale, at.getHeight()/scale);
        Blue = new Rectangle(new Point(coordx+distancehor-mf.getWidth()/(scale*2), coordy-mf.getHeight()/(scale*2)), mf.getWidth()/scale, mf.getHeight()/scale);
        Pink = new Rectangle(new Point(coordx+(2*distancehor)-dfm.getWidth()/(scale*2), coordy-dfm.getHeight()/(scale*2)), dfm.getWidth()/2, dfm.getHeight()/scale);
        Green = new Rectangle(new Point(coordx+(3*distancehor)-df.getWidth()/(scale*2), coordy-df.getHeight()/(scale*2)), df.getWidth()/2, df.getHeight()/scale);
        White = new Rectangle(new Point(coordx+(4*distancehor)-gk.getWidth()/(scale*2), coordy-gk.getHeight()/(scale*2)), gk.getWidth()/2, gk.getHeight()/scale);

    }

    public void addLevel(){
        level++;
        for (ItemRegister r : ItemRegister.values()){
            if (r.getCountry().equals(Country) && r.getDifficulty() == level)  {
                prod.put(r, r.getPosition());
                r.setCardImage();
            }
        }
    }

    /**
     * Returns the products for this buypanel
     * @return products in a Linked Hash Map
     */
    public LinkedHashMap<ItemRegister, String> getProd() {
        return prod;
    }

    /**
     * Returns an ItemRegister
     * @param input input from the game's player
     * @param money money in the game
     * @return Item Register to place
     */
    public ItemRegister render(Input input, int money){
        ItemRegister item = null;
        draw();
        //this.moneyfont.drawString("$" + money, this.panel.getWidth()-200, 65);

        printImage();


        // Count the number of players if the button was activated and set the button property
        if (input.wasPressed(MouseButtons.LEFT)&&Red.intersects(input.getMousePosition())){
            countPanelSize("AT");
            RedSet = true;
            BlueSet = false;
            GreenSet = false;
            WhiteSet=false;
            PinkSet =false;
        } else if (input.wasPressed(MouseButtons.LEFT)&&Blue.intersects(input.getMousePosition())){
            countPanelSize("AM");
            RedSet = false;
            BlueSet = true;
            GreenSet = false;
            WhiteSet=false;
            PinkSet =false;
        } else if (input.wasPressed(MouseButtons.LEFT)&&Green.intersects(input.getMousePosition())){
            countPanelSize("DF");
            RedSet = false;
            BlueSet = false;
            GreenSet = true;
            WhiteSet=false;
            PinkSet =false;
        } else if (input.wasPressed(MouseButtons.LEFT)&&White.intersects(input.getMousePosition())){
            countPanelSize("GK");
            RedSet = false;
            BlueSet = false;
            GreenSet = false;
            WhiteSet=true;
            PinkSet =false;
        } else if (input.wasPressed(MouseButtons.LEFT)&&Pink.intersects(input.getMousePosition())){
            countPanelSize("DM");
            RedSet = false;
            BlueSet = false;
            GreenSet = false;
            WhiteSet=false;
            PinkSet =true;
        }

        // activate the sliding panel for the right button
        if (RedSet){
            item = activateSlidingPanel("AT", Red.left(), input, money);
            if (isOutsideSlidingPanel(input, Red.left())){
                deactivateAll();
            }
        } else if (BlueSet) {
            item = activateSlidingPanel("AM", Blue.left(), input, money);
            if (isOutsideSlidingPanel(input, Blue.left())){
                deactivateAll();
            }
        } else if (GreenSet) {
            item = activateSlidingPanel("DF", Green.left(), input, money);
            if (isOutsideSlidingPanel(input, Green.left())){
                deactivateAll();
            }
        } else if (PinkSet) {
            item = activateSlidingPanel("DM", Pink.left(), input, money);
            if (isOutsideSlidingPanel(input, Pink.left())){
                deactivateAll();
            }
        }else if (WhiteSet) {
            item = activateSlidingPanel("GK", White.left(), input, money);
            if (isOutsideSlidingPanel(input, White.left())){
                deactivateAll();
            }
        }

        // in case the item was unset by clicking with the right button.
        if (item!=null){
            deactivateAll();
        }

        // Printing instructions to screen
        this.textfont.drawString("Key Binds:", this.panel.getWidth()/2, 20);
        this.textfont.drawString("S - Start Wave", this.panel.getWidth()/2, 50);
        this.textfont.drawString("L - Increase Timescale", this.panel.getWidth()/2, 65);
        this.textfont.drawString("L - Decrease Timescale", this.panel.getWidth()/2, 80);

        return item ;
    }


    private void printImage(){
        at.draw(coordx, coordy, new DrawOptions().setScale((double)1/scale, (double)1/scale));
        mf.draw(coordx+distancehor, coordy, new DrawOptions().setScale((double)1/scale, (double)1/scale));
        dfm.draw(coordx+distancehor*2, coordy, new DrawOptions().setScale((double)1/scale, (double)1/scale));
        df.draw(coordx+distancehor*3, coordy, new DrawOptions().setScale((double)1/scale, (double)1/scale));
        gk.draw(coordx+distancehor*4, coordy, new DrawOptions().setScale((double)1/scale, (double)1/scale));
    }

    private ItemRegister activateSlidingPanel(String s, double xposition, Input input, int money){
        slidingPanelIsActivated = true;
        yposition = (int) (this.panel.getHeight());
        Drawing.drawRectangle(xposition, yposition, slidingPanelWidth, slidingPanelHeight, new Colour(0,0,0, 0.7));
        yposition -= 15;
        for (Map.Entry<ItemRegister, String> entry : prod.entrySet()) {
            if (entry.getKey().getPosition().equals(s)){

                // Draw the sprite
                entry.getKey().getImage().draw(xposition + entry.getKey().getImage().getWidth(), yposition += entry.getKey().getImage().getHeight()*1.5);

                // Draw the name
                if (entry.getKey().isOnscreen()){
                    this.textfont.drawString((entry.getKey().getPosition()) + "-" + entry.getKey().getName(), xposition + entry.getKey().getImage().getWidth()*2,
                            yposition,new DrawOptions().setBlendColour(new Colour(0, 0, 0)));
                } else {
                    this.textfont.drawString((entry.getKey().getPosition()) + "-" + entry.getKey().getName(), xposition + entry.getKey().getImage().getWidth()*2,
                            yposition, setColorOption(entry.getKey().getPosition()));
                }

                // Draw the card if mouse over the sprite
                if (entry.getKey().getImage().getBoundingBoxAt(new Point(xposition + entry.getKey().getImage().getWidth(), yposition)).intersects(input.getMousePosition())){
                    SoccerGame.drawCardRightUp(entry.getKey().getCardImage(), cardFont, entry.getKey().getSpeed(), entry.getKey().getEfRadius(), entry.getKey().getPower(), entry.getKey().getResistance());
                }
                if (input.wasPressed(MouseButtons.LEFT) &&  entry.getKey().getImage().getBoundingBoxAt(new Point(xposition + entry.getKey().getImage().getWidth(), yposition)).intersects(input.getMousePosition()) && money >= entry.getKey().getValue()){
                    if (!entry.getKey().isOnscreen()){

                        return entry.getKey();
                    }
                    //System.out.println("Buying " + entry.getKey().getName() + " for " + entry.getValue());
                }


            }
        }
        return null;
    }

    private void countPanelSize(String s){
        slidingPanelHeight = 0;
        slidingPanelWidth = 300;
        slidingPanelHeight += 15;

        for (Map.Entry<ItemRegister, String> entry : prod.entrySet()) {
            if (entry.getKey().getPosition().equals(s)){
                //slidingPanelHeight += entry.getKey().getImage().getHeight()*1.5;
                slidingPanelHeight += 31*1.5;
            }
        }


    }

    public static boolean isSlidignPanelActivated() {
        return slidingPanelIsActivated;
    }

    private boolean isOutsideSlidingPanel(Input input, double xposition){
        return !this.getImage().getBoundingBoxAt(this.getCenter()).intersects(input.getMousePosition()) && !new Rectangle((int) (xposition), (int) (this.panel.getHeight()), slidingPanelWidth, slidingPanelHeight).intersects(input.getMousePosition());
    }

    public void deactivateAll(){
        RedSet = false;
        slidingPanelIsActivated = false;
        BlueSet = false;
        GreenSet = false;
        WhiteSet = false;
        PinkSet = false;
    }

    public DrawOptions setColorOption(String p) {
        if (p.equals("AT")){
            colorOption.setBlendColour(new Colour(0.8, 0, 0));
        } else if (p.equals("AM")){
            colorOption.setBlendColour(new Colour(0, 0.7, 0.6));
        } else if (p.equals("DM")){
            colorOption.setBlendColour(new Colour(50, 0, 128));
        } else if (p.equals("DF")){
            colorOption.setBlendColour(new Colour(0, 0.7, 0));
        } else if (p.equals("GK")){
            colorOption.setBlendColour(Colour.WHITE);
        }
        return this.colorOption ;
    }

    public static void reset(){
        for (Map.Entry<ItemRegister, String> entry : prod.entrySet()) {
            entry.getKey().unsetOnscreen();
        }
    }
}
