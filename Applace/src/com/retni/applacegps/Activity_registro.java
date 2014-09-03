/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del registro de activity_registro.xml y los agrega 
a la base de datos como nuevo usuario, para luego darle acceso al mapa si es un usuario Arrendatario, mientras
que si es un Arrendador, se le muestra la página principal de sus pripiedades.
**************************************************************************************************************/

package com.retni.applacegps;


import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity_registro extends ActionBarActivity {
	
	EditText mail,pass,nom,ap,username;
	Button registrar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
  	    Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
  	                                
  	    mail = (EditText)findViewById(R.id.reg_correo);
		pass = (EditText)findViewById(R.id.reg_pass);
		nom = (EditText)findViewById(R.id.reg_nombre);
		ap = (EditText)findViewById(R.id.reg_apellido);
		username = (EditText)findViewById(R.id.reg_username);
		
		registrar = (Button)findViewById(R.id.regis);
		registrar.setOnClickListener(listener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_config).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_camara).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_search:
	            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();;
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }	 
	    return true;
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.regis) {
				
				if(nom.getText().toString().matches("") || pass.getText().toString().matches("") || ap.getText().toString().matches("") || mail.getText().toString().matches("")){
					Toast.makeText( getApplicationContext(),"Por favor llene todos los campos",Toast.LENGTH_SHORT ).show();
				}
				else{
					ParseUser user = new ParseUser();
					user.setUsername(username.getText().toString());
					user.setPassword(pass.getText().toString());
					user.setEmail(mail.getText().toString());
					user.put("NombreCompleto",nom.getText().toString()+" "+ap.getText().toString());
					 
					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
						    if (e == null) {					    	
						    	// Hooray! Let them use the app now.
						    	ParseUser.logInInBackground(username.getText().toString(), pass.getText().toString(), new LogInCallback() {
						    		public void done(ParseUser user, ParseException e) {
									    if (user != null) {
									    	// Hooray! The user is logged in.
									    	String nombre = (String) user.getUsername();
											Toast.makeText( getApplicationContext(),"Bienvenido a Applace, "+nombre,Toast.LENGTH_SHORT ).show();
											
											Intent intent = new Intent(Activity_registro.this, Logueado.class );
											startActivity(intent);
											finish();
									    }
									}
								});					    	
						    } else {					    	
						    	// Sign up didn't succeed. Look at the ParseException
						    	// to figure out what went wrong
						    	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						    	mail.setText("");
						    	username.setText("");
						    	pass.setText("");
						    }
						 }
					});
				}				
			}
		}
	};
}