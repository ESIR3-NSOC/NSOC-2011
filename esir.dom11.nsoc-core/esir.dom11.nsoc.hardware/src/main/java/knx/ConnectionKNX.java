package knx;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 09/01/12
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@Provides({
        @ProvidedPort(name = "ConnectionKNX", type = PortType.SERVICE, className = IntToConnect.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "ADRESSE_PC", defaultValue = "192.168.1.127", optional = true,
                vals = {"192.168.1.128"}),
        @DictionaryAttribute(name = "ADRESSE_MAQUETTE", defaultValue = "", optional = true,
                vals = {"192.168.1.120"})
})

@Library(name = "NSOC_2011")
@ComponentType
public class ConnectionKNX extends AbstractComponentType implements IntToConnect {

    @Start
    public void startComponent() {
        System.out.println("ConnectionKNX: Start");
        String adressePC = this.getDictionary().get("ADRESSE_PC").toString();
        String adresseMaquette = this.getDictionary().get("ADRESSE_MAQUETTE").toString();
        System.out.println("ConnectionKNX: " + "adressePC:" + adressePC + " adresseMaquette:" + adresseMaquette);
    }

    @Stop
    public void stopComponent() {
        System.out.println("ConnectionKNX: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("ConnectionKNX: Update");
    }

    @Override
    @Port(name = "ConnectionKNX", method = "connected")
    public void connected() {
        System.out.println("ConnectionKNX: Connected");
        //connection.connected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "disconnected")
    public void disconnected() {
        System.out.println("ConnectionKNX: disonnected");
        //connection.disconnected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "read")
    public boolean read(String adresseGroupe) {
        System.out.println("ConnectionKNX: read");
        //return connection.read(adresseGroupe);
        return false;
    }

    @Override
    @Port(name = "ConnectionKNX", method = "write")
    public void write(String adresseGroupe, boolean bool) {
        System.out.println("ConnectionKNX: write");
        //connection.write(adresseGroupe, bool);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "listener")
    public void listener(String adresse) {
        System.out.println("ConnectionKNX: listener");
        //connection.listener(adresse);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "searchSketch")
    public String searchSketch() {
        System.out.println("ConnectionKNX: searchSketch");
        // return connection.searchSketch();
        return null;
    }

    @Override
    @Port(name = "ConnectionKNX", method = "getProtocol")
    public String getProtocol() {
        return "knx";
    }
}

