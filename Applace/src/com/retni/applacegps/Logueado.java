/**************************************************************************************************************
ACtividad principal que se utilizara para mostrar el mapa en la aplicación. Acá se trabajará la mayoría del 
tiempo.
**************************************************************************************************************/

package com.retni.applacegps;

import com.parse.Parse;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class Logueado extends ActionBarActivity{
	
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] opcionesMenu;
    
    CharSequence tituloApp;
    ActionBarDrawerToggle drawerToggle;
    CharSequence tituloSeccion;
    
    String passUser, mailUser, nameUser = "Mi Cuenta";
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
	    
	    if(user!=null){
	    	mailUser = user.getEmail();
		    nameUser = (String) user.getUsername();
	    }	    
		
		opcionesMenu = new String[] {nameUser, "Mapa", "Publicar Alojamiento","Mis Alojamientos","Opción 3","Salir"};
        drawerLayout = (DrawerLayout) findViewById(R.id.container);
        drawerList = (ListView) findViewById(R.id.left_drawer);
 
        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, opcionesMenu));
        
        tituloApp = getSupportActionBar().getTitle();
        tituloSeccion = tituloApp;
        
        drawerToggle = new ActionBarDrawerToggle(this,
            drawerLayout,
            R.drawable.ic_slide,
            R.string.drawer_open,
            R.string.drawer_close) {
     
            public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(tituloSeccion);
                ActivityCompat.invalidateOptionsMenu(Logueado.this);
            }
     
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(tituloApp);
                ActivityCompat.invalidateOptionsMenu(Logueado.this);
            }
        };
     
        drawerLayout.setDrawerListener(drawerToggle);
        
        // Add FragmentMain as the initial fragment       
        FragmentManager fm = Logueado.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        Fragment fragment = new Fragment_mapa();
        ft.replace(R.id.content_frame, fragment);
        ft.commit(); 
        
        drawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
     
                Fragment fragment = null;
                Intent intent = null;
     
                switch (position) {
                    case 0:
                    	//Propiedades de la cuenta de usuario
                        //fragment = new Fragment_inicio();
                    	intent = new Intent(Logueado.this, Activity_perfil.class );
						startActivity(intent);
                        break;
                    case 1:
                    	//Carga el mapa
                        fragment = new Fragment_mapa();                    	
                        break;
                    case 2:
                    	//Publica un anuncio
                        fragment = new Fragment_tipoAloj();
                        break;
                    case 3:
                    	//Lista de alojamientos
                    	fragment = new Fragment_listaAloj();
                    	break;
                    case 4:
                    	intent = new Intent(Logueado.this, Activity_verAlojamiento.class );
						startActivity(intent);
                    	break;
                    case 5:
                    	ParseUser.logOut();
                    	intent = new Intent(Logueado.this, MainActivity.class );
						startActivity(intent);
                    	break;
                }
                
                if (fragment != null){
                	FragmentManager fragmentManager =
                            getSupportFragmentManager();
             
                        fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                	
                }               
     
                drawerList.setItemChecked(position, true);
     
                tituloSeccion = opcionesMenu[position];
                getSupportActionBar().setTitle(tituloSeccion);
     
                drawerLayout.closeDrawer(drawerList);
            }
        });
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);       
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.findItem(R.id.como_funciona).setVisible(false);
		menu.findItem(R.id.codiciones).setVisible(false);
		menu.findItem(R.id.politica).setVisible(false);
		menu.findItem(R.id.ayuda).setVisible(false);
		
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_config).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_camara).setVisible(false);
		
		boolean menuAbierto = drawerLayout.isDrawerOpen(drawerList);
		 
	    if(menuAbierto){
	    	//return menuAbierto;
	        menu.findItem(R.id.action_search).setVisible(false);
	    	menu.hasVisibleItems();
	    }
	    else
	    	//return menuAbierto;
	        menu.findItem(R.id.action_search).setVisible(true);
	    
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
		
		switch(item.getItemId())
	    {
	        case R.id.action_search:
	            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    drawerToggle.syncState();
	}
	 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    drawerToggle.onConfigurationChanged(newConfig);
	}	
}

