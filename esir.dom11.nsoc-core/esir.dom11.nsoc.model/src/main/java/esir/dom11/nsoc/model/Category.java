package esir.dom11.nsoc.model;

public enum Category {
       AUTO,                      // command categroy from the controller
       USER,                      // command category from an user
       SECURITY;                  // command from a security device
    
public String getValue(){
    return this.name();
}

}
