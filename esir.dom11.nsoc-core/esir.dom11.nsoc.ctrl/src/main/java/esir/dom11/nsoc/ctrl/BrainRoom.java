package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 17/01/12
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class BrainRoom {
    /*class composed of different algorithm*/
  
    //Attribute
    String building;
    String room;
    
    boolean fullAuto;
    
    /*Constructor
    @param: String location: type "Building/Room"
    Initialize the BrainRoom location, building and room
    */
    public BrainRoom(String location){
        //location
        String[] temp = new String[2];
        temp = location.split("/");
        this.building = temp[0];
        this.room = temp[1];
        this.fullAuto = false;

    }
    
    /*method 1 : Algorithm of light control
    @param: String info; info relate of up or down
    */
    private void lightControl(String info){
        if(fullAuto){
            if(info.equals("up")){

            }
            else if(info.equals("down")){

            }
        }
        else{
            if(info.equals("up")){

            }
            else if(info.equals("down")){

            }
        }
    }
    
    /*method 2 : Algorithm temperatureControl
    @param: String info; info relate of up or down
    */
    private void temperatureControl(String info){
        if(fullAuto){
            if(info.equals("up")){

            }
            else if(info.equals("down")){

            }
        }
        else{
            if(info.equals("up")){

            }
            else if(info.equals("down")){

            }
        }

    }
    /*
    Scenario
    */
    // leaving scenario
    private void leavingScenario(){

    }
    // coming scenario
    private void comingScenario(){


    }
    // video conference scenario
    private void videoConference(){
        //switch on video projector

        //turn off light

        //close shutters

        //get off screen

    }
    

    public void receiveCommand(String user, Command command){

    }
    public void receiveData(Data data){
        
    }

    /*
     Room properties
     */

    // to obtain the room
    public String getRoom(){
        return room;
    }
    // to obtain the building
    public String getBuilding(){
        return building;
    }
    //scan the room to have all sensors and actuators
    public void getAllDevices(){

    }
    // to have all sensors of the room
    public void getAllSensors(){

    }
    // to have all sensors of the room
    public void getAllActuators(){

    }
    // to add a sensor or actuator to the room
    public void addDevice(){

    }
    // to remove one device of the room
    public void removeDevice(){

    }

    /*
     Algorithm methods
     */
    public void fullAuto(boolean info){
        fullAuto = info;
    }


    /*
     Stop brain's room
     */
    public void stop(){
        building = null;
        room = null;
    }
}
