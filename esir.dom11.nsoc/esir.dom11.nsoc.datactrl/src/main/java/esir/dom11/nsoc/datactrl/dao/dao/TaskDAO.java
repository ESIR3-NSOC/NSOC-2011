package esir.dom11.nsoc.datactrl.dao.dao;

import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.TaskState;

import java.util.LinkedList;
import java.util.UUID;

public interface TaskDAO extends DAO<UUID,Task> {

    public LinkedList<Task> findByState(TaskState taskState);

}
