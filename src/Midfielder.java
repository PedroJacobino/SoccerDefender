import bagel.util.Point;
import bagel.*;
import java.util.ArrayList;

public class Midfielder extends Slicer {


    private int ballGen; // to change
    private Attacker focus;
    private ArrayList<Projectile> p = new ArrayList<Projectile>();
    private boolean ready = true;
    private int counterFrames = 0;
    private int projectileCooldown = 3000;

    public Midfielder(ItemRegister r, Point[] par, int penalty, int reward){
        super(r, par,  r.getResistance()*5, penalty, reward);
        this.ballGen = (int) (3000/((double)r.getPower()/4));

    }


    public void chooseAttacker(ArrayList<Slicer> slicers){
        this.focus = null; // focus null in the beginning
        for( Slicer t: slicers ){

            if ( t != null && t instanceof Attacker  && ((Attacker) t).projsSlots() > 0 && t.x > 0  && this.getEffectiveRadius() > Math.sqrt(Math.pow(t.getX()-this.getX(), 2) + Math.pow(t.getY()-this.getY(), 2) )){
                // only throws if no full for attacker
                this.focus  = (Attacker) t;
            }
        }
    }

    @Override
    public void renderEffect(int gameSpeed){
        counterFrames++;

        if (counterFrames*gameSpeed*1000 >= (60 * (projectileCooldown))){ // cooldown application
            ready = true;
            counterFrames = 0;
        }

        if (ready && focus != null){
            sendBall();
            ready =false;
        } else if (ready && (focus == null)){ //still have to test this might want to delete this
            counterFrames=0;
        }

        //projectiles rendering
        if (!p.isEmpty()){
            for (Projectile proj : p){
                if (!proj.isTransfered()){
                    proj.renderPassive(gameSpeed);
                } // to complicated to remove if not transfered as I have to create an iterator
            }
        }

    }

    private void sendBall(){
        if (focus != null) {
            p.add(new Projectile((int) this.getX(), (int) this.getY(), new Image("res/images/Soccer/Balls/ball_soccer3.png"), focus));
            //.out.println("sending new porjectile");
        }
    }
}
