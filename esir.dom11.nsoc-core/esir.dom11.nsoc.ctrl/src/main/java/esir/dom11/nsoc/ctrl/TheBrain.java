package esir.dom11.nsoc.ctrl;

import java.util.LinkedList;

public class TheBrain {
    //Attribute
    public LinkedList<BrainRoom> roomList;

    // Constructor
    public TheBrain(){
         roomList = new LinkedList<BrainRoom>(); 
    }

    //to stop the brain
    public void stopTheBrain(){
        roomList.remove();
    }

    // Method to create a brain room
    public void createRoom(String location){
        //create a brainRoom
        BrainRoom temp = new BrainRoom(location);
        //add location to roomList
        roomList.add(temp);
    }

    //search room into the list
    public BrainRoom searchRoom(String location){
        BrainRoom room = null;
        for(int i = 0; i<roomList.size(); i++){
            if(roomList.get(i).getLocation().equals(location)){
                room = roomList.get(i);
                break;
            }
        }
        return room;
    }
}
