package esir.dom11.nsoc.context.presence;
import java.util.Date;


public class CalendarEvent {
	
	private String id;
	private String summary;
	private Date dateStart;
	private Date dateEnd;
	
	CalendarEvent(String id, String summary, Date dateStart, Date dateEnd){
		
		this.id = id ;
		this.summary = summary;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		
		this.printEvent();
	}
	
	String getId(){ return this.id ; }
	String getSummary() { return this.summary ; }
	Date getDateStart() { return this.dateStart ;}
	Date getDateEnd() {return this.dateEnd ; }
		
	private void printEvent(){
		System.out.println("----NEW EVENT----");
		System.out.println("ID : "+ id);
		System.out.println("Summary : " + summary);
		System.out.println("Date Start : " + dateStart.toString());
		System.out.println("Date End : " + dateEnd.toString());
		System.out.println();
	}
	
	

}
