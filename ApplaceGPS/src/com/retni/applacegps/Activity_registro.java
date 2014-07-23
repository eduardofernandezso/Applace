/**************************************************************************************************************
Actividad que se encarga de tomar los datos del formulario del registro de activity_registro.xml y los agrega 
a la base de datos como nuevo usuario, para luego darle acceso al mapa si es un usuario Arrendatario, mientras
que si es un Arrendador, se le muestra la página principal de sus pripiedades.
**************************************************************************************************************/

package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Activity_registro extends FragmentActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
	}
}