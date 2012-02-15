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
        presence = true;
    }

    /*
    method 1 : Algorithm of light control
    @param: String info; info relate of up or down
    */
    public LinkedList<Action> lightControl(String info) {
        System.out.println("********************************** " + info);
        LinkedList<Action> actionList = new LinkedList<Action>();
        if (presence) {
            int lumInt = Integer.parseInt(searchDevice("/bat7/salle930/lum/0"));
            int lumExt = Integer.parseInt(searchDevice("/bat7/salle930/lum/1"));
            String lamp = searchDevice("/bat7/salle930/lamp/0");
            String shutter = searchDevice("/bat7/salle930/shutter/0");
            System.out.println("presence");
            if (info.equals("UP")) {
                System.out.println("up");
                if (shutter.equals("OPEN")) {
                    System.out.println("shutter open");
                    if (lamp.equals("ON")) {
                        System.out.println("lamp on");
                        System.out.println("Control: max brightness");
                    } else if (lamp.equals("OFF")) {
                        System.out.println("lamp off");
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                        Action ac1 = new Action(act, "ON");
                        actionList.add(ac1);
                    } else System.out.println("Control: lamp bad value : " + lamp);
                } else if (shutter.equals("CLOSE")) {
                    System.out.println("shutter close");
                    if (lumExt > lumInt) {
                        System.out.println("lumExt > lumInt");
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                        Action ac1 = new Action(act, "OPEN");
                        actionList.add(ac1);
                    } else if (lumExt <= lumInt) {
                        System.out.println("lumExt < lumInt");
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                        Action ac1 = new Action(act, "ON");
                        actionList.add(ac1);
                    } else System.out.println("Control : lumInt or lum ext bad value : " + lumInt + " , " + lumExt);
                } else System.out.println("Control: shutter bad value : " + shutter);
            } else if (info.equals("DOWN")) {
                System.out.println("down");
                if (shutter.equals("OPEN")) {
                    System.out.println("shutter open");
                    if (lamp.equals("ON")) {
                        System.out.println("lamp on");
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                        Action ac1 = new Action(act, "OFF");
                        actionList.add(ac1);
                    } else if (lamp.equals("OFF")) {
                        System.out.println("lamp off");
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/shutter/0");
                        Action ac1 = new Action(act, "CLOSE");
                        actionList.add(ac1);
                    } else System.out.println("Control: lamp bad value : " + lamp);
                } else if (shutter.equals("CLOSE")) {
                    System.out.println("shutter close");
                    Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/lamp/0");
                    Action ac1 = new Action(act, "OFF");
                    actionList.add(ac1);
                }
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
            String shutter = searchDevice("/bat7/salle930/shutter/0");
            int tempOut = Integer.parseInt(searchDevice("/bat7/salle930/temp/1"));
            int tempInt = Integer.parseInt(searchDevice("/bat7/salle930/temp/0"));
            if (info.equals("UP")) {
                if (tempInt < tempOut) {
                    if (shutter.equals("OPEN")) {
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/heat/0");
                        Action ac1 = new Action(act, "ON");
                        actionList.add(ac1);
                    } else if (shutter.equals("CLOSE")) {
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/shutter/0");
                        Action ac1 = new Action(act, "OPEN");
                        actionList.add(ac1);
                    }

                } else if (tempInt > tempOut) {
                    Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/heat/0");
                    Action ac1 = new Action(act, "ON");
                    actionList.add(ac1);
                } else System.out.println("Control : tempInt or tempExt ext bad value : " + tempInt + " , " + tempOut);

            } else if (info.equals("DOWN")) {
                if (tempInt < tempOut) {
                    if (shutter.equals("OPEN")) {
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/shutter/0");
                        Action ac1 = new Action(act, "CLOSE");
                        actionList.add(ac1);
                    } else if (shutter.equals("CLOSE")) {
                        Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/heat/0");
                        Action ac1 = new Action(act, "OFF");
                        actionList.add(ac1);
                    }
                } else if (tempInt > tempOut) {
                    Actuator act = new Actuator(DataType.LAMP, "/bat7/salle930/heat/0");
                    Action ac1 = new Action(act, "OFF");
                    actionList.add(ac1);
                } else System.out.println("Control : tempInt or tempExt ext bad value : " + tempInt + " , " + tempOut);

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

    public String searchDevice(String location) {
        String state = null;
        for (int i = 0; i < devicesStates.size(); i++) {
            if (devicesStates.get(i).getSensor().getLocation().equals(location)) {
                state = devicesStates.get(i).getValue();
            }
        }
        return state;
    }
}
