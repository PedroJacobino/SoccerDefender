import bagel.Image;
import bagel.util.Rectangle;

import java.util.*;


public class NerfTower extends Tower{

    private int effectiveNerfing;
    /**
     * Create a Tower
     *
     * @param x     coordinate
     * @param y     coordinate
     *
     */
    public NerfTower(ItemRegister r, int x, int y) {
        super(r, x, y, r.getResistance()*7);
        this.effectiveNerfing = r.getPower()/3;
    }

    public void render(ArrayList<Slicer> slicers){
        this.draw();
        this.drawName();

        if(slicers != null){

            for (int i = 0; i < slicers.size(); i ++){

                if (slicers.get(i) != null
                        && this.effectRadius > Math.sqrt(Math.pow(slicers.get(i).getX()-this.getX(), 2) + Math.pow(slicers.get(i).getY()-this.getY(), 2) )) {
                      slicers.get(i).slowDownBy(effectiveNerfing);

                }
            }
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
