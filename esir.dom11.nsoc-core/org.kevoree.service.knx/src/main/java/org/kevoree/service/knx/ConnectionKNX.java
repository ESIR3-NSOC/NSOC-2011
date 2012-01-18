package org.kevoree.service.knx;

import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;
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


@ComponentType
public class ConnectionKNX extends AbstractComponentType implements IntToConnect{

    private ToConnect connection;
    @Start
    public void startComponent() {
        System.out.println("ConnectionKNX:: Start");
        String adressePC = this.getDictionary().get("ADRESSE_PC").toString();
        String adresseMaquette = this.getDictionary().get("ADRESSE_MAQUETTE").toString();
        System.out.println("Connection à la maquette: " + "adressePC:" + adressePC + " adresseMaquette:" + adresseMaquette);
    }

    @Stop
    public void stopComponent() {
        System.out.println("ConnectionKNX:: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("ConnectionKNX:: Update");
    }

    /*  @Port(name = "ConnexionKNX")
  public void consumeHello(Object o) {
      System.out.println("ConnectionKNX:: Received " + o.toString());
      if (o instanceof String) {
          String msg = (String) o;
          System.out.println("Hello ConnectionKNX received: " + msg);
      }
  }  */

    @Override
    @Port(name = "ConnectionKNX", method = "connected")
    public void connected() {
        System.out.println("Connected Called");
        //connection.connected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "disconnected")
    public void disconnected() {
        //To change body of implemented methods use File | Settings | File Templates.
        connection.disconnected();
    }

    @Override
    @Port(name = "ConnectionKNX", method = "lire")
    public boolean lire(String adresseGroupe) {
        //To change body of implemented methods use File | Settings | File Templates.
        return connection.lire(adresseGroupe);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "ecrire")
    public void ecrire(String adresseGroupe, boolean bool) {
        //To change body of implemented methods use File | Settings | File Templates.
        connection.ecrire(adresseGroupe, bool);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "listener")
    public void listener(String adresse) {
        //To change body of implemented methods use File | Settings | File Templates.
        connection.listener(adresse);
    }

    @Override
    @Port(name = "ConnectionKNX", method = "chercheAdresseMaquette")
    public void chercheAdresseMaquette() {
        //To change body of implemented methods use File | Settings | File Templates.
        connection.chercheAdresseMaquette();
    }
}

