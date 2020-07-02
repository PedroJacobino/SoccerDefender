import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.*;



public class HijackerTower extends Tower {



    private int power; // how much to damage the slicer
    private DrawOptions options;
    private Image kick;


    public HijackerTower(ItemRegister r, int x, int y) {
        super(r,"-Defender", x, y, r.getResistance()); // show the defender for the tower
        this.power = (r.getPower()*50);
        this.options = new DrawOptions();
        this.kick = r.playerImages().getRight();
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
                    if (this.getImage().getBoundingBoxAt(this.getCenter()).intersects(s.getCenter())){
                        s.setThrow();
                        s.receiveDamage(this.power);
                        this.setFainted();
                    }
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
    } // angle setting

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


