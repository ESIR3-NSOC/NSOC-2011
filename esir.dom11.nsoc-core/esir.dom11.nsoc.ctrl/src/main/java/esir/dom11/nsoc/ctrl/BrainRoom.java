package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Sensor;

import java.util.LinkedList;

public class BrainRoom {

    String roomLocation;
    //list of sensors
    LinkedList<Sensor> sensorsList;
    LinkedList<Actuator> actuatorList;
    LinkedList<Data> devicesStates;

    //mode full auto or semi auto
    boolean fullAuto;

    //presence in the room
    boolean presence;

    //Constructor
    public BrainRoom(String location) {
        this.roomLocation = location;
        this.fullAuto = false;
        this.devicesStates = new LinkedList<Data>();
    }

    /*
    method 1 : Algorithm of light control
    @param: String info; info relate of up or down
    */
    public LinkedList<Action> lightControl(String info) {
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (presence) {
            if (info.equals("up")) {

            } else if (info.equals("down")) {

            }
        } else {
            Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
            Action lamp = new Action(act, "OFF");
            Actuator act2 = new Actuator(DataType.LAMP, "/bat7/salle930/shutter/0");
            Action shutter = new Action(act2, "OFF");
            actionList.add(lamp);
            actionList.add(shutter);
        }
        return actionList;
    }
    public void lightControlFullAuto() {
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (fullAuto) {
            if (presence) {
                //mise a jour capteur

                //algo
            } else {
                Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                Action lamp = new Action(act, "OFF");
                Actuator act2 = new Actuator(DataType.LAMP, "/bat7/salle930/shutter/0");
                Action shutter = new Action(act2, "OFF");
                actionList.add(lamp);
                actionList.add(shutter);
            }
        }
    }

    /*
    method 2 : Algorithm temperatureControl
    @param: String info; info relate of up or down
    */
    public LinkedList<Action> temperatureControl(String info) {
        LinkedList<Action> actionList = new LinkedList<Action>();

        if (presence) {
            if (info.equals("up")) {

            } else if (info.equals("down")) {

            }
        } else {
            Actuator act = new Actuator(DataType.HEAT, "/bat7/salle930/heat/0");
            Action heat = new Action(act, "OFF");
            actionList.add(heat);
        }
        return actionList;
    }
    public void temperatureControlFullAuto() {
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (!fullAuto) {
            if (presence) {


            } else {
                Actuator act = new Actuator(DataType.HEAT, "/bat7/salle930/heat/0");
                Action heat = new Action(act, "OFF");
                actionList.add(heat);
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
    // update data sensor's room
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

    // to get location of the room
    public String getLocation() {
        return roomLocation;
    }

    // stop or start fullAuto on thread
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

    //Stop the brain
    public void stop() {

    }
}
