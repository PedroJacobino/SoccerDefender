import bagel.util.*;
import bagel.*;

/**
 * Represents a Tower in the game
 */
public abstract class Tower extends Sprite{


    private int[] stats = new int[4];
    private boolean fainted = false;



    protected int effectRadius;
    private String name;
    private String p;
    protected int health;
    protected int initialhealth;

    private Font f = new Font("res/fonts/DejaVuSans-Bold.ttf", 16);
    protected final DrawOptions drawOption = new DrawOptions();


    /**
     * Create a Tower
     * @param x coordinate
     * @param y coordinate
     *
     */
    public Tower(ItemRegister r, int x, int y, int health){
        super(x, y, r.playerImages().getStanding(), r.getCardImage());


        stats[0]= (r.getSpeed());
        stats[1]= (r.getPower());
        stats[2]= (r.getResistance());
        stats[3]= (r.getEfRadius());

        this.effectRadius = r.getEfRadius()*20;
        this.name = r.getName();
        p = r.getPosition();
        // Set the name string to show on top of character
        if (r.getPosition().equals("AT")){
            drawOption.setBlendColour(Colour.RED);
        } else if (r.getPosition().equals("AM")){
            drawOption.setBlendColour(Colour.BLUE);
        } else if (r.getPosition().equals("DM")){
            drawOption.setBlendColour(new Colour(50, 0, 128));
        } else if (r.getPosition().equals("DF")){
            drawOption.setBlendColour(Colour.GREEN);
        } else if (r.getPosition().equals("GK")){
            drawOption.setBlendColour(Colour.WHITE);
        }
        this.initialhealth = health;
        this.health = health;

    }

    // Tower with only file loaded not the Image before so you can
    public Tower(ItemRegister r, String file, int x, int y, int health){
        super(x, y, r.playerImages().getDefender(), r.getCardImage());

        stats[0]= (r.getSpeed());
        stats[1]= (r.getPower());
        stats[2]= (r.getResistance());
        stats[3]= (r.getEfRadius());

        this.effectRadius = r.getEfRadius()*20;
        this.name = r.getName();
        p = r.getPosition();
        // Set the name string to show on top of character
        if (p.equals("AT")){
            drawOption.setBlendColour(new Colour(0.8, 0, 0));
        } else if (p.equals("AM")){
            drawOption.setBlendColour(new Colour(0, 0.7, 0.6));
        } else if (p.equals("DM")){
            drawOption.setBlendColour(new Colour(50, 0, 128));
        } else if (p.equals("DF")){
            drawOption.setBlendColour(new Colour(0, 0.7, 0));
        } else if (p.equals("GK")){
            drawOption.setBlendColour(Colour.WHITE);
        }
        this.initialhealth = health;
        this.health = health;
    }

    public int getStatSpeed(){
        return stats[0];
    }

    public int getStatPower(){
        return stats[1];
    }

    public int getStatResistance(){
        return stats[2];
    }

    public int getStatRadius(){
        return stats[3];
    }

    public String getPosition() {
        return p;
    }

    public boolean isFainted() {
        return fainted;
    }

    protected void setFainted() { fainted = true;}

    /**
     * renders the tower to the game by drawing it
     */
    public void render(){
        draw();
    }

    public void drawCircle(){
        Drawing.drawCircle(new Point(this.getX(), this.getY()), effectRadius, new Colour(128, 255, 0, 0.5)); //make it opaque
    }

    public void receiveDamage(int damage){
        if (health - damage <= 0){
            setFainted();
        } else{
            health -= damage;
        }
    }


    public void drawName(){
        if (SoccerGame.infoLevel >= 2){
            f.drawString(p +"-"+ name, this.getX() - name.length()*9/2, this.getY() - 20, drawOption);
        } else if (SoccerGame.infoLevel == 1){
            f.drawString(p, this.getX() - 10, this.getY() - 20, drawOption);
        }

        if (SoccerGame.infoLevel >=1){
            drawLife();
        }
    }

    public void drawLife(){
        f.drawString( ""+ this.health, this.getX() - 10, this.getY() + 40, new DrawOptions().setBlendColour(Colour.BLACK));
    }

    public void regen(){
        this.health = initialhealth;
    }

    public String getName() {
        return name;
    }
}
