import bagel.util.Point;

public class Defender extends Slicer{


    private int shieldLife;
    private int counterFrames=0;
    private int shieldGenerationCooldown; // 2 seconds

    /*
    Health	r.Resistance*10
    Speed	r.Speed/4
    Radius	r.Radius*20
    ShieldLife	r.Power/4
    ShieldGenCD	2000/(r.Speed/4)
     */
    public Defender(ItemRegister r, Point[] par, int penalty, int reward){
        super(r, par, r.getResistance()*7, penalty, reward);
        this.shieldLife = (r.getResistance()*7)/4;
        this.receiveShield(new Shield(shieldLife));
        this.receiveShield(new Shield(shieldLife));
        shieldGenerationCooldown = (int) (10000/((double)r.getPower()/4));
    }

    private void regenerateAShield(){
        if (!this.shieldsFilled()){
            for (Shield s : this.shields){
                if (s != null){
                    if (s.isBroken()){
                        this.receiveShield(new Shield(shieldLife)); //creates new shield if last one was broken then breaks the iteration
                        //System.out.println("Created new shield because there was space ");
                        break;
                    } else if (s.getInitialresistance() > s.getResitance()){
                        s.regenerate(); // comes back to initial resistance and breaks
                        //System.out.println("regenerated the resitance of the shield");
                        break;
                    }
                } else {
                    this.receiveShield(new Shield(shieldLife)); // generates new shield in the place
                    //System.out.println("Created new shield because there was space ");
                    break;
                }
            }
        }
    }

    public void renderEffect(int gameSpeed){
        counterFrames++;
        if (counterFrames*gameSpeed*1000 >= (60 * (shieldGenerationCooldown))){ // cooldown application
            regenerateAShield();
            counterFrames = 0;
        }
    }




}