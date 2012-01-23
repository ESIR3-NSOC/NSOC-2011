package fr.esir2011.nsoc;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	ClientAndro client;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        client = new ClientAndro();
        client.creationClient("http://148.60.83.56:8182");
    }
}