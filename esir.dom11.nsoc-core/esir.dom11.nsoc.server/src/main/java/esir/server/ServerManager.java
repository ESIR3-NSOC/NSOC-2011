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
     * Demarre le serveur REST pour l'IHM
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
     * Arrete le serveur REST pour l'IHM
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
     * Recupere l'adresse ip de la machine
     *
     * @return String l'adresse ip de la machine
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
    public String requestGet(){
        return "Connexion Client/Serveur etablie";
    }

    @Post
    public void requestPost(){}

    @Put
    public void requestPut(){}
}
