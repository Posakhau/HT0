public class Lamp extends Item implements Service{
    public double getItemSquare() {return -1;}
    public String getItemName() {return "Unnamed lamp";}
    public void printAttributes() {}


    private int type = 2;
    private double luminosity;

    private Lamp() {}

    public Lamp (double luminosity) {
        setLuminosity(luminosity);
    }

    public int getType() {
        return this.type;
    }

    public double getLuminosity() {
        return this.luminosity;
    }
    public void setLuminosity(double luminosity) {
        if(luminosity>0) {
            this.luminosity = luminosity;
        } else {
            this.luminosity = 0;
        }
    }
}
