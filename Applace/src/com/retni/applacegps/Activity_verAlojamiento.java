package com.retni.applacegps;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.parse.DeleteCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_verAlojamiento extends ActionBarActivity{
	
	RatingBar ratingBar;
	TextView txtRatingValue;
	Button btnSubmit;
	ViewPager viewPager;
    PagerAdapter adapter;
    String id_aloj = "jaja", name_receptor, mail_receptor, vis_direccion;
    TextView vis_tit, vis_estado, vis_rating_count, vis_precio, vis_descrip;
    LinearLayout vis_tv, vis_wifi, vis_telefono, vis_piscina, vis_calefaccion, vis_cocina, vis_estacionamiento;
    LinearLayout vis_lavadora, vis_papel, vis_quincho, vis_aireacondicionado, vis_desayuno, vis_perro;
    RatingBar vis_rating;
    List<Bitmap> fotitos = new ArrayList<Bitmap>();
    int[] fotos;
    ImageView vis_img1, vis_img2, vis_temp;
    TextView vis_comentario1, vis_comentario2, vis_date1, vis_date2, vis_emisor1, vis_emisor2, vis_verlist, vis_verDisp;
    FrameLayout vis_coment1, vis_coment2, vis_comentar;
    String texto = "";
    ProgressBar delete_bar;
    ParseObject caract, convers;
    EditText vis_guardar_comentario;
    FrameLayout vis_rankear;
    RatingBar vis_calificar;
    Button vis_enviar, vis_dueno, vis_multiRuta;
    String id_user_dueno;
    Bitmap foto_dueno;
    String id_user_emisor;
    String name_emisor;
    ParseFile foto_emisor;
    TransparentProgressDialog pd;
    Button vis_ruta, vis_mensaje;
    List<ParseObject> emisor = null;
    List<ParseObject> pushi = null;
    List<Boolean> services = new ArrayList<Boolean>();
    String idConv="new", fechaConv="hoy";
    String titulo;
    int not=0;
    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    ArrayList<String> id_pic = new ArrayList<String>();
    LinearLayout rep_lay;
    TextView rep_link;
    TextView tipo_text, cap_text, hab_text, cam_text, wc_text;
    
    private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;
	private String name = "";
	byte[] data1;
	Bitmap bito,pic;
	int tamano=0;
	String comentario;
	EditText com_new;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_veralojamiento);
		
		name = Environment.getExternalStorageDirectory() + "/test.jpg";
	
		//fotos = new int[] { R.drawable.subir};
		
		rep_lay = (LinearLayout) findViewById(R.id.rep_lay);
		rep_link = (TextView) findViewById(R.id.rep_link);
		
		tipo_text = (TextView) findViewById(R.id.tipo_text);
		cap_text = (TextView) findViewById(R.id.cap_text);
		hab_text = (TextView) findViewById(R.id.hab_text);
		cam_text = (TextView) findViewById(R.id.cam_text);
		wc_text = (TextView) findViewById(R.id.wc_text);
		
		delete_bar = (ProgressBar) findViewById(R.id.delete_bar);
		vis_verlist = (TextView) findViewById(R.id.vis_verlist);
		vis_ruta = (Button) findViewById(R.id.vis_ruta);
		vis_mensaje = (Button) findViewById(R.id.vis_mensaje);
		vis_dueno = (Button) findViewById(R.id.vis_dueno);
		vis_verDisp = (TextView) findViewById(R.id.vis_verDisp);
		vis_multiRuta = (Button) findViewById(R.id.vis_multiRuta);
		vis_dueno.setOnClickListener(listener);
		vis_verDisp.setOnClickListener(listener);
		vis_ruta.setOnClickListener(listener);
		vis_mensaje.setOnClickListener(listener);
		vis_multiRuta.setOnClickListener(listener);
		rep_link.setOnClickListener(listener);
		
		//comentarios
		vis_comentario1 = (TextView) findViewById(R.id.vis_comentario1);
		vis_comentario2 = (TextView) findViewById(R.id.vis_comentario2);
		vis_date1 = (TextView) findViewById(R.id.vis_date1);
		vis_date2 = (TextView) findViewById(R.id.vis_date2);
		vis_emisor1 = (TextView) findViewById(R.id.vis_emisor1);
		vis_emisor2 = (TextView) findViewById(R.id.vis_emisor2);
		vis_coment1 = (FrameLayout) findViewById(R.id.vis_coment1);
		vis_coment2 = (FrameLayout) findViewById(R.id.vis_coment2);
		vis_img1 = (ImageView) findViewById(R.id.vis_img1);
		vis_img2 = (ImageView) findViewById(R.id.vis_img2);
		vis_comentar = (FrameLayout) findViewById(R.id.vis_rankear);
		
		//calificar
		vis_enviar = (Button) findViewById(R.id.vis_enviar);
		vis_guardar_comentario = (EditText) findViewById(R.id.vis_guardar_comentario);
		vis_calificar = (RatingBar) findViewById(R.id.vis_calificar);
		//vis_rankear = (FrameLayout) findViewById(R.id.vis_rankear);
		
		vis_tit = (TextView) findViewById(R.id.vis_tit);
		vis_estado = (TextView) findViewById(R.id.vis_estado);
		vis_rating = (RatingBar) findViewById(R.id.vis_rating);
		vis_rating_count = (TextView) findViewById(R.id.vis_count_rating);
		vis_precio = (TextView) findViewById(R.id.vis_precio);
		vis_descrip = (TextView) findViewById(R.id.vis_descrip);
		
		//iconos
		vis_tv = (LinearLayout) findViewById(R.id.vis_tv);
		vis_wifi = (LinearLayout) findViewById(R.id.vis_wifi);
		vis_telefono = (LinearLayout) findViewById(R.id.vis_telefono);
		vis_piscina = (LinearLayout) findViewById(R.id.vis_piscina);
		vis_calefaccion = (LinearLayout) findViewById(R.id.vis_calefaccion);
		vis_cocina = (LinearLayout) findViewById(R.id.vis_cocina);
		vis_estacionamiento = (LinearLayout) findViewById(R.id.vis_estacionamiento);
		vis_lavadora = (LinearLayout) findViewById(R.id.vis_lavadora);
		vis_papel = (LinearLayout) findViewById(R.id.vis_papel);
		vis_quincho = (LinearLayout) findViewById(R.id.vis_quincho);
		vis_aireacondicionado = (LinearLayout) findViewById(R.id.vis_aireacondicionado);
		vis_desayuno = (LinearLayout) findViewById(R.id.vis_desayuno);
		vis_perro = (LinearLayout) findViewById(R.id.vis_perro);		
		pd = new TransparentProgressDialog(this, R.drawable.prog_applace);	
		
		Bundle bundle = getIntent().getExtras();
		id_aloj=bundle.getString("idAloj");
		
        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        
        id_user_emisor = user.getObjectId();
        name_emisor = user.getString("NombreCompleto");
        foto_emisor = user.getParseFile("Foto");
        
        //Query que descarga toda la información correspondiente al alojamiento******************************************
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
		query.whereEqualTo("objectId", id_aloj);
		
		List<ParseObject> car = null;
		try {
			car = query.find();
		} catch (ParseException e) {

		}
		
		if(car.size()!=0){
			caract = car.get(0);
			titulo = caract.getString("titulo");
			vis_tit.setText(caract.getString("titulo"));
			if(caract.getBoolean("estado")==true){
				vis_estado.setText("Disponible");
			} else if(caract.getBoolean("estado")==false){
				vis_estado.setText("No disponible");
			}
			
			id_user_dueno = caract.getString("User");
			
			//Query que busca al dueño del arriendo*********************************************************************
			ParseQuery query7 = ParseUser.getQuery();
			query7.whereEqualTo("objectId", id_user_dueno);
			
			List<ParseObject> cari = null;
			try {
				cari = query7.find();
			} catch (ParseException e) {

			}
			if(cari.size()!=0){
				ParseObject iduser = cari.get(0);
				//id_user_receptor = iduser.getObjectId();
				name_receptor = iduser.getString("NombreCompleto");
				mail_receptor = iduser.getString("email");
				
				ParseFile img = iduser.getParseFile("Foto");	
    			if(img != null){
    			    img.getDataInBackground(new GetDataCallback() {
    			    	Bitmap bmp = null;
    			        public void done(byte[] data, com.parse.ParseException e) {
    			            if (e == null){
    			                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);			                
    			                foto_dueno = bmp;
    			            }
    			            else{
    			            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    			            }
    			        }
    			    }); 
    			} else{
    				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    			}
				//Toast.makeText(getApplicationContext(),name_receptor, Toast.LENGTH_LONG).show();
			}
			
			tipo_text.setText(caract.getString("tipo_aloj"));
			cap_text.setText(caract.getInt("capacidad")+" personas");
			hab_text.setText(caract.getInt("numeroPiezas")+ " piezas");
			cam_text.setText(caract.getInt("numeroCamas")+ " camas");
			wc_text.setText(caract.getInt("numeroBanos")+ " baños");
			
			vis_rating.setRating((float) caract.getDouble("calificacion"));
			vis_rating_count.setText(""+caract.getInt("count_calificacion"));
			vis_precio.setText("$"+caract.getInt("precio"));
			vis_descrip.setText(caract.getString("descripcion"));
			vis_direccion=caract.getString("dir_escrita");
			
			if(caract.getBoolean("articulos_higiene")==false)
				vis_papel.setVisibility(View.GONE);
			if(caract.getBoolean("aire_acond")==false)
				vis_aireacondicionado.setVisibility(View.GONE);
			if(caract.getBoolean("cocina")==false) 
				vis_cocina.setVisibility(View.GONE);
			if(caract.getBoolean("calefaccion")==false)
				vis_calefaccion.setVisibility(View.GONE);
			if(caract.getBoolean("desayuno")==false)
				vis_desayuno.setVisibility(View.GONE);
			if(caract.getBoolean("estacionamiento")==false)
				vis_estacionamiento.setVisibility(View.GONE);
			if(caract.getBoolean("lavadora")==false)
				vis_lavadora.setVisibility(View.GONE);
			if(caract.getBoolean("mascota")==false)
				vis_perro.setVisibility(View.GONE);
			if(caract.getBoolean("piscina")==false)
				vis_piscina.setVisibility(View.GONE);
			if(caract.getBoolean("quincho")==false)
				vis_quincho.setVisibility(View.GONE);
			if(caract.getBoolean("tv")==false) 
				vis_tv.setVisibility(View.GONE);
			if(caract.getBoolean("internet")==false)
				vis_wifi.setVisibility(View.GONE);
			if(caract.getBoolean("telefono")==false)
				vis_telefono.setVisibility(View.GONE);
		}
		
		load_pic();		
		mostrarComentarios();		
		verificarComentario();
		report();
	}
	
	public void report(){
		ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Viajes");
        
		query3.whereEqualTo("id_user", id_user_emisor);
		query3.whereEqualTo("id_alojamiento", id_aloj);
		List<ParseObject> cal = null;
		try {
			cal = query3.find();
		} catch (ParseException e) {

		}
		if(cal.size()==0){
			rep_lay.setVisibility(View.GONE);			
		}
	}
	
	public void load_pic(){
		new AsyncTask<Void, Void, Void>() {

            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                //list_bar.setVisibility(View.VISIBLE);        
                pd.show();
                adapter = new ViewPagerAdapter1(Activity_verAlojamiento.this, imgs, id_pic);
            }
            
            protected Void doInBackground(Void... params) {
            	
            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Fotos");
        		query.whereEqualTo("id_alojamiento", id_aloj);
        		query.orderByAscending("_created_at");
        		
        		List<ParseObject> fot = null;
        		try {
        			fot = query.find();
        		} catch (ParseException e) {

        		}
        		
        		if(fot.size()!=0){
        			tamano=fot.size();
        			ParseObject im = null;
        			ParseFile ic = null;
        			for(int h=0;h<fot.size();h++){
        				im = fot.get(h);
        				id_pic.add(im.getObjectId());
        				adapter.notifyDataSetChanged();
        				ic = im.getParseFile("foto");
        				if(ic != null){
    	    			    ic.getDataInBackground(new GetDataCallback() {
    	    			    	Bitmap bmp = null;
    	    			        public void done(byte[] data, com.parse.ParseException e) {
    	    			            if (e == null){
    	    			                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);		
    	    			                int outWidth;
    	    			                int outHeight;
    	    			                int inWidth = bmp.getWidth();
    	    			                int inHeight = bmp.getHeight();
    	    			                if(inWidth > inHeight){
    	    			                    outWidth = 500;
    	    			                    outHeight = (inHeight * 500) / inWidth; 
    	    			                } else {
    	    			                    outHeight = 500;
    	    			                    outWidth = (inWidth * 500) / inHeight; 
    	    			                }
    	    			                imgs.add(Bitmap.createScaledBitmap(bmp, outWidth, outHeight, false));
    	    			                adapter.notifyDataSetChanged();
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
        			
        			
        		} else{
        			if(id_user_dueno.matches(id_user_emisor)){
        				Drawable myDrawable = getResources().getDrawable(R.drawable.subir);
				    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
	        			imgs.add(fo);
	        			adapter.notifyDataSetChanged();
        			} else{
        				Drawable myDrawable = getResources().getDrawable(R.drawable.nohay);
				    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
	        			imgs.add(fo);
	        			adapter.notifyDataSetChanged();
        			}
        		}
        		
            	return null;
            }

            protected void onPostExecute(Void result) {
            	//list_bar.setVisibility(View.GONE); 
            	
            	if (pd.isShowing() ) {
        			pd.dismiss();
        		}
            	viewPager = (ViewPager) findViewById(R.id.pager_fotos);
    	        viewPager.setAdapter(adapter);
            }
        }.execute();
	}
	
	public class ViewPagerAdapter1 extends PagerAdapter {
	    Context context;
	    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
	    ArrayList<String> id_pic = new ArrayList<String>();
	    LayoutInflater inflater;
	 
	    public ViewPagerAdapter1(Context context, ArrayList<Bitmap> imgs, ArrayList<String> id_pic) {
	        this.context = context;
	        this.imgs = imgs;
	        this.id_pic = id_pic;
	    }
	 
	    @Override
	    public int getCount() {
	        return imgs.size();
	    }
	 
	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == ((RelativeLayout) object);
	    }
	 
	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	 
	        ImageView imgfotos;
	 
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View itemView = inflater.inflate(R.layout.viewpager_fotos, container,
	                false);
	 
	        imgfotos = (ImageView) itemView.findViewById(R.id.fotos2);
	        imgfotos.setImageBitmap(imgs.get(position));
	        itemView.setTag(position);	
	        ((ViewPager) container).addView(itemView);
	        
	        itemView.setOnClickListener(new OnClickListener(){        	
				public void onClick(View v) {
					Integer pos = (Integer) v.getTag();
					Vibrator h = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        	h.vibrate(25);
		        	//Toast.makeText(getApplicationContext(), "pos: " + pos, Toast.LENGTH_SHORT).show();
		        	
					Intent intent = new Intent(context,Activity_detalleFoto.class);
					intent.putExtra("idAloj", id_aloj);
					intent.putExtra("titulo", titulo);
					intent.putExtra("pos", pos);
					context.startActivity(intent);			
				}		
			});  
	        
	        itemView.setOnLongClickListener(new OnLongClickListener(){        	
				public boolean onLongClick(View v) {
					final Integer pos = (Integer) v.getTag();
					Vibrator h = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        	h.vibrate(25);
		        	//Toast.makeText(getApplicationContext(), "pos: " + pos, Toast.LENGTH_SHORT).show();
		        	AlertDialog.Builder dialog;
		        	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
	    	        dialog.setTitle("Eliminar imágen");		
	    	        dialog.setIcon(R.drawable.ic_launcher);	
	    	        
	    	        View vi = getLayoutInflater().inflate( R.layout.dialog, null );
	    	        TextView text = (TextView) vi.findViewById(R.id.dialog_text);
	    	        text.setText("¿Desea borrar de forma permanente esta imágen?");
	    			
	    	        dialog.setView(vi);
	    	        dialog.setNegativeButton("Cancelar", null);  
	    	        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {  
	    	            public void onClick(DialogInterface dialogo1, int id) {
	    	            	pd.show();
	    	            	Toast.makeText( getApplicationContext(),"Eliminando imágen...",Toast.LENGTH_SHORT ).show();
	    	            	//Acá se elimina la imágen de la base de datos
	    	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Fotos");
	    	        		query.whereEqualTo("objectId", id_pic.get(pos));
	    	        		
	    	        		List<ParseObject> obj = null;
	    	        		try {
	    	        			obj = query.find();
	    	        		} catch (ParseException e) {
	    	        		}
	    	        		if(obj.size()!=0){
	    	        			obj.get(0).deleteInBackground(new DeleteCallback() {
	    	        				public void done(ParseException e) {
	    	        				    if (e == null) {	
	    	        				    	pd.dismiss();
	    	        				    	Toast.makeText( getApplicationContext(),"¡Imágen eliminada correctamente!",Toast.LENGTH_SHORT ).show();
	    	        				    	finish();    
	    	        				    	startActivity(getIntent());
	    	        				    } else{
	    	        				    	Toast.makeText(getApplicationContext(), "Error al borrar la imágen, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
	    	        				    }
	    	        				}
	    	        		    });
	    	        		}                    	
	    	            }  
	    	        });  
	    	        
	    	        dialog.show();
					
					return false;
				}	
			});   
	 
	        return itemView;
	    }
	 
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        ((ViewPager) container).removeView((RelativeLayout) object);	 
	    }
	}

	
	public void mostrarComentarios(){
		//Query que carga los comentarios del alojamiento*****************************************************************
		ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Comentarios");
		query2.whereEqualTo("id_alojamiento", id_aloj);
		query2.orderByDescending("_created_at");
		
		List<ParseObject> com = null;
		try {
			com = query2.find();
		} catch (ParseException e) {

		}
		
		ParseObject comentario = null;		
		if(com.size()==0){
			//Si el alojamiento no tiene comentarios
			vis_coment1.setVisibility(View.GONE);
			vis_coment2.setVisibility(View.GONE);
			vis_verlist.setText("Este alojamiento no tiene comentarios");
			//vis_verlist.setVisibility(View.GONE);
		} else{
			for(int a=0; a < com.size(); a++){
				comentario = com.get(a);
				if(com.size()<3){
					if(a==0){						
						//Se muestra solo 1 comentario en la actividad principal
						vis_comentario1.setText(comentario.getString("comentario"));
						vis_date1.setText(comentario.getUpdatedAt().toGMTString());
						vis_emisor1.setText(comentario.getString("nombre_user_emisor"));
						
						ParseQuery query9 = ParseUser.getQuery();
        				query9.whereEqualTo("objectId", comentario.getString("id_user_emisor"));
        				
        				try {
                			emisor = query9.find();
                		} catch (ParseException e) {

                		}	
        				if(emisor.size()!=0){
        					ParseObject emi = null;
        					emi=emisor.get(0);
        					
        					ParseFile img = emi.getParseFile("Foto");
            				if(img != null){
            				    img.getDataInBackground(new GetDataCallback() {
            				    	Bitmap bmp = null;
            				        public void done(byte[] data, com.parse.ParseException e) {
            				            if (e == null){
            				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
            				                setImage(vis_img1, bmp);	
            				            }
            				            else{
            				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            				            }
            				        }
            				    }); 
            				} else{
            					Drawable myDrawable = getResources().getDrawable(R.drawable.img_defecto);
    					    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
    					    	
    					    	setImage(vis_img1, fo);
            				}
        				}
					    
						vis_coment2.setVisibility(View.GONE);
						vis_verlist.setVisibility(View.GONE);
					}
					else if(a==1){
						//Toast.makeText(getApplicationContext(), "2 comentarios", Toast.LENGTH_SHORT).show();
						//Se muestra solo 1 comentario en la actividad principal
						vis_coment2.setVisibility(View.VISIBLE);
						vis_comentario2.setText(comentario.getString("comentario"));
						vis_date2.setText(comentario.getUpdatedAt().toGMTString());
						vis_emisor2.setText(comentario.getString("nombre_user_emisor"));
						
						ParseQuery query9 = ParseUser.getQuery();
        				query9.whereEqualTo("objectId", comentario.getString("id_user_emisor"));
        				
        				try {
                			emisor = query9.find();
                		} catch (ParseException e) {

                		}	
        				if(emisor.size()!=0){
        					ParseObject emi = null;
        					emi=emisor.get(0);
        					
        					ParseFile img = emi.getParseFile("Foto");
            				if(img != null){
            				    img.getDataInBackground(new GetDataCallback() {
            				    	Bitmap bmp = null;
            				        public void done(byte[] data, com.parse.ParseException e) {
            				            if (e == null){
            				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
            				                setImage(vis_img2, bmp);	
            				            }
            				            else{
            				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            				            }
            				        }
            				    }); 
            				} else{
            					Drawable myDrawable = getResources().getDrawable(R.drawable.img_defecto);
    					    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
    					    	
    					    	setImage(vis_img1, fo);
            				}
        				}
					}
				}				
				else if(com.size()>2){
					//Se muestran los 2 comentarios y otra actividad con todos los comentarios que tenga el alojamiento
					if(a==0){
						vis_comentario1.setText(comentario.getString("comentario"));
						vis_date1.setText(comentario.getUpdatedAt().toGMTString());
						vis_emisor1.setText(comentario.getString("nombre_user_emisor"));
						
						ParseQuery query9 = ParseUser.getQuery();
        				query9.whereEqualTo("objectId", comentario.getString("id_user_emisor"));
        				
        				try {
                			emisor = query9.find();
                		} catch (ParseException e) {

                		}	
        				if(emisor.size()!=0){
        					ParseObject emi = null;
        					emi=emisor.get(0);
        					
        					ParseFile img = emi.getParseFile("Foto");
            				if(img != null){
            				    img.getDataInBackground(new GetDataCallback() {
            				    	Bitmap bmp = null;
            				        public void done(byte[] data, com.parse.ParseException e) {
            				            if (e == null){
            				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
            				                setImage(vis_img1, bmp);	
            				            }
            				            else{
            				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            				            }
            				        }
            				    }); 
            				} else{
            					Drawable myDrawable = getResources().getDrawable(R.drawable.img_defecto);
    					    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
    					    	
    					    	setImage(vis_img1, fo);
            				}
        				}
					}
					else if(a==1){						
						vis_comentario2.setText(comentario.getString("comentario"));
						vis_date2.setText(comentario.getUpdatedAt().toGMTString());
						vis_emisor2.setText(comentario.getString("nombre_user_emisor"));

						ParseQuery query9 = ParseUser.getQuery();
        				query9.whereEqualTo("objectId", comentario.getString("id_user_emisor"));
        				
        				try {
                			emisor = query9.find();
                		} catch (ParseException e) {

                		}	
        				if(emisor.size()!=0){
        					ParseObject emi = null;
        					emi=emisor.get(0);
        					
        					ParseFile img = emi.getParseFile("Foto");
            				if(img != null){
            				    img.getDataInBackground(new GetDataCallback() {
            				    	Bitmap bmp = null;
            				        public void done(byte[] data, com.parse.ParseException e) {
            				            if (e == null){
            				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
            				                setImage(vis_img2, bmp);	
            				            }
            				            else{
            				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            				            }
            				        }
            				    }); 
            				} else{
            					Drawable myDrawable = getResources().getDrawable(R.drawable.img_defecto);
    					    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
    					    	
    					    	setImage(vis_img1, fo);
            				}
        				}
					}					
				}						
			}
			
			if(vis_verlist.getVisibility()==View.VISIBLE){
				if(!vis_verlist.getText().toString().matches("Este alojamiento no tiene comentarios"))
					vis_verlist.setOnClickListener(listener);
			}
		}
	}
	
	public void verificarComentario(){
		//Query que obtiene los datos de la persona que comenta el alojamiento*******************************************
		
		new AsyncTask<Void, Void, Void>() {
			List<ParseObject> cal = null;
			
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                //list_bar.setVisibility(View.VISIBLE);        
                pd.show();
            }
            
            protected Void doInBackground(Void... params) {
            	
            	ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Comentarios");
        		
                
                if(id_user_dueno.matches(id_user_emisor)){
                	vis_comentar.setVisibility(View.GONE);
        			vis_ruta.setVisibility(View.GONE);
        			vis_mensaje.setVisibility(View.GONE);
        			vis_dueno.setVisibility(View.GONE);
        			vis_multiRuta.setVisibility(View.GONE);
        		}
                
        		query3.whereEqualTo("id_user_emisor", id_user_emisor);
        		query3.whereEqualTo("id_alojamiento", id_aloj);
        		
        		
        		try {
        			cal = query3.find();
        		} catch (ParseException e) {

        		}
            	
            	return null;
            }

            protected void onPostExecute(Void result) {
            	//list_bar.setVisibility(View.GONE); 
            	
            	if (pd.isShowing() ) {
        			pd.dismiss();
        		}
            	if(cal.size()==0){ //Si no ha comentado nunca ese alojamiento
        			vis_enviar.setOnClickListener(listener);
        	
        		}
        		else{ //si comentó ese alojamiento entonces no le muestra la opción
        			vis_comentar.setVisibility(View.GONE);
        		}	            	
            }
        }.execute();        
	}
	
	public void comentar(){ //Comentar un alojamiento*********************************************************************
		
		if(vis_guardar_comentario.getText().toString() == null || vis_calificar.getRating()==0){
			Toast.makeText( getApplicationContext(),"Por favor completar todos los datos!",Toast.LENGTH_SHORT ).show();
		}
		else{
			pd.show();
			ParseObject calif = new ParseObject("Comentarios");
			calif.put("comentario", vis_guardar_comentario.getText().toString());
			calif.put("calificacion", vis_calificar.getRating());
			calif.put("id_alojamiento", id_aloj);
			calif.put("id_user_emisor", id_user_emisor);
			calif.put("foto_user_emisor", foto_emisor);
			calif.put("nombre_user_emisor", name_emisor);
			
			calif.saveInBackground(new SaveCallback() {
				public void done(ParseException e) {
				    if (e == null) {			
				    	pd.dismiss();
						ranking();
						Toast.makeText( getApplicationContext(),"Calificación ingresada con éxito!",Toast.LENGTH_SHORT ).show();
						ParseObject notif = new ParseObject("Notificacion");
						notif.put("notificacion", "Su alojamiento titulado '"+titulo+ "', ha sido comentado y calificado!");
						notif.put("id_user", id_user_dueno);
						notif.put("id_alojamiento", id_aloj);
						
						notif.saveInBackground(new SaveCallback() {
							public void done(ParseException e) {
							    if (e == null) {			
									//Toast.makeText( getApplicationContext(),"Notificación enviada con éxito!",Toast.LENGTH_SHORT ).show();
							    } else{
							    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
							    }
							}
					    });
						Intent intent = new Intent(Activity_verAlojamiento.this, Logueado.class);
						startActivity(intent);
				    } else{
				    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
				    }
				}
		    });
		}
	}

	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.vis_verlist) {
				//Muestra una lista con los demás comentarios************************************************************
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
				Intent intent = new Intent(Activity_verAlojamiento.this, Activity_listaComentarios.class);
				intent.putExtra("id_aloj", id_aloj); //se envía el id del alojamiento
				startActivity(intent);
			}
			else if(id == R.id.vis_enviar){
				comentar();				
			}
			else if(id == R.id.vis_ruta){
				//Se muestra la ruta para llegar a este alojamiento
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
				ParseObject notif = new ParseObject("Notificacion");
				notif.put("notificacion", "Es probable que un usuario de Applace vaya a visitar su alojamiento titulado '"+titulo+ "'.");
				notif.put("id_user", id_user_dueno);
				notif.put("id_alojamiento", id_aloj);
				
				notif.saveInBackground(new SaveCallback() {
					public void done(ParseException e) {
					    if (e == null) {	
							//Toast.makeText( getApplicationContext(),"Notificación enviada con éxito!",Toast.LENGTH_SHORT ).show();
					    } else{
					    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
					    }
					}
			    });
				
				ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Viajes");
        		                
        		query3.whereEqualTo("id_user", id_user_emisor);
        		query3.whereEqualTo("id_alojamiento", id_aloj);
        		List<ParseObject> cal = null;
        		try {
        			cal = query3.find();
        		} catch (ParseException e) {

        		}
        		if(cal.size()==0){
					ParseObject viaje = new ParseObject("Viajes");
					viaje.put("id_user", id_user_emisor);
					viaje.put("id_alojamiento", id_aloj);
					
					viaje.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {
						    if (e == null) {	
								//Toast.makeText( getApplicationContext(),"Notificación enviada con éxito!",Toast.LENGTH_SHORT ).show();
						    } else{
						    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
						    }
						}
				    });
        		}
				
				FragmentManager fm = Activity_verAlojamiento.this.getSupportFragmentManager();
		        FragmentTransaction ft = fm.beginTransaction();
		        
		        Fragment fragment = new Fragment_googlemaps_ruta();
		        String tag = fragment.getTag();
		        Bundle j = new Bundle();
		        j.putString("direccion", vis_direccion);
		        fragment.setArguments(j);
		        ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		        ft.replace(R.id.vis_frame_content, fragment,tag);
		        ft.addToBackStack(tag);
		        ft.commit(); 
			}
			else if(id == R.id.vis_mensaje){
				//Se muestran opciones de enviar un mensaje al propietario
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
	        	
	        	ParseQuery<ParseObject> query6 = new ParseQuery<ParseObject>("Conversacion");
		        query6.whereEqualTo("id_user1", id_user_emisor);
		        query6.whereEqualTo("id_user2", id_user_dueno);	
		        
		        List<ParseObject> con = null;
				try {
					con = query6.find();
				} catch (ParseException e) {}   	
		        
		        if(con.size() != 0){
		        	convers = null;
					convers = con.get(0);
					idConv = convers.getObjectId();
					fechaConv = convers.getUpdatedAt().toLocaleString();
				} else{
					ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>("Conversacion");
			        query4.whereEqualTo("id_user1", id_user_dueno);
			        query4.whereEqualTo("id_user2", id_user_emisor);
			        
			        try {
						con = query4.find();
					} catch (ParseException e) {}   	
			        
			        if(con.size() != 0){
			        	convers = null;
			        	convers = con.get(0);
						idConv = convers.getObjectId();
						fechaConv = convers.getUpdatedAt().toLocaleString();
			        } else{
			        	idConv = "new";
			        	fechaConv = "hoy";
			        }
				}
		        
				Intent intent = new Intent(Activity_verAlojamiento.this, Activity_conversacion.class);
				intent.putExtra("idConv", idConv);
				intent.putExtra("nomOtro", name_receptor);
				intent.putExtra("fecha", fechaConv);
				intent.putExtra("idOtros", id_user_dueno);
				intent.putExtra("fotOtro", Bitmap.createScaledBitmap(foto_dueno, 200, 200, false));
				startActivity(intent);	
			}
			else if(id == R.id.vis_verDisp){
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
				Intent intent = new Intent(Activity_verAlojamiento.this, Activity_disp.class);
				intent.putExtra("idAloj", id_aloj);
				startActivity(intent);	
			}
			else if(id == R.id.vis_dueno){
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
				Intent intent = new Intent(Activity_verAlojamiento.this, Activity_perfil.class);
				intent.putExtra("idDueno", id_user_dueno);
				startActivity(intent);	
			}
			else if(id == R.id.vis_multiRuta){
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
				Fragment_rutaMultiple.directions.add(vis_direccion);
				Activity_verAlojamiento.this.finish();
				Toast.makeText(getApplicationContext(), "Alojamiento añadido para la ruta múltiple.", Toast.LENGTH_SHORT).show();
			}
			else if(id == R.id.rep_link){
				Vibrator h = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	        	h.vibrate(25);
	        	pd.show();
	        	ParseObject notif = new ParseObject("Notificacion");
				notif.put("notificacion", "Su alojamiento titulado '"+titulo+"', ha sido reportado por disponibilidad incorrecta. Le recordamos que debe actualizar el estado de su propiedad constantemente.");
				notif.put("id_user", id_user_dueno);
				notif.put("id_alojamiento", id_aloj);
				
				notif.saveInBackground(new SaveCallback() {
					public void done(ParseException e) {
					    if (e == null) {	
					    	pd.dismiss();					    	
							Toast.makeText( getApplicationContext(),"¡Se ha reportado la disponibilidad de este alojamiento exitosamente!",Toast.LENGTH_SHORT ).show();
					    } else{
					    	Toast.makeText(getApplicationContext(), "Error al reportar el alojamiento.", Toast.LENGTH_SHORT).show();
					    }
					}
			    });
			}
		}
	};
	
private class TransparentProgressDialog extends Dialog {
		
		private ImageView iv;
			
		public TransparentProgressDialog(Context context, int resourceIdOfImage) {
			super(context, R.style.TransparentProgressDialog);
	        	WindowManager.LayoutParams wlmp = getWindow().getAttributes();
	        	wlmp.gravity = Gravity.CENTER_HORIZONTAL;
	        	getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			iv = new ImageView(context);
			iv.setImageResource(resourceIdOfImage);
			layout.addView(iv, params);
			addContentView(layout, params);
		}
			
		@Override
		public void show() {
			super.show();
			RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(Animation.INFINITE);
			anim.setDuration(3000);					
			iv.setAnimation(anim);
			iv.startAnimation(anim);
		}
	}
	
	public void loadBitmap(Bitmap b) {
		vis_temp.setImageBitmap(circle(Bitmap.createScaledBitmap(b, 150, 150, false)));
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
	
	public int favito(){
		int val=0;
		ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Favoritos");        
		query3.whereEqualTo("id_user", id_user_emisor);
		query3.whereEqualTo("id_alojamiento", id_aloj);
		List<ParseObject> cal = null;
		try {
			cal = query3.find();
		} catch (ParseException e) {}
		
		if(cal.size()==0){
			val=1;
		}
		return val;
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
		menu.findItem(R.id.action_new).setVisible(false);
		
		if(id_user_dueno.matches(id_user_emisor)){
			menu.findItem(R.id.action_delete).setVisible(true);
			menu.findItem(R.id.action_camara).setVisible(true);
			menu.findItem(R.id.action_fav).setVisible(false);
			menu.findItem(R.id.action_fav_no).setVisible(false);
		} else{			
			menu.findItem(R.id.action_camara).setVisible(false);	
			menu.findItem(R.id.action_delete).setVisible(false);			
			int i = favito();
			if(i==1){
				menu.findItem(R.id.action_fav_no).setVisible(true);
				menu.findItem(R.id.action_fav).setVisible(false);
			} else{
				menu.findItem(R.id.action_fav).setVisible(true);
				menu.findItem(R.id.action_fav_no).setVisible(false);
			}
		}
		return true;
	}
	
	public void ranking(){
		ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Comentarios");
        
		query3.whereEqualTo("id_alojamiento", id_aloj);
		
		List<ParseObject> cal = null;
		try {
			cal = query3.find();
		} catch (ParseException e) {

		}
		
		if(cal.size()==0){
			vis_enviar.setOnClickListener(listener);
		}
		else{
			ParseObject califi = null;
			Float aux = (float) 0;
			for(int h=0; h < cal.size() ; h++){
				califi = cal.get(h);
				aux = aux + (float) califi.getDouble("calificacion");
			}
			aux = aux/cal.size();
			
			//Luego se cambia el total en la tabla del alojamiento
			ParseQuery<ParseObject> query5 = new ParseQuery<ParseObject>("Alojamiento");
			query5.whereEqualTo("objectId", id_aloj);
			
			List<ParseObject> aloj = null;
			try {
				aloj = query5.find();
			} catch (ParseException e) {

			}
			ParseObject aloji=null;
			if(aloj.size()!=0){ 
				aloji = aloj.get(0);
				aloji.put("calificacion", aux);
				aloji.put("count_calificacion", cal.size());
			}
			
			aloji.saveInBackground(new SaveCallback() {
				public void done(ParseException e) {
				    if (e == null) {
						//Toast.makeText( getApplicationContext(),"Alojamiento ingresado con éxito!",Toast.LENGTH_SHORT ).show();
				    } else{
				    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
				    }
				}
		    });
			
		}	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AlertDialog.Builder dialog;
		switch(item.getItemId()){	
	        case R.id.action_delete:
	        	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
    	        dialog.setTitle("Eliminar Alojamiento");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View v = getLayoutInflater().inflate( R.layout.dialog, null );
    	        TextView text = (TextView) v.findViewById(R.id.dialog_text);
    	        text.setText("¿Desea borrar de forma permanente este anuncio?");
    			
    	        dialog.setView(v);
    	        dialog.setNegativeButton("Cancelar", null);  
    	        dialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	delete_bar.setVisibility(View.VISIBLE);
    	            	Toast.makeText( getApplicationContext(),"Eliminando anuncio...",Toast.LENGTH_SHORT ).show();
    	            	//Acá se elimina el alojamiento de la base de datos
    	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
    	        		query.whereEqualTo("objectId", id_aloj);
    	        		
    	        		List<ParseObject> obj = null;
    	        		try {
    	        			obj = query.find();
    	        		} catch (ParseException e) {
    	        		}
    	        		if(obj.size()!=0){
    	        			obj.get(0).deleteInBackground(new DeleteCallback() {
    	        				public void done(ParseException e) {
    	        				    if (e == null) {	
    	        				    	delete_bar.setVisibility(View.INVISIBLE);
    	        				    	Toast.makeText( getApplicationContext(),"Alojamiento eliminado correctamente!",Toast.LENGTH_SHORT ).show();
    	        				    	finish();    	        						
    	        				    } else{
    	        				    	Toast.makeText(getApplicationContext(), "Error al borrar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
    	        				    }
    	        				}
    	        		    });
    	        		}                    	
    	            }  
    	        });  
    	        
    	        dialog.show();
            	break;
	        case R.id.action_camara:	        	
	        	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
    	        dialog.setTitle("Agregar una foto al alojamiento");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View vista = getLayoutInflater().inflate( R.layout.dialog, null );
    	        
    	        TextView textito = (TextView) vista.findViewById(R.id.dialog_text);
    	        textito.setText("Para subir una imágen debes tomar una foto o escogerla desde tu galería.");
    			      
    	        dialog.setView(vista);
    	        dialog.setNegativeButton("Buscar en galería", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    	            	int code = SELECT_PICTURE;
    	            	startActivityForResult(intent, code);	
    	            }  
    	        });   
    	        dialog.setPositiveButton("Tomar fotografía", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo2, int id) {
    	            	Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
    	       			int code = TAKE_PICTURE;
    	       			Uri output = Uri.fromFile(new File(name));
           		    	intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
           		    	startActivityForResult(intent, code);	
    	            }  
    	        });          	        
    	        dialog.show();
	        	break;
	        case R.id.action_fav_no:
	        	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
    	        dialog.setTitle("Agregar a favoritos");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View ve = getLayoutInflater().inflate( R.layout.dialog, null );
    	        TextView tex = (TextView) ve.findViewById(R.id.dialog_text);
    	        tex.setText("¿Deseas agregar este alojamiento a tu lista de favoritos?");
    			
    	        dialog.setView(ve);
    	        dialog.setNegativeButton("Cancelar", null);  
    	        dialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	pd.show();
    	            	Toast.makeText( getApplicationContext(),"Agregando a favoritos...",Toast.LENGTH_SHORT ).show();
    	            	
    	            	ParseObject favi = new ParseObject("Favoritos");
    					favi.put("id_user", id_user_emisor);
    					favi.put("id_alojamiento", id_aloj);
    					
    					favi.saveInBackground(new SaveCallback() {
    						public void done(ParseException e) {
    						    if (e == null) {	
    						    	pd.dismiss();
	        				    	Toast.makeText( getApplicationContext(),"¡Este alojamiento ha sido agregado a su lista de favoritos!",Toast.LENGTH_SHORT ).show();
	        				    	finish();    
	        				    	startActivity(getIntent());
    						    } else{
    						    	Toast.makeText(getApplicationContext(), "Error al ingresar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
    						    }
    						}
    				    });            	
    	            }  
    	        });  
    	        
    	        dialog.show();
	        	break;
	        case R.id.action_fav:
	        	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
    	        dialog.setTitle("Eliminar alojamiento de favoritos");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View vd = getLayoutInflater().inflate( R.layout.dialog, null );
    	        TextView texta = (TextView) vd.findViewById(R.id.dialog_text);
    	        texta.setText("¿Deseas borrar este alojamiento de tu lista de favoritos?");
    			
    	        dialog.setView(vd);
    	        dialog.setNegativeButton("Cancelar", null);  
    	        dialog.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	pd.show();
    	            	//Toast.makeText( getApplicationContext(),"Eliminando anuncio...",Toast.LENGTH_SHORT ).show();
    	            	//Acá se elimina el alojamiento de la base de datos
    	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Favoritos");
    	        		query.whereEqualTo("id_user", id_user_emisor);
    	        		query.whereEqualTo("id_alojamiento", id_aloj);
    	        		
    	        		List<ParseObject> obj = null;
    	        		try {
    	        			obj = query.find();
    	        		} catch (ParseException e) {
    	        		}
    	        		if(obj.size()!=0){
    	        			obj.get(0).deleteInBackground(new DeleteCallback() {
    	        				public void done(ParseException e) {
    	        				    if (e == null) {	
    	        				    	pd.dismiss();
    	        				    	Toast.makeText( getApplicationContext(),"Alojamiento borrado de tu lista de favoritos!",Toast.LENGTH_SHORT ).show();
    	        				    	finish();    
    	        				    	startActivity(getIntent());
    	        				    } else{
    	        				    	Toast.makeText(getApplicationContext(), "Error al borrar el alojamiento, por favor intente nuevamente.", Toast.LENGTH_SHORT).show();
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
	
	public void save_image(final Bitmap bit){   
		final ParseObject picture = new ParseObject("Fotos");
		
		AlertDialog.Builder dialog;
    	dialog = new AlertDialog.Builder(Activity_verAlojamiento.this);  
        dialog.setTitle("Agregar un comentario a la imágen");		
        dialog.setIcon(R.drawable.ic_launcher);	
        
        View vis = getLayoutInflater().inflate( R.layout.dialog_editarperfil, null );
        
        TextView textito = (TextView) vis.findViewById(R.id.dialogedit_text);
        com_new = (EditText) vis.findViewById(R.id.dialogedit_new);
        textito.setText("Ingrese comentario:");
        com_new.setHint("Comentario");
		com_new.setInputType(EditorInfo.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        dialog.setView(vis);
        dialog.setNegativeButton("Cancelar", null);  
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {
            	comentario = com_new.getText().toString();
            	if(comentario == ""){
            		Toast.makeText( getApplicationContext(),"¡Ingrese un comentario válido!",Toast.LENGTH_SHORT ).show();
            	} 
            	else{	        	            	
	            	picture.put("comentario", comentario);
	            	pd.show();
	        		Toast.makeText( getApplicationContext(),"Subiendo imágen",Toast.LENGTH_LONG ).show();
	        	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        	    bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	        	    data1 = stream.toByteArray();
	        	    
	        	    bito = bit;
	        	    tamano=tamano+1;
	        	    ParseFile file = new ParseFile(id_aloj+"_"+tamano+"_foto.jpg", data1);
	        		
	        	    
	        		picture.put("foto", file);
	        		picture.put("id_alojamiento", id_aloj);
	        		picture.saveInBackground(new SaveCallback() {
	        			public void done(ParseException e) {
	        			    if (e == null) {	
	        			    	pd.dismiss();
	        			    	imgs.add(bito);
	        			    	adapter.notifyDataSetChanged();
	        			    	Toast.makeText( getApplicationContext(),"Finalizado",Toast.LENGTH_SHORT ).show();
	        			    	
	        			    } else{
	        			    	Toast.makeText(getApplicationContext(), "Error al guardar la imágen.", Toast.LENGTH_SHORT).show();
	        			    }
	        			}
	        	    });
            	}
            }  
        });         	        
        dialog.show();
        
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {    	
    	if (requestCode == TAKE_PICTURE) {
    		if (data != null) {
    			save_image((Bitmap) data.getParcelableExtra("data"));
    		}
    		else{    			
    			BitmapFactory.Options options = new BitmapFactory.Options();
    	        options.inSampleSize = 2;
    			save_image(BitmapFactory.decodeFile(name, options));
    			//perf_foto.setImageBitmap(BitmapFactory.decodeFile(name));
    			new MediaScannerConnectionClient() {
    				private MediaScannerConnection msc = null; {
    					msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
    				}
    				public void onMediaScannerConnected() { 
    					msc.scanFile(name, null);
    				}
    				public void onScanCompleted(String path, Uri uri) { 
    					msc.disconnect();
    				} 
    			};				
    		}    	
    	} else if (requestCode == SELECT_PICTURE){
    		Uri selectedImage = data.getData();
    		InputStream is;
    		try {
    			is = getContentResolver().openInputStream(selectedImage);
    	    	BufferedInputStream bis = new BufferedInputStream(is);
    	    	pic = BitmapFactory.decodeStream(bis);
    	    	save_image(pic);			
    		} catch (FileNotFoundException e) {}
    	}
    }
}
