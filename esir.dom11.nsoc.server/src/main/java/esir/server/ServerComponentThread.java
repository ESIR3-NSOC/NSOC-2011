package esir.server;

/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 08/01/12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class ServerComponentThread extends Thread{
    private ServerComponent sc;
    private String port;
    private String message;


    public ServerComponentThread(String port, String message){
        sc = new ServerComponent();
        this.port = port;
        this.message = message;
    }

    public void run(){
        System.out.println("Thread lance!");
        sc.sendMessage(port, message);
    }
}
