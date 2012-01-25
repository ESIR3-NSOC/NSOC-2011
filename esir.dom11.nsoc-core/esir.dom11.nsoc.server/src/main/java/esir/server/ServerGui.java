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
    JLabel ipServer;

    public ServerGui(){
        cm = new ServerManager();
        fenetre = new JFrame();
        JPanel panel = new JPanel();

        // frame management
        fenetre.setTitle("Server GUI");
		fenetre.setSize(300, 150);
		fenetre.setLocationRelativeTo(null);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new GridLayout(1, 1));
        panel.setLayout(new GridLayout(3,2));


        // components management
        JLabel labelIpServer = new JLabel("Adresse ip du serveur : ");
        JLabel labelPortServer = new JLabel("Port du serveur : ");

        ipServer = new JLabel(cm.getIpServer());
        portServer = new JTextField("8182");

        buttonStart = new JButton("Start server");
        buttonStop = new JButton("Stop server");
        buttonStop.setEnabled(false);


        // Button Listener management
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(portServer.getText().matches("^[\\d]{4,5}$")){
                    if(cm.startServer(Integer.parseInt(portServer.getText()))){
                        //update the ip in case of Connections losses
                        ipServer.setText(cm.getIpServer());
                        portServer.setEnabled(false);
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
                    //update the ip in case of Connections losses
                    ipServer.setText(cm.getIpServer());
                    portServer.setEnabled(true);
                    buttonStart.setEnabled(true);
                    buttonStop.setEnabled(false);
                }
            }
        });

        // lorsque l'on ferme la fenetre (clic sur la croix rouge)
        // Method launched on the red cross click
		fenetre.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {

                // kill the client
				cm.stopServer();
                // close the window
				System.exit(0);
			}
		});


        // Add the components to the frame
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
     * Hide the frame during the stop
     */
    public void stopGui(){
        cm.stopServer();
        fenetre.setVisible(false);
    }

}
