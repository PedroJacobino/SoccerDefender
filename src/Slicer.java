import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.Random;

/**
 * Represents the Slicers
 */
public class Slicer {

    // get thrown animation working
    public static DrawOptions rotOption = new DrawOptions();
    public static double rotAnimation = 0.0;
    public static boolean canbethrown = false;
    public static Image thrown;
    public static double xT = 0;
    public static double yT = 0;
    public static double randomX;
    public static double randomY;


    // keep record
    private int[] stats = new int[4];

    // variables and constant for the functionning of the slicer instance
    protected double x;
    protected double y;
    protected final Image slicer;
    private final Image cardImage;
    private final int effectiveRadius;
    protected Vector2 vector;
    protected final DrawOptions options;
    protected Point[] points;
    protected int next_point = 0;
    private boolean not_moving = false;
    private int health;
    private int initialhealth;
    private int penalty;
    private int reward;
    private boolean dead = false;
    protected final Image rightFirst; // Walking Image
    protected final Image leftFirst; // Walking Image
    protected int counter = 0;
    protected Shield[] shields = new Shield[4]; // all slicers have 4 shields.

    private String name;
    private String position;
    private Font f = new Font("res/fonts/DejaVuSans-Bold.ttf", 16);
    protected final DrawOptions drawOption = new DrawOptions();


    private int speedNerf = 1;
    private int speedBoost = 1;
    private double normalSpeed;




    /**
     * Creates a Slicer with the params
     * @param par point array it has to traverse
     * @param health its health
     * @param penalty the penalty
     * @param reward the reward from killing it
     */
    public Slicer(ItemRegister r, Point[] par, int health, int penalty, int reward){

        stats[0]= (r.getSpeed());
        stats[1]= (r.getPower());
        stats[2]= (r.getResistance());
        stats[3]= (r.getEfRadius());

        initialhealth = health; // change depending on position how to find it
        cardImage = r.getCardImage();
        effectiveRadius = r.getEfRadius()*20;
        points = par;
        x = points[0].x; // spawn in the first point of the polyline
        y = points[0].y; // spawn in the first point of the polyline
        slicer = r.playerImages().getStanding();
        this.rightFirst = r.playerImages().getRight();
        this.leftFirst = r.playerImages().getLeft();
        //unit vector
        vector = new Vector2(Vector2.right.x, Vector2.right.y);
        options = new DrawOptions(); // options used for turning the slicer so it faces its movement
        this.health = health;
        this.penalty = penalty;
        this.reward = reward;

        this.normalSpeed = (double) r.getSpeed()/4;
        this.name = r.getName();
        this.position = r.getPosition();
        setColour();
    }

    private void setColour(){
        if (position.equals("AT")){
            drawOption.setBlendColour(new Colour(0.8, 0, 0));
        } else if (position.equals("AM")){
            drawOption.setBlendColour(new Colour(0, 0.7, 0.6));
        } else if (position.equals("DM")){
            drawOption.setBlendColour(new Colour(50, 0, 128));
        } else if (position.equals("DF")){
            drawOption.setBlendColour(new Colour(0, 0.7, 0));
        } else if (position.equals("GK")){
            drawOption.setBlendColour(Colour.WHITE);
        }
    }

    public void setThrow(){
        Slicer.thrown = this.getImage();
        Slicer.xT = this.getX();
        Slicer.yT = this.getY();
        Slicer.randomX = ((Math.random() * (1.0 - -1.0)) + -1.0);
        Slicer.randomY = ((Math.random() * (1.0 - -1.0)) + -1.0);
        Slicer.canbethrown = true;
    }

    public static void getThrown(){
        if (Slicer.thrown != null && Slicer.canbethrown && Slicer.xT > 0 &&Slicer.yT >0 && Slicer.xT <Window.getWidth() && Slicer.yT < Window.getHeight()) {
            Slicer.thrown.draw(xT += randomX*5, yT += randomY*5, rotOption.setRotation(rotAnimation += 0.2));
        }else {
            Slicer.canbethrown = false;
        }
    }

    public int getStatSpeed(){
        return stats[0];
    }

    public int getStatPower(){
        return stats[1];
    }

    public int getStatResistance(){
        return stats[2];
    }

    public int getStatRadius(){
        return stats[3];
    }

    /**
     * Gets the health of this slicer
     * @return int representing the health
     */
    public int getInitialHealth() {
        return initialhealth;
    }

    public int getEffectiveRadius() {
        return effectiveRadius;
    }

    public void drawCircle(){
        Drawing.drawCircle(new Point(this.getX(), this.getY()), this.effectiveRadius, new Colour(128, 255, 0, 0.5)); //make it opaque
    }

    public Image getCardImage(){
        return cardImage;
    }

    public int getDamage() {
        return penalty;
    }

    /**
     * Gets the center of the Slicer
     * @return the Center as a point
     */
    public Point getCenter(){
        return new Point(x, y);
    }

    /**
     * gets the Slicer Image
     * @return the image for this slicer
     */
    public Image getImage(){
        return slicer;
    }

    /**
     * destroys the slicer, setting the states to done
     */
    public void destroyed(){
        dead = true;
        not_moving = true;
    }

    /**
     * Receive the damage specified
     * @param damage int representing damage to take
     */
    public void receiveDamage(int damage){
        if (calculateTotalShield() >= damage){ //only inflict damage on shield
            //inflict damage to shield possibly destroying all
            inflictDamageShield(damage);
        } else{
            if (calculateTotalShield()+health - damage <= 0 ){ // only inflict damage on slicer and slicer will die
                health = 0;
                SoccerGame.increaseCash(this.reward);
                // destroy shields
                inflictDamageShield(calculateTotalShield());
                destroyed();
            } else if (calculateTotalShield() == 0) { // no shield so all damage goes to health
                health -= damage;
            } else { // shield gets destroyed but rest of damage goes to health
                health -= damage - calculateTotalShield();
                // destroy shields
                inflictDamageShield(calculateTotalShield());
            }
        }
    }

    public void inflictDamageShield(int damage){

        if (damage > calculateTotalShield()){
            damage = calculateTotalShield();
        }
        int c = damage;

        for (int i = 0; i<shields.length; i++){
            if (shields[i] != null){
                if (shields[i].isBroken()){
                    shields[i] = null;
                } else {
                    c = shields[i].receiveDamage(c);
                }
            }
        }

    }

    public int calculateTotalShield(){
        int c = 0;
        for (int i = 0; i<shields.length; i++){
            if (shields[i] != null){
                if (shields[i].isBroken()){
                    shields[i] = null;
                } else {
                    c += shields[i].getResitance();
                }
            }
        }
        //System.out.println("Total Shield:" + c);
        return c;
    }

    public void getPushedBack(double power){
        if (Math.abs(this.vector.x) > Math.abs(this.vector.y*2)){
            if (this.vector.x < 0){
                this.x += power;
            } else {
                this.x -= power;
            }
        } else if (Math.abs(this.vector.y) > Math.abs(this.vector.x*2)){
            if (this.vector.y < 0){
                this.y += power;
            } else {
                this.y -= power;
            }
        } else {
            if (this.vector.x < 0){
                this.x += power;
            } else {
                this.x -= power;
            }
            if (this.vector.y < 0){
                this.y += power;
            } else {
                this.y -= power;
            }
        }
    }

    /**
     * Know when the slicer has traversed the entire line
     * @return state o not moving as a boolean
     */
    public boolean isNot_moving() {
        return not_moving;
    }

    /**
     * Get the x coordinate
     * @return int representing x coordinate
     */
    public double getX(){
        return x;
    }

    /**
     * Get the x coordinate
     * @return int representing x coordinate
     */
    public double getY(){
        return y;
    }

    /**
     * code to make the slicer move following its vector every frame (each time it is rendered)
     * @param speed Game Speed state
     */
    public void move(int speed){
        if ((Math.abs(points[next_point].x - x) < speed*normalSpeed*speedNerf) && (Math.abs(points[next_point].y - y) < speed*normalSpeed*speedNerf) ){
            next_point++;
        }
        if (next_point < points.length) {
            moveTowards(points[next_point], speed);
        }
        x += vector.x*normalSpeed*speedBoost/speedNerf;
        y += vector.y*normalSpeed*speedBoost/speedNerf;
    }

    /**
     * method to change the vector so it points to the next point in the polyline
     * @param point Next point in the polyline
     * @param speed Game Speed
     */
    protected void moveTowards(Point point, int speed){
            vector = new Vector2(point.x - this.x, point.y - this.y);
            vector = vector.normalised();
            vector = new Vector2(vector.x*speed, vector.y*speed);

    }

    /**
     *  method to get the angle of the vector, in order to turn the image the right angle
     * @param a vector of the Slicer
     * @return a double representing the angle in radians
     */
    private double angleVector(Vector2 a){
        return Math.atan2(a.y, a.x);
    }

    /**
     * code to render everytime the slicer is called in the update method
     * @param speed Game Speed
     */
    public void render(int speed){
        if (next_point < points.length) {
            move(speed);
            options.setRotation(angleVector(vector));
            draw(speed);
        }else{
            SoccerGame.damageLife(this.penalty);
            not_moving = true;
        }


        speedNerf = 1; // reset the nerf each time
        speedBoost = 1;

        renderEffect(speed);

    }

    public int shieldSlots(){
        int c = 0;
        for (int i = 0; i<shields.length; i++){
            if (shields[i] == null){
                c++;
            }else{
                if (shields[i] .isBroken()){
                    shields[i] = null;
                    c++;
                }
            }
        }
        return c;
    }

    protected boolean shieldsFilled() { // still have to test
        for (int i = 0; i < shields.length; i++) {
            if (shields[i] == null) {
                return false; // returning that there is no shield in a slot so can regenerate
            } else {
                if (shields[i].isBroken()) {
                    shields[i] = null;
                    return false;
                    // not initial resistance so can regenerate
                } else if (shields[i].getInitialresistance() > shields[i].getResitance()) {
                    return false;
                }
            }
        }
        return true;
    }


    public void receiveShield(Shield shieldReceived){
        if (shieldSlots() > 0) {
            for (int i = 0; i < shields.length; i++) {
                if (shields[i] == null) {
                    shields[i] = shieldReceived;
                    break;
                }
            }
        }
        //System.out.println("added new Shield to this Slicer which is not a Defender");
    }

    public void renderEffect(int speed){ // kind of abstract but still do not know what to do with it.

    }
    public void drawShields(){
        double adder = this.getImage().getHeight() - 10;
        double counterX = this.getX()+adder;
        double counterY = this.getY();
        int rotation = 0;
        for (int i = 0; i<shields.length; i++){
            if (shields[i] != null && !shields[i].isBroken()){

                if (rotation == 0){
                    counterX -= adder;
                    counterY -= adder;
                    shields[i].printShieldHorizontal((int)counterX, (int)counterY);
                } else if (rotation == 1){
                    counterX -= adder;
                    counterY += adder;
                    shields[i].printShieldVertical((int)counterX, (int)counterY);
                } else if (rotation == 2){
                    counterX += adder ;
                    counterY += adder;
                    shields[i].printShieldHorizontal((int)counterX, (int)counterY);
                } else if (rotation == 3){
                    counterX += adder;
                    counterY -= adder;
                    shields[i].printShieldVertical((int)counterX, (int)counterY);
                }
                rotation ++;
            }
        }
    }

    public void drawName(){
        if (SoccerGame.infoLevel >= 2){
            f.drawString(position + "-" + name, this.getX() - name.length()*6, this.getY() + 25, drawOption); // draw the name
        } else  if (SoccerGame.infoLevel == 1){
            f.drawString(position, this.getX() - 10, this.getY() + 25, drawOption); // draw the name
        }
    }

    public void slowDownBy(int s){ //called each time
        if (speedNerf+s< 5){ // 5 is the maximum decrease in speed
            speedNerf+=s;
        } else {
            speedNerf = 5;
        }
    }

    public void draw(int speed){
        counter++;
        if (counter < 10/speed) {
            slicer.draw(this.x, this.y, options);
        }else if (counter < 20/speed){
            rightFirst.draw(this.x, this.y, options);
        } else if (counter < 30/speed){
            slicer.draw(this.x, this.y, options);
        } else if (counter < 40/speed){
            leftFirst.draw(this.x, this.y, options);
        } else {
            slicer.draw(this.x, this.y, options);
            counter =0;
        }
        drawName();
        drawShields();
    }

    // draws the card to the screen, with the stats
    public void drawCard(){

    }

}
