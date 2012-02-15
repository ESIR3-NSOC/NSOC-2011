package fr.esir2011.nsoc;

import android.app.Activity;

import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;


public class MainActivity extends Activity {
	
	ClientAndro client;
	Button validServer;
	EditText editServer;
	EditText editPort;
	CheckBox checkConnect;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setServerButtonClickListener();
        
    }
       
        
    private void setServerButtonClickListener() {
        	
        //initialisation du bouton validServer
        validServer = (Button)findViewById(R.id.validServer);
        validServer.setOnClickListener(new MyOnClickListener(this));
        
        
		checkConnect = (CheckBox)findViewById(R.id.checkConnect);
		
    }
        
    public class MyOnClickListener implements View.OnClickListener {
		
    	private MainActivity mainActivity;
    	
    	public MyOnClickListener(MainActivity activity) {
    		mainActivity = activity;
    	}
    	
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("On click OK");
			editServer=(EditText)findViewById(R.id.editServer);
			editPort=(EditText)findViewById(R.id.editPort);
			
			System.out.println("Données récupérées OK");
			String addressServer = editServer.getText().toString();
			String port = editPort.getText().toString();
			String full_address = "http://"+addressServer+":"+port;
			
			
			
			client = new ClientAndro(mainActivity);
			System.out.println("client andro ok");
	        client.creationClient(full_address);
	        
			
	        if (client.cr.getStatus().isSuccess()){
				
					checkConnect.setChecked(true);
				
					Intent intent = new Intent(MainActivity.this,Interface_salle.class);
					//envoi de l'adresse complete du serveur
					intent.putExtra("adress_serveur",full_address);
					startActivity(intent);
				
				}
			
				else {
					Toast.makeText(MainActivity.this, "La connection au serveur est impossible", Toast.LENGTH_LONG).show();
				}
	   
	        
	        
		}
    }
}