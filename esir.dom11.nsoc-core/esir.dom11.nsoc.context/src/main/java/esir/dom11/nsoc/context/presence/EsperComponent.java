/**
 * Project: EnTiMid
 * Copyright: INRIA/IRISA 2011
 * Contributor(s) :
 * Author: barais
 */
package esir.dom11.nsoc.context.presence;

import com.espertech.esper.client.*;
import org.kevoree.annotation.*;
import org.kevoree.classloader.ClassLoaderInterface;
import org.kevoree.classloader.ClassLoaderWrapper;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.osgi.framework.Bundle;

import java.util.Arrays;
import java.util.List;

/**
 * This Kevoree component encapsulates Esper. 
 * @author Olivier Barais
 * @copyright INRIA
 */
@Provides({
    @ProvidedPort(name = "port1", type = PortType.MESSAGE)
})
@Requires({
    @RequiredPort(name = "compositeMessage", type = PortType.MESSAGE)
})
@DictionaryType({
    @DictionaryAttribute(name = "request"),
    @DictionaryAttribute(name = "eventypes")
})
@Library(name = "Kevoree::Esper")
@ComponentType
public class EsperComponent extends AbstractComponentType  implements UpdateListener{


    public EsperComponent(){

    }

    private List<String> eventypes;
    private String request;
	private EPRuntime cepRT;
	private EPServiceProvider cep;
	private EPStatement cepStatement;

    @Start
    public void start() {
    	eventypes = Arrays.asList(this.getDictionary().get("eventypes").toString().split(","));	
    	request = (String) this.getDictionary().get("request").toString();
    	
        


     // get current context classloader                                                                                                                                  
      // ClassLoader contextClassloader = Thread.currentThread().getContextClassLoader();
       
       
       Bundle ctx = (Bundle) this.getDictionary().get("osgi.bundle");
       ClassLoaderInterface itf = new OsgiClassLoader(ctx);
       ((ClassLoaderWrapper)ClassLoaderInterface.instance).setWrap(itf);
       
       // then alter the class-loader (but which one ? the one used to load this class itself) with:
    //   Thread.currentThread().setContextClassLoader(Tick.class.getClassLoader());
        Configuration cepConfig = new Configuration();
        // create my Esper statement, and finally restore the class loader to its original value:
         for (String s : eventypes){
            cepConfig.addEventType(s.split(":")[0], s.split(":")[1]);
        }
         
        cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        
        cepRT = cep.getEPRuntime();
      
        EPAdministrator cepAdm = cep.getEPAdministrator();
        cepStatement = cepAdm.createEPL(request );

  //      Thread.currentThread().setContextClassLoader(contextClassloader);
        
        cepStatement.addListener(this);
        
       
    }

    @Stop
    public void stop() {
    	cepStatement.removeAllListeners();
        cep.destroy();
    }

    @Update
    public void update() {
    	cepStatement.removeAllListeners();
        cep.destroy();
        
    	eventypes = Arrays.asList(this.getDictionary().get("eventypes").toString().split(","));	
    	request = (String) this.getDictionary().get("request").toString();
    	
        Configuration cepConfig = new Configuration();
        for (String s : eventypes){
            cepConfig.addEventType(s, s);
        	
        }
        cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        cepRT = cep.getEPRuntime();
        cep.destroy();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        cepStatement = cepAdm.createEPL(request );

        cepStatement.addListener(this);
       
    }


    @Port(name = "port1")
    public void port1(Object message) {
       cepRT.sendEvent(message);
    }

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		 if (this.isPortBinded("compositeMessage")) {
	            this.getPortByName("compositeMessage", MessagePort.class).process("Event received: " + arg0[0].getUnderlying());
	        }
	}

}
