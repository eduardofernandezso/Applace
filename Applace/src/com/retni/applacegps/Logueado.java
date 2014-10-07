/**************************************************************************************************************
ACtividad principal que se utilizara para mostrar el mapa en la aplicación. Acá se trabajará la mayoría del 
tiempo.
**************************************************************************************************************/

package com.retni.applacegps;

import java.util.ArrayList;
import com.parse.Parse;
import com.parse.ParseUser;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
			
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<Double> lax = new ArrayList<Double>();
		@SuppressWarnings("unused")
		ArrayList<Double> lox = new ArrayList<Double>();
		
		int flag;
		Intent in = getIntent();
		flag = in.getIntExtra("flag",-1);
			
		if(flag!=-1){
			lax = (ArrayList<Double>) getIntent().getSerializableExtra("latis");
			lox = (ArrayList<Double>) getIntent().getSerializableExtra("longis");
			
			Toast.makeText(this, "Filtrados: " + lax.size(), Toast.LENGTH_SHORT).show();
		}

		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
	    
	    if(user!=null){
	    	mailUser = user.getEmail();
		    nameUser = (String) user.getString("NombreCompleto");
	    }
	    
		opcionesMenu = new String[] {"Mi perfil, "+ nameUser, "Mapa", "Publicar Alojamiento","Mis Alojamientos","Buscar Alojamiento", "Ruta", "Cerrar Sesión"};
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
        drawerList.setItemChecked(1, true);
        
        // Add FragmentMain as the initial fragment       
        FragmentManager fm = Logueado.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        
        //Fragment fragment = new Fragment_mapa();
        Fragment fragment = new Fragment_googlemaps();

        /*
        Bundle j = new Bundle();
        j.putSerializable("las",lax);
        j.putSerializable("los", lox);
        j.putInt("flag", 0);
        fragment.setArguments(j);*/
        
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
                        fragment = new Fragment_googlemaps();                    	
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
                    	Intent i = new Intent(Logueado.this, Activity_filtro.class );
						startActivity(i);
                    	break;
                    case 5:
                    	//Pregunta por ruta
                    	fragment = new Fragment_mapaRuta();
                    	break;
                    case 6:
                    	AlertDialog.Builder dialog = new AlertDialog.Builder(Logueado.this);  
            	        dialog.setTitle("Cerrar Sesión");		
            	        dialog.setIcon(R.drawable.ic_launcher);	
            	        
            	        View v = getLayoutInflater().inflate( R.layout.dialog, null );
            			      
            	        dialog.setView(v);
            	        dialog.setNegativeButton("Cancelar", null);  
            	        dialog.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {  
            	            public void onClick(DialogInterface dialogo1, int id) {
            	            	ParseUser.logOut();
                            	Intent i = new Intent(Logueado.this, MainActivity.class );
        						startActivity(i);
            	            }  
            	        });  
            	        
            	        dialog.show();
                    	break;
                    
                }
                
                if (fragment != null){
                	FragmentManager fragmentManager =
                            getSupportFragmentManager();
             
                        fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
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
		menu.findItem(R.id.action_delete).setVisible(false);
		
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
	        	Intent intent = new Intent(this, Activity_filtro.class );
				startActivity(intent);
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