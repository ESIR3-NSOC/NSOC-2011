package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.LinkedList;

public class HmiRequest {

    /*
     * Attributes
     */
    private HmiRestRequest _request;
    private String _location;
    private LinkedList<DataType> _datatypes;
    private Action _action;
    private Date _beginDate;
    private Date _endDate;


    /*
     * Constructors
     */

    public HmiRequest(){}

    // Constructor for Get all request
    public HmiRequest(String location, LinkedList<DataType> datatypes) {
        _request = HmiRestRequest.GET;
        _location = location;
        _datatypes = datatypes;
    }

    // Constructor for Get detail request
    public HmiRequest(String location, LinkedList<DataType> datatypes, Date beginDate, Date endDate) {
        _request = HmiRestRequest.GET;
        _location = location;
        _datatypes = datatypes;
        _beginDate = beginDate;
        _endDate = endDate;
    }

    // Constructor for Post request
    public HmiRequest(String location, Action action){
        _request = HmiRestRequest.POST;
        _location = location;
        _action = action;
    }

    /*
     * Getters / Setters
     */

    public HmiRestRequest getRequest(){
        return _request;
    }
    public String getLocation(){
        return _location;
    }
    public LinkedList<DataType> getDataTypes(){
        return _datatypes;
    }
    public Action getAction(){
        return _action;
    }
    public Date getBeginDate(){
        return _beginDate;
    }
    public Date getEndDate(){
        return _endDate;
    }

    public void setRequest(HmiRestRequest request){
        _request = request;
    }
    public void setLocation(String location){
        _location = location;
    }
    public void setDataTypes(LinkedList<DataType> datatypes){
        _datatypes = datatypes;
    }
    public void setAction(Action action){
        _action = action;
    }
    public void setBeginDate(Date beginDate){
        _beginDate = beginDate;
    }
    public void setEndDate(Date endDate){
        _endDate = endDate;
    }


    /*
     * Define the kind of action we send to the controller
     * "get"  => we want to have the values of the data
     * "post" => we want to send an action to change the value of an actuator
     */
    public enum HmiRestRequest {
    GET,
    POST;

        public String getValue() {
            return this.name();
        }
    }

}
