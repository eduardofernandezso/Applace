package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.DeleteCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.retni.applacegps.R.color;

public class Activity_conversacion extends ActionBarActivity{
	
	TextView user,mensaje,fecha,texto;
	ImageView foto;
	String idConv;
	Bitmap fo;
	Bitmap b;
	String ide;
	ProgressBar bar_send;
	ImageView vis_img1, vis_img2, vis_temp;
	List<ParseObject> convers, convers4, date;
	LinearLayout conv_text;
	EditText msje_new;
	ImageButton msje_send;
	String fechita;
	int nueva=0;
	String nomMio="yo", nomOtro="otro", idMia, idOtro;
	String idMsje, fechaMsje;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversacion);
		
		bar_send = (ProgressBar) findViewById(R.id.bar_send);
		foto = (ImageView) findViewById(R.id.lafoto);
		user = (TextView) findViewById(R.id.elnombre);
		mensaje = (TextView) findViewById(R.id.elmensaje);
		fecha = (TextView) findViewById(R.id.lafecha);
		conv_text = (LinearLayout) findViewById(R.id.conv_text);
		msje_new = (EditText) findViewById(R.id.msje_new);
		msje_send = (ImageButton) findViewById(R.id.msje_send);
		
		msje_send.setOnClickListener(listener);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Intent in = getIntent();		
		nomOtro = in.getStringExtra("nomOtro");
		user.setText(nomOtro);
		fechita = in.getStringExtra("fecha");		
		fo = (Bitmap) in.getParcelableExtra("fotOtro");
		idConv = in.getStringExtra("idConv");
		idOtro = in.getStringExtra("idOtros");
		
		if(fechita.matches("hoy") && idConv.matches("new")){
			nueva=1;	//Se enciende modo de nueva conversación
		} else{
			fecha.setText(in.getStringExtra("fecha"));
		}
		
		BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true; 
	    
        setImage(foto, fo);	
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        idMia = user.getObjectId();
        
        nomMio = user.getString("NombreCompleto");        
		        	
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensaje");
        query.whereEqualTo("id_conversacion", idConv);
		query.orderByAscending("_created_at");

		try {
			convers = query.find();
		} catch (ParseException e) {}   	
        
        if(convers.size() != 0){
        	ParseObject conv = null;
			for(int i=0 ; i<convers.size() ; i++){	
				conv = convers.get(i);		
				idMsje = conv.getObjectId();
				fechaMsje = conv.getUpdatedAt().toLocaleString();
				
				final TextView tv=new TextView(this);
				TextView nom=new TextView(this);
				final TextView fe = new TextView(this);
				fe.setText(fechaMsje);
				fe.setTextSize(10);
				fe.setTextColor(Color.GRAY);
				tv.setText(conv.getString("Mensaje"));
				nom.setTextSize(18);
				tv.setTextSize(18);
				nom.setPadding(0, 10, 0, 0);	
				fe.setPadding(0, 0, 0, 10);
				nom.setTypeface(null, Typeface.BOLD);
				
				if(conv.getString("id_envia").matches(user.getObjectId())){	
					nom.setText(nomMio+":");
					nom.setGravity(Gravity.RIGHT);
					tv.setGravity(Gravity.RIGHT);
					fe.setGravity(Gravity.RIGHT);
				} else{
					nom.setText(nomOtro+":");
				}
				
				conv_text.addView(nom);
				conv_text.addView(tv);		
				conv_text.addView(fe);
				
				tv.setOnLongClickListener(new OnLongClickListener() {
			        @Override
			        public boolean onLongClick(View vi) {
			            // TODO Auto-generated method stub
			        	Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			        	h.vibrate(25);
			        	
			        	AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_conversacion.this);  
		    	        dialog.setTitle("Mensaje");		
		    	        dialog.setIcon(R.drawable.ic_launcher);	
		    	        
		    	        String[] opcionesMenu;
		    	        opcionesMenu = new String[] {"Copiar texto", "Reenviar", "Eliminar"};		    	        
		    	        
		    	        View v = getLayoutInflater().inflate( R.layout.dialog_mensaje, null );
		    	        ListView list = (ListView) v.findViewById(R.id.dialog_list);
		    	        list.setAdapter(new ArrayAdapter<String>(
		    	                getSupportActionBar().getThemedContext(),
		    	            android.R.layout.simple_list_item_activated_1, opcionesMenu));
		    			
		    	        dialog.setView(v);
		    	        list.setOnItemClickListener(new OnItemClickListener() {
		    	            @Override
		    	            public void onItemClick(AdapterView<?> parent, View view,
		    	                    int position, long id) {
		    	     
		    	                switch (position) {
		    	                    case 0: //copiar texto
		    	                    	ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
		    	                    	ClipData clip = ClipData.newPlainText("label", tv.getText().toString());
		    	                    	clipboard.setPrimaryClip(clip);
		    	                    	
		    	                    	Toast.makeText( getApplicationContext(),"Copiado al portapapeles",Toast.LENGTH_SHORT ).show();
		    	                    	break;
		    	                    case 1: //reenviar
		    	                    	Toast.makeText( getApplicationContext(),"reenviar",Toast.LENGTH_SHORT ).show();
		    	                    	break;
		    	                    case 2: //eliminar
		    	                    	
		    	                    	AlertDialog.Builder dialog2 = new AlertDialog.Builder(Activity_conversacion.this);  
		    			    	        dialog2.setTitle("¿Eliminar mensaje?");		
		    			    	        dialog2.setIcon(R.drawable.ic_launcher);	
		    			    	        
		    			    	        View v = getLayoutInflater().inflate( R.layout.dialog, null );
		    			    	        TextView text = (TextView) v.findViewById(R.id.dialog_text);
		    			    	        text.setText("Una vez eliminado el mensaje, no se puede volver a restaurar.");
		    			    	        dialog2.setView(v);		    			    	        
		    			    	        dialog2.setNegativeButton("Cancelar", null);  
		    			    	        dialog2.setPositiveButton("Eliminar mensaje", new DialogInterface.OnClickListener() {  
		    			    	            public void onClick(DialogInterface dialogo1, int id) {
		    			    	            	bar_send.setVisibility(View.VISIBLE);
		    			    	            	Toast.makeText( getApplicationContext(),"Eliminando mensaje...",Toast.LENGTH_SHORT ).show();
		    			    	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensaje");
		    			    	        		query.whereEqualTo("objectId", idMsje);
		    			    	        		
		    			    	        		List<ParseObject> obj = null;
		    			    	        		try {
		    			    	        			obj = query.find();
		    			    	        		} catch (ParseException e) {
		    			    	        		}
		    			    	        		if(obj.size()!=0){
		    			    	        			obj.get(0).deleteInBackground(new DeleteCallback() {
		    			    	        				public void done(ParseException e) {
		    			    	        				    if (e == null) {	
		    			    	        				    	bar_send.setVisibility(View.INVISIBLE);
		    			    	        				    	Toast.makeText( getApplicationContext(),"Mensaje eliminado correctamente!",Toast.LENGTH_SHORT ).show();
		    			    	        				    	Intent intent = getIntent();
		    			    	        				    	finish();
		    			    	        				    	startActivity(intent);
		    			    	        				    } else{
		    			    	        				    	Toast.makeText(getApplicationContext(), "Error al borrar el mensaje, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
		    			    	        				    }
		    			    	        				}
		    			    	        		    });
		    			    	        		}                    	
		    			    	            }  
		    			    	        });  
		    			    	        
		    			    	        dialog2.show();		    	                    	
		    	                    	break;
		    	                }
		    	            }
		    	        });	    	        
		    	        
		    	        dialog.show();
			        	return false;
			        }
			    });
			}			
        }
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.msje_send) {				
				bar_send.setVisibility(View.VISIBLE);
				if(nueva==1){
					nuevaConversa();
					
					ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Conversacion");
			        query3.whereEqualTo("id_user1", idMia);
			        query3.whereEqualTo("id_user2", idOtro);				

					try {
						convers = query3.find();
					} catch (ParseException e) {}   	
			        
			        if(convers.size() != 0){
			        	ParseObject conv = null;
						conv = convers.get(0);
						idConv = conv.getObjectId();
						fecha.setText(conv.getUpdatedAt().toLocaleString());
					} else{
						ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>("Conversacion");
				        query4.whereEqualTo("id_user1", idOtro);
				        query4.whereEqualTo("id_user2", idMia);
				        
				        try {
							convers = query3.find();
						} catch (ParseException e) {}   	
				        
				        if(convers.size() != 0){
				        	ParseObject conv = null;
							conv = convers.get(0);
							idConv = conv.getObjectId();
							fecha.setText(conv.getUpdatedAt().toLocaleString());
						} 
					}
				}
				
				ParseObject msje = new ParseObject("Mensaje");
				msje.put("Mensaje", msje_new.getText().toString());
				msje.put("id_conversacion", idConv);
				msje.put("id_envia", idMia);
				msje.put("id_recibe", idOtro);
				
				msje.saveInBackground(new SaveCallback() {
					public void done(ParseException e) {
					    if (e == null) {	
					    	bar_send.setVisibility(View.INVISIBLE);
					    	TextView tv2=new TextView(Activity_conversacion.this);
							TextView nom2=new TextView(Activity_conversacion.this);
							tv2.setText(msje_new.getText().toString());
							nom2.setTextSize(18);
							tv2.setTextSize(18);
							nom2.setPadding(0, 10, 0, 0);
							tv2.setPadding(0, 0, 0, 10);				
							nom2.setTypeface(null, Typeface.BOLD);		
							nom2.setText(nomMio+":");
							nom2.setGravity(Gravity.RIGHT);
							tv2.setGravity(Gravity.RIGHT);		
							conv_text.addView(nom2);
							conv_text.addView(tv2);
							msje_new.setText("");
							Toast.makeText( getApplicationContext(),"Mensaje enviado con éxito!",Toast.LENGTH_SHORT ).show();
					    } else{
					    	Toast.makeText(getApplicationContext(), "Error al enviar el mensaje, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
					    }
					}
			    });			
			}			
		}
	};
	
	public void nuevaConversa(){
		ParseObject conv = new ParseObject("Conversacion");
		conv.put("id_user1", idMia);
		conv.put("id_user2", idOtro);		
		conv.saveInBackground(new SaveCallback() {
			public void done(ParseException e) {
			    if (e == null) {
					Toast.makeText( getApplicationContext(),"Nueva conversación creada!",Toast.LENGTH_SHORT ).show();				
			    } else{
			    	Toast.makeText(getApplicationContext(), "Error al crear la conversación, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
			    }
			}
	    });	
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
    	            	bar_send.setVisibility(View.VISIBLE);
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
    	        				    	bar_send.setVisibility(View.INVISIBLE);
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