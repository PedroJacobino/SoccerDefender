/**
 * Represents a Delay Event
 */

public class Delay extends WaveEvent {

    private final int delay; // amount of itme in seconds to wait
    private int counterFrames = 0; // counter for the time

    /**
     * Returns a delay with the specified time
     * @param sd delay in seconds
     */
    public Delay(int sd){
        super("delay");
        delay = sd;

    }

    /**
     *
     * @return the delay time
     */
    public int getDelay() {
        return delay;
    }

    /**
     * renders the delayEvent, by couting until the time has passed
     * @param gameSpeed Speed of the game
     */
    public void render(int gameSpeed){
        counterFrames++;
        if (counterFrames*gameSpeed*1000 >= (60 * (delay))){
            setDone();
        }
    }
}
