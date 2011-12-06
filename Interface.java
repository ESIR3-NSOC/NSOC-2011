import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tuwien.auto.calimero.link.KNXNetworkLinkIP;

public class Interface implements ActionListener{

	private JFrame frame;
	JPanel haut, milieu, bas;
	JPanel j1, j2, j3, j4;
	JButton b1, b2, b3, b4, slower,faster, start, stop, erase; 
	JLabel dialog;
	Boolean startch=false;
	int sleepvalue=1000;
	int i=0; // pour l'ajout dans le tableau order
	JButton[] order;
	KNXNetworkLinkIP netLinkIp;
	Connection connection;
	Thread thread;

	ImageIcon starticon = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/start.png");
	ImageIcon stopicon = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/stop.png");
	ImageIcon eraseicon = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/erase.png");
	ImageIcon slowericon = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/slower.png");
	ImageIcon fastericon = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/faster.png");
	ImageIcon green = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/green.png");
	ImageIcon red = new ImageIcon("/Users/Kmi/Documents/ESIR/ESIR 2/PROG/Workspace/Chenillard/src/images/red.png");

	public Interface(Connection connect) {
		
		connection = connect;
		frame = new JFrame("Chenillard");
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener( new WindowAdapter(){
									public void windowClosing(WindowEvent e) {
											connection.deconnection();
									}
								});
		
		frame.setResizable(false);

		haut = new JPanel();
		milieu = new JPanel();
		bas = new JPanel();

		GridLayout gl = new GridLayout(3, 1);
		frame.setLayout(gl);

		GridLayout glhaut = new GridLayout(1, 3);
		haut.setLayout(glhaut);

		this.panelHaut();

		GridLayout glmilieu = new GridLayout(1, 3);
		milieu.setLayout(glmilieu);

		this.panelMilieu();

		GridLayout glbas = new GridLayout(1, 4);
		bas.setLayout(glbas);

		this.panelBas(bas);

		frame.add(haut);
		frame.add(milieu);
		frame.add(bas);
		
		this.init();
		
		
		order = new JButton[4];
		
		this.defaultOrder();
		
		dialog.setText("Ordre par défaut : 1 - 2 - 3 - 4");
		dialog.setHorizontalAlignment(SwingConstants.CENTER);
		
		frame.setVisible(true);
		
		
	}

	void lumiere(boolean etat, JButton button){
		if (etat == true) {
			button.setIcon(green);
		} else {
			button.setIcon(red);
		}
	}

	void init() {
		this.lumiere(false,b1);
		this.lumiere(false,b2);
		this.lumiere(false,b3);
		this.lumiere(false,b4);
	}

	void panelHaut() {

		start = new JButton("start");
		stop = new JButton();
		erase = new JButton();
		
		this.joliBoutton(start);
		this.joliBoutton(stop);
		this.joliBoutton(erase);
		
		start.setIcon(starticon);
		stop.setIcon(stopicon);
		erase.setIcon(eraseicon);
		
		start.addActionListener(this);
		stop.addActionListener(this);
		erase.addActionListener(this);

		haut.add(start);
		haut.add(stop);
		haut.add(erase);

	}

	void panelMilieu() {

		slower = new JButton();
		faster = new JButton();
		
		dialog = new JLabel();
		
		this.joliBoutton(slower);
		this.joliBoutton(faster);
		
		slower.setIcon(slowericon);
		faster.setIcon(fastericon);
		
		slower.addActionListener(this);
		faster.addActionListener(this);
	

		milieu.add(slower);
		milieu.add(dialog);
		milieu.add(faster);
	}

	void panelBas(JPanel jpanel) {

		j1 = new JPanel();
		j2 = new JPanel();
		j3 = new JPanel();
		j4 = new JPanel();

		b1 = new JButton();
		b2 = new JButton();
		b3 = new JButton();
		b4 = new JButton();
		
		this.joliBoutton(b1);
		this.joliBoutton(b2);
		this.joliBoutton(b3);
		this.joliBoutton(b4);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		j1.add(b1);
		j2.add(b2);
		j3.add(b3);
		j4.add(b4);

		bas.add(j1);
		bas.add(j2);
		bas.add(j3);
		bas.add(j4);

	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(start)) {
			
			if(i>=3){
				dialog.setText("Demarrage du chenillard");
				setStart(true);
			}
			else{
				dialog.setText("Selectionnez l'ordre des 4 lumières");
			}
		}
		else if (e.getSource().equals(stop)) {
			dialog.setText("Arrêt du chenillard");
			setStart(false);
			//System.out.println("NETLINKIP : "+ netLinkIp.isOpen());
			//connection.deconnection(netLinkIp);
		} 
		else if (e.getSource().equals(erase)) {
			setStart(false);
			dialog.setText("Selectionnez l'ordre");
			this.init();
			i=0;
			order = new JButton[4];
		}
		else if (e.getSource().equals(slower)) {
			dialog.setText("Diminution de la vitesse");
			setSleepValue(false);
		}
		else if (e.getSource().equals(faster)) {
			dialog.setText("Augmentation de la vitesse");
			setSleepValue(true);
		}
		else if (e.getSource().equals(b1)){
				setOrder(b1);
		}
		else if (e.getSource().equals(b2)){
				setOrder(b2);
		}
		else if (e.getSource().equals(b3)){
				setOrder(b3);
		}
		else if (e.getSource().equals(b4)){
				setOrder(b4);
		}
	}
	
	void setStart(boolean value){
		startch = value;
	}
	boolean getStart(){
		return startch;
	}
	
	void setSleepValue(Boolean value){
		if(value == true){
			if (sleepvalue <= 1000){
				sleepvalue = 1000; 
				dialog.setText("Vitesse maximale");
				
			}
			else{
				sleepvalue = sleepvalue/2 ;
			}
		}
		else if(value == false){
			if(sleepvalue <8000){
				sleepvalue = sleepvalue*2;
			}
			else{
				sleepvalue = 8000 ;
				dialog.setText("Vitesse minimale");
			}
		}
		
	}
	
	int getSleepValue(){
		return sleepvalue;
	}
	
	void setOrder(JButton button){
		
		if(i<=3){
			order[i]= button;
			i++;
		}
		else {
			dialog.setText("Nombre max de lumières");
		}
		
	}
	
	JButton[] getOrder(){
		return order;
	}
	
	void joliBoutton(JButton button){
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
	}
	
	void defaultOrder(){
		order[0] = b1;
		order[1] = b2;
		order[2] = b3;
		order[3] = b4;
		i=4;
	}
}
