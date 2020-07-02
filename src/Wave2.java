import java.util.ArrayList;

import bagel.util.*;

/**
 * Represents a Wave in the Game
 */
public class Wave2 {

    private ArrayList<WaveEvent> waveEvents = new ArrayList<WaveEvent>();
    private boolean started = false;
    private boolean finished = false;
    private final int waveNumber;
    private int wavepoint = 0;
    private int numberOfEvents = 0;
    private boolean eventSet = false;
    private Spawn currentSpawning;
    private Delay currentDelay;

    /**
     * Creates a Wave
     * @param n number of the wave
     */
    Wave2(int n){
        waveNumber = n;
    }

    /**
     * Add an event to this wave
     * @param line line read from the file
     * @param points points in the polyline
     */
    public void eventAdd(String line, Point[] points){
        String[] event = line.split(",");

        if (event[1].equals("spawn") && event.length == 5){

            waveEvents.add(new Spawn(Integer.parseInt(event[2]), event[3], Integer.parseInt(event[4]), points));

        } else if (event[1].equals("delay") && event.length == 3){
            waveEvents.add(new Delay(Integer.parseInt(event[2])));
        } // throw exception otherwise
        numberOfEvents++;

    }

    public int getWavepoint() {
        return wavepoint;
    }

    /**
     * rendering the slicers in the wave or null if none
     * @param gameSpeed Speed of the Game
     * @return the slicers in the wave event
     */
    public ArrayList<Slicer> render(int gameSpeed){
        if (wavepoint < numberOfEvents ){
            if (!eventSet) {
                if (waveEvents.get(wavepoint) instanceof Spawn) {
                    currentSpawning = (Spawn) waveEvents.get(wavepoint);
                } else if (waveEvents.get(wavepoint) instanceof Delay){
                    currentDelay = (Delay) waveEvents.get(wavepoint);
                }
                eventSet = true;
            }
            if (waveEvents.get(wavepoint).getType().equals("spawn")){
                if (currentSpawning.isDone()){
                    wavepoint++;
                    eventSet = false;
                }
                return currentSpawning.render(gameSpeed);
            } else if (waveEvents.get(wavepoint).getType().equals("delay")){
                currentDelay.render(gameSpeed);
                if (currentDelay.isDone()){
                    wavepoint++;
                    eventSet = false;
                }
            }
        } else if (wavepoint == numberOfEvents){
            finished = true;
        }
        return null;
    }

    /**
     * know if the wave has finished
     * @return boolean representing finished state
     */
    public boolean isFinished() {
        return finished;
    }
}

