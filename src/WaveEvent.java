/**
 * Represents a Wave Event in the Wave
 */
public abstract class WaveEvent {

    private final String type;
    private boolean done = false;


    /**
     * Create a Wave event
     * @param eventType type of the wave event to create
     */
    public WaveEvent(String eventType){
        type = eventType;

    }

    /**
     * set the wave event to be done
     */
    protected void setDone(){
        done = true;
    }

    /**
     * know if the event is done
     * @return boolean state of the event
     */
    public boolean isDone(){
        return done;
    }

    /**
     * Get the type of the event
     * @return String representing the type
     */
    public String getType(){
        return type;
    }
}
