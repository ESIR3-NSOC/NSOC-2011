package esir.dom11.nsoc.model;

public class User {
    
    /*
     * Attributes
     */
    
    private String _id;
    private String _pwd;

    /*
     * Constructors
     */

    public User(){}

    public User(String id, String pwd){
        _id = id;
        _pwd = pwd;
    }

    /*
     * Getters / Setters
     */

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getPwd() {
        return _pwd;
    }

    public void setPwd(String pwd) {
        _pwd = pwd;
    }
    
    /*
     * Overrides
     */
    
    public String toString() {
        return "User: " + getId();
    }
}
