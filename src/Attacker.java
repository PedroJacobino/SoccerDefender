import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;

import java.sql.Array;
import java.util.ArrayList;

public class Attacker extends Slicer{


    private int damagePower; // to change
    private Tower focus;
    Projectile [] attackerProjs;
    private boolean ready = true;
    private int counterFrames = 0;
    private int projectileCooldown = 3000;
/*
Slicer	Attacker
Health	r.Resistance*5
Speed	r.Speed/4
Radius	r.Radius*20
DamagePower	r.Power
SizeArray	r.Power
 */
    public Attacker(ItemRegister r, Point[] par, int penalty, int reward){
        super(r, par, r.getResistance()*5, penalty, reward);
        this.damagePower = r.getPower();
        this.attackerProjs = new Projectile[r.getPower()]; // depends on the power of the attacker, he will have more footballs to throw
    }



    public int projsSlots(){
        int c = 0;
        for (int i = 0; i<attackerProjs.length; i++){
            if (attackerProjs[i] == null){
                c++;
            }else{
                if (attackerProjs[i] .isFinished()){
                    attackerProjs[i] = null;
                    c++;
                }
            }
        }
        return c;
    }

    public void receiveBall(Projectile p){

        if (projsSlots() > 0){
            //System.out.println("Added new ball to attacker");
            for (int i = 0; i<attackerProjs.length; i++){
                if (attackerProjs[i] == null){
                    attackerProjs[i] = p;
                    break;
                }
            }
        }
    }

    public void chooseTower(ArrayList<Tower> towers){ // change all slicers here
        if(towers != null){
            focus = null;
            for (int i = 0; i < towers.size(); i ++){
                // keeper non targetable
                if (towers.get(i) != null && !(towers.get(i).getPosition().equals("GK")) && towers.get(i).getImage().getBoundingBoxAt(towers.get(i).getCenter()).intersects(this.getRad())) {
                    focus  = towers.get(i);
                }
            }
        }
    }

    private void throwBall(){
        for (Projectile proj : attackerProjs){
            if (proj != null && proj.isTransfered() && !proj.isFinished() && proj.getTargetedTower() == null && focus != null && !focus.isFainted()){
                proj.setTargetTower(focus, damagePower, this.getX(), this.getY()); // throw at the tower
                break;
            } else if (proj != null && proj.isTransfered() && proj.isFinished()){ // proj has finished so set to null
                proj=null;
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

        if (ready && focus != null) {
            throwBall();
            ready =false;
        } else if (ready){ // does not accumulate the time if charged and did not throw yet
            counterFrames=0;
        }

        if ((attackerProjs.length - projsSlots()) != 0){
            for (Projectile proj : attackerProjs){
                if (proj != null && proj.isTransfered() && !proj.isFinished() && proj.getTargetedTower() != null){
                    proj.renderAttacker(gameSpeed);
                } else if (proj != null && proj.isFinished())  {
                    proj = null;
                }
            }
        }
    }

    /**
     *
     * @return the Rectagle representing this Slicer's Radius
     */
    public Rectangle getRad(){
        //Drawing.drawRectangle(this.getX() - this.effectRadius, this.getY() - this.effectRadius, this.effectRadius*2, this.effectRadius*2, Colour.RED);
        return new Rectangle(this.getX() - this.getEffectiveRadius(), this.getY() - this.getEffectiveRadius(), this.getEffectiveRadius()*2, this.getEffectiveRadius()*2);
    }

    public void drawBalls(){

        double adder = this.getImage().getHeight() - 5;
        double counterX = this.getX()+adder;
        double counterY = this.getY();
        int rotation = 0;
        for (int i = 0; i<attackerProjs.length; i++){
            if (attackerProjs[i] != null){
                if (rotation%4 == 0){
                    counterX -= adder;
                    counterY -= adder;
                } else if (rotation%4 == 1){
                    counterX -= adder;
                    counterY += adder;
                } else if (rotation%4 == 2){
                    counterX += adder ;
                    counterY += adder;
                } else if (rotation%4 == 3){
                    counterX += adder;
                    counterY -= adder;
                }
                rotation ++;
                if (rotation <=4){
                    attackerProjs[i].drawAt(new Point(counterX, counterY));
                }
                if (rotation >12) { // show different colours instead of different balls. draw them on paint
                    new Image("res/images/Soccer/Balls/ball_soccer2.png").draw(counterX, counterY);
                } else if (rotation >8) {
                    new Image("res/images/Soccer/Balls/ball_soccer1.png").draw(counterX, counterY);
                } else if (rotation>4){
                    new Image("res/images/Soccer/Balls/ball_soccer4.png").draw(counterX, counterY);
                }
            }
        }
    }


    @Override
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

        drawBalls();
        drawShields();
        drawName();
    }

}
