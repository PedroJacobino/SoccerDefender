import bagel.*;
import bagel.util.Colour;
import bagel.util.Vector2;
import bagel.util.*;
import java.util.*;

/**
 * Represents an Active Tower
*/
public class ActiveTower extends Tower{


    private double projectileCooldown;
    private Slicer focus = null;
    private Image projectileImage;
    private boolean canThrow = true;
    private int damage;
    private DrawOptions options;
    private ArrayList<Projectile> p = new ArrayList<Projectile>();
    private int counterFrames = 0;
    private int damageBoost = 0;

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     * @param x The x coordinate of the Active Tower
     * @param y The y coordinate of the Active Tower
     * @param r register for the information of the item
     * @param projImage projectile Image
     * @return ActiveTower with these parameters
     */
    public ActiveTower(ItemRegister r, int x, int y, Image projImage){
        super(r, x, y, r.getResistance()*5);
        this.projectileCooldown = (int) (1500/((double)r.getSpeed()/4));
        this.projectileImage = projImage;
        this.damage = r.getPower();
        this.options = new DrawOptions();
    } //constructor

    /**
     *
     * @return The projectile for this ActiveTower
     */
    public Projectile throwProjectile(){
        return new Projectile(this.getX(), this.getY(), projectileImage, focus, damage+damageBoost, this);
    } // projectile creation

    private double angleVector(Slicer a){
        return Math.atan2(a.y-this.getY(), a.x-this.getX());
    } // angle setting

    private double angleVector(Vector2 a){
        return Math.atan2(a.y, a.x);
    }

    /**
     * Facing of the slicer
     * @param s Slicer to face
     */
    public void faceEnemy(Slicer s){
        if (s!=null){
            double x = angleVector(s);

            options.setRotation(x);
            if (canThrow){
                //System.out.println("creating new Projectile");
                p.add(throwProjectile());
                canThrow = false;
            }
        } else if (canThrow){ // does not accumulate over and ove the counter even if no slicer to throw at
            counterFrames = 0;
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

    /**
     * chooses the slicer
     * @param slicers ArrayList with all the slicers currently in the game
     */
    public void chooseSlicer(ArrayList<Slicer> slicers){ // change all slicers here

        if(slicers != null){
            focus = null;
            for (int i = 0; i < slicers.size(); i ++){

                if (slicers.get(i) != null && slicers.get(i).getImage().getBoundingBoxAt(slicers.get(i).getCenter()).intersects(this.getRad())) {
                    focus  = slicers.get(i);

                }
            }
        }
    } // slicer choosing

    /**
     * Renders the Tower
     * @param gameSpeed The speed set by the player
     */
    public void render(int gameSpeed){
        counterFrames++;

        if (counterFrames*gameSpeed*1000 >= (60 * (projectileCooldown))){ // cooldown application

            canThrow = true;
            counterFrames = 0;
        }

        faceEnemy(focus);

        this.draw();
        this.drawName();

        if (!p.isEmpty()){
            for (Projectile proj : p){
                if (!proj.isFinished()){
                    proj.render(gameSpeed);
                }
            }
        }
        damageBoost = 0;
    } // rendering the object

    @Override
    protected void draw() {
        if (options != null){
            this.getImage().draw(this.getX(), this.getY(), options);
        }
    } // drawing object

    public void increaseDamageBoost(int boost){
        if (damageBoost + boost < 150){

        damageBoost += boost;

        } else {
            damageBoost = 150;
        }
    }
}
