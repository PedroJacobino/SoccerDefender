/**
 * Represents the Game to be run
 */
public abstract class ShadowDefend{

    /**
     * Creates the new Game with the levels in the file specified and the wave file
     * @param args optional arguments for main
     */
    public static void main(String[] args) {
        // Create new instance of game and run it


        new SoccerGame("res/levels/1.tmx", "res/levels/waves3.txt").run(); // pass the levels string array and the waves file as arguments
    }

}
