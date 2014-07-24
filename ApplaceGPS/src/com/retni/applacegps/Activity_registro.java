/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del registro de activity_registro.xml y los agrega 
a la base de datos como nuevo usuario, para luego darle acceso al mapa si es un usuario Arrendatario, mientras
que si es un Arrendador, se le muestra la página principal de sus pripiedades.
**************************************************************************************************************/

package com.retni.applacegps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity_registro extends ActionBarActivity {
	
	EditText mail,pass,nom,ap;
	Button registrar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		mail = (EditText)findViewById(R.id.reg_correo);
		pass = (EditText)findViewById(R.id.reg_pass);
		nom = (EditText)findViewById(R.id.reg_nombre);
		ap = (EditText)findViewById(R.id.reg_apellido);
		
		registrar = (Button)findViewById(R.id.regis);
	}
	private OnClickListener listener = new OnClickListener(){		//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			int id = v.getId();
			if (id == R.id.regis) {
				intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
				startActivity(intent);
			}
		}
	};
	
}