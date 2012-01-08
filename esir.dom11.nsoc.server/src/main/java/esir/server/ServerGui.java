package esir.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class ServerGui {
    JFrame fenetre;
    ConnectionManager cm;

    JButton buttonStart;
    JButton buttonStop;

    public ServerGui(){
        cm = new ConnectionManager();
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
        JLabel portServer = new JLabel(cm.getPortServer());

        buttonStart = new JButton("Start");
        buttonStop = new JButton("Stop");
        buttonStop.setEnabled(false);


        // Gestion des apuis boutons
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cm.createServer();
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cm.killServer();
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
            }
        });

        // lorsque l'on ferme la fenetre (clic sur la croix rouge)
		fenetre.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {

				//on tue le client
				cm.killServer();
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
    public void hideGui(){
        fenetre.setVisible(false);
    }

}
