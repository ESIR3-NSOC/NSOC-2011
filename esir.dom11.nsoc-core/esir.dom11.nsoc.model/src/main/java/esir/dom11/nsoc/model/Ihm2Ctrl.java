package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.LinkedList;

public class Ihm2Ctrl {

    /*
     * Attributes
     */

    /*
     * Define the kind of action we send to the controller
     * "get"  => we want to have the values of the data
     * "post" => we want to send an action to change the value of an actuator
     */
    private IhmAction _action;
    private String _location;
    private LinkedList<DataType> _datatypes;
    private Date _beginDate;
    private Date _endDate;


    /*
    * Constructors
    */

    public Ihm2Ctrl(){
        _location = new String();
        _datatypes = new LinkedList<DataType>();
        _beginDate = new Date();
        _endDate = new Date();
    }

    public Ihm2Ctrl(IhmAction action, String location, LinkedList<DataType> datatypes, Date beginDate, Date endDate) {
        _action = action;
        _location = location;
        _datatypes = datatypes;
        _beginDate = beginDate;
        _endDate = endDate;
    }

    /*
     * Getters / Setters
     */

    public IhmAction getAction(){
        return _action;
    }

    public String getLocation(){
        return _location;
    }
    public LinkedList<DataType> getDataTypes(){
        return _datatypes;
    }
    public Date getBeginDate(){
        return _beginDate;
    }
    public Date getEndDate(){
        return _endDate;
    }

    public void setAction(IhmAction action){
        _action = action;
    }

    public void setLocation(String location){
        _location = location;
    }
    public void setDataTypes(LinkedList<DataType> datatypes){
        _datatypes = datatypes;
    }
    public void setBeginDate(Date beginDate){
        _beginDate = beginDate;
    }
    public void setEndDate(Date endDate){
        _endDate = endDate;
    }

    public enum IhmAction {
    GET,
    POST;

        public String getValue() {
            return this.name();
        }
    }

}
