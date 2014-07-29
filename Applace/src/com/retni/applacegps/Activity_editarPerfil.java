package com.retni.applacegps;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_editarPerfil extends ActionBarActivity{
	
	TextView nombre, email;
	String nameUser, mailUser;
	LinearLayout nom, pass, mail, about;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);
        
        //Conexión backendless
        String appVersion = "v1";
	    Backendless.initApp( this, "0A10A8FF-1F4C-0FB5-FFB6-0DC451109500", "9B122EE8-E46B-63D2-FFEA-023DD8271E00", appVersion );
	
	    BackendlessUser user = new BackendlessUser();
	    user = Backendless.UserService.CurrentUser();
	    
	    mailUser = user.getEmail();
	    nameUser = (String) user.getProperty("name");
        
        nombre = (TextView) findViewById(R.id.edit_nom);
        email = (TextView) findViewById(R.id.edit_email);
        nom = (LinearLayout) findViewById(R.id.nom);
        pass = (LinearLayout) findViewById(R.id.pass);
        mail = (LinearLayout) findViewById(R.id.mail);
        about = (LinearLayout) findViewById(R.id.about);
        
        nombre.setText(nameUser);
        email.setText(mailUser);
        
        nom.setOnClickListener(listener);
        pass.setOnClickListener(listener);
        mail.setOnClickListener(listener);
        about.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){		//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {			
			switch(v.getId()){			
				case R.id.nom:		//Lanzamiento de una nueva actividad
					Toast.makeText(getApplicationContext(), "nombre", Toast.LENGTH_SHORT).show();
					break;
				case R.id.pass:		//Lanzamiento de una nueva actividad
					Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
					break;
				case R.id.mail:		//Lanzamiento de una nueva actividad
					Toast.makeText(getApplicationContext(), "mail", Toast.LENGTH_SHORT).show();
					break;
				case R.id.about:		//Lanzamiento de una nueva actividad
					Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_SHORT).show();
					break;					
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);	
		
		menu.findItem(R.id.como_funciona).setVisible(false);
		menu.findItem(R.id.codiciones).setVisible(false);
		menu.findItem(R.id.politica).setVisible(false);
		menu.findItem(R.id.ayuda).setVisible(false);
		
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_config).setVisible(false);
		
		getSupportActionBar().setTitle("Editar perfil");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_camara:
	            Toast.makeText(this, "Camara", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
}
