package com.retni.applacegps;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class Activity_caracteristicas extends ActionBarActivity{
	
	String bk_dir_escrita, bk_descripcion, bk_fotografia, bk_titulo, bk_tipo_aloj;
	
	int bk_capacidad, bk_num_piezas, bk_num_camas, bk_num_banos, bk_precio;
	double bk_dir_latitud, bk_dir_longitud;
	
	boolean bk_higiene, bk_aire_acondicionado, bk_calefaccion, bk_cocina, bk_internet, bk_piscina;
	boolean bk_lavadora, bk_desayuno, bk_estacionamiento, bk_mascota, bk_quincho, bk_tv, bk_telefono;
	
	Button publicar;
	CheckBox ch_higiene, ch_aire_acondicionado, ch_calefaccion, ch_cocina, ch_internet, ch_piscina;
	CheckBox ch_lavadora, ch_desayuno, ch_estacionamiento, ch_mascota, ch_quincho, ch_tv, ch_telefono;
	EditText car_foto, car_titulo, car_descrip, car_precio;
	Spinner sp_capacidad, sp_camas, sp_banos, sp_piezas;
	
	String[] cap, cam, ban, hab;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caracteristicas);  		
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		
		
		ch_higiene = (CheckBox) findViewById(R.id.ch_higiene);
		ch_aire_acondicionado = (CheckBox) findViewById(R.id.ch_aire);
		ch_calefaccion = (CheckBox) findViewById(R.id.ch_calefaccion);
		ch_cocina = (CheckBox) findViewById(R.id.ch_cocina);
		ch_internet = (CheckBox) findViewById(R.id.ch_internet);
		ch_piscina = (CheckBox) findViewById(R.id.ch_piscina);
		ch_lavadora = (CheckBox) findViewById(R.id.ch_lavadora);
		ch_desayuno = (CheckBox) findViewById(R.id.ch_desayuno);
		ch_estacionamiento = (CheckBox) findViewById(R.id.ch_estacionamiento);
		ch_mascota = (CheckBox) findViewById(R.id.ch_mascota);
		ch_quincho = (CheckBox) findViewById(R.id.ch_quincho);
		ch_tv = (CheckBox) findViewById(R.id.ch_tv);
		ch_telefono = (CheckBox) findViewById(R.id.ch_telefono);
		
		sp_capacidad = (Spinner) findViewById(R.id.sp_capacidad);
		sp_camas = (Spinner) findViewById(R.id.sp_camas);
		sp_banos = (Spinner) findViewById(R.id.sp_banos);
		sp_piezas = (Spinner) findViewById(R.id.sp_piezas);
		
		car_foto = (EditText) findViewById(R.id.car_foto);
		car_titulo = (EditText) findViewById(R.id.car_titulo);
		car_descrip = (EditText) findViewById(R.id.car_descrip);
		car_precio = (EditText) findViewById(R.id.car_precio);
		
		publicar = (Button)findViewById(R.id.but_publicar);
		
		Intent intent = getIntent();
		bk_tipo_aloj = intent.getStringExtra("tipo_aloj");
		bk_dir_latitud = intent.getDoubleExtra("lat", 0);
		bk_dir_longitud = intent.getDoubleExtra("lon", 0);
		
		
		cap = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15", "Más"};
		cam = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15", "Más"};
		ban = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15", "Más"};
		hab = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15", "Más"};
		
		sp_capacidad.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, cap));
		
		sp_camas.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, cam));
		
		sp_banos.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, ban));
		
		sp_piezas.setAdapter(new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, hab));
		
		sp_capacidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        bk_capacidad = Integer.parseInt(item.toString());
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		sp_camas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        bk_num_camas = Integer.parseInt(item.toString());
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		sp_banos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        bk_num_banos = Integer.parseInt(item.toString());
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		sp_piezas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        bk_num_piezas = Integer.parseInt(item.toString());
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
		
		publicar.setOnClickListener(listener);		
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
	            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	
	public void saveNewAlojamiento(){
		//String appVersion = "v1";
  	   // Backendless.initApp( this, "0A10A8FF-1F4C-0FB5-FFB6-0DC451109500", "9B122EE8-E46B-63D2-FFEA-023DD8271E00", appVersion );
  	   
		

		bk_precio = Integer.parseInt(car_precio.getText().toString());
		bk_titulo = car_titulo.getText().toString();
		bk_descripcion = car_descrip.getText().toString();
		bk_fotografia = car_foto.getText().toString();
		
		ParseObject alo = new ParseObject("Alojamiento");
		ParseUser user = ParseUser.getCurrentUser();
		String username = user.getUsername();
		alo.put("User",username);
		alo.put("dir_longitud",bk_dir_longitud);
		alo.put("dir_latitud",bk_dir_latitud);
		alo.put("tipo_aloj",bk_tipo_aloj);
		alo.put("precio",bk_precio);
		alo.put("titulo",bk_titulo);
		alo.put("descripcion",bk_descripcion);
		//alo.put("fotografia",bk_fotografia);
		alo.put("capacidad", bk_capacidad);
		alo.put("numeroCamas", bk_num_camas);
		alo.put("numeroBanos",bk_num_banos);
		alo.put("numeroPiezas",bk_num_piezas);

		if (ch_higiene.isChecked()){
			bk_higiene = true;
			alo.put("articulos_higiene", true);
		}
		else {alo.put("articulos_higiene", false);}

		if (ch_aire_acondicionado.isChecked()){
			bk_aire_acondicionado = true;
			alo.put("aire_acond", true);
		}
		else {alo.put("aire_acond", false);}

		if (ch_calefaccion.isChecked()){
			bk_calefaccion = true;
			alo.put("calefaccion", true);
		}
		else {alo.put("calefaccion", false);}

		if (ch_cocina.isChecked()){
			bk_cocina = true;
			alo.put("cocina", true);
		}
		else {alo.put("cocina", false);}

		if (ch_internet.isChecked()){
			bk_internet = true;
			alo.put("internet", true);
		}
		else {alo.put("internet", false);}

		if (ch_piscina.isChecked()){
			bk_piscina = true;
			alo.put("piscina", true);
		}
		else {alo.put("piscina", false);}

		if (ch_tv.isChecked()){
			bk_tv = true;
			alo.put("tv", true);
		}
		else {alo.put("tv", false);}

		if (ch_telefono.isChecked()){
			bk_telefono = true;
			alo.put("telefono", true);
		}
		else {alo.put("telefono", false);}

		if (ch_lavadora.isChecked()){
			bk_lavadora = true;
			alo.put("lavadora", true);
		}
		else {alo.put("lavadora", false);}

		if (ch_desayuno.isChecked()){
			bk_desayuno = true;
			alo.put("desayuno", true);
		}
		else {alo.put("desayuno", false);}

		if (ch_estacionamiento.isChecked()){
			bk_estacionamiento = true;
			alo.put("estacionamiento", true);
		}
		else {alo.put("estacionamiento", false);}

		if (ch_mascota.isChecked()){
			bk_mascota = true;
			alo.put("mascota", true);
		}
		else {alo.put("mascota", false);}

		if (ch_quincho.isChecked()){
			bk_quincho = true;
			alo.put("quincho", true);
		}
		else {alo.put("quincho", false);}		
	 	
	 	alo.saveInBackground();

	 	/*

		Alojamiento aloj = new Alojamiento();
		
		aloj.setTipo_aloj(bk_tipo_aloj);
		aloj.setAire_acond(bk_aire_acondicionado);
		aloj.setBanos(bk_num_banos);
		aloj.setCalefaccion(bk_calefaccion);
		aloj.setCamas(bk_num_camas);
		aloj.setCapacidad(bk_capacidad);
		aloj.setCocina(bk_cocina);
		aloj.setDesayuno(bk_desayuno);
		aloj.setDescripciona(bk_descripcion);
		//aloj.setDir_escrita(bk_dir_escrita);
		aloj.setEstacionamiento(bk_estacionamiento);
		//aloj.setFotografia(bk_fotografia);
		aloj.setHigiene(bk_higiene);
		aloj.setInternet(bk_internet);
		aloj.setLatitud(bk_dir_latitud);
		aloj.setLongitud(bk_dir_longitud);
		aloj.setLavadora(bk_lavadora);
		aloj.setMascota(bk_mascota);
		aloj.setPiezas(bk_num_piezas);
		aloj.setPiscina(bk_piscina);
		aloj.setPrecio(bk_precio);
		aloj.setQuincho(bk_quincho);
		aloj.setTelefono(bk_telefono);
		aloj.setTitulo(bk_titulo);
		aloj.setTv(bk_tv);
	 
		
		// save object asynchronously
		Backendless.Persistence.save( aloj, new AsyncCallback<Alojamiento>() {
		    public void handleResponse( Alojamiento response ){
		    	Toast.makeText( getApplicationContext(),"Correcto!",Toast.LENGTH_SHORT ).show();
		    }
		 
		    public void handleFault( BackendlessFault fault ){
		    	Toast.makeText( getApplicationContext(),"Fallo!"+fault.getMessage(),Toast.LENGTH_LONG ).show();
		    }
		});*/
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.but_publicar) {
				saveNewAlojamiento();
				Intent intent = new Intent(Activity_caracteristicas.this, Logueado.class );
				startActivity(intent);
			}
		}
	};
}
