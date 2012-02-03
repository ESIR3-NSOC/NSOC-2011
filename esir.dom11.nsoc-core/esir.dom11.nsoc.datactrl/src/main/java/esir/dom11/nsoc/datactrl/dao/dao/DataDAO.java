package esir.dom11.nsoc.datactrl.dao.dao;

import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public interface DataDAO extends DAO<UUID,Data> {
    
    LinkedList<Data> findByDate(Date startDate, Date endDate, String location, DataType dataType);

    LinkedList<Data> findByDateAndStep(Date startDate, Date endDate, String role, int step);

    LinkedList<Data> findByDateAndDataMax(Date startDate, Date endDate, String role, int datMax);
    
}
