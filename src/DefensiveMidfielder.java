import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class DefensiveMidfielder extends Slicer {


    // attributes for functioning
    private Slicer focus;
    private ArrayList<Shield> shields = new ArrayList<Shield>();
    private boolean ready = true;
    private int counterFrames = 0;

    // Stats
    private int shieldsLife; // to change
    private int shieldGen;

    public DefensiveMidfielder(ItemRegister r, Point[] par, int penalty, int reward) {
        super(r, par, r.getResistance()*7, penalty, reward);
        this.shieldsLife = r.getPower();
        shieldGen = (int) (3000/((double)r.getPower()/4)); // still have to test if working
    }


    public void chooseSlicer(ArrayList<Slicer> slicers) {
        this.focus = null; // focus null in the beginning
        for (Slicer t : slicers) {

            if (t != null && !(t instanceof Defender) && t!= this && t.shieldSlots() > 0 && t.x > 0 && this.getEffectiveRadius() > Math.sqrt(Math.pow(t.getX() - this.getX(), 2) + Math.pow(t.getY() - this.getY(), 2))) {
                // only throws if no full for attacker
                this.focus = t;
            }
        }
    }
    @Override
    public void renderEffect ( int gameSpeed){
        counterFrames++;

        if (counterFrames * gameSpeed * 1000 >= (60 * (shieldGen))) { // cooldown application
            ready = true;
            counterFrames = 0;
        }

        if (ready && focus != null) { // if ready to send the shield and focus is not null otherwise if ready and no focus restart the counter
            sendBall();
            ready = false;
        } else if (ready && (focus == null)) { //still have to test this might want to delete this
            counterFrames = 0;
        }

        if (!shields.isEmpty()) {
            for (Shield s : shields) {
                if (!s.isTransfered()) {
                    s.renderPassive(gameSpeed);
                }
            }
        }
    }
    private void sendBall () {
        if (focus != null) {
            shields.add(new Shield((int) this.x, (int) this.y, focus, shieldsLife));
        }
    }
}