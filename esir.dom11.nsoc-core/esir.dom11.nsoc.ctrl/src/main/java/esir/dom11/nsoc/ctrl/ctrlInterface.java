package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

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
    void send2HMI(Data data);
    
    //send everythg that could have been modified
    void send2DAO(Data data );
    void send2DAO(Command command);
    
    //Send an actions list (= command) to conflict 
    void send2Conflict(Command command);
    
    // Ask the context for a precise variable (eg : temp sensor from a room )
    Data receiveFromContext(DataType datatype);
    
    //Receive... vont permettre de recevoir les données des différents composants
    //Use of listeners
    
    //HMI ask us for some data
    Data receiveHMI();
    //Conflict ask us for some data
    Data receiveConflict();
    //Context ask us for some data
    Data getContext();

}
