import bagel.util.*;
import bagel.*;

import java.util.ArrayList;

public class GamePanel {
    private String file = "res/images/GamePanel/";
    private Image mainPanel = new Image(file + "greyPanel.png");
    private Image backMainPanel= new Image(file + "bluePanel.png");
    private Image redbutton = new Image(file + "red.png");
    private Rectangle redSurface;
    private Rectangle redSurface2;
    private Image redbuttonPressed = new Image(file + "redP.png");
    private Image bluebutton = new Image(file + "blueButton.png");
    private Image bluebuttonPressed = new Image(file + "blueButtonPressed.png");
    private Image bluebutton2 = new Image(file + "blueButton2.png");
    private Image bluebuttonPressed2 = new Image(file + "blueButtonPressed2.png");
    private Image arrowDown = new Image(file + "arrowD.png");
    private Image arrowUp = new Image(file + "arrowU.png");
    private Font normalFont = new Font("res/fonts/kenvector_future_thin.ttf", 40);
    private Font lowerFont = new Font("res/fonts/kenvector_future_thin.ttf", 30);
    private Font evenlowerFont = new Font("res/fonts/kenvector_future_thin.ttf", 20);
    private Point redCentre = new Point(Window.getWidth()/2, Window.getHeight()/2);
    private Point redCentre2 = new Point(Window.getWidth()/2, Window.getHeight()/2 + redbutton.getHeight()*1.5);
    private Point blueCenter = new Point(Window.getWidth()/2+50, Window.getHeight()/2 -100);
    private Point blueCenter2 = new Point (Window.getWidth()/2+200, Window.getHeight()/2 -300);
    private DrawOptions optionsBlack =  new DrawOptions().setBlendColour(Colour.BLACK);
    private Image backgroundImg = new Image(file + "background.jpg");
    private DrawOptions scale2 = new DrawOptions().setScale(1.2, 1.2);
    private DrawOptions scale1 = new DrawOptions().setScale(4, 4);
    private int pageNumber = 0;
    private Image yL = new Image(file + "yellowLeft.png");
    private Image yR = new Image(file + "yellowRight.png");
    private Point yLPoint= new Point(220, Window.getHeight()/2 + 300);
    private Point yRPoint= new Point(800, Window.getHeight()/2 + 300);
    private String manFile = "res/texts/manual/";

    private ArrayList<TextVisualization> manualPages = new ArrayList<TextVisualization>();


    private boolean manual = false;

    public GamePanel(){
        redSurface = redbutton.getBoundingBoxAt(redCentre);
        redSurface2 = redbutton.getBoundingBoxAt(redCentre2);
        manualPages.add(new TextVisualization(manFile+"Explanation.txt"));
        manualPages.add(new TextVisualization(manFile+"Explanation2.txt"));
        manualPages.add(new TextVisualization(manFile+"Explanation3.txt"));
        manualPages.add(new TextVisualization(manFile+"Information.txt"));
        manualPages.add(new TextVisualization(manFile+"Information2.txt"));
        manualPages.add(new TextVisualization());
        manualPages.add(new TextVisualization(manFile +"Attacker.txt"));
        manualPages.add(new TextVisualization(manFile +"AttackingMidfielder.txt"));
        manualPages.add(new TextVisualization(manFile +"DefensiveMidfielder.txt"));
        manualPages.add(new TextVisualization(manFile + "Defender.txt"));
        manualPages.add(new TextVisualization(manFile +"Goalkeeper.txt"));
        manualPages.add(new TextVisualization(manFile +"Credits.txt"));
    }

    public void render(Input input){
        backgroundImg.draw(Window.getWidth()/2, Window.getHeight()/2+45, scale2);

        if (!manual){
            // main Panel
            backMainPanel.draw(Window.getWidth()/2, Window.getHeight()/2 - 20*3, scale1);
            mainPanel.draw(Window.getWidth()/2, Window.getHeight()/2, scale1);
            normalFont.drawString("Soccer Defender", Window.getWidth()/2-(12*15), Window.getHeight()/2 - 110-backMainPanel.getHeight());

            //Blue button
            if (bluebutton.getBoundingBoxAt(blueCenter).intersects(input.getMousePosition()) && input.isDown(MouseButtons.LEFT)){
                bluebuttonPressed.draw(blueCenter.x, blueCenter.y);
            } else {
                bluebutton.draw(blueCenter.x, blueCenter.y);
            }
            if (bluebutton.getBoundingBoxAt(blueCenter).intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                SoccerGame.changeInfoLevel();
            }
            lowerFont.drawString("Info Level:", blueCenter.x-200, blueCenter.y+10, optionsBlack);
            normalFont.drawString(""+SoccerGame.infoLevel, blueCenter.x-7, blueCenter.y +10);


            // Red starting button
            if (redSurface.intersects(input.getMousePosition()) && input.isDown(MouseButtons.LEFT)){
                redbuttonPressed.draw(redCentre.x, redCentre.y);
            } else {
                redbutton.draw(redCentre.x, redCentre.y);
            }
            if (redSurface.intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                SoccerGame.unfreeze();
            }

            lowerFont.drawString("Play", redCentre.x-35, redCentre.y+5);

            // Red Manual button
            if (redSurface2.intersects(input.getMousePosition()) && input.isDown(MouseButtons.LEFT)){
                redbuttonPressed.draw(redCentre2.x, redCentre2.y);
            } else {
                redbutton.draw(redCentre2.x, redCentre2.y);
            }
            if (redSurface2.intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                manual = true;
            }

            lowerFont.drawString("Manual", redCentre2.x-55, redCentre2.y+5);

            evenlowerFont.drawString("by Pedro Jacob", Window.getWidth()/2-20 , Window.getHeight()/2 + mainPanel.getHeight()*2 -20, optionsBlack);

        } else {
            manualPages.get(pageNumber).render();



            //back to Menu button
            if (bluebutton2.getBoundingBoxAt(blueCenter2).intersects(input.getMousePosition()) && input.isDown(MouseButtons.LEFT)){
                bluebuttonPressed2.draw(blueCenter2.x, blueCenter2.y);
            } else {
                bluebutton2.draw(blueCenter2.x, blueCenter2.y);
            }
            evenlowerFont.drawString("Back to Menu", blueCenter2.x-65, blueCenter2.y+5);
            if (bluebutton2.getBoundingBoxAt(blueCenter2).intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                manual = false;
            }




            // Page surfing

            if (pageNumber < manualPages.size()-1){
                yR.draw(yRPoint.x, yRPoint.y);
                if (yR.getBoundingBoxAt(yRPoint).intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                    increasePage();
                }
            }
            if (pageNumber > 0){
                yL.draw(yLPoint.x, yLPoint.y);
                if (yL.getBoundingBoxAt(yLPoint).intersects(input.getMousePosition()) && input.wasReleased(MouseButtons.LEFT)){
                    decreasePage();
                }
            }



        }



    }

    private void increasePage(){
        if (pageNumber < manualPages.size()-1){
            pageNumber++;
        }
    }

    private void decreasePage(){
        if (pageNumber > 0){
            pageNumber--;
        }
    }

}
