/**************************************************************************************************************
Actividad inicial que carga el fragmento con las opciones de registro o inicio de sesi�n. Si se detecta que ya 
hay alg�n usuario logueado anteriormente, se carga el mapa, es decir, la clase Logueado.java
**************************************************************************************************************/

package com.retni.applacegps;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        	Intent i = new Intent(MainActivity.this,Logueado.class);
        	startActivity(i);
          // do stuff with the user
        } else {
          // show the signup or login screen
        	launch_home();
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
		menu.findItem(R.id.action_delete).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_search:
	            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	  if (keyCode == KeyEvent.KEYCODE_BACK) {AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);  
      dialog.setTitle("Salir");		
      dialog.setIcon(R.drawable.ic_launcher);	
      
      View v = getLayoutInflater().inflate( R.layout.dialog2, null );
		      
      dialog.setView(v);
      dialog.setNegativeButton("Cancelar", null);  
      dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
          public void onClick(DialogInterface dialogo1, int id) {
        	  Intent startMain = new Intent(Intent.ACTION_MAIN);
        	  startMain.addCategory(Intent.CATEGORY_HOME);
        	  startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	  startActivity(startMain);
          }  
      });  
      
      dialog.show();           	   }
	//para las demas cosas, se reenvia el evento al listener habitual
	  return super.onKeyDown(keyCode, event);
	}
}