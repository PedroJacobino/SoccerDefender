import bagel.*;
import bagel.map.TiledMap;
import bagel.util.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class SoccerGame extends AbstractGame {

    // Static
    private static boolean lost = false; // You begin the game without losing
    private static int money = 0; // Initial money
    private static int lives = 25; // Initial lives

    private String panelFile = "res/images/GamePanel/";

    private Image bluebutton = new Image(panelFile + "blueButton.png");
    private Image bluebuttonPressed = new Image(panelFile + "blueButtonPressed.png");
    private Point blueCenter = new Point(Window.getWidth()-300,  50);


    public static boolean freeze = true;
    public static int infoLevel = 1; // level of information on the screen so the player does not get too many things on screen


    // declaring the variables that are necessary to the functionning of the Game
    private final TiledMap map; // tiled map of the game
    private int gameSpeed; // gameSpeed for the game, anything that is counted in seconds will be this amount faster
    private int currentWave; // the number of the current Wave the game is in
    private boolean wave; // wave on/off turns off at the end of every wave
    private boolean placing; // Keep track if player is placing a tower
    private Point[] pointArray;      // point array from the map
    private String waveStatus; // The wave status of the game
    private boolean levelFinished;
    private String waveFile;
    private Font cardFont = new Font("res/fonts/DejaVuSans-Bold.ttf", 23);
    private int wavEventCounter = 0;

    //Panels
    private BuyPanel buypanel;    // buying panel
    private StatusPanel statuspanel;  // status panel
    //RegisterItems for the panel
    private ItemRegister placer;
    private ItemRegister placingtheplacer;

    // ArrayLists
    private ArrayList<ActiveTower> towersinmap; // Keep all the ActiveTowers which are in the map
    private ArrayList<Tower>alltowersinmap;
    private ArrayList<Slicer> slicers; // Keep all the slicers which are in the spawning event, otherwise it is set to null
    private ArrayList<Wave2> waves;  // Keep all the waves

    private GamePanel gamePanel;

    public SoccerGame(String level, String wavefile) { // initiating the game, setting the level to be the first one given

        //Setting all the variables for this level
        gameSpeed = 1;
        wave = false;
        currentWave = 0;
        waves = new ArrayList<Wave2>();
        towersinmap = new ArrayList<ActiveTower>(); // Attackers from the user
        alltowersinmap = new ArrayList<Tower>();
        waveStatus = "Awaiting Start";
        placing = false;
        slicers = null;
        levelFinished = false;
        waveFile = wavefile;
        map = new TiledMap(level); // initialising the map

        // Iterates through each point in the polyline and include it in the Point array
        pointArray = new Point[map.getAllPolylines().get(0).size()];
        int i = 0;
        for (Point point : map.getAllPolylines().get(0)) {
            pointArray[i] = point;
            i++;
        }

        // Setting the panels
        Image buybackg = new Image("res/images/buypanel.png");
        Image statbackg = new Image("res/images/statuspanel.png");
        buypanel = new BuyPanel(0, 0, buybackg);
        statuspanel = new StatusPanel(0, Window.getHeight(), statbackg);

        // setting the level
        loadLevel(); // using the Scanner to read form the wave file
        gamePanel = new GamePanel();
    }



    @Override
    protected void update(Input input) {
        // freezed game so can Show the Game Panel
        if (input.wasPressed(Keys.I)){augmentInfoLevel(); }
        if (input.wasPressed(Keys.U)){decreaseInfoLevel(); }
        if (input.wasPressed(Keys.ESCAPE)){System.exit(0); }

        if (freeze){

            gamePanel.render(input);


        } else{ // the game is rendering, as it is not freezed
            gameRender(input);
            // animation for slicer rotating being thrown and dying
            if (Slicer.canbethrown){
                Slicer.getThrown();
            }

            //Blue button
            if (bluebutton.getBoundingBoxAt(blueCenter).intersects(input.getMousePosition()) && input.isDown(MouseButtons.LEFT)){
                bluebuttonPressed.draw(blueCenter.x, blueCenter.y);
            } else {
                bluebutton.draw(blueCenter.x, blueCenter.y);
            }
            if (bluebutton.getBoundingBoxAt(blueCenter).intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                SoccerGame.freeze = !SoccerGame.freeze;
            }
            cardFont.drawString("M", blueCenter.x-10, blueCenter.y+5);

        }



    }

    public void resetForNextLevel(){
        towersinmap = new ArrayList<ActiveTower>(); // Attackers from the user
        alltowersinmap = new ArrayList<Tower>();
        BuyPanel.reset();
    }

    // all static:

    /**
     * Damages the life of the player
     * @param i amount of damage
     */
    public static void damageLife(int i){
        if(lives - i <= 0){
            lost = true;
            lives = 0;
        } else {
            lives -= i;
        }
        if (lost){
            //System.out.println("You lost the game, better luck next Time!");
            System.exit(0);
        }
    }

    /**
     * increase cash of the player by
     * @param i this amount
     */
    public static void increaseCash(int i ){
        money += i;
    }

    /**
     * Resets the Game state for next level
     */
    public static void resetGame(){
        money = 500;
        lives = 25;
        lost = false;
    }

    /**
     * Gets the Money the player has
     * @return the money the player has
     */
    public static int getMoney(){
        return money;
    }

    /**
     * Gets lives remaining
     * @return int representing the lives remaining
     */
    public static int getLives(){
        return lives;
    }

    /**
     * decreases the money of the player by
     * @param i this amount
     */
    public static void decreaseCash(int i ){
        money -= i;
    }

    public static void augmentInfoLevel() {
        if (SoccerGame.infoLevel < 3){
            SoccerGame.infoLevel += 1;
        }

    }

    public static void changeInfoLevel() {
        if (SoccerGame.infoLevel < 3){
            SoccerGame.infoLevel += 1;
        } else {
            SoccerGame.infoLevel = 0;
        }

    }

    public static void decreaseInfoLevel() {
        if (SoccerGame.infoLevel > 0){
            SoccerGame.infoLevel -= 1;
        }
    }

    public static void unfreeze(){
        freeze = false;
    }

    public static void printSmallCard(Image img, Point p, int y1, int y2, double scale){
        img.draw(p.x, p.y, new DrawOptions()
                .setSection(0,0, img.getWidth(), img.getHeight()-y1).setScale(scale, scale));
        img.draw(p.x, p.y + img.getHeight()-y2 -(y1-y2)*scale, new DrawOptions()
                .setSection(0,img.getHeight()-y2, img.getWidth(), y2).setScale(scale, scale));
    }


    private void gameRender(Input input){
        // drawing the map on the screen
        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());

        //Inputs
        // exit the game if escape button is pressed
        if (input.wasPressed(Keys.ESCAPE)){System.exit(0); }
        // checking if the key was pressed once, for the gamespeed change, and the wave start
        if (input.wasPressed(Keys.L)&& (gameSpeed < 5)){gameSpeed += 1; }
        if (input.wasPressed(Keys.K) && (gameSpeed > 1)){gameSpeed --; }
        if (input.wasPressed(Keys.S) && !wave && currentWave < waves.size()){
            wave = true;
            currentWave++;
        }




        // Wave Logic, traversing the waves loaded
        if (wave && (waves.size() >= currentWave)) { // if you
            if (!waves.get(currentWave-1).isFinished()){ // only continue this wave if it is not finished
                waveStatus = "Wave In Progress"; // shows that wave is in progress
                // change here
                slicers = waves.get(currentWave-1).render(gameSpeed); // get the slicers reference to this map for the towers

                if (waves.get(currentWave-1).getWavepoint() > wavEventCounter ){
                    wavEventCounter++;
                    regenTower();
                }

            } else { // if it is finished
                wavEventCounter=0;
                buypanel.addLevel();
                resetForNextLevel(); //each time a level ends, reposition all your players.
                SoccerGame.increaseCash((currentWave*100) + 150);
                slicers = null; // because the wave has finished we do not want to have the same slicers here
                waveStatus = "Awaiting Start";
                wave = false; // set wave to false, to make the game wait
            }
        }
        // Last wave finished
        if (currentWave == waves.size() && waves.get(currentWave-1).isFinished()){
            waveStatus = "Winner!";
            levelFinished = true;
        }

        // choosing the tower for ther attacker slicer
        chooseTowerForAttacker();


        for (Tower t: alltowersinmap){
            if (t!=null && !t.isFainted()){
                if (t instanceof ActiveTower){
                    if (slicers != null){
                        ((ActiveTower) t).chooseSlicer(slicers); // change here
                    }
                    ((ActiveTower) t).render(gameSpeed);

                } else if (t instanceof HelperTower){
                    ((HelperTower)t).render(towersinmap);

                } else if (t instanceof NerfTower){
                    ((NerfTower)t).render(slicers);

                } else if (t instanceof WallTower){

                    ((WallTower)t).render(slicers);

                } else if (t instanceof HijackerTower){

                    ((HijackerTower) t).render(slicers);

                }
            } //else if (t != null && !t.isFainted()){

                //t = null;
            //}

        }



        drawCircles(input); // draw the circles and cards if the mouse is over the player.


        // Panels Logic
        // Buy Panel Logic, getting the output from the render, to know what Image to load
        placer = buypanel.render(input, SoccerGame.getMoney());
        if (placer != null){
            placing = true;
            placingtheplacer = placer;
        }
        // Deselects, same rule apllied for active and passive towers when choosing location
        if (placing && !waveStatus.equals("Winner!")){
            waveStatus = "Placing";
            if (input.wasPressed(MouseButtons.RIGHT)){ //deselected the item
                placing = false;
            }
            // need to state that the placer has to be null, as it means that the sliding panel is not activated
            if ( placer == null && !BuyPanel.isSlidignPanelActivated() && notOutsideWindow(input) && ((getMapStatus(input) && !(placingtheplacer.getPosition().equals("DF") || placingtheplacer.getPosition().equals("GK")))
                    || ((placingtheplacer.getPosition().equals("DF") || placingtheplacer.getPosition().equals("GK")) && !getMapStatus(input)))
                    && !getPanelPlace(input, buypanel) && !getPanelPlace(input, statuspanel) && !towerIntersection(input.getMousePosition())){ // Should be either not a defender and not in a blocked area, or a defender and in a blocked area.
                placingtheplacer.getImage().draw(input.getMousePosition().x, input.getMousePosition().y);

                Drawing.drawCircle(input.getMousePosition().x, input.getMousePosition().y, placingtheplacer.getEfRadius()*20, new Colour(255, 69, 0, 0.4));
                // not necessary, need to decide if still wants to use it
                drawCardRightUp(placingtheplacer.getCardImage(), cardFont, placingtheplacer.getSpeed(), placingtheplacer.getEfRadius(), placingtheplacer.getPower(), placingtheplacer.getResistance());
                if (input.wasPressed(MouseButtons.LEFT)){
                    placing = false;
                    SoccerGame.decreaseCash(placingtheplacer.getValue());
                    placingtheplacer.setOnscreen();
                    placeTower((int) input.getMousePosition().x, (int) input.getMousePosition().y, placingtheplacer);
                }
            }
        }
        // render the status panel with the new variables
        statuspanel.render(currentWave, gameSpeed, waveStatus, SoccerGame.getLives());
        drawCards(input);

    }

    private boolean getMapStatus(Input input){
        return map.getProperty((int) input.getMousePosition().x, (int) input.getMousePosition().y, "blocked", "default").equals("default");
    }

    private boolean getPanelPlace(Input input, Panel p){
        return p.getImage().getBoundingBoxAt(p.getCenter()).intersects(input.getMousePosition());
    }

    /**
     * place a tower from register to x, y in the map
     *
     * @param x coordinate
     * @param y coordinate
     * @param r Register with Item info
     */
    private void placeTower(int x, int y, ItemRegister r) {
        if (r.getPosition().equals("AT")){
            ActiveTower t = new ActiveTower(r, x, y, new Image("res/images/Soccer/Balls/ball_soccer1.png"));
            towersinmap.add(t); // stores it in tower so it is easier for midfielder tower to find the attacker towers to boost
            alltowersinmap.add(t);
        } else if (r.getPosition().equals("AM")){
            HelperTower t = new HelperTower(r, x, y);
            alltowersinmap.add(t);
        } else if (r.getPosition().equals("DM")){
            NerfTower t = new NerfTower(r, x, y);
            alltowersinmap.add(t);
        } else if (r.getPosition().equals("DF")){
            WallTower t = (new WallTower(r, x, y));
            alltowersinmap.add(t);
        }  else if (r.getPosition().equals("GK")){
            HijackerTower t = new HijackerTower(r, x, y);
            alltowersinmap.add(t);
        }
    }

    /**
     * If any tower intersects with the point p, you cannot put the tower there
     * @param p point the cursor is now
     * @return the state of the place where the cursor is
     */
    private boolean towerIntersection (Point p){
        for (Tower t : alltowersinmap) {
            if (t!=null && !t.isFainted() && t.getImage().getBoundingBoxAt(t.getCenter()).intersects(p)) { // add fainted
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the mouse pointer is not outside the window
     * @param inp input from user
     * @return if the mouse is outside the window
     */
    private boolean notOutsideWindow(Input inp){
        if (inp.getMousePosition().x < 0 || inp.getMousePosition().y < 0 || inp.getMousePosition().x > Window.getWidth() || inp.getMousePosition().y > Window.getHeight()){
            return false;
        }
        return true;
    }
    /**
     * Load waves into this level
     */
    private void loadLevel() {
        try {
            Scanner scanner = new Scanner(new File(waveFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (Integer.parseInt(line.charAt(0) + "") > waves.size()){
                    waves.add(new Wave2(Integer.parseInt(""+ line.charAt(0))));
                }
                waves.get(Integer.parseInt("" +line.charAt(0))-1).eventAdd(line, pointArray);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void drawCircles(Input input){
        if (infoLevel > 0){
            for(Tower t: alltowersinmap) {
                if (t!= null && !t.isFainted() && (!BuyPanel.isSlidignPanelActivated()) && t.getImage().getBoundingBoxAt(t.getCenter()).intersects(input.getMousePosition())) { // Draws a circle to reference for the effect radius of the player
                    t.drawCircle();
                }
            }
            if (slicers != null){
                for (Slicer s: slicers){
                    if (s!= null && !s.isNot_moving() && (!BuyPanel.isSlidignPanelActivated()) && s.getImage().getBoundingBoxAt(s.getCenter()).intersects(input.getMousePosition())) {
                        s.drawCircle();
                    }
                }
            }
        }
    }

    private void drawCards(Input input){
        if (infoLevel > 1){
            for(Tower t: alltowersinmap) {
                if (t!= null && !t.isFainted() && (!BuyPanel.isSlidignPanelActivated()) && t.getImage().getBoundingBoxAt(t.getCenter()).intersects(input.getMousePosition())) { // Draws a circle to reference for the effect radius of the player

                    drawCardRightUp(t.getCardImage(), cardFont, t.getStatSpeed(), t.getStatRadius(), t.getStatPower(), t.getStatResistance());
                }
            }
            if (slicers != null){
                for (Slicer s: slicers){
                    if (s!= null && !s.isNot_moving() && (!BuyPanel.isSlidignPanelActivated()) && s.getImage().getBoundingBoxAt(s.getCenter()).intersects(input.getMousePosition())) {
                        drawCardRightUp(s.getCardImage(), cardFont, s.getStatSpeed(), s.getStatRadius(), s.getStatPower(), s.getStatResistance() );
                    }
                }
            }
        }

    }

    public static void drawCardRightUp(Image cardImage, Font cF, int spd, int rad, int pwr, int res){
        printSmallCard(cardImage, new Point(900, 165), 275, 225, 0.40);
        cF.drawString(""+spd, 900-cardImage.getWidth()*0.40/4, 165+cardImage.getHeight()*0.285/4);
        cF.drawString(""+pwr, 900+cardImage.getWidth()*0.40/4 -40, 165+cardImage.getHeight()*0.285/4);
        cF.drawString(""+rad, 900-cardImage.getWidth()*0.40/4, 165+2*cardImage.getHeight()*0.285/4 - 40);
        cF.drawString(""+res, 900+cardImage.getWidth()*0.40/4 -40, 165+2*cardImage.getHeight()*0.285/4 - 40);
    }

    private void chooseTowerForAttacker(){
        if (slicers != null){
            for ( Slicer s : slicers) {
                if (s != null) { // change here
                    if (!s.isNot_moving() && s instanceof  Attacker){

                        ((Attacker) s).chooseTower(alltowersinmap);
                    }
                }
            }
        }
    }

    private void regenTower(){

        for ( Tower t : alltowersinmap){
            if (t!=null && !t.isFainted() ){
                t.regen();

            } else if ( t!= null && t.isFainted() && !(t instanceof HijackerTower)){ // can change to make game easier
                ItemRegister.findItemRevive(t.getName());
                t=null;
            }
        }
    }


}
