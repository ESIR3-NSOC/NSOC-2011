package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.TaskDAO;
import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.UUID;

public class TaskDAOMongoDb implements TaskDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TaskDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public TaskDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
    }

    /*
     * Overrides
     */

    @Override
    public LinkedList<Task> findByState(TaskState taskState) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Task create(Task task) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Task retrieve(UUID uuid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Task update(Task task) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
