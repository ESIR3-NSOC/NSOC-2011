package esir.dom11.nsoc.model;


import java.util.Date;
import java.util.LinkedList;

public class Ihm2Ctrl {

    /*
     * Attributes
     */

    private String _action;
    private String _choice;
    private String _location;
    private LinkedList<DataType> _datatypes;
    private Date _beginDate;
    private Date _endDate;

    /*
    * Constructors
    */

    public Ihm2Ctrl(){
        _action = new String();
        _choice = new String();
        _location = new String();
        _datatypes = new LinkedList<DataType>();
        _beginDate = new Date();
        _endDate = new Date();
    }

    public Ihm2Ctrl(String action, String choice, String location, LinkedList<DataType> datatypes, Date beginDate, Date endDate) {
        _action = action;
        _choice = choice;
        _location = location;
        _datatypes = datatypes;
        _beginDate = beginDate;
        _endDate = endDate;
    }

    /*
     * Getters / Setters
     */

    public String getAction(){
        return _action;
    }
    public String getChoice(){
        return _choice;
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

    // indicates the kind of action ("get"/"post" request)
    public void setAction(String action){
        _action = action;
    }
    public void setChoice(String choice){
        _choice = choice;
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
}
