package esir.dom11.nsoc.service;

public class RequestResult {
    /*
     * Attributes
     */

    private Object _result;
    private boolean _success;

    /*
     * Constructors
     */

    public RequestResult(Object result, boolean success) {
        _result = result;
        _success = success;
    }

    /*
     * Getters
     */

    public Object getResult() {
        return _result;
    }

    public boolean isSuccess() {
        return _success;
    }
}
