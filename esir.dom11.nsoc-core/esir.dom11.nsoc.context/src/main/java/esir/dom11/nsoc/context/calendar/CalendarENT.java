package esir.dom11.nsoc.context.calendar;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Requires({
        @RequiredPort(name = "calendar", type = PortType.MESSAGE, optional = true)
})
@DictionaryType({
        @DictionaryAttribute(name = "codeSalle", defaultValue = "358", optional = false)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class CalendarENT extends AbstractComponentType {

    private String adresse;
    private URL url;
    private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    private SimpleDateFormat formatday = new SimpleDateFormat("MM-dd-yyyy");

    public CalendarENT() {
    }

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        String salle = (String) getDictionary().get("codeSalle");
        adresse = "http://plannings.univ-rennes1.fr/ade/custom/modules/plannings/direct_cal.jsp?calType=ical&login=cal&password=visu&resources=" + salle + "&&firstDate=2011-08-22&lastDate=2012-12-31&&projectId=85";
        try {
            createEvent(this.adresse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //ENVOYER LES ELEMENTS SEULEMENT DE LA JOURNEE
    //getLocalTime
    private void createEvent(String adresse) throws IOException, ParseException {
        url = new URL(adresse);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStreamReader isr = new InputStreamReader(con.getInputStream());
        BufferedReader in = new BufferedReader(isr);


        java.util.Date date = new java.util.Date();
        String dateToday = (formatday.format(date)).toString(); //Date du type MM-dd-yyyy "02-07-2012"

        //Vector vCal = new Vector();
        String line = null;
        line = "start";
        while (line != null) {
            line = in.readLine();

            if (line == null) {
                System.exit(0);
            }

            if (line.equals("BEGIN:VEVENT")) {
                if (!line.equals("END:EVENT")) {

                    //Lecture de toute les lignes et enregistrement dans variables

                    String dateAmp = StringAnalysor(in.readLine());
                    Date dateStart = stringToDate(StringAnalysor(in.readLine()));
                    Date dateEnd = stringToDate(StringAnalysor(in.readLine()));
                    String summary = StringAnalysor(in.readLine());
                    String location = StringAnalysor(in.readLine());
                    String description = in.readLine() + in.readLine();
                    String id = StringAnalysor(in.readLine());
                    String created = in.readLine();
                    String lastmodified = StringAnalysor(in.readLine());
                    //System.out.println(formatday.format(dateStart).toString());

                    if (dateToday.equals(formatday.format(dateStart))) { // si event aujourd'hui
                        CalendarEvent ce = new CalendarEvent(id, summary, dateStart, dateEnd);
                    }
                }
            }
        }
        in.close();
    }

    //Get the interessant things after the :
    private String StringAnalysor(String str) {
        String ret = "";
        String[] tab = str.split(":");
        ret = tab[1];
        return ret;
    }

    //Create a Date with a String
    public Date stringToDate(String sDate) throws ParseException {
        String dateToSplit = sDate;
        String year = dateToSplit.substring(0, 4);
        String month = dateToSplit.substring(4, 6);
        String day = dateToSplit.substring(6, 8);
        String hours = dateToSplit.substring(9, 11);
        String minutes = dateToSplit.substring(11, 13);
        String secondes = "00";
        String dateToCreate = month + "-" + day + "-" + year + " " + hours + ":" + minutes + ":" + secondes;
        Date dateminus1 = formatter.parse(dateToCreate);
        Date realDate = new Date(dateminus1.getTime() + 3600000); // Ajout d'1 heure
        return realDate;
    }

    public void sendCalendar(Calendar calendar) {
        if (this.isPortBinded("calendar")) {
            this.getPortByName("calendar", MessagePort.class).process(calendar);
        }
    }
}
