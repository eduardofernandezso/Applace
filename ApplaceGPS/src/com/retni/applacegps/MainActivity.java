/**************************************************************************************************************
Actividad inicial que carga el fragmento con las opciones de registro o inicio de sesión. Si se detecta que ya 
hay algún usuario logueado anteriormente, se carga el mapa, es decir, la clase Logueado.java
**************************************************************************************************************/

package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        launch_home();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void launch_home() {//identificamos y cargamos el fragmento
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main"))==null){
			ft.add(R.id.frame_ini, new Fragment_inicio(),"main"); //en caso que sea null cargamos uno nuevo en el frame y con el tag "main"
		}
		else{
			if(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main")).isDetached()){
				ft.attach(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main")));//en caso que no sea null, cargamos el que ya existe
			}
		}
		ft.commit();
	}
}
