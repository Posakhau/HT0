import java.text.DecimalFormat;
import java.util.ArrayList;

public class Room {

    // CONSTRUCTORS
    private Room() {}

    public Room (String roomName, double roomSquare, int windowsAmount) {
        setRoomName(roomName);
        setRoomSquare(roomSquare);

        try {
            if (!isLuminosityExceeded(windowLuminosity*windowsAmount)) {
                setWindowsAmount(windowsAmount);
            } else
            {
                throw new IlluminanceTooMuchException("Превышена освещенность помещения");
            }

        } catch (IlluminanceTooMuchException ex) {
            System.out.println(ex.getMessage());
        }
        calculateLuminosity();
        calculateItemsSquare();
    }

    //ADDING PROPERTIES
    public void add(Item item) {
        if (item.getType()==1) {
            try {
                if (!isSquarePercentageLimitExceeded(item.getItemSquare())) {
                    Furniture.add(item);
                    calculateItemsSquare();
                    calculateFreeSquare();
                } else {
                    throw new SpaceUsageTooMuchException("Лимит площади предметов в помещении превышен");
                }
            }
            catch (SpaceUsageTooMuchException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (item.getType()==2) {
            try {
                if(isLuminosityExceeded(item.getLuminosity())) {
                    throw new IlluminanceTooMuchException("Превышена освещенность помещения "+getRoomName());
                } else {
                    Lamps.add(item);
                    calculateLuminosity(); }
            } catch (IlluminanceTooMuchException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // VARIABLES
    private int windowsAmount;     // amounts of windows in the room (by constructor)
    private String roomName;       // name of the room (by constructor)

    private double roomSquare = 0; // room square what has been set up (by constructor)
    private double itemsSquare =0; //summary square of all items, placed in the room  (by calculation)
    private double freeRoomSquare =0; //amount of free square in the room (by calculation )
    private double avaliableItemsSquarePercentage = 70;


    private double roomLuminosity = 0; // summary room luminosity (by calculation)
    private double windowLuminosity = 700; // by the task
    private double maximumLuminosityParameter = 4000; // by the task

    private ArrayList<Item>Lamps = new ArrayList();   // keeps amount of lamps in the room
    private ArrayList<Item> Furniture = new ArrayList(); //keeps amount of furniture in the room

    // GETTERS & SETTERS
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getRoomSquare() {
        return roomSquare;
    }
    public void setRoomSquare(double roomSquare) {
        try {
            if (roomSquare>0) {
                this.roomSquare=roomSquare;
            } else {
                throw new IncorrectDataInputException("Некорректное значение площади");
            }
        } catch (IncorrectDataInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public double getItemsSquare() {
        return itemsSquare;
    }
    public void setItemsSquare(double itemsSquare) {
        if (itemsSquare>0) {
            this.itemsSquare = itemsSquare;
        } else {
        this.itemsSquare = 0;
        }
    }

    public double getFreeRoomSquare() {
        return freeRoomSquare;
    }
    public void setFreeRoomSquare(double freeRoomSquare) {
        this.freeRoomSquare = freeRoomSquare;
    }

    public int getWindowsAmount() {
        return windowsAmount;}
    public void setWindowsAmount(int windowsAmount) {
        try {
            if (windowsAmount>=0) {this.windowsAmount = windowsAmount;}
            else {
                this.windowsAmount = 0;
                throw new IncorrectDataInputException("Некорректное число окон");
            }
        } catch (IncorrectDataInputException ex){
            System.out.println(ex.getMessage());
        }
    }

    public double getRoomLuminosity() {
        return roomLuminosity;
    }
    public void setRoomLuminosity(double roomLuminosity) {
        this.roomLuminosity = roomLuminosity;
    }

    //SERVICE METHODS

    //returns true is luminosity of the room is exceeded - boolean
    boolean isLuminosityExceeded(double luminosity) {
        if ((getRoomLuminosity()+luminosity)>this.maximumLuminosityParameter) {
            return true;
        } else return false;
    }

    //returns true if summary square with possible item is exceeded - boolean
    boolean isSquarePercentageLimitExceeded(double itemSquare) {
        double possibleItemSquarePercentage = ((getItemsSquare()+itemSquare)*100)/getRoomSquare();
        if(possibleItemSquarePercentage>avaliableItemsSquarePercentage) {
            return true;
        } else {
         return false;
        }
    }

    //calculates summary room luminosity
    private void calculateLuminosity() {
        double totalWindowsLuminosity = windowsAmount*windowLuminosity;
        setRoomLuminosity(totalWindowsLuminosity+calculateLampsLuminosity());
    }

    // calculates all luminosity what lamps produced in the room
    private double calculateLampsLuminosity() {
        double lampsLuminosity =0;
        for (int i=0;i<Lamps.size();++i) {
            lampsLuminosity+=Lamps.get(i).getLuminosity();
        }
        return lampsLuminosity;
    }

    //calculates summary square of all furniture items in the room
    private void calculateItemsSquare() {
        double summaryItemsSquare = 0;
        for (int i = 0; i< Furniture.size(); ++i) {
            summaryItemsSquare+= Furniture.get(i).getItemSquare();
        }
        setItemsSquare(summaryItemsSquare);
    }

    //calculates free square in the room
    private void calculateFreeSquare() {
        setFreeRoomSquare((getRoomSquare()-getItemsSquare()));
    }

    //calculates free square percentage of the room
    private double getFreeSquarePercentage() {
        return(100-((getItemsSquare()*100)/getRoomSquare()));
    }

    //calculates busy square percentage of the room
    private double getBusySquarePercentage() {
        return ((getItemsSquare()*100)/getRoomSquare());
    }

    // REPORTING METHODS
    public void describe () {
        System.out.println("Описание комнаты "+getRoomName()+": ");
        // Освещённость = 2500 (3 окна по 700 лк, лампочки 150 лк и 250 лк)

        //LUMINOSITY DESCRIPTION PART
        //if no lamps in the room
        if (Lamps.size()==0) {
            System.out.println("Освещённость = "+getRoomLuminosity()+" ("+getWindowsAmount()+" окна по "+windowLuminosity+"лк)");
        }
        // if one lamp in the room
        if (Lamps.size()==1) {
            System.out.println("Освещённость = "+getRoomLuminosity()+" ("+getWindowsAmount()+" окна по "+windowLuminosity+"лк,"+
            " лампочка "+Lamps.get(0).getLuminosity()+"лк)");
        }
        //if lamps more then one on the room
        if (Lamps.size()>1) {
            System.out.print("Освещённость = "+getRoomLuminosity()+" ("+getWindowsAmount()+" окна по "+windowLuminosity+"лк, лампочки ");

            for (int i=0;i<Lamps.size();++i) {
                if(i!=(Lamps.size()-1)) {
                    System.out.print(Lamps.get(i).getLuminosity()+" лк и ");
                } else {
                    System.out.print(Lamps.get(i).getLuminosity()+" лк)\n");
                }
            }
        }

        // SQUARE DESCRIPTION PART
        //if all room is free
        if (getItemsSquare()==0) {
            System.out.println("Площадь = "+getRoomSquare()+"м^2, свободно 100% площади комнаты");
        } else {
            String formattedFSP = new DecimalFormat("0.00").format(getFreeSquarePercentage());
        System.out.println("Площадь = "+getRoomSquare()+"м^2 (занято "+getItemsSquare()+"м^2," +
                " гарантированно свободно "+getFreeRoomSquare()+" м^2 или "+formattedFSP+"% площади)"); }
       // Площадь = 100 м^2 (занято 4-5 м^2, гарантированно свободно 95 м^2 или 95% площади)

       //FURNITURE DESCRIPTION
        if (Furniture.size()==0) {
            System.out.println("Мебели нет");
        } else {
            System.out.print("Мебель: \n");
            for (int i = 0; i < Furniture.size(); ++i) {
                Furniture.get(i).printAttributes();
            }
            System.out.println("\n");
        }
    }

} // end of class
