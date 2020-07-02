import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.*;


public class WallTower extends Tower{

    private int power; // how much to push the player
    private DrawOptions options;
    private double push = 0.0;


    public WallTower(ItemRegister r, int x, int y) {
        super(r,"-Defender", x, y, r.getResistance()*20); // show the defender for the tower

        this.power = r.getPower();
        this.options = new DrawOptions();

    }



    /**
     * Receive the damage specified
     * @param damage int representing damage to take
     */
    public void receiveDamage(int damage){
        if (health - damage <= 0){
            health = 0;
            this.setFainted();
        } else {
            health -= damage;
        }
    }

    public int getPower() {
        return power;
    }

    public void render(ArrayList<Slicer> slicers){
        this.draw();
        this.drawName();
        if (slicers != null){
            for (Slicer s : slicers){

                if ((s != null) && this.getRad().intersects(s.getCenter())){
                    faceEnemy(s);
                    s.getPushedBack( push+=0.5);
                    receiveDamage(s.getDamage());
                }

            }
        }

    }


    @Override
    protected void draw() {
        if (options != null){
            this.getImage().draw(this.getX(), this.getY(), options);
        }
    } // drawing object

    private double angleVector(Slicer a){
        return Math.atan2(a.y-this.getY(), a.x-this.getX());
    }// angle setting

    /**
     * Facing of the slicer
     * @param s Slicer to face
     */
    public void faceEnemy(Slicer s){
        if (s!=null){
            double x = angleVector(s);
            options.setRotation(x);
        }
    }

    /**
     *
     * @return the Rectagle representing this Tower's Radius
     */
    public Rectangle getRad(){
        //Drawing.drawRectangle(this.getX() - this.effectRadius, this.getY() - this.effectRadius, this.effectRadius*2, this.effectRadius*2, Colour.RED);
        return new Rectangle(this.getX() - this.effectRadius, this.getY() - this.effectRadius, this.effectRadius*2, this.effectRadius*2);
    }

}
