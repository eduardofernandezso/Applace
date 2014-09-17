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
import android.widget.TextView;

public class Activity_registro extends ActionBarActivity {
	
	EditText mail,pass,nom,ap;
	String username;
	Button registrar;
	TextView link;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
  	    Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
  	                                
  	    mail = (EditText)findViewById(R.id.reg_correo);
		pass = (EditText)findViewById(R.id.reg_pass);
		nom = (EditText)findViewById(R.id.reg_nombre);
		ap = (EditText)findViewById(R.id.reg_apellido);
		link = (TextView) findViewById(R.id.ini_link);
		
		registrar = (Button)findViewById(R.id.regis);


		registrar.setOnClickListener(listener);
		link.setOnClickListener(listener);
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
				username = mail.getText().toString();
				if(nom.getText().toString().matches("") || pass.getText().toString().matches("") || ap.getText().toString().matches("") || mail.getText().toString().matches("")){
					Toast.makeText( getApplicationContext(),"Por favor llene todos los campos",Toast.LENGTH_SHORT ).show();
				}				
				else{
					ParseUser user = new ParseUser();
					user.setUsername(username);
					user.setPassword(pass.getText().toString());
					user.setEmail(mail.getText().toString());
					user.put("NombreCompleto",nom.getText().toString()+" "+ap.getText().toString());
					 
					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
						    if (e == null) {
						    	ParseUser.logInInBackground(username, pass.getText().toString(), new LogInCallback() {
						    		public void done(ParseUser user, ParseException e) {
									    if (user != null) {
									    	String nombre = user.getString("NombreCompleto");
											Toast.makeText( getApplicationContext(),"Bienvenido a Applace, "+nombre,Toast.LENGTH_SHORT ).show();
											
											Intent intent = new Intent(Activity_registro.this, Logueado.class );
											startActivity(intent);
									    }
									}
								});					    	
						    } else {
						    	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						    	mail.setText("");
						    	pass.setText("");
						    }
						 }
					});
				}				
			}
			else if(id == R.id.ini_link){
				Intent intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
				startActivity(intent);
			}
		}
	};
}