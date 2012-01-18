package esir.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;


/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class ServerGui {
    JFrame fenetre;
    ServerManager cm;

    JButton buttonStart;
    JButton buttonStop;
    JTextField portServer;

    public ServerGui(){
        cm = new ServerManager();
        fenetre = new JFrame();
        JPanel panel = new JPanel();

        // Gestion de la fenetre
        fenetre.setTitle("Server GUI");
		fenetre.setSize(300, 150);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new GridLayout(1, 1));
        panel.setLayout(new GridLayout(3,2));


        //Gestion des composants
        JLabel labelIpServer = new JLabel("Adresse ip du serveur : ");
        JLabel labelPortServer = new JLabel("Port du serveur : ");

        JLabel ipServer = new JLabel(cm.getIpServer());
        portServer = new JTextField("8182");

        buttonStart = new JButton("Start server");
        buttonStop = new JButton("Stop server");
        buttonStop.setEnabled(false);


        // Gestion des apuis boutons
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(portServer.getText().matches("^[\\d]{4,5}$")){
                    if(cm.startServer(Integer.parseInt(portServer.getText()))){
                        buttonStart.setEnabled(false);
                        buttonStop.setEnabled(true);
                    }
                } else {
                    System.out.println("ERROR :: Vous n'avez pas entre un port");

                }

            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(cm.stopServer()){
                    buttonStart.setEnabled(true);
                    buttonStop.setEnabled(false);
                }
            }
        });

        // lorsque l'on ferme la fenetre (clic sur la croix rouge)
		fenetre.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {

				//on tue le client
				cm.stopServer();
				// on ferme la fenetre
				System.exit(0);
			}
		});


        // Ajout des composants a la fenetre
        panel.add(labelIpServer);
        panel.add(ipServer);
        panel.add(labelPortServer);
        panel.add(portServer);
        panel.add(buttonStart);
        panel.add(buttonStop);
        fenetre.add(panel);
        fenetre.setVisible(true);
    }


    /*
     * cache la fenetre lors du stop
     */
    public void stopGui(){
        cm.stopServer();
        fenetre.setVisible(false);
    }

}
