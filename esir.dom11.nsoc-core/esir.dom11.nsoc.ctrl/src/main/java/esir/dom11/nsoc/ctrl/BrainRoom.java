package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Sensor;

import java.util.LinkedList;

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

    //list of sensors
    LinkedList<Sensor> sensorsList;
    LinkedList<Actuator> actuatorList;

    LinkedList<Data> devicesStates;

    //mode full auto or semi auto
    boolean fullAuto;

    //presence in the room
    boolean presence;

    /*Constructor
    @param: String location: type "Building/Room"
    Initialize the BrainRoom location, building and room
    */
    public BrainRoom(String location) {
        //location
        String[] temp = new String[2];
        temp = location.split("/");
        this.building = temp[0];
        this.room = temp[1];
        this.fullAuto = false;

        devicesStates = new LinkedList<Data>();
    }


    /*
    method 1 : Algorithm of light control
    @param: String info; info relate of up or down
    */
    public LinkedList<Action> lightControl(String info) {
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (info.equals("up")) {

        } else if (info.equals("down")) {

        }
        return actionList;
    }

    public void lightControlFullAuto() {
        if (!fullAuto) {
            if (presence) {
                //mise a jour capteur

                //algo
            } else {
                //light off
                //shutter close
            }
        }
    }

    /*
    method 2 : Algorithm temperatureControl
    @param: String info; info relate of up or down
    */
    public LinkedList<Action> temperatureControl(String info) {
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (info.equals("up")) {

        } else if (info.equals("down")) {

        }
        return actionList;
    }

    public void temperatureControlFullAuto() {
        if (!fullAuto) {
            if (presence) {
                //algo
                for (int i = 0; i < sensorsList.size(); i++) {
                    if (sensorsList.get(i).getDataType().equals(DataType.TEMPERATURE)) {

                    }
                }
            } else {
                //chauffage off
            }
        }
    }

    /*
    Scenario
    */
    // leaving scenario
    private void leavingScenario() {

    }

    // coming scenario
    private void comingScenario() {


    }

    // video conference scenario
    private void videoConference() {
        //switch on video projector

        //turn off light

        //close shutters

        //get off screen

    }

    /*
     Room properties
     */

    public void updateRoom(Data data) {
        boolean find = false;
        for (int i = 0; i < devicesStates.size(); i++) {
            if (data.getId().equals(devicesStates.get(i).getId())) {
                devicesStates.set(i, data);
                find = true;
                break;
            }
        }
        if (!find) {
            devicesStates.add(data);
        }
    }

    // to obtain the room
    public String getRoom() {
        return room;
    }

    // to obtain the building
    public String getBuilding() {
        return building;
    }

    //scan the room to have all sensors and actuators
    public void getAllDevices() {

    }

    // to have all sensors of the room
    public LinkedList<Sensor> getAllSensors() {
        return sensorsList;
    }

    // to have all sensors of the room
    public LinkedList<Actuator> getAllActuators() {
        return actuatorList;
    }

    // to add a sensor to the room
    public void addSensor(Sensor sensor) {

    }

    // to remove one sensor of the room
    public void removeSensor(Sensor sensor) {

    }

    /*
     Algorithm methods
     */
    public void fullAuto(boolean info) {
        fullAuto = info;
        if (fullAuto) {
            new Thread() {
                public void run() {
                    while (fullAuto) {
                        try {
                            temperatureControlFullAuto();
                            lightControlFullAuto();
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }.start();
        }
    }

    /*
     Stop brain's room
     */
    public void stop() {
        building = null;
        room = null;
    }
}
