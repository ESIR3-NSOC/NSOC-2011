package esir.server;

import esir.dom11.nsoc.model.Data;
import java.util.LinkedList;

/**
 *  This class is a Singleton.
 *  It will save all data from the Controller in this class
 */
public final class LocalStorage {
    private static LocalStorage _reference;
    private LinkedList<Data> _info;

    public static LocalStorage getLocalStorageObject(){
        if (_reference == null){
            _reference = new LocalStorage();
        }
        return _reference;
    }

    public LinkedList<Data> getAllData(){
        return _info;
    }


    public void setAllData(LinkedList<Data> info){
        _info = info;
        System.out.println("LocalStorage filled!");

    }

}
