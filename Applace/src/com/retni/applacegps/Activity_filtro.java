package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
public class Activity_filtro extends ActionBarActivity{
	
	List<ParseObject> objects ;
	
	String bk_dir_escrita, bk_descripcion, bk_fotografia, bk_titulo, bk_tipo_aloj;
	
	public ArrayList<Double> la = new ArrayList<Double>();
	public ArrayList<Double> lo = new ArrayList<Double>();
	int bk_capacidad, bk_num_piezas, bk_num_camas, bk_num_banos, bk_precio;
	double bk_dir_latitud, bk_dir_longitud;
	
	boolean bk_tv,bk_internet,bk_telefono,bk_cocina;
	
	Button filtrar;
	CheckBox ch_tv, ch_internet, ch_telefono, ch_cocina;
	EditText car_precio;
	Spinner sp_capacidad, sp_camas, sp_banos, sp_piezas;
	
	String[] cap, cam, ban, hab;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtro);  		
		

		ch_cocina = (CheckBox) findViewById(R.id.ch_cocina);
		ch_internet = (CheckBox) findViewById(R.id.ch_internet);
		ch_tv = (CheckBox) findViewById(R.id.ch_tv);
		ch_telefono = (CheckBox) findViewById(R.id.ch_telefono);
		
		sp_capacidad = (Spinner) findViewById(R.id.sp_capacidad);
		sp_camas = (Spinner) findViewById(R.id.sp_camas);
		sp_banos = (Spinner) findViewById(R.id.sp_banos);
		sp_piezas = (Spinner) findViewById(R.id.sp_piezas);
		
		car_precio = (EditText) findViewById(R.id.car_precio);
		
		filtrar = (Button)findViewById(R.id.but_filtrar);
		
		Bundle bundle = new Bundle();
		bk_tipo_aloj = bundle.getString("tipoAloj");
		bk_dir_latitud = bundle.getDouble("lat");
		bk_dir_longitud = bundle.getDouble("lon");
		
		cap = new String[] {"0","1","2","3","4 o Más"};
		cam = new String[] {"0","1","2","3","4 o Más"};
		ban = new String[] {"0","1","2","3","4 o Más"};
		hab = new String[] {"0","1","2","3","4 o Más"};
		
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
		
		filtrar.setOnClickListener(listener);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.findItem(R.id.como_funciona).setVisible(false);
		menu.findItem(R.id.codiciones).setVisible(false);
		menu.findItem(R.id.politica).setVisible(false);
		menu.findItem(R.id.ayuda).setVisible(false);
		
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
	
	public void saveNewAlojamiento(){
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Alojamiento");
        
		String t = car_precio.getText().toString();
		if(t.matches("")) bk_precio = 0;
		else bk_precio = Integer.parseInt(car_precio.getText().toString());
		
		if(bk_precio!=0)query.whereLessThanOrEqualTo("precio", bk_precio);
		
		if(bk_capacidad != 0)query.whereGreaterThanOrEqualTo("capacidad", bk_capacidad);
		if(bk_num_camas != 0)query.whereGreaterThanOrEqualTo("numeroCamas", bk_num_camas);
		if(bk_num_banos != 0)query.whereGreaterThanOrEqualTo("numeroBanos", bk_num_banos);
		if(bk_num_piezas != 0)query.whereGreaterThanOrEqualTo("numeroPiezas", bk_num_piezas);
		
		if (ch_cocina.isChecked()){
			bk_cocina = true;
			query.whereEqualTo("cocina", true);
		}
		else
			bk_cocina = false;
		
		if (ch_internet.isChecked()){
			bk_internet = true;
			query.whereEqualTo("internet", true);
		}
		else 
			bk_internet = false;

		if (ch_tv.isChecked()){
			bk_tv = true;
			query.whereEqualTo("tv", true);
		}
		else
			bk_tv = false;
		
		if (ch_telefono.isChecked()){
			bk_telefono = true;
			query.whereEqualTo("telefono", true);
		}
		else
			bk_telefono=false;
		
		
        try{
        	objects = query.find();
        }catch(ParseException e){
        	
        }
        
       // query.findInBackground(new FindCallback <ParseObject>() {
         // @Override
         // public void done(List<ParseObject> objects, ParseException e) {
           if (objects == null) Toast.makeText(getApplicationContext(),
        		   "No Existen Alojamientos Cercanos con las especificaciones dadas" , Toast.LENGTH_SHORT).show();
           else {
            	int i=0;
            	int size = objects.size();
            	ParseObject filtrados = null;
            	for(i=0;i<size;i++){
            		filtrados = objects.get(i);
            		la.add(filtrados.getDouble("dir_latitud"));
            		lo.add(filtrados.getDouble("dir_longitud"));
            	}
           }

	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.but_filtrar) {
				saveNewAlojamiento();
				
				irse();
				
			}
		}
	};
	public void irse(){
		
		Intent intent = new Intent(Activity_filtro.this, Logueado.class );
		intent.putExtra("latis", la);
		intent.putExtra("longis", lo);
		intent.putExtra("flag",1);
		
		startActivity(intent);
	}
}
