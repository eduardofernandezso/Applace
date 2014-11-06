package com.retni.applacegps;

import java.util.List;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_verMensaje extends ActionBarActivity{
	
	TextView user,mensaje,fecha,texto;
	ImageView foto;
	int id;
	Bitmap fo;
	Bitmap b;
	String ide;
	ProgressBar delete_bar1;
	ImageView vis_img1, vis_img2, vis_temp;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vermensajes);
		
		delete_bar1 = (ProgressBar) findViewById(R.id.delete_bar1);
		foto = (ImageView) findViewById(R.id.lafoto);
		user = (TextView) findViewById(R.id.elnombre);
		mensaje = (TextView) findViewById(R.id.elmensaje);
		fecha = (TextView) findViewById(R.id.lafecha);
		texto = (TextView) findViewById(R.id.eltexto);
		
		Intent in = getIntent();
		user.setText(in.getStringExtra("usuario"));
		mensaje.setText(in.getStringExtra("mensaje"));
		fecha.setText(in.getStringExtra("fecha"));
		fo = (Bitmap) in.getParcelableExtra("imagen");
		id = in.getIntExtra("id", 0);
		ide = in.getStringExtra("ide");
		
		BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
        options.inPurgeable = true; // inPurgeable is used to free up memory while required
	    
        setImage(foto, fo);	
		if(id==1) texto.setText("Le Escribiste: ");
		else if (id==2) texto.setText("Te Escribió: ");
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
	        	AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_verMensaje.this);  
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
    	        				    	Intent i = new Intent(Activity_verMensaje.this,Activity_tabmensajes.class);
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
