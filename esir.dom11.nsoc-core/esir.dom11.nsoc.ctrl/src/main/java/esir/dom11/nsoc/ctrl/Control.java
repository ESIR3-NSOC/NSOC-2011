package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

public class Control implements ctrlInterface {
	


	@Override
	public void send2DAO(Data data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send2DAO(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send2Conflict(Command command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send2HMI(Data data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Data receiveHMI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data receiveConflict() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Data receiveFromContext(DataType datatype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data getContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
