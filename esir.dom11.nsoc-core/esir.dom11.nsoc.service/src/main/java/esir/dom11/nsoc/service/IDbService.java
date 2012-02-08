package esir.dom11.nsoc.service;

import java.util.LinkedList;

public interface IDbService {

    public RequestResult create(Object obj);

    public RequestResult retrieve(String className, Object obj);

    public RequestResult update(Object obj);

    public RequestResult delete(String className, Object obj);
        
    public RequestResult get(String method, String className, LinkedList<Object> params);
}
