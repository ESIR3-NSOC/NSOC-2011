package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 16/01/12
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public interface ctrlInterface {

	//Send2... will allow us to send data to different components
	
	//HMI need some data so...
    void send2HMI(Command command);
    void send2HMI(LinkedList<Data> dataList);

    //send everythg that could have been modified
    void send2DAO(Data data );
    void send2DAO(Command command);
    
    //Send an actions list (= command) to conflict 
    void send2Conflict(Command command);

    void send2Sensors(DataType dataType);


    // Ask the context for a precise variable (eg : temp sensor from a room )
    void receiveFromContext(Object o);
    
    //Receive... vont permettre de recevoir les données des différents composants
    //Use of listeners
    
    //HMI ask us for some data
    void receiveHMI(Object o);
    //Conflict ask us for some data
    void receiveConflict(Object o);

    //Sensors ask us for some data
    void receiveSensors(Object o);


}
