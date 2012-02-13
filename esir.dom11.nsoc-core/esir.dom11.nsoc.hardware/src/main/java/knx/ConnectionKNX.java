package knx;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;

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
        @DictionaryAttribute(name = "ADRESSE_PC", defaultValue = "192.168.1.127", optional = true),
        @DictionaryAttribute(name = "ADRESSE_MAQUETTE", defaultValue = "192.168.1.128", optional = true)
})

@Library(name = "NSOC_2011")
@ComponentType
public class ConnectionKNX extends AbstractComponentType implements IntToConnect {

    private ToConnect connection;
    @Start
    public void startComponent() {
        System.out.println("ConnectionKNX: Start");
        String adressePC = this.getDictionary().get("ADRESSE_PC").toString();
        String adresseMaquette = this.getDictionary().get("ADRESSE_MAQUETTE").toString();
        System.out.println("ConnectionKNX: " + "adressePC:" + adressePC + " adresseMaquette:" + adresseMaquette);
        connection = new ToConnect(adressePC, adresseMaquette);
        connection.connected();
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
        connection.connected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "disconnected")
    public void disconnected() {
        System.out.println("ConnectionKNX: disonnected");
        connection.disconnected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "read")
    public String read(String adresseGroupe) {
        System.out.println("ConnectionKNX: read");
        return connection.read(adresseGroupe);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "write")
    public void write(String adresseGroupe, boolean bool) {
        System.out.println("ConnectionKNX: write Address : " + adresseGroupe);
        connection.write(adresseGroupe, bool);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "listener")
    public void listener(String adresse) {
        System.out.println("ConnectionKNX: listener");
        connection.listener(adresse);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "searchSketch")
    public String searchSketch() {
        System.out.println("ConnectionKNX: searchSketch");
        return connection.searchSketch();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "getProtocol")
    public String getProtocol() {
        return "knx";
    }

    @Override
    @Port(name = "ConnectionKNX", method = "getNetLink")
    public KNXNetworkLinkIP getNetLink() {
        return connection.getNetLink();  //To change body of implemented methods use File | Settings | File Templates.
    }
}

