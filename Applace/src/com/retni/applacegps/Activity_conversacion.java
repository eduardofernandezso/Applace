package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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

public class Activity_conversacion extends ActionBarActivity{
	
	TextView user,mensaje,fecha,texto;
	ImageView foto;
	String id;
	Bitmap fo;
	Bitmap b;
	String ide;
	ProgressBar delete_bar1;
	ImageView vis_img1, vis_img2, vis_temp;
	List<ParseObject> convers, convers4, date;
	LinearLayout conv_text;
	
	Bitmap imgMia, imgOtro;
	String nomMio="yo", nomOtro="otro";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversacion);
		
		delete_bar1 = (ProgressBar) findViewById(R.id.delete_bar1);
		foto = (ImageView) findViewById(R.id.lafoto);
		user = (TextView) findViewById(R.id.elnombre);
		mensaje = (TextView) findViewById(R.id.elmensaje);
		fecha = (TextView) findViewById(R.id.lafecha);
		//texto = (TextView) findViewById(R.id.eltexto);
		conv_text = (LinearLayout) findViewById(R.id.conv_text);
		
		Intent in = getIntent();
		
		nomOtro = in.getStringExtra("nomOtro");
		user.setText(nomOtro);
		fecha.setText(in.getStringExtra("fecha"));
		fo = (Bitmap) in.getParcelableExtra("fotOtro");
		id = in.getStringExtra("idConv");
		
		BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
        options.inPurgeable = true; // inPurgeable is used to free up memory while required
	    
        setImage(foto, fo);	
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        
        nomMio = user.getString("NombreCompleto");
        
        ParseFile img = user.getParseFile("Foto");	
		if(img != null){
			img.getDataInBackground(new GetDataCallback() {
		    	Bitmap bmp = null;
		        public void done(byte[] data, com.parse.ParseException e) {
		            if (e == null){
		                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
		                imgMia = bmp;
		            }
		            else{
		            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		            }
		        }
		    }); 
		} else{
			Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
		}		
		        	
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensaje");
        query.whereEqualTo("id_conversacion", id);
		query.orderByAscending("_created_at");

		try {
			convers = query.find();
		} catch (ParseException e) {}   	
        
        if(convers.size() != 0){
        	ParseObject conv = null;
			for(int i=0 ; i<convers.size() ; i++){	
				conv = convers.get(i);				
				
				TextView tv=new TextView(this);
				TextView nom=new TextView(this);
				tv.setText(conv.getString("Mensaje"));
				nom.setTextSize(18);
				tv.setTextSize(18);
				nom.setPadding(0, 10, 0, 0);
				tv.setPadding(0, 0, 0, 10);
				nom.setTypeface(null, Typeface.BOLD);
				
				if(conv.getString("id_envia").matches(user.getObjectId())){	
					nom.setText(nomMio+":");
					nom.setGravity(Gravity.RIGHT);
					tv.setGravity(Gravity.RIGHT);		
				} else{
					nom.setText(nomOtro+":");
				}
				conv_text.addView(nom);
				conv_text.addView(tv);				
			}
        }
	}	
	
	public void loadBitmap(Bitmap b) {
		vis_temp.setImageBitmap(circle(b));
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
		menu.findItem(R.id.action_delete).setVisible(true);
		menu.findItem(R.id.action_new).setVisible(false);
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_delete:
	        	AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_conversacion.this);  
    	        dialog.setTitle("Eliminar Mensaje");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View v = getLayoutInflater().inflate( R.layout.dialog, null );
    	        TextView text = (TextView) v.findViewById(R.id.dialog_text);
    	        text.setText("¿Desea borrar de forma permanente este mensaje?");
    			
    	        dialog.setView(v);
    	        dialog.setNegativeButton("Cancelar", null);  
    	        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	delete_bar1.setVisibility(View.VISIBLE);
    	            	Toast.makeText( getApplicationContext(),"Eliminando mensaje...",Toast.LENGTH_SHORT ).show();
    	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensajes");
    	        		query.whereEqualTo("objectId", ide);
    	        		
    	        		List<ParseObject> obj = null;
    	        		try {
    	        			obj = query.find();
    	        		} catch (ParseException e) {
    	        		}
    	        		if(obj.size()!=0){
    	        			obj.get(0).deleteInBackground(new DeleteCallback() {
    	        				public void done(ParseException e) {
    	        				    if (e == null) {	
    	        				    	delete_bar1.setVisibility(View.INVISIBLE);
    	        				    	Toast.makeText( getApplicationContext(),"Mensaje eliminado correctamente!",Toast.LENGTH_SHORT ).show();
    	        				    	Intent i = new Intent(Activity_conversacion.this,Activity_tabmensajes.class);
    	        				    	startActivity(i);
    	        				    } else{
    	        				    	Toast.makeText(getApplicationContext(), "Error al borrar el mensaje, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
    	        				    }
    	        				}
    	        		    });
    	        		}                    	
    	            }  
    	        });  
    	        
    	        dialog.show();
            	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
}