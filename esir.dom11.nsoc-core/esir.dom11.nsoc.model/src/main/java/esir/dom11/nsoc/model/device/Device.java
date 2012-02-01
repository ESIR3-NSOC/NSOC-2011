package esir.dom11.nsoc.model.device;

import esir.dom11.nsoc.model.DataType;

import java.util.UUID;

public interface Device {

    public UUID getId();

    public String getLocation();
    
    public void setLocation(String location);

    public DataType getDataType();
}
