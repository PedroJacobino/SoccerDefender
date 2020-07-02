import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.*;

/**
 * represents a Projectile
 */
public class Projectile extends Sprite {
    private Slicer target;
    private Attacker targetedAttacker;
    private Tower targetedTower;
    private int damageCaused;
    private Vector2 v;
    private boolean finished = false;
    private boolean transfered = false;
    private ActiveTower fromActTower; // keeps the reference of the tower which send it in case the keeper throws it back


    /**
     * Creates a new Projectile
     * @param x initial coordinate
     * @param y inital coordinate
     * @param image Image of this Projectile
     * @param target Slicer target to hit
     * @param damage Damage it will do to the target
     */
    public Projectile(int x, int y, Image image, Slicer target, int damage, ActiveTower t){ // create a projectile to hit the slicer
        super(x, y, image);
        fromActTower = t;
        this.target = target;
        damageCaused = damage;
        //System.out.println("Created Proj with damage " + damageCaused);
    }

    public Projectile(int x, int y, Image image, Attacker target){ // creating a proj to send it to the attacker
        super(x, y, image);
        this.targetedAttacker = target;
        //System.out.println("Created Proj for attacker");
    }

    public Projectile(int x, int y, Image image, ActiveTower target, int damage){ // creating a proj to send it to the attacker
        super(x, y, image);
        this.targetedTower = target;
        damageCaused = damage;
        //System.out.println("Created Proj for keeper");
    }

    public int getDamageCaused() {
        return damageCaused;
    }

    public void setTargetTower(Tower t, int damage, double x, double y){
        targetedTower = t;
        this.damageCaused = damage;
        this.setX((int) x);
        this.setY((int) y);
    }

    public Tower getTargetedTower() {
        return targetedTower;
    }

    /**
     * move with the given
     * @param speed Game Speed
     */
    public void move(int speed){
        if (target != null){
            moveTowards(target.getCenter(), speed);
        }

        if (targetedAttacker != null && !transfered){
            moveTowards(targetedAttacker.getCenter(), speed);
        }

        if (targetedTower != null && !finished){
            moveTowards(targetedTower.getCenter(), speed);
        }

        this.setX((int) (this.getX() + v.x*5)); //default speed set to 5
        this.setY((int) (this.getY() + v.y*5)); //default speed set to 5
    }

    /**
     * Move Towards the Target
     * @param point Where the Target location is
     * @param speed Game Speed
     */
    // method to change the vector so it points to the next point in the polyline
    protected void moveTowards(Point point, int speed){
        v = new Vector2(point.x - this.getX(), point.y - this.getY());
        v = v.normalised();
        v = new Vector2(v.x*speed, v.y*speed);

    }

    /**
     * Projectile state
     * @return wether it is done with its purpose
     */
    public boolean isFinished(){
        return finished;
    }

    /**
     * Get center of the projectile accounting for the vectore (checking if it will the target)
     * @return the center as Point
     */
    public Point getCenterProj(){
        return (new Point(this.getX(), this.getY())); // changed for testing
    }

    public void dealDamageToTarget(){
        target.receiveDamage(this.damageCaused);
    }

    public ActiveTower getFromActTower() {
        return fromActTower;
    }

    public void render(int speed){

        move(speed);


        Rectangle srect = target.getImage().getBoundingBoxAt(target.getCenter());

        // hitted the target
        if (srect.intersects(this.getCenterProj()) && !finished){
            if (target instanceof Goalkeeper &&((Goalkeeper) target).retrieves()){
                ((Goalkeeper) target).receiveBall(this);
                finished =true;
            } else{
                finished = true;
                dealDamageToTarget();
            }

        }
        this.draw();
    }

    public void renderAttacker(int speed){ // render when the attacker has it
        move(speed);

        Rectangle srect = targetedTower.getImage().getBoundingBoxAt(targetedTower.getCenter());

        // hitted the target
        if (srect.intersects(this.getCenterProj()) && !finished){
            finished = true;
            dealDamageToTargetTower();
        }
        this.draw();
    }

    public void dealDamageToTargetTower(){
        //System.out.println("dealing damage to the tower");
        targetedTower.receiveDamage(damageCaused);
    }

    public void renderPassive(int speed ){
        move(speed);


        Rectangle srect = targetedAttacker.getImage().getBoundingBoxAt(targetedAttacker.getCenter());

        // hitted the target
        if (srect.intersects(this.getCenterProj()) && !finished){
            transferToTarget();
            transfered = true;
        }
        this.draw();
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void transferToTarget(){
        if (targetedAttacker != null){
            targetedAttacker.receiveBall(this);
        }
    }



    public void drawAt(Point p){
        this.getImage().draw(p.x, p.y);
    }

}
