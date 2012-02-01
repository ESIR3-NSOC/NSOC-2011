package esir.dom11.nsoc.context.presence;

import com.espertech.esper.client.*;

public class Main {

    public Main() {

//The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("Presence", Presence.class.getName());
        cepConfig.addEventType("PresenceAgenda", PresenceAgenda.class.getName());
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();

        EPAdministrator cepAdm = cep.getEPAdministrator();
//        EPStatement var_presence = cepAdm.createEPL("create variable boolean var_presence = false");
//        var_presence.start();
//        EPStatement var_presence_true = cepAdm.createEPL("on Presence(presence=true) set var_presence = true");
//        EPStatement var_presence_false = cepAdm.createEPL("on Presence(presence=false) set var_presence = false");
//
//        EPStatement var_agenda_presence = cepAdm.createEPL("create variable boolean var_agenda_presence = false");
//        var_presence.start();
        String confirmation_startWindow = "1 sec";
        String confirmation_minDuration = "1 sec";
        EPStatement confirmation = cepAdm.createEPL("select * from pattern[" +
                "every (PresenceAgenda(presence=true) and Presence(presence=true))where timer:within("+confirmation_startWindow+") " +
                "->  timer:interval("+confirmation_minDuration+") and not Presence(presence=false) ]");

        EPStatement passage = cepAdm.createEPL("select * from pattern[" +
                "every Presence(presence=true) " +
                "-> Presence(presence=false) where timer:within(1 sec)]");

        EPStatement newPresence = cepAdm.createEPL("select * from pattern" +
                "[every (" +
                "( (Presence(presence=true) and not PresenceAgenda(presence=true))where timer:within(1 sec) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) )" +
                " or " +
                "( (PresenceAgenda(presence=false) and not Presence(presence=false)) where timer:within(1 sec) )" +
                ")]");

        EPStatement endPresence = cepAdm.createEPL("select * from pattern[" +
                "every Presence(presence=false) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) ]");

        EPStatement cancel = cepAdm.createEPL("select * from pattern[" +
                "every PresenceAgenda(presence=true) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) ]");



        confirmation.addListener(new EventUpdateLst("confirmation"));
        cancel.addListener(new EventUpdateLst("cancel"));
        newPresence.addListener(new EventUpdateLst("newPresence"));
        passage.addListener(new EventUpdateLst("passage"));
        endPresence.addListener(new EventUpdateLst("endPresence"));

        cepRT.sendEvent(new PresenceAgenda("1", true));
        cepRT.sendEvent(new Presence("1", true));
        tempo(2000);
        cepRT.sendEvent(new PresenceAgenda("1", false));
        tempo(2000);
        cepRT.sendEvent(new Presence("1", false));
        tempo(2000);
        cepRT.sendEvent(new Presence("1", true));
        tempo(2000);
        cepRT.sendEvent(new Presence("1", false));
        tempo(2000);
        cepRT.sendEvent(new PresenceAgenda("1", true));
        tempo(2000);
//        tempo(200);
//        cepRT.sendEvent(new Presence("2", false));
//        tempo(1000);
//        cepRT.sendEvent(new Presence("3", true));
//        tempo(1000);
//        cepRT.sendEvent(new Presence("4", false));
//        tempo(1000);
    }


    public static void main(String[] args) {
        new Main();
    }

    public void setPresence(boolean presence) {

    }

    public void tempo(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
