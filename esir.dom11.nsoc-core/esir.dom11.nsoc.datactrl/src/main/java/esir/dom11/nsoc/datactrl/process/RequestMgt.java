package esir.dom11.nsoc.datactrl.process;

import esir.dom11.nsoc.datactrl.dao.dao.DAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.service.RequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class RequestMgt {
    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(RequestMgt.class.getName());

    /*
     * Attributes
     */
    
    private DAO _dao;
    private Method _method;
    private RequestResult _result;
    
    /*
     * Constructors
     */
    
    public RequestMgt(DAOFactory daoFactory, String methodName, String className, Object[] params) {
        // Retrieve DAO
        if (getDAO(daoFactory, className)) {
            if (getMethod(methodName, params)) {
                execService(params);
            } else {
                _result = new RequestResult("dbService.get: method not found.",false);
            }
        }
    }

    /*
     * Getters /setters
     */

    public RequestResult getResult() {
        return _result;
    }
    
    /*
     * Methods
     */
    
    public boolean getDAO(DAOFactory daoFactory, String className) {
        try {
            _dao = daoFactory.getDAO(Class.forName(className));
            return true;
        } catch (ClassNotFoundException exception) {
            logger.error("dbService.get: class not found.",exception);
            _result = new RequestResult("dbService.get: class not found.\n"+exception.getMessage(),false);
        }
        return false;
    }

    public boolean getMethod(String methodName, Object[] params) {
        LinkedList<Method> methodList = new LinkedList<Method>();
        Method[] methodArray = _dao.getClass().getDeclaredMethods();
        for (Method method : methodArray) {
            if (method.getName().compareTo(methodName)==0) {
                methodList.add(method);
            }
        }

        if (methodList.size()==0) {
            logger.error("dbService.get: method not found.");
            return false;
        }

        // Try to match params
        for (Method method : methodList) {
            if (method.getParameterTypes().length == params.length) {
                boolean findMethod = true;
                for (int i=0 ; i<method.getParameterTypes().length ; i++) {
                    if (!method.getParameterTypes()[i].equals(params[i].getClass())) {
                        if (method.getParameterTypes()[i].isPrimitive()) {
                            Class primitiveClass = primitiveToObject(method.getParameterTypes()[i].getName());
                            if (primitiveClass!=null && params[i].getClass().equals(primitiveClass)) {
                                continue;
                            }
                            findMethod = false;
                            break;
                        }
                        findMethod = false;
                        break;
                    }
                }
                if (findMethod) {
                    _method = method;
                    break;
                }
            }
        }
        return _method!=null;
    }

    /**
     * @param params params of method
     * @return result of invoked method
     */
    public void execService(Object[] params) {
        try {
            _result = new RequestResult(_method.invoke(_dao, params),true);
        } catch (IllegalAccessException exception) {
            logger.error("IllegalAccessException",exception);
            _result = new RequestResult("Exec request error: IllegalAccessException",false);
        } catch (InvocationTargetException exception) {
            logger.error("InvocationTargetException",exception);
            _result = new RequestResult("Exec request error: InvocationTargetException",false);
        }
    }

    /**
     * Give Object type for primitive
     * @param type primitive type
     * @return Object type
     */
    public static Class primitiveToObject(String type) {
        if (type.compareTo("int")==0) {
            return Integer.class;
        } else if (type.compareTo("float")==0) {
            return Float.class;
        } else if (type.compareTo("byte")==0) {
            return Byte.class;
        } else if (type.compareTo("short")==0) {
            return Short.class;
        } else if (type.compareTo("long")==0) {
            return Long.class;
        } else if (type.compareTo("double")==0) {
            return Double.class;
        } else if (type.compareTo("char")==0) {
            return Character.class;
        } else if (type.compareTo("boolean")==0) {
            return Boolean.class;
        }
        return null;
    }
}
