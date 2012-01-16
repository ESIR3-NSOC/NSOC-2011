package esir.ctrl;

/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 16/01/12
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public interface crtlInterface {
    //
    void send2IHM();
    void send2DAO();
    void send2Conflict();
    void getContext();
    void send2Context();
    void receiveIHM();
    void receiveConflict();


}
