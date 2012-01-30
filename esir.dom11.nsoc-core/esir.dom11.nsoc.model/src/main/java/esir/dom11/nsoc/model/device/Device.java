package esir.dom11.nsoc.model.device;

import java.util.UUID;

public interface Device {

    public UUID getId();

    public String getLocation();
    
    public void setLocation(String location);
}
