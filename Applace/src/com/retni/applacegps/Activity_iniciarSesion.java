/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del inicio de sesión de ini_sesion.xml y los 
verifica para luego darle acceso directo al mapa que se encuentra en Logueado.java
**************************************************************************************************************/

package com.retni.applacegps;


import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_iniciarSesion extends ActionBarActivity {
	
	EditText email;
	EditText pass;
	Button iniciar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ini_sesion);
		
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");

  		  email = (EditText)findViewById(R.id.ini_correo);
  		  pass = (EditText)findViewById(R.id.ini_pass);
  		  iniciar = (Button)findViewById(R.id.iniSesion);
		
  		  iniciar.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.iniSesion) {
				
				if(pass.getText().toString().matches("") || email.getText().toString().matches("")){
					Toast.makeText( getApplicationContext(),"Por favor llene todos los campos",Toast.LENGTH_SHORT ).show();
				}
				else{
					ParseUser.logInInBackground(email.getText().toString(), pass.getText().toString(), new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      // Hooray! The user is logged in.
					    	String nombre = (String) user.getUsername();
							Toast.makeText( getApplicationContext(),"Bienvenido a Applace, "+nombre,Toast.LENGTH_SHORT ).show();
							
							Intent intent = new Intent(Activity_iniciarSesion.this, Logueado.class);
							intent.putExtra("mailUser", email.getText().toString());
							intent.putExtra("nameUser", nombre);
							intent.putExtra("passUser", pass.getText().toString());
							startActivity(intent);
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
					    	email.setText("");
							pass.setText("");
					    }
					  }
					});
				}				
			} 
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_config).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_camara).setVisible(false);
		menu.findItem(R.id.action_delete).setVisible(false);
		return true;
	}
}
