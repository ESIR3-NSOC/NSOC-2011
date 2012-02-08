package esir.dom11.nsoc.server;

/**
 *  This class is a Singleton.
 *  It will save all data from the Controller in this class
 */
public final class LocalStorage {
    private static ServerComponent _sc;
    private static LocalStorage _reference;

   // get the singleton of the class
    public static LocalStorage getLocalStorageObject(){
        if (_reference == null){
            _reference = new LocalStorage();
        }
        return _reference;
    }

    // get the reference to the ServerComponent
    public ServerComponent getServerComponent(){
        return _sc;
    }

    public void setServerComponent(ServerComponent sc){
        _sc = sc;
    }
}
