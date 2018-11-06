
public class Item implements Service {

    public double getLuminosity(){
        return 1;
    }
    public double getItemSquare() {return 1;}
    public String getItemName() {return "Unknown Item";}
    public void printAttributes() {}


    private int type = 1;
    public int getType() {
        return this.type;
    }
}
