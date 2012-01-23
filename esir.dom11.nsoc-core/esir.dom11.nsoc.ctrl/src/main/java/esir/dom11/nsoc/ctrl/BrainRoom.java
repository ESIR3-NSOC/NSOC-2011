package esir.dom11.nsoc.ctrl;

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

    }
    
    /*method 1 : Algorithm of light control
    @param: String info; info relate of up or down
    */
    private void lightControl(String info){
        if(info.equals("up")){

        }
        else if(info.equals("down")){

        }
    }
    
    /*method 2 : Algorithm temperatureControl
    @param: String info; info relate of up or down
    */
    private void temperatureControl(String info){
        if(info.equals("up")){

        }
        else if(info.equals("down")){

        }
    }
    /*method 3 : leaving scenario

    */
    private void leavingScenario(){

    }
    /*method 4 : coming scenario

    */
    private void comingScenario(){

    }
    
    public String getRoom(){
        return room;
    }

    public String getBuilding(){
        return building;
    }
    /*Stop brain's room*/
    public void stop(){
        building = null;
        room = null;
    }
}
