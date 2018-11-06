import java.util.ArrayList;


public class Building {

    public Building(String buildingName){
        setBuildingName(buildingName);
    }

    private Building() {}

    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    private String buildingName="Undefined Building";
    private int roomsAmount = 0;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    //returns true if room name is unique
    public boolean isRoomNameUnique(String roomName) {
        for (int i=0;i<rooms.size();++i) {
            if(rooms.get(i).getRoomName().equals(roomName)) {
                return false;
            }
        }
        return true;
    }

    public void describe() {
        System.out.println("Здание "+getBuildingName()+": ");
        if(rooms.size()==0) {
            System.out.println("Здание пустое, нет комнат, нет мебели, нет освещения");
        } else {
            for (int i=0;i<rooms.size();++i) {
                rooms.get(i).describe();
            }
        }
    }

    /*this method adds a new room to the building*/
    public void addRoom(String roomName, double roomSquare, int windowsAmount) {
        try {
            if (isRoomNameUnique(roomName)) {
                Room newRoom = new Room (roomName,roomSquare,windowsAmount);
                rooms.add(newRoom);
                refreshRoomsAmount();
            } else {
                throw new IncorrectDataInputException("Не уникальное имя помещения");
            }
        }
        catch (IncorrectDataInputException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*this method allows to access the room to add properties into the room */
    public Room getRoom(String roomName){
        for (int i=0;i<rooms.size();++i) {
            if (rooms.get(i).getRoomName().equals(roomName)) {
                return rooms.get(i);
            }
        }
        return null;
    }

    /*returns amount of rooms in the building*/
    public int getRoomsAmount() {
        return roomsAmount;
    }
    public void refreshRoomsAmount () {
        this.roomsAmount = rooms.size();
    }



} // end of class
