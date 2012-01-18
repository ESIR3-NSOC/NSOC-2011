package org.kevoree.service.knx;




public interface IntToConnect {
	
	public void connected();
	
	public void disconnected();
	
	public boolean lire(String adresseGroupe);
	
	public void ecrire(String adresseGroupe, boolean bool);

	public void listener(final String adresse);
	
	public void chercheAdresseMaquette();
}
