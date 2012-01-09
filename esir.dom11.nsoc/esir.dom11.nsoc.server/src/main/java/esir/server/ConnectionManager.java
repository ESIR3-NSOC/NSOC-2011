package esir.server;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */


public class ConnectionManager {
    private Server serv;
    private Integer portServer;

    public ConnectionManager(){
        this.portServer = 8182;
    }

    /*
     * Demarre le serveur REST pour l'IHM
     */
    public void createServer(){
        serv = new Server(Protocol.HTTP, portServer, ConnectionManager.class);
        try{
            System.out.println("/** Lancement du serveur **/");
            serv.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     * Arrete le serveur REST pour l'IHM
     */
    public void killServer(){
        try{
            System.out.println("/** Arret du serveur **/");
            serv.stop();
        } catch (Exception e){
            e.printStackTrace();
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

    /*
     * Recupere le port de la machine
     *
     * @return String le port de la machine
     */
    public String getPortServer(){
        return portServer.toString();
    }

    @Get
    public String getRequest(){
        return "Connexion Client/Serveur etablie";
    }

    @Post
    public void postRequest(){}

    @Put
    public void putRequest(){}
}
