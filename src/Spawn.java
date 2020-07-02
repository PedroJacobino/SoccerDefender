import bagel.*;
import bagel.util.Point;
import java.util.*;

/**
 * Represents a Spawn Event
 */
public class Spawn extends WaveEvent{
    private int enemyDifficulty;
    private String nation;
    private int spawnDelay;
    private ArrayList<Slicer> slicerArray = new ArrayList<Slicer>();
    private int counterFrames = 0;
    private int slicerCounter = 1;
    private final Point[] points;

    /**
     * creates a Spawn event
     * @param ne number of enemies
     * @param et enemy type for this spawn event
     * @param sd spawn Delay
     * @param points points to traverse
     */
    public Spawn(int ne, String et, int sd, Point[] points){
        super("spawn");
        enemyDifficulty = ne;
        nation = et;

        spawnDelay = sd;
        this.points = points;
        setSlicers();
    }

    /**
     * Sets the slicers in the spawn event to be spawned
     */
    public void setSlicers(){

        // go over the registered players from this nation with this difficulty or less and spawn them
        for (ItemRegister r : ItemRegister.values()){
            r.setCardImage();
            //r.setImage();
            //gets the ones that are less than or equal to the difficulty, while being from this nation
            if (r.getDifficulty() <= enemyDifficulty && r.getCountry().equals(nation)) {
                // per position to add as the right class
                if (r.getPosition().equals("AM")){
                    slicerArray.add(new Midfielder(r, points,  1, 2));
                } else if (r.getPosition().equals("DM")){
                    slicerArray.add(new DefensiveMidfielder(r, points, 1, 2));
                } else if (r.getPosition().equals("DF")){
                    slicerArray.add(new Defender(r, points,  1, 2));
                } else if (r.getPosition().equals("AT")){
                    slicerArray.add(new Attacker(r, points,  1, 2));
                } else if (r.getPosition().equals("GK")){
                    slicerArray.add(new Goalkeeper(r, points, 1, 2));

                }
            }
        }


    }

    /**
     * Returns wether all the slicers are set to null (finished)
     * @return boolean representing null state
     */
    public boolean allNull(){
        for (int j = 0;  j < slicerArray.size(); j++) {
            if (slicerArray.get(j) != null) { // change here
                return false;
            }
        }
        return true;
    }


    /**
     * Renders with the game Speed and returns the list of slicers to the game level
     * @param gameSpeed Speed of the game set by the player
     * @return list of slicers to the game level as an ArrayList
     */
    public ArrayList<Slicer> render(int gameSpeed){

        if (allNull()){
            setDone();
        }

        // logic to only render a slicer if it was spawned before, and if it is not set to null
        for (int j = 0;  j < slicerCounter && j < slicerArray.size(); j++) {
            if (slicerArray.get(j) != null) { // change here


                if (slicerArray.get(j).isNot_moving()) {

                    slicerArray.set(j, null);
                } else {

                    slicerArray.get(j).render(gameSpeed);
                    if (slicerArray.get(j) instanceof Midfielder){

                        ((Midfielder) slicerArray.get(j)).chooseAttacker(slicerArray);
                        ((Midfielder) slicerArray.get(j)).renderEffect(gameSpeed);
                    }
                    if (slicerArray.get(j) instanceof Attacker){

                        // chooses the towers outside int the game part, where we have info about towers
                        ((Attacker) slicerArray.get(j)).renderEffect(gameSpeed);
                    }
                    if (slicerArray.get(j) instanceof DefensiveMidfielder){
                        ((DefensiveMidfielder) slicerArray.get(j)).chooseSlicer(slicerArray);
                        ((DefensiveMidfielder)slicerArray.get(j)).renderEffect(gameSpeed);
                    }
                    if (slicerArray.get(j) instanceof Defender){
                        ((Defender)slicerArray.get(j)).renderEffect(gameSpeed);
                    }
                    if (slicerArray.get(j) instanceof Goalkeeper){
                        ((Goalkeeper)slicerArray.get(j)).renderEffect(gameSpeed);
                    }
                }
            }
        }

        counterFrames++; //  essential to know how many frames were processed after the last slicer was spawned
            /* Frame code, to show that 60 frames are being processed each second
            if (counterFrames%60 == 0){
                second ++;
                System.out.println("\n New second: " + second);
            }
            */
        // take into account the gamespeed set by the user, to know if it passed 60 frames which is a second times the spawn delay
        if (counterFrames*gameSpeed*1000 >= (60 * (spawnDelay))){
            if (slicerCounter < slicerArray.size()){
                slicerCounter++;
            }

            counterFrames = 0;
        }


        return slicerArray; // change here
    }
}
