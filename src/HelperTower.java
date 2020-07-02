import bagel.Image;
import java.util.*;

public class HelperTower extends Tower {

    private int effectiveBoosting;
    /**
     * Create a Tower
     *
     * @param x     coordinate
     * @param y     coordinate
     * @param r register to use
     */
    public HelperTower(ItemRegister r, int x, int y) {
        super(r, x, y, r.getResistance()*5);
        this.effectiveBoosting = (r.getPower()/2);
    }

    public void render(ArrayList<ActiveTower> actT){
        this.draw();
        this.drawName();
        for(ActiveTower t: actT){
            if (t != null && this.effectRadius > Math.sqrt(Math.pow(t.getX()-this.getX(), 2) + Math.pow(t.getY()-this.getY(), 2) )){
                t.increaseDamageBoost(effectiveBoosting);
            } //else if (t!= null && t.isFainted()) {
                //t =null;
            //}
        }
    }
}
