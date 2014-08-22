package com.retni.applacegps;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.parse.Parse;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_perfil extends ActionBarActivity{
	
	String passUser, mailUser, nameUser = "Nombre";
	TextView nombre;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);	
		
		nombre = (TextView) findViewById(R.id.perf_nom);
        
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();

	    
	    if(user!=null){
	    	mailUser = user.getEmail();
		    nameUser = (String) user.getString("NombreCompleto");
	    }	    
	    
	    nombre.setText(nameUser);
	}

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
		menu.findItem(R.id.action_camara).setVisible(false);
		
		getSupportActionBar().setTitle("Perfil");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_edit:
	            Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(Activity_perfil.this, Activity_editarPerfil.class );
				startActivity(intent);
	            break;
	        case R.id.action_config:
	            Toast.makeText(this, "Configurar", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
}