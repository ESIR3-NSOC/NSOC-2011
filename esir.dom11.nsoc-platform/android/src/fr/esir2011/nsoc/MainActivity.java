package fr.esir2011.nsoc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;


public class MainActivity extends Activity {
	
	ClientAndro client;
	Button validServer;
	EditText editServer;
	EditText editPort;

	
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
    }
        
    public class MyOnClickListener implements View.OnClickListener {
		
    	private MainActivity mainActivity;
    	
    	public MyOnClickListener(MainActivity activity) {
    		mainActivity = activity;
    	}
    	
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			editServer=(EditText)findViewById(R.id.editServer);
			editPort=(EditText)findViewById(R.id.editPort);
			
			String addressServer = editServer.getText().toString();
			String port = editPort.getText().toString();
			
			
			client = new ClientAndro(mainActivity);
	        client.creationClient("http://"+addressServer+":"+port);
	   
	        
	        
		}
    }
}