package esir.dom11.nsoc.context.presence;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.LinkedList;

@Requires({
        @RequiredPort(name = "fakeAgenda", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class FakeAgenda extends AbstractComponentType {

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        sendAgenda(generateAgenda());
    }

    public Agenda generateAgenda() {
        long currentTime = new Date().getTime();
        LinkedList<AgendaEvent> events = new LinkedList<AgendaEvent>();
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 2000),
                        new Date(currentTime + 6000)
                )
        );
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 12000),
                        new Date(currentTime + 18000)
                )
        );


        Agenda agenda = new Agenda();
        agenda.getEvents().addAll(events);
        return agenda;
    }


    public void sendAgenda(Agenda agenda) {
        if (this.isPortBinded("fakeAgenda")) {
            this.getPortByName("fakeAgenda", MessagePort.class).process(agenda);
        }
    }

}
