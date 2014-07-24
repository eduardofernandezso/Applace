/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del inicio de sesión de ini_sesion.xml y los 
verifica para luego darle acceso directo al mapa que se encuentra en Logueado.java
**************************************************************************************************************/

package com.retni.applacegps;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class Activity_iniciarSesion extends ActionBarActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ini_sesion);
		
	}
}
