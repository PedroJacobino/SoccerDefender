import bagel.*;

/**
 * An Item Register for keeping track of item information
 */
public enum ItemRegister {

    // Brazilian Defender
    CARLOS("Brazil",3, "Carlos", SpriteImages.GREEN3,
            0, "DF", 3, 8, 9, 9),
    THIAGO("Brazil",2, "ThiagoS", SpriteImages.GREEN3,
            0, "DF", 3, 7, 8, 6),
    MARQUINHOS("Brazil", 2, "Marquinhos", SpriteImages.GREEN3,
            0, "DF", 3, 7, 6, 6),
    ALEXS("Brazil", 1, "AlexS", SpriteImages.GREEN4,
            0, "DF", 3, 7, 7, 7),
    DAVIDLUIZ("Brazil", 1, "DavidLuiz", SpriteImages.GREEN2,
            0, "DF", 3, 6, 6, 5),
    MARCELO("Brazil", 0, "Marcelo", SpriteImages.GREEN3,
            0, "DF", 3, 5, 5, 6),
    MILITAO("Brazil", 0, "Militao", SpriteImages.GREEN4,
            0, "DF", 3, 4, 5, 6),
    RENANLODI("Brazil", 0, "RenanLodi", SpriteImages.GREEN4,
            0, "DF", 3, 4, 4, 6),

    //Brazilian Attacking Midfielders
    RONALDINHO("Brazil", 3, "Ronaldinho", SpriteImages.GREEN3,
            0, "AM", 9, 7, 9, 9),
    COUTINHO("Brazil", 3, "Coutinho", SpriteImages.GREEN3,
            0, "AM", 8, 6, 8, 7),
    DOUGLASC("Brazil", 2, "DouglasC", SpriteImages.GREEN4,
            0, "AM", 7, 7, 7, 8),
    ARTHUR("Brazil", 1, "Arthur", SpriteImages.GREEN5,
            0, "AM", 6, 6, 5, 5),
    FELIPEANDERSON("Brazil", 1, "Anderson", SpriteImages.GREEN3,
            0, "AM", 6, 6, 5, 7),
    OSCAR("Brazil", 1, "Oscar", SpriteImages.GREEN1,
            0, "AM", 6, 6, 5, 6),
    PAQUETA("Brazil", 0, "Paqueta", SpriteImages.GREEN3,
            0, "AM", 5, 5, 4, 5),
    TALISCA("Brazil", 0, "Talisca", SpriteImages.GREEN4,
            0, "AM", 5, 5, 4, 5),

    // Brazilian Defensive Midfielders
    FERNANDINHO("Brazil", 2, "Fernandinho", SpriteImages.GREEN4,
            0, "DM", 8, 8, 8, 7),
    CASEMIRO("Brazil", 2, "Casemiro", SpriteImages.GREEN3,
            0, "DM", 7, 7, 7, 5),
    FABINHO("Brazil", 2, "Fabinho", SpriteImages.GREEN3,
            0, "DM", 6, 7, 7, 6),
    ALLAN("Brazil", 0, "Allan", SpriteImages.GREEN3,
            0, "DM", 5, 6, 6, 6),
    PAULINHO("Brazil", 1, "Paulinho", SpriteImages.GREEN4,
            0, "DM", 5, 5, 6, 5),
    THIAGOMENDES("Brazil", 0, "ThiagoM", SpriteImages.GREEN4,
            0, "DM", 5, 4, 5, 6),
    FRED("Brazil", 0, "Fred", SpriteImages.GREEN4,
            0, "DM", 5, 5, 5, 5),

    // Brazilian Attackers
    RONALDO("Brazil", 3, "Ronaldo",SpriteImages.GREEN3,
            0, "AT", 9, 9, 9, 9),
    NEYMAR("Brazil", 2, "Neymar",SpriteImages.GREEN3,
            0, "AT", 8, 7, 9, 8),
    FIRMINO("Brazil", 2, "Firmino",SpriteImages.GREEN1,
            0, "AT", 7, 8, 8, 7),
    GABRIELJ("Brazil", 1, "GabrielJ",SpriteImages.GREEN3,
            0, "AT", 7, 7, 7, 7),
    LUCAS("Brazil", 1, "Lucas",SpriteImages.GREEN3,
            0, "AT", 6, 7, 7, 7),
    RICHARLISON("Brazil", 1, "Richarlison",SpriteImages.GREEN3,
            0, "AT", 6, 8, 7, 7),
    RODRYGO("Brazil", 0, "Rodrygo",SpriteImages.GREEN4,
            0, "AT", 5, 5, 5, 7),
    VINICIUS("Brazil", 0, "Vinicius",SpriteImages.GREEN4,
            0, "AT", 5, 5, 4, 7),
    MATHEUSC("Brazil", 0, "MatheusC",SpriteImages.GREEN3,
            0, "AT", 6, 6, 4, 6),


    //Keepers
    ALISSON("Brazil", 3, "Alisson", SpriteImages.GREEN3,
            0, "GK", 3, 7, 9, 7),
    EDERSON("Brazil", 2, "Ederson", SpriteImages.GREEN3,
            0, "GK", 3, 6, 8, 7),
    NETO("Brazil", 1, "Neto", SpriteImages.GREEN1,
            0, "GK", 3, 6, 6, 6),
    GOMES("Brazil", 0, "Gomes", SpriteImages.GREEN5,
            0, "GK", 3, 6, 5, 5),


    // France

    // DF
    BLANC("France", 3, "Blanc", SpriteImages.BLUE5,
            0, "DF", 3, 9, 9, 7),
    VARANE("France", 3, "Varane", SpriteImages.BLUE3,
            0, "DF", 3, 7, 8, 7),
    LAPORTE("France", 2, "Laporte", SpriteImages.BLUE1,
            0, "DF", 3, 6, 6, 5),
    HERNANDEZ("France", 1, "Hernandez", SpriteImages.BLUE3,
            0, "DF", 3, 4, 7, 6),
    MUKIELE("France", 0, "Mukiele", SpriteImages.BLUE4,
            0, "DF", 3, 6, 5, 5),
    PAVARD("France", 0, "Pavard", SpriteImages.BLUE2,
            0, "DF", 3, 5, 5, 4),


    // AM
    GRIEZMANN("France", 3, "Griezmann", SpriteImages.BLUE1,
            0, "AM", 8, 7, 9, 7),
    FEKIR("France", 2, "Fekir", SpriteImages.BLUE3,
            0, "AM", 7, 6, 7, 6),
    AOUAR("France", 1, "Aouar", SpriteImages.BLUE3,
            0, "AM", 5, 7, 6, 5),
    PAYET("France", 0, "Payet", SpriteImages.BLUE3,
            0, "AM", 6, 7, 5, 6),
    VALBUENA("France", 0, "Valbuena", SpriteImages.BLUE1,
            0, "AM", 4, 7, 4, 5),






    // DM
    KANTE("France", 3, "Kante", SpriteImages.BLUE4,
            0, "DM", 9, 9, 9, 8),
    POGBA("France", 3, "Pogba", SpriteImages.BLUE4,
            0, "DM", 8, 8, 7, 6),
    MATUIDI("France", 2, "Matuidi", SpriteImages.BLUE4,
            0, "DM", 7, 7, 6, 5),
    SISSOKO("France", 1, "Sissoko", SpriteImages.BLUE4,
            0, "DM", 6, 5, 5, 4),
    BAKAYOKO("France", 0, "Bakayoko", SpriteImages.BLUE4,
            0, "DM", 6, 5, 5, 4),

    // AT
    RIBERY("France", 3, "Ribery",SpriteImages.BLUE3,
            0, "AT", 8, 7, 8, 8),
    MBAPPE("France", 3, "Mbappe",SpriteImages.BLUE4,
            0, "AT", 8, 7, 7, 9),
    BENZEMA("France", 2, "Benzema",SpriteImages.BLUE3,
            0, "AT", 7, 7, 6, 6),
    LACAZETTE("France", 2, "Lacazette",SpriteImages.BLUE4,
            0, "AT", 7, 6, 6, 6),
    YEDDER("France", 1, "Yedder",SpriteImages.BLUE3,
            0, "AT", 6, 5, 7, 6),
    GIGNAC("France", 1, "Gignac",SpriteImages.BLUE3,
            0, "AT", 8, 6, 6, 4),
    SAINTMAXIMIM("France", 0, "Saint-Maximin",SpriteImages.BLUE4,
            0, "AT", 5, 5, 4, 6),
    DEMBELE("France", 0, "Dembele",SpriteImages.BLUE4,
            0, "AT", 5, 5, 4, 4),



    // GK
    LLORIS("France", 3, "Lloris", SpriteImages.BLUE3,
            0, "GK", 3, 7, 8, 6),
    MANDANDA("France", 2, "Mandanda", SpriteImages.BLUE4,
            0, "GK", 3, 7, 6, 6),
    AREOLA("France", 1, "Areola", SpriteImages.BLUE3,
            0, "GK", 3, 5, 5, 5),
    RUFFIER("France", 0, "Ruffier", SpriteImages.BLUE5,
            0, "GK", 3, 5, 4, 4),


    //Germany

    //DF
    HUMMELS("Germany", 3, "Hummels", SpriteImages.WHITE3,
            0, "DF", 3, 8, 8, 5),
    BOATENG("Germany", 2, "Boateng", SpriteImages.WHITE4,
            0, "DF", 3, 6, 7, 6),
    GINTER("Germany", 1, "Ginter", SpriteImages.WHITE1,
            0, "DF", 3, 6, 6, 5),
    SULE("Germany", 0, "Sule", SpriteImages.WHITE1,
            0, "DF", 3, 5, 5, 4),
    KEHRER("Germany", 0, "Kehrer", SpriteImages.WHITE4,
            0, "DF", 3, 4, 4, 5),

    //AM
    REUS("Germany", 3, "Reus", SpriteImages.WHITE1,
            0, "AM", 8, 7, 9, 7),
    HAVERTZ("Germany", 2, "Havertz", SpriteImages.WHITE3,
            0, "AM", 7, 6, 8, 5),
    OZIL("Germany", 2, "Ozil", SpriteImages.WHITE3,
            0, "AM", 7, 5, 7, 4),
    DRAXLER("Germany", 1, "Draxler", SpriteImages.WHITE3,
            0, "AM", 6, 5, 5, 4),
    MEYER("Germany", 0, "Meyer", SpriteImages.WHITE5,
            0, "AM", 5, 5, 5, 4),
    DEMIRBAY("Germany", 0, "Demirbay", SpriteImages.WHITE3,
            0, "AM", 5, 5, 4, 4),



    // DM
    KIMMICH("Germany", 3, "Kimmich", SpriteImages.WHITE1,
            0, "DM", 8, 7, 8, 6),
    GROETZKA("Germany", 2, "Goretzka", SpriteImages.WHITE1,
            0, "DM", 5, 6, 7, 5),
    KROOS("Germany", 1, "Kroos", SpriteImages.WHITE5,
            0, "DM", 5, 6, 6, 4),
    SERDAR("Germany", 0, "Serdar", SpriteImages.WHITE3,
            0, "DM", 5, 5, 5, 4),

    // AT
    MULLER("Germany", 3, "Muller",SpriteImages.WHITE1,
            0, "AT", 7, 7, 8, 5),
    WERNER("Germany", 3, "Werner",SpriteImages.WHITE3,
            0, "AT", 6, 6, 6, 7),
    GNABRY("Germany", 2, "Gnabry",SpriteImages.WHITE4,
            0, "AT", 6, 7, 5, 7),
    SANE("Germany", 1, "Sane",SpriteImages.WHITE4,
            0, "AT", 6, 5, 5, 7),
    KRUSE("Germany", 1, "Kruse",SpriteImages.WHITE1,
            0, "AT", 5, 4, 4, 4),
    VOLLAND("Germany", 0, "Volland",SpriteImages.WHITE3,
            0, "AT", 4, 4, 4, 4),
    BELLARABI("Germany", 0, "Bellarabi",SpriteImages.WHITE3,
            0, "AT", 4, 3, 3, 5),

    // GK
    NEUER("Germany", 3, "Neuer", SpriteImages.WHITE5,
            0, "GK", 3, 7, 8, 7),
    STEGEN("Germany", 2, "Stegen", SpriteImages.WHITE5,
            0, "GK", 3, 7, 7, 6),
    LENO("Germany", 1, "Leno", SpriteImages.WHITE1,
            0, "GK", 3, 6, 6, 5),


    //Holland

    //DF
    VANDIJK("Holland", 3, "Van Dijk", SpriteImages.RED4,
            0, "DF", 3, 8, 9, 7),
    DELIGT("Holland", 2, "De Ligt", SpriteImages.RED5,
            0, "DF", 3, 8, 8, 6),
    DEVRIJ("Holland", 2, "De Vrij", SpriteImages.RED3,
            0, "DF", 3, 6, 7, 5),
    DUMFRIES("Holland", 0, "Dumfries", SpriteImages.RED4,
            0, "DF", 3, 5, 5, 6),
    HATEBOER("Holland", 0, "Hateboer", SpriteImages.RED1,
            0, "DF", 3, 4, 5, 5),

    //AM
    BERGKAMP("Holland", 3, "Bergkamp", SpriteImages.RED5,
            0, "AM", 9, 8, 9, 7),
    DEJONG("Holland", 2, "De Jong", SpriteImages.RED5,
            0, "AM", 8, 6, 8, 5),
    PROMES("Holland", 1, "Promes", SpriteImages.RED4,
            0, "AM", 7, 7, 6, 6),
    BERGWIJN("Holland", 0, "Bergwijn", SpriteImages.RED4,
            0, "AM", 6, 6, 5, 6),
    CHONG("Holland", 0, "Chong", SpriteImages.RED4,
            0, "AM", 5, 5, 4, 6),

    // DM
    GULLIT("Holland", 3, "Gullit", SpriteImages.RED4,
            0, "DM", 9, 9, 9, 9),
    BEEK("Holland", 2, "Beek", SpriteImages.RED5,
            0, "DM", 7, 7, 8, 5),
    WIJNALDUM("Holland", 1, "Wijnaldum", SpriteImages.RED4,
            0, "DM", 6, 6, 7, 6),
    VORMER("Holland", 0, "Vormer", SpriteImages.RED1,
            0, "DM", 5, 5, 5, 4),

    // AT
    CRUYFF("Holland", 3, "Cruyff",SpriteImages.RED5,
            0, "AT", 9, 9, 9, 9),
    DEPAY("Holland", 2, "Depay",SpriteImages.RED3,
            0, "AT", 7, 7, 6, 7),
    MALEN("Holland", 1, "Malen",SpriteImages.RED4,
            0, "AT", 5, 6, 5, 6),
    DOST("Holland", 0, "Dost",SpriteImages.RED1,
            0, "AT", 4, 5, 4, 4),

    //GK
    SAR("Holland", 3, "Sar", SpriteImages.RED5,
            0, "GK", 3, 8, 9, 7),
    CILLESSEN("Holland", 2, "Cillessen", SpriteImages.RED1,
            0, "GK", 3, 5, 7, 6),
    BIZOT("Holland", 0, "Bizot", SpriteImages.RED3,
            0, "GK", 3, 4, 4, 4),
    ;


    private final String  cardsFolder="res/images/Cards/";
    private final String spriteFolder="res/images/Soccer/";
    private SpriteImages playerImages;
    private final String name;
    private Image image;
    private Image cardImage;
    private final String imagefile;
    private final String cardFile;
    private final int value;
    private String position;
    private final int efRadius;
    private final int resistance;
    private final int power;
    private final int level;
    private final String country;
    private final int speed;
    private boolean onscreen = false;

    /**
     * Creates a new Item Register
     * @param name Name of the item
     * @param cost how much it is going to cost as an int
     */
    // for Attackers
    ItemRegister(String country, int level, String name, SpriteImages sprite, int cost, String position, int effectRadius, int resistance, int power, int speed){
        this.playerImages = sprite;
        this.name = name;
        this.level = level;
        this.imagefile = spriteFolder + sprite;
        this.cardFile = cardsFolder + country + "/" + position + "/" + name + ".png";
        this.value = cost;
        this.position = position;
        this.efRadius = effectRadius;
        this.resistance = resistance;
        this.power = power;
        this.speed = speed;
        this.country = country;
    }

    // Not loading the images in compile time only in running.

    public void setCardImage(){
        cardImage = new Image(cardFile);
    }
/*
    public void setImage(){

        this.image = new Image(imagefile + ".png");

    }

     */

    public SpriteImages playerImages() {
        return playerImages;
    }

    public String getImagefile() {
        return imagefile;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPower() {
        return power;
    }

    public int getResistance() {
        return resistance;
    }

    public int getDifficulty() {
        return level;
    }

    public String getCountry() {
        return country;
    }

    /**
     * gets the Image
     * @return returns this Item's image
     */
    public Image getImage() {
        return playerImages.getStanding();
    }

    /**
     * Gets the name of the Item
     * @return the name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value (cost) of the Item
     * @return the cost as an int
     */
    public int getValue() {
        return value;
    }

    //gets the card Image to print
    public Image getCardImage() {
        return cardImage;
    }

    public String getPosition() {
        return position;
    }

    public int getEfRadius() {
        return efRadius;
    }

    public void setOnscreen() {
        this.onscreen = true;
    }

    public void unsetOnscreen() {
        this.onscreen = false;
    }

    public boolean isOnscreen() {
        return onscreen;
    }

    public static void findItemRevive(String name){
        for (ItemRegister r : ItemRegister.values()){
            if (r.getName().equals(name)){
                r.unsetOnscreen();
            }
        }

    }

}
