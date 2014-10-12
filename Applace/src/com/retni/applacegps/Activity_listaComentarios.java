package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Activity_listaComentarios extends ActionBarActivity{
	
	ListView list_comentarios;
	String id_aloj;
	
	List<String> comentarios = new ArrayList<String>();
	List<String> fechas = new ArrayList<String>();
	List<Bitmap> fotos = new ArrayList<Bitmap>();
	List<String> users = new ArrayList<String>();	
	List<String> id_coment = new ArrayList<String>();
	
	List<ParseObject> comenta = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_comentarios);
		
		//Se obtiene el valor del id del alojamiento que se desea ver los comentarios.
		Intent intent = getIntent();
		id_aloj = intent.getStringExtra("id_aloj");
		Toast.makeText(getApplicationContext(), id_aloj, Toast.LENGTH_SHORT).show();
        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Comentarios");
		query2.whereEqualTo("id_alojamiento", id_aloj);
		query2.orderByDescending("_created_at");
		
		try {
			comenta = query2.find();
		} catch (ParseException e) {

		}		
		
		int size_com = 0;
		size_com = comenta.size();
		
		//Toast.makeText(getApplicationContext(), size_com+"", Toast.LENGTH_SHORT).show();
		if (size_com == 0){
			//msje.setVisibility(View.VISIBLE);
		} else{		
			ParseObject com = null;
			for(int i=0;i<size_com; i++){
				com = comenta.get(i);
				comentarios.add(com.getString("comentario"));
				fechas.add(com.getUpdatedAt().toGMTString());
				users.add(com.getString("nombre_user_emisor"));
				id_coment.add(com.getObjectId());
								
				ParseFile img = com.getParseFile("foto_user_emisor");
				if(img != null){
				    img.getDataInBackground(new GetDataCallback() {
				    	Bitmap bmp = null;
				        public void done(byte[] data, com.parse.ParseException e) {
				            if (e == null){
				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
				                fotos.add(bmp);
				            }
				            else{
				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				            }
				        }
				    }); 
				} else{
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
				}
			}		
			//Toast.makeText(getApplicationContext(), comentarios.size()+"", Toast.LENGTH_SHORT).show();
			list_comentarios = (ListView) findViewById(R.id.com_list);
	        list_comentarios.setAdapter(new Comentarios_Adapter(Activity_listaComentarios.this, comentarios, fechas, fotos, users, id_coment));        
	        //list_comentarios.setVisibility(View.VISIBLE);        
		}	
    }
	
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
	        case R.id.action_delete:	        	
            	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
}
