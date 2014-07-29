/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del inicio de sesión de ini_sesion.xml y los 
verifica para luego darle acceso directo al mapa que se encuentra en Logueado.java
**************************************************************************************************************/

package com.retni.applacegps;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

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
	String flag;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ini_sesion);
		
		//Conexión backendless
		String appVersion = "v1";
  	    Backendless.initApp( this, "0A10A8FF-1F4C-0FB5-FFB6-0DC451109500", "9B122EE8-E46B-63D2-FFEA-023DD8271E00", appVersion );
		
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
				Backendless.UserService.login( email.getText().toString(), pass.getText().toString(), new AsyncCallback<BackendlessUser>(){
					public void handleResponse( BackendlessUser registeredUser ){
						
						String nombre = (String) registeredUser.getProperty("name");
						Toast.makeText( getApplicationContext(),"Bienvenido a Applace, "+nombre,Toast.LENGTH_SHORT ).show();
						
						Intent intent = new Intent(Activity_iniciarSesion.this, Logueado.class);
						intent.putExtra("mailUser", email.getText().toString());
						intent.putExtra("nameUser", nombre);
						intent.putExtra("passUser", pass.getText().toString());
						startActivity(intent);
					}
					public void handleFault( BackendlessFault fault ){
						Toast.makeText( getApplicationContext(),"Usuario o contraseña incorrecta, porfavor intente nuevamente"
								,Toast.LENGTH_SHORT ).show();
						email.setText("");
						pass.setText("");
					}
				});	
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
		return true;
	}
}
