package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

public class Control implements ctrlInterface {
	

	@Override
	//HMI need some data so...
	public void send2HMI(Data data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//send everythg that could have been modified
	public void send2DAO(Data data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send2DAO(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//Send an actions list (= command) to conflict 
	public void send2Conflict(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//HMI ask us for some data
	public Data receiveHMI(DataType datatype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//Conflict ask us for some data
	public Data receiveConflict() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	//The context ask for a precise variable saved in database (eg : temp sensor from a room )
	public Data receiveFromContext(DataType datatype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//Ask Context for some data
	public Data getContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
