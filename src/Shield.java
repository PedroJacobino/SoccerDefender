import bagel.Drawing;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import bagel.util.Colour;

public class Shield {
    private Slicer targetedSlicer;
    private int initialresistance;
    private int resitance;
    private Vector2 v;
    private boolean broken = false;
    private boolean transfered = false;
    private int coordx;
    private int coordy;
    private int width;
    private int height;


    public Shield(int x, int y, Slicer target, int resistance){
        coordx = x;
        coordy = y;
        width = 20;
        initialresistance = resistance;
        height = resistance;
        this.targetedSlicer = target;
        this.resitance = resistance;
    }

    public Shield(int resistance){
        width = 20;
        height = resistance;
        initialresistance = resistance;
        this.resitance = resistance;
    }

    public int getInitialresistance() {
        return initialresistance;
    }

    public void regenerate(){
        resitance = initialresistance;
    }

    public static Shield standardShield(){
        return new Shield(16); // standard shields have 5 of resistance
    }

    public void printShieldHorizontal(int x, int y) {
        if (SoccerGame.infoLevel == 3 && !broken){
            Drawing.drawRectangle(x - width / 2, y - height / 2, width, resitance, new Colour(0, 0, 0, 0.50));
        }
    }
    public void printShieldVertical(int x, int y) {
        if (SoccerGame.infoLevel == 3 && !broken){
            Drawing.drawRectangle(x - height / 2, y - width / 2, resitance, width, new Colour(0, 0, 0, 0.50));
        }
    }

    /**
     * move with the given
     * @param speed Game Speed
     */
    public void move(int speed){

        if (targetedSlicer != null && !broken){
            moveTowards(targetedSlicer.getCenter(), speed);
        }

        coordx += v.x*10;
        coordy += v.y*10;
    }

    protected void moveTowards(Point point, int speed){
        v = new Vector2(point.x - this.coordx, point.y - this.coordy);
        v = v.normalised();
        v = new Vector2(v.x*speed, v.y*speed);
    }

    public boolean isBroken(){
        return broken;
    }

    public Point getCenterShield(){
        return (new Point(coordx, coordy)); // changed for testing
    }

    public void renderPassive(int speed ){
        move(speed);
        Rectangle srect = targetedSlicer.getImage().getBoundingBoxAt(targetedSlicer.getCenter());
        // hitted the target
        if (srect.intersects(this.getCenterShield()) && !broken){
            transferToTarget();
            transfered = true;
        }
        printShieldVertical(coordx, coordy);
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void transferToTarget(){
        if (targetedSlicer != null){
            targetedSlicer.receiveShield(this);
        }
    }

    public int receiveDamage(int damage){

        int temp;
        if (this.resitance -damage <= 0){
            temp = (damage - this.resitance);
            this.resitance = 0;
            broken = true;

            return temp;
        } else {

            this.resitance -= damage;
            return 0;
        }
    }

    public int getResitance() {
        return resitance;
    }
}



