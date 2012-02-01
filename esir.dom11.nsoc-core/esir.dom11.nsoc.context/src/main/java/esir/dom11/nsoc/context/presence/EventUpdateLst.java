package esper;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class EventUpdateLst implements UpdateListener{
    
    private String name;
    
    public EventUpdateLst(String name){
          this.name = name;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        for(EventBean event : newData){
            System.out.println(this.name + " : " + event);
        }
    }
}
