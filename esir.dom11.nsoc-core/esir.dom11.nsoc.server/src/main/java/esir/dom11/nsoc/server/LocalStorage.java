package esir.dom11.nsoc.server;

import esir.dom11.nsoc.model.Data;
import java.util.LinkedList;

/**
 *  This class is a Singleton.
 *  It will save all data from the Controller in this class
 */
public final class LocalStorage {
    private static ServerComponent _sc;
    private static LocalStorage _reference;
    private LinkedList<Data> _info;


   // get the singleton of the class
    public static LocalStorage getLocalStorageObject(){
        if (_reference == null){
            _reference = new LocalStorage();
        }
        return _reference;
    }

    // get all data saved by the Controller
    public LinkedList<Data> getAllData(){
        return _info;
    }
    // get the reference to the ServerComponent
    public ServerComponent getServerComponent(){
        return _sc;
    }



    public void setServerComponent(ServerComponent sc){
        _sc = sc;
    }
    public void setAllData(LinkedList<Data> info){
        _info = info;
        System.out.println("LocalStorage filled!");

    }

}
