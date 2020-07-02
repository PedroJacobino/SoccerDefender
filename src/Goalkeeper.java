import bagel.util.Point;
import java.util.Random;
import java.util.ArrayList;

public class Goalkeeper extends Slicer{


    private int chanceOfRepelling; // to change
    ArrayList<Projectile>  keeperProjs;
    Random rand;

    public Goalkeeper(ItemRegister r, Point[] par, int penalty, int reward){
        super(r, par, r.getResistance()*7, penalty, reward);
        this.chanceOfRepelling = r.getPower()/2;
        keeperProjs = new ArrayList<Projectile>();
        rand = new Random();
    }
    // in this case will only retrieve if the random number chosen is less than the power/2 of the goalkeeper, so it does not happen that often
    public boolean retrieves(){
        return (rand.nextInt(10)<chanceOfRepelling);
    }

    public void receiveBall(Projectile p){

        if (keeperProjs != null){
            System.out.println("Added new ball to attacker");
            keeperProjs.add(new Projectile((int)this.getX(), (int)this.getY(), p.getImage(), p.getFromActTower(), p.getDamageCaused()));
        }
    }

    public void renderEffect(int gameSpeed){

        for (Projectile proj : keeperProjs){
            if (proj != null && !proj.isFinished() && proj.getTargetedTower() != null){
                proj.renderAttacker(gameSpeed);
            } else if (proj != null && proj.isFinished())  {
                proj = null;
            }
        }
    }

}