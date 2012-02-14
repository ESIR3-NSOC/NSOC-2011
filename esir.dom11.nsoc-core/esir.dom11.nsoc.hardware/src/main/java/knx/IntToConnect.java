package knx;


import tuwien.auto.calimero.link.KNXNetworkLinkIP;

public interface IntToConnect {
	
	public void connected();
	
	public void disconnected();
	
	public String read(String adresseGroupe);
	
	public void write(String adresseGroupe, boolean bool);

	public void listener(final String adresse);
	
	public  String searchSketch();
    
    public String getProtocol();

    public KNXNetworkLinkIP getNetLink();
}
