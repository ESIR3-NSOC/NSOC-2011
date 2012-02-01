package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

import java.util.Date;
import java.util.LinkedList;


/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 18/01/12
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class TheBrain {
    /*
     *Attribute
     */
    private LinkedList<BrainRoom> roomList;
    
    
    /*
     * Constructor
     */
    public TheBrain(){
         roomList = new LinkedList<BrainRoom>(); 
    }
    
    /*
     * Method to create a brain room
     */
    public void createRoom(String building, String room){
        String location = "/" + building + "/" + room + "/";
        //create a brainRoom
        BrainRoom temp = new BrainRoom(location);
        //add location to roomList
        roomList.add(temp);
    }

    //send data of sensors to room
    public void sendInfoTo(String location, LinkedList<DataType> dataTypeList, Date begin, Date end){
        //room search into roomList
        BrainRoom room = searchRoom(location);
        //send data to the brainRoom
      //  room.receiveData(data);
        
    }
    public void sendCommandTo(String user,Command command){
//    public void sendCommandTo(String user,String location, Command command){
/*        //room search into roomList
        BrainRoom room = searchRoom(location);
        //send command to the brainRoom
        room.receiveCommand(user, command);
*/    }
    
    public void stopTheBrain(){

    }
    
    //search room into the list
    private BrainRoom searchRoom(String location){
        BrainRoom room = null;
        for(int i = 0; i<roomList.size(); i++){
            String temp[] = location.split("/");
            if(roomList.get(i).getBuilding().equals(temp[1]) && roomList.get(i).getRoom().equals(temp[2])){
                room = roomList.get(i);
                break;
            }
        }
        return room;
    }
    
    
}
