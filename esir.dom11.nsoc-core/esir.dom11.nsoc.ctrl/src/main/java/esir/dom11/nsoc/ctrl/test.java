package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.service.IDbService;
import esir.dom11.nsoc.service.IServerService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.LinkedList;


@Library(name = "NSOC_2011")
@ComponentType

@Requires({
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true)
})

public class test extends AbstractComponentType {
    @Start
    public void start() {
        System.out.println("Control : Start");
        long currentTime = new Date().getTime();
        LinkedList<AgendaEvent> events = new LinkedList<AgendaEvent>();
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 15000),
                        new Date(currentTime + 18000)
                )
        );
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 22000),
                        new Date(currentTime + 26000)
                )
        );
        send2HMI(events);

        try {
            Thread.sleep(19000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        currentTime = new Date().getTime();
        events = new LinkedList<AgendaEvent>();
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 10000),
                        new Date(currentTime + 16000)
                )
        );
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 19000),
                        new Date(currentTime + 45000)
                )
        );
        send2HMI(events);
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        System.out.println("Control : update");
        stop();
        start();
    }

    //HMI need some data so...
    public void send2HMI(LinkedList<AgendaEvent> command) {
        System.out.println("Control : send2HMI");
        getPortByName("Context", MessagePort.class).process(command);
    }
}
