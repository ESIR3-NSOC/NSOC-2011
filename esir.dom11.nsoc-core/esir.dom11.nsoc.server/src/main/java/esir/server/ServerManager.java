package esir.server;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */


public class ServerManager extends ServerResource{
    private Server serv;

    /*
     * Start the REST server for the IHM
     */
    public Boolean startServer(Integer port){
        serv = new Server(Protocol.HTTP, port, ServerManager.class);
        try{
            System.out.println("/** Lancement du serveur **/");
            System.out.println("Server cree : http://"+this.getIpServer()+":"+port);
            serv.start();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Stop the REST server for the IHM
     */
    public Boolean stopServer(){
        try{
            System.out.println("/** Arret du serveur **/");
            serv.stop();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
     * @return String the IP address of the computer
     */
    public String getIpServer() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr.getHostAddress().toString();
	}

    @Get
    public String receiveGetRequest(){
        return "Connexion Client/Serveur etablie";
    }

    @Post
    public void receivePostRequest(){}

    @Put
    public void receivePutRequest(){}
}
