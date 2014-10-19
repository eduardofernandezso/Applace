package com.retni.applacegps;

import java.util.List;

import com.parse.DeleteCallback;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_crearMen extends ActionBarActivity {
	
	TextView new_receptor;
	EditText new_mensaje;
	ParseFile img;
	Button new_enviar;
	String men;
	String id_user_receptor, id_user_emisor, name_receptor, mail_receptor;
	
	String mail1, mail2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crearmen);
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		
		new_receptor = (TextView) findViewById(R.id.new_receptor);
		new_mensaje = (EditText) findViewById(R.id.new_mensaje);
		new_enviar = (Button) findViewById(R.id.new_enviar);
		ParseUser user = ParseUser.getCurrentUser();		

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
		query.whereEqualTo("email", user.getEmail());
		
		List<ParseObject> aa = null;
		try {
			aa = query.find();
		} catch (ParseException e) {}
		ParseObject hola = aa.get(0);
		img = hola.getParseFile("Foto");
	    if(img != null){
		    img.getDataInBackground(new GetDataCallback() {
		    	Bitmap bmp = null;
		        public void done(byte[] data, com.parse.ParseException e) {
		            if (e == null){
		            	BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
		                options.inPurgeable = true; // inPurgeable is used to free up memory while required
		        	    
		                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		                
		            }
		            else{
		            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		            }
		        }
		    }); 
	    }
	
		
		
		Intent in = getIntent();

		
		mail1 = in.getStringExtra("mail_emisor");
		mail2 = in.getStringExtra("mail_receptor");
		
		new_receptor.setText(mail2);
		new_enviar.setOnClickListener(listener);
		//new_enviar.setOnClickListener(listener);
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
            	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.new_enviar) {

				men = new_mensaje.getText().toString();
				if(men.matches("")){
					Toast.makeText( getApplicationContext(),"Por favor ingrese un mensaje",Toast.LENGTH_SHORT ).show();
				}				
				else{					
					//ParseUser user = new ParseUser();
	               // user = ParseUser.getCurrentUser();        	        	    
	        	   // if(user!=null){
	        	    	ParseObject alo = new ParseObject("Mensajes");
	        	    	alo.put("envia",mail1);
	        	    	alo.put("recibe",mail2);
	        		    alo.put("mensaje", men);
	        		    alo.put("Foto",img);
	        		    //Toast.makeText(getApplicationContext(),mail1+mail2, Toast.LENGTH_LONG).show();
	        		    alo.saveInBackground(new SaveCallback() {
							public void done(ParseException e) {
							    if (e == null) {
							 
									Toast.makeText( getApplicationContext(),"Enviado",Toast.LENGTH_SHORT ).show();
									mandar();
							    } else{
							    	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
							    }
							}
	        		    });
	        	   // }
					
				}			
			}
			/*else if(id == R.id.ini_link){
				Intent intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
				startActivity(intent);
			}*/
		}
	};
	public void mandar() {
		
		Intent intent = new Intent(Activity_crearMen.this, Logueado.class);
		startActivity(intent);
		}			
}
