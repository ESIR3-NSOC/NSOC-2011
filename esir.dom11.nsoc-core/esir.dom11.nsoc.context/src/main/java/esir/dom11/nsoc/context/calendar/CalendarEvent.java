package esir.dom11.nsoc.context.calendar;

import java.util.Date;


public class CalendarEvent {

    private String id;
    private String summary;
    private Date dateStart;
    private Date dateEnd;

    public CalendarEvent(String id, String summary, Date dateStart, Date dateEnd) {
        this.id = id;
        this.summary = summary;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public CalendarEvent(Date dateStart, Date dateEnd) {
        this.id = " ";
        this.summary = " ";
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getId() {
        return this.id;
    }

    public String getSummary() {
        return this.summary;
    }

    public Date getDateStart() {
        return this.dateStart;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }

    public  void printEvent() {
        System.out.println("----NEW EVENT----");
        System.out.println("ID : " + id);
        System.out.println("Summary : " + summary);
        System.out.println("Date Start : " + dateStart.toString());
        System.out.println("Date End : " + dateEnd.toString());
        System.out.println();
    }

    public String toString() {
        String str;
        str = "----NEW EVENT----\n";
        str += "ID : " + id + "\n";
        str += "Summary : " + summary + "\n";
        str += "Date Start : " + dateStart.toString() + "\n";
        str += "Date End : " + dateEnd.toString() + "\n";
        str += "\n";
        return str;
    }


}
