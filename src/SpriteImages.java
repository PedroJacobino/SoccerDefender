import bagel.*;

/*
Stores all the 4 images for each slicer Image (Standing, Right foot first, Left foot first, Defending)
 */

public enum SpriteImages {

    //Green sprites
    GREEN1("Green", 1),
    GREEN2("Green", 2),
    GREEN3("Green", 3),
    GREEN4("Green", 4),
    GREEN5("Green", 5),

    //Blue sprites
    BLUE1("Blue", 1),
    BLUE2("Blue", 2),
    BLUE3("Blue", 3),
    BLUE4("Blue", 4),
    BLUE5("Blue", 5),

    //Red (Orange)
    RED1("Red", 1),
    RED2("Red", 2),
    RED3("Red", 3),
    RED4("Red", 4),
    RED5("Red", 5),

    //Blue sprites
    WHITE1("White", 1),
    WHITE2("White", 2),
    WHITE3("White", 3),
    WHITE4("White", 4),
    WHITE5("White", 5);

    private Image standing;
    private Image right;
    private Image left;
    private Image defender;
    private String colour;
    private int number;
    private String fileChars= "res/images/Soccer/";

    private SpriteImages(String colour, int number){
        this.colour = colour;
        this.number = number;
        this.standing = new Image(fileChars + "/" + colour + "/" + "character"  +colour + number + ".png");
        this.right = new Image(fileChars + "/" + colour + "/" + "character" + colour + number + "-Right.png");
        this.left = new Image(fileChars + "/" + colour + "/" + "character" + colour + number + "-Left.png");
        this.defender = new Image(fileChars + "/" + colour + "/" + "character" + colour + number + "-Defender.png");
    }

    public Image getDefender() {
        return defender;
    }

    public Image getLeft() {
        return left;
    }

    public Image getRight() {
        return right;
    }

    public Image getStanding() {
        return standing;
    }

    public String getFileStanding() {
        return (fileChars + "/" + colour + "/" + "character"  +colour + number + ".png");
    }
}
