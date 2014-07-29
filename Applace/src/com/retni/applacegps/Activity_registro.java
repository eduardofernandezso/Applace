/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del registro de activity_registro.xml y los agrega 
a la base de datos como nuevo usuario, para luego darle acceso al mapa si es un usuario Arrendatario, mientras
que si es un Arrendador, se le muestra la página principal de sus pripiedades.
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
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity_registro extends ActionBarActivity {
	
	EditText mail,pass,nom,ap;
	Button registrar;
	String Fname,Fmail;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		Fname = getIntent().getExtras().getString("n");
		Fmail = getIntent().getExtras().getString("m");
		
  		String appVersion = "v1";
  	    Backendless.initApp( this, "0A10A8FF-1F4C-0FB5-FFB6-0DC451109500", "9B122EE8-E46B-63D2-FFEA-023DD8271E00", appVersion );
		
  	    if(Fname==null){
			mail = (EditText)findViewById(R.id.reg_correo);
			pass = (EditText)findViewById(R.id.reg_pass);
			nom = (EditText)findViewById(R.id.reg_nombre);
			ap = (EditText)findViewById(R.id.reg_apellido);
			
			registrar = (Button)findViewById(R.id.regis);
			
			
	  	   
			registrar.setOnClickListener(listener);
		}
		else{
			final BackendlessUser user = new BackendlessUser();
			
			user.setEmail(Fmail);
			user.setPassword("0");		  	    
			user.setProperty( "name", Fname );
			
			Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>(){
				public void handleResponse( BackendlessUser registeredUser ){
					Toast.makeText( getApplicationContext(),"Correcto!",Toast.LENGTH_SHORT ).show();
					
					Intent intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
					intent.putExtra("p","0");
					intent.putExtra("n",Fname);
					intent.putExtra("m",Fmail);
					startActivity(intent);
				}
				public void handleFault( BackendlessFault fault ){
					Toast.makeText( getApplicationContext(),"Fallo!"+fault.getCode(),Toast.LENGTH_SHORT ).show();
				}
			});  
			
		}
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
				final BackendlessUser user = new BackendlessUser();
				
				user.setEmail(mail.getText().toString());
				user.setPassword(pass.getText().toString());		  	    
				user.setProperty( "name", nom.getText().toString()+" "+ap.getText().toString() );
				
				Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>(){
					public void handleResponse( BackendlessUser registeredUser ){
						Toast.makeText( getApplicationContext(),"Correcto!",Toast.LENGTH_SHORT ).show();
						
						Intent intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
						startActivity(intent);
					}
					public void handleFault( BackendlessFault fault ){
						Toast.makeText( getApplicationContext(),"Fallo!"+fault.getCode(),Toast.LENGTH_SHORT ).show();
					}
				});  
			}
		}
	};
}