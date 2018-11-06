public class FurnitureItem extends Item implements Service{

    private FurnitureItem() {}
    public double getLuminosity(){
        return -1;
    }

    //CONSTRUCTOR v.1
    public FurnitureItem(String itemName, double itemSquare) {
        setItemName(itemName);
        setItemSquare(itemSquare);
        setMinimalItemSquare(itemSquare);
        setMaximalItemSquare(itemSquare);
    }

    //CONSTRUCTOR v.2
    public FurnitureItem(String itemName, double itemSquareMin,double itemSquareMax) {
        try {
            if (itemSquareMax >= itemSquareMin) {
                setItemName(itemName);
                setMinimalItemSquare(itemSquareMin);
                setMaximalItemSquare(itemSquareMax);
                setItemSquare(itemSquareMax);
            } else{
            throw new IncorrectDataInputException("Параметры минимальной и максимальной площадей некорректны");
            }
        }
        catch(IncorrectDataInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // VARIABLES OF FURNITURE ITEM
    private String itemName="";
    private int type = 1;
    private double itemSquare;
    private double minimalAllowedSquare = 0;

    private double minimalItemSquare=0;
    private double maximalItemSquare=0;

    //GETTERS & SETTERS
    public int getType() {
        return this.type;
    }

    public double getItemSquare() {
        return itemSquare;
    }
    public void setItemSquare(double square) {
        try {
            if (square > 0) {
                this.itemSquare = square;
            } else {
                throw new IncorrectDataInputException("Величина площади предмета некорректна");
            }
        }
        catch (IncorrectDataInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getItemName() {return this.itemName;}
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getMinimalItemSquare() {return minimalItemSquare;
    }
    public void setMinimalItemSquare(double minimalItemSquare) {
        try {
            if (minimalItemSquare > 0) {
                this.minimalItemSquare = minimalItemSquare;
            } else {
                throw new IncorrectDataInputException("Параметр минимальной площади некорректен");
            }
        }
        catch(IncorrectDataInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public double getMaximalItemSquare() {
        return maximalItemSquare;
    }
    public void setMaximalItemSquare(double maximalItemSquare) {
       try {
           if (maximalItemSquare > 0) {
               this.maximalItemSquare = maximalItemSquare;
           } else {
               throw new IncorrectDataInputException("Параметр максимальной площади некорректен");
           }
       }
       catch(IncorrectDataInputException ex) {
           System.out.println(ex.getMessage());
       }
    }

    //REPORT METHOD
    public void printAttributes(){
        if (getMinimalItemSquare()==getMaximalItemSquare()) {
            System.out.print(getItemName()+" (площадь "+getItemSquare()+" м^2)");
        } else {
            System.out.print(getItemName()+" (площадь от "+getMinimalItemSquare()+"м^2 до "+getMaximalItemSquare()+"м^2)");
        }
        System.out.println();
    }

} // end of class
