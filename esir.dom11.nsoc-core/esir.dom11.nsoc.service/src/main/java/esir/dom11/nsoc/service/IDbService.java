package esir.dom11.nsoc.service;

import java.util.LinkedList;

public interface IDbService {

    public Object create(Object obj);

    public Object retrieve(String className, Object obj);

    public Object update(Object obj);

    public boolean delete(String className, Object obj);
        
    public RequestResult get(String method, String className, LinkedList<Object> params);
}
