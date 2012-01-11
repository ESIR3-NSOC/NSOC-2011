package esir.dom11.nsoc.datactrl.dao.dao;

import esir.dom11.nsoc.model.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public interface DataDAO extends DAO<UUID,Data> {
    
    LinkedList<Data> findByDate(Date startDate, Date endDate, String role);
    
}
