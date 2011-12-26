package esir.dom11.nsoc.service;

import esir.dom11.nsoc.model.Task;

public interface ISchedulerService {
    
    public void scheduleTask(Task task);
    
    public void cancelTask(Task task);
}
