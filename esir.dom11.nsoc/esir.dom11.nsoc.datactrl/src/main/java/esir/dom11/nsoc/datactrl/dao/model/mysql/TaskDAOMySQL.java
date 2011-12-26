package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.dao.DAO;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.TaskDAO;
import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class TaskDAOMySQL implements TaskDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TaskDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public TaskDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Task create(Task task) {
        Task newTask = retrieve(task.getId());
        if (newTask.getId()==null) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(
                                "INSERT INTO tasks (id, description, date, taskstate, script)"
                                + " VALUES('" + task.getId() + "',"
                                + " '" + task.getDescription() + "',"
                                + " '" + task.getDate() + "',"
                                + " '" + task.getTaskState() + "',"
                                + " '" + task.getScript() + "')"
                        );
                prepare.executeUpdate();
                newTask = retrieve(task.getId());
                logger.info("Task insert success: " + task.toString());
            } catch (SQLException exception) {
                logger.error("Task insert error", exception);
            }
        }
        return newTask;
    }

    @Override
    public Task retrieve(UUID id) {
        Task task = new Task();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM tasks WHERE id = '" + id + "'");
            if(result.first()) {
                task = new Task(id,result.getString("description"),
                        result.getDate("date"),result.getString("script"),
                        TaskState.valueOf(result.getString("taskstate")));
            }
        } catch (SQLException exception) {
            logger.error("Task retrieve error", exception);
        }
        return task;
    }

    @Override
    public Task update(Task task) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(
                            "UPDATE tasks SET description = '" + task.getDescription() + "',"
                                    + "date = '" + task.getDate() + "',"
                                    + "taskstate = '" + task.getTaskState() + "',"
                                    + "script = '" + task.getScript() + "',"
                                    + " WHERE id = '" +task.getId() + "'"
                    );

            task = this.retrieve(task.getId());
        } catch (SQLException exception) {
            logger.error("Task update error",exception);
        }
        return task;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM tasks WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Task delete error",exception);
        }
        return false;
    }

    public LinkedList<Task> findByState(TaskState taskState) {
        LinkedList<Task> taskList = new LinkedList<Task>();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM tasks WHERE taskstate='" + taskState.getValue() + "'");
            result.beforeFirst();
            while (result.next()) {
                taskList.add(new Task(UUID.fromString(result.getString("id")),
                                    result.getString("description"),
                                    result.getDate("date"),result.getString("script"),
                                    TaskState.valueOf(result.getString("taskstate"))));
            }
        } catch (SQLException exception) {
            logger.error("Task retrieve error", exception);
        }
        return taskList;
    }
}
