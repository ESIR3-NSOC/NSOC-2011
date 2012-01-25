package esir.dom11.nsoc.ctrl;

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
        //create a brainRoom

        //add location to roomList
        
    }
    //send data to room
    public void sendInfoTo(String location){
        //room search into roomList


        
        
    }
    public void stopTheBrain(){

    }
    
}
