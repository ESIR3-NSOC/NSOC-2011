package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;


@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "HMI", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "Context", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "DAO", type = PortType.MESSAGE)  ,
        @ProvidedPort(name = "Conflict", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "HMI", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "DAO", type = PortType.MESSAGE, optional = true) ,
        @RequiredPort(name = "Conflict", type = PortType.MESSAGE, optional = true)
})

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
