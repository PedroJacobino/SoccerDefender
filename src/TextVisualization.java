import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import bagel.*;
import bagel.util.Colour;

public class TextVisualization {
    private String file = "res/images/GamePanel/";
    private Image mainPanel = new Image(file + "greyPanel.png");
    private ArrayList<String> lines = new ArrayList<String>();
    private Font font = new Font("res/fonts/DejaVuSans-Bold.ttf", 30);
    private Font lowfont = new Font("res/fonts/DejaVuSans-Bold.ttf", 15);
    private Font fontM = new Font("res/fonts/DejaVuSans-Bold.ttf", 40);
    private DrawOptions redOption = new DrawOptions().setBlendColour(new Colour(0.7, 0, 0.2));
    private DrawOptions blueOption = new DrawOptions().setBlendColour(new Colour(0.2, 0.2, 0.2));
    private DrawOptions blackOption = new DrawOptions().setBlendColour(new Colour(0, 0, 0, 0.7));
    private double coordX = 200;
    private double coordY = 150;
    private DrawOptions scale1 = new DrawOptions().setScale(7, 7);
    private Colour black = new Colour(0, 0, 0, 0.7);

    private Image[] images;


    TextVisualization(String text){
        loadText(text);
    }

    TextVisualization(){
        images = new Image[5];
        images[0] = new Image("res/images/BuyPanel/Aim.png");
        images[1] = new Image("res/images/BuyPanel/Maestro.png");
        images[2] = new Image("res/images/BuyPanel/Tower.png");
        images[3] = new Image("res/images/BuyPanel/Wall.png");
        images[4] = new Image("res/images/BuyPanel/Glove.png");
    }


    private void loadText(String text) {
        try {
            Scanner scanner = new Scanner(new File(text));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void render(){
        mainPanel.draw(Window.getWidth()/2, Window.getHeight()/2, scale1);
        for (int i = 0; i < lines.size(); i++){
            if ( i == 0){
                fontM.drawString(lines.get(i), coordX, coordY-50, redOption);
            } else if (lines.get(i).equals("\\n")) {
                font.drawString("", coordX, coordY + (i * 30));
            } else {
                font.drawString(lines.get(i), coordX, coordY + (i * 30), blueOption);
            }
        }

        if (images != null){
            font.drawString("Icons Panel for Towers Placement:", coordX, coordY, redOption);
            // Panel behing
            Drawing.drawRectangle(coordX+65 - images[0].getWidth()/2,coordY+100 - images[0].getHeight()/2, 100 +(4*images[4].getWidth() + 10),  images[0].getHeight(), black);

            for(int i =0; i < images.length; i++){
                images[i].draw(coordX+50 +(i*images[i].getWidth() + 10), coordY+100);
            }

            lowfont.drawString("Attacker", coordX+25, coordY+200, blackOption );
            lowfont.drawString("AtMidfielder", coordX+(1*images[1].getWidth() + 10), coordY+200, blackOption );
            lowfont.drawString("DFMidfielder", coordX+(2*images[2].getWidth() + 10), coordY+200, blackOption );
            lowfont.drawString("Defender", coordX+20+(3*images[3].getWidth() + 10), coordY+200, blackOption );
            lowfont.drawString("Goalkeeper", coordX+20+(4*images[4].getWidth() + 10), coordY+200, blackOption );

            font.drawString("After clicking the panel a sliding window ", coordX, coordY+300, blackOption);
            font.drawString("will appear with the choices of the Towers", coordX, coordY+340, blackOption);
        }

    }



}
