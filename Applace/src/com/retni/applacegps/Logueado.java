/**************************************************************************************************************
ACtividad principal que se utilizara para mostrar el mapa en la aplicación. Acá se trabajará la mayoría del 
tiempo.
**************************************************************************************************************/

package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseUser;
import com.retni.applacegps.Fragment_listaAloj.Cursor_AdapterList;
import com.retni.applacegps.R.drawable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class Logueado extends ActionBarActivity{
	
	private static DrawerLayout drawerLayout;
    private static ListView drawerList;
    private String[] opcionesMenu;
    private int[] fotos;
    CharSequence tituloApp;
    ActionBarDrawerToggle drawerToggle;
    static CharSequence tituloSeccion;
    int SELECTED_POSITION=0;
    String passUser, mailUser, nameUser = "Mi Cuenta",idUser;
			
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
		    idUser = user.getObjectId();
	    }
	    
	    fotos = new int[] { R.drawable.miperfil2,
                R.drawable.mapa, R.drawable.publicar,
                R.drawable.misalojamientos, R.drawable.mismensajes, R.drawable.rutamultiple,
                R.drawable.mismensajes2, R.drawable.cerrarsesion};
	    
		opcionesMenu = new String[] {"Mi perfil, "+ nameUser, "Mapa", "Publicar Alojamiento","Mis Alojamientos","Mis Estadísticas", "Ruta Multiple", "Mis Mensajes","Cerrar Sesión"};
        drawerLayout = (DrawerLayout) findViewById(R.id.container);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        //drawerList.setItemChecked(1, true);
        
        BaseAdapter listAdapter = new Cursor_AdapterSl(Logueado.this, opcionesMenu, fotos);
    	drawerList.setAdapter(listAdapter);  
        /*
        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, opcionesMenu));
 
        drawerList.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, opcionesMenu));
            */
        
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
        String tag = fragment.getTag();

        /*
        Bundle j = new Bundle();
        j.putSerializable("las",lax);
        j.putSerializable("los", lox);
        j.putInt("flag", 0);
        fragment.setArguments(j);*/
        
        ft.replace(R.id.content_frame, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit(); 

        
        /*
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
                    	intent.putExtra("idDueno", "soy yo");
						startActivity(intent);
						overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        break;
                    case 1:
                    	//Carga el mapa
                        fragment = new transitiva();                    	
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
                    	fragment = new fragment_listAloj2();
                    	break;
                    case 5:
                    	//Pregunta por ruta
                    	fragment = new Fragment_rutaMultiple();
                    	break;
                    case 6:
                    	Intent i2 = new Intent(Logueado.this,Activity_mensajes.class);
                    	startActivity(i2);
                    	overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    	break;
                    case 7:
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
                	String tag = fragment.getTag();
             
                    fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment,tag)
                        .addToBackStack(tag)
                        .commit();
                	
                }               
     
                drawerList.setItemChecked(position, true);
     
                tituloSeccion = opcionesMenu[position];
                getSupportActionBar().setTitle(tituloSeccion);
     
                drawerLayout.closeDrawer(drawerList);
            }
        });
        */
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);       
	}
	
public static class Cursor_AdapterSl extends BaseAdapter{
		
		ImageView vis_img1, vis_img2, vis_temp;
		Context context;
	    String [] textos;
	    int [] iconos;
	    int sel=1;
	    int pos = 0;
	    
	    @SuppressWarnings("unused")
		private static LayoutInflater inflater = null;

	    public Cursor_AdapterSl(Context context, String [] textos, int[] iconos) {
	        // TODO Auto-generated constructor stub
	        this.context = context;
	        this.textos = textos;
	        this.iconos = iconos;
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return textos.length;
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return textos[position];
	    }

	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }


	    public View getView(int position, View convertView, final ViewGroup parent) {
	        // TODO Auto-generated method stub
	    	if(convertView==null){
		    	@SuppressWarnings("static-access")
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		    	convertView = inflater.inflate(R.layout.sliding_row, null); 
		    }
	    	
	    	if(position==1){
	    		convertView.setBackgroundResource(drawable.applace_btn_default_disabled_focused_holo_light);
	    	}
	    	
		    final TextView texto = (TextView)convertView.findViewById(R.id.sl_text);
		    final ImageView ic = (ImageView)convertView.findViewById(R.id.sl_img);
		    
		    if(iconos.length != 0 ){
		    	ic.setImageResource(iconos[position]);
		    }
		    if(textos.length!=0){
		    	convertView.setTag(position);	    
		    	texto.setText(textos[position]);
		    }
		    
	        convertView.setOnClickListener(new OnClickListener(){        	
				public void onClick(View v) {
					Integer pos = (Integer) v.getTag();
					
					Vibrator h = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        	h.vibrate(25);
		        	
		        	v.setBackgroundResource(drawable.ab_background_textured_applacegps);
					
					Fragment fragment = null;
	                Intent intent = null;
	     
	                switch (pos) {
	                    case 0:
	                    	//Propiedades de la cuenta de usuario
	                        //fragment = new Fragment_inicio();
	                    	intent = new Intent(context, Activity_perfil.class );
	                    	intent.putExtra("idDueno", "soy yo");
	                    	context.startActivity(intent);
	                    	((Activity) context).overridePendingTransition(R.anim.left_in, R.anim.left_out);
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
	                    	fragment = new fragment_listAloj2();
	                    	break;
	                    case 5:
	                    	//Pregunta por ruta
	                    	fragment = new Fragment_rutaMultiple();
	                    	break;
	                    case 6:
	                    	Intent i2 = new Intent(context,Activity_mensajes.class);
	                    	context.startActivity(i2);
	                    	((Activity) context).overridePendingTransition(R.anim.left_in, R.anim.left_out);
	                    	break;
	                    case 7:
	                    	AlertDialog.Builder dialog = new AlertDialog.Builder(context);  
	            	        dialog.setTitle("Cerrar Sesión");		
	            	        dialog.setIcon(R.drawable.ic_launcher);	
	            	        
	            	        View vi = ((Activity) context).getLayoutInflater().inflate( R.layout.dialog, null );
	            			      
	            	        dialog.setView(vi);
	            	        dialog.setNegativeButton("Cancelar", null);  
	            	        dialog.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {  
	            	            public void onClick(DialogInterface dialogo1, int id) {
	            	            	ParseUser.logOut();
	                            	Intent i = new Intent(context, MainActivity.class );
	                            	context.startActivity(i);
	            	            }  
	            	        });  
	            	        
	            	        dialog.show();
	                    	break;
	                    
	                }
	                
	                if (fragment != null){
	                	FragmentManager fragmentManager =
	                			((FragmentActivity) context).getSupportFragmentManager();
	                	String tag = fragment.getTag();
	             
	                    fragmentManager.beginTransaction()
	                        .replace(R.id.content_frame, fragment,tag)
	                        .addToBackStack(tag)
	                        .commit();
	                	
	                }
	                
	                
	                
	                //drawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	                drawerList.setItemChecked(pos, true);
	                
	                //tituloSeccion = textos[pos];
	                ((ActionBarActivity) context).getSupportActionBar().setTitle(textos[pos]);
	     
	                drawerLayout.closeDrawer(drawerList);
					
					/*
					Integer pos = (Integer) v.getTag();
					Vibrator h = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        	h.vibrate(25);
					Intent intent = new Intent(context,Activity_verAlojamiento.class);
					intent.putExtra("idAloj", id_aloj.get(pos));
					context.startActivity(intent);	
					*/			
				}		
			});        
	        
	        
	        return convertView;
	    }	
	    public void loadBitmap(Bitmap b) {
			vis_temp.setImageBitmap(circle(Bitmap.createScaledBitmap(b, 200, 200, false)));
		}
		
		public void unloadBitmap() {
		   if (vis_temp != null)
			   vis_temp.setImageBitmap(null);
		}
		
		public void setImage(ImageView i, Bitmap sourceid) {
			vis_temp = i;
			unloadBitmap();	   
			loadBitmap(sourceid);
		}
		
		public Bitmap circle(Bitmap bit){
			Bitmap circleBitmap = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Bitmap.Config.ARGB_8888);
		    BitmapShader shader = new BitmapShader (bit,  TileMode.CLAMP, TileMode.CLAMP);
		    Paint paint = new Paint();
		    paint.setShader(shader);
		    
		    Canvas c = new Canvas(circleBitmap);
		    c.drawCircle(bit.getWidth()/2, bit.getHeight()/2, bit.getWidth()/2, paint);
			
			return circleBitmap;
		}
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
		menu.findItem(R.id.action_share).setVisible(true);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_camara).setVisible(false);
		menu.findItem(R.id.action_delete).setVisible(false);
		menu.findItem(R.id.action_new).setVisible(false);
		
		getSupportActionBar().setTitle("Applace");
		
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
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
	            break;
	        case R.id.action_share:
	        	Intent intent2 = new Intent(this, Compartir.class );
				startActivity(intent2);
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
	/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	  if (keyCode == KeyEvent.KEYCODE_BACK) {AlertDialog.Builder dialog = new AlertDialog.Builder(Logueado.this);  
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
	*/
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
			case KeyEvent.KEYCODE_BACK:
				Intent startMain = new Intent(Intent.ACTION_MAIN);
	        	startMain.addCategory(Intent.CATEGORY_HOME);
	        	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	startActivity(startMain);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    finish();
	}
	
}