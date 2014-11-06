package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
	TransparentProgressDialog pd;
	List<String> comentarios = new ArrayList<String>();
	List<String> fechas = new ArrayList<String>();
	List<Bitmap> fotos = new ArrayList<Bitmap>();
	List<String> users = new ArrayList<String>();	
	List<String> id_coment = new ArrayList<String>();
	
	List<ParseObject> comenta = null;
	List<ParseObject> emisor = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_comentarios);
		
		//Se obtiene el valor del id del alojamiento que se desea ver los comentarios.
		Intent intent = getIntent();
		id_aloj = intent.getStringExtra("id_aloj");
		//Toast.makeText(getApplicationContext(), id_aloj, Toast.LENGTH_SHORT).show();
		pd = new TransparentProgressDialog(this, R.drawable.prog_applace);
        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        
        new AsyncTask<Void, Void, Void>() {
			List<ParseObject> cal = null;
			
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                //list_bar.setVisibility(View.VISIBLE);        
                pd.show();
            }
            
            protected Void doInBackground(Void... params) {
            	
            	ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Comentarios");
        		query2.whereEqualTo("id_alojamiento", id_aloj);
        		query2.orderByAscending("_created_at");
        		
        		try {
        			comenta = query2.find();
        		} catch (ParseException e) {

        		}		
        		
        		int size_com = 0;
        		size_com = comenta.size();
        		
        		//Toast.makeText(getApplicationContext(), size_com+"", Toast.LENGTH_SHORT).show();
        		if (size_com != 0){	
        			ParseObject com = null;
        			String id_emisor;
        			//Toast.makeText(getApplicationContext(), "size: "+size_com, Toast.LENGTH_SHORT).show();
        			for(int i=0;i<size_com; i++){
        				com = comenta.get(i);
        				comentarios.add(com.getString("comentario"));
        				//Toast.makeText(getApplicationContext(), com.getString("comentario"), Toast.LENGTH_SHORT).show();
        				fechas.add(com.getUpdatedAt().toLocaleString());
        				users.add(com.getString("nombre_user_emisor"));
        				id_coment.add(com.getObjectId());
        				
        				ParseQuery query = ParseUser.getQuery();
        				query.whereEqualTo("objectId", com.getString("id_user_emisor"));
        				
        				try {
                			emisor = query.find();
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
        			}	
        			list_comentarios = (ListView) findViewById(R.id.com_list);
        		}
            	
            	return null;
            }

            protected void onPostExecute(Void result) {            	
            	if (pd.isShowing() ) {
        			pd.dismiss();
        		}
            	BaseAdapter listAdapter = new Cursor_AdapterCom(Activity_listaComentarios.this, comentarios, fechas, fotos, users, id_coment);
            	list_comentarios.setAdapter(listAdapter);  
            }
        }.execute();        
    }
	
	public static class Cursor_AdapterCom extends BaseAdapter{
	
		Context context;
	    List<String> comentarios;
	    List<String> fechas;
	    List<Bitmap> fotos;
	    List<String> users;
	    List<String> id_coment;
	    int pos = 0;
		ImageView vis_img1, vis_img2, vis_temp;
		
	    
	    @SuppressWarnings("unused")
		private static LayoutInflater inflater = null;

 	    public Cursor_AdapterCom(Context context, List<String> comentarios, List<String> fechas, List<Bitmap> fotos, List<String> users, List<String> id_coment) {
 	        // TODO Auto-generated constructor stub
 	        this.context = context;
 	        this.comentarios = comentarios;
 	        this.fechas = fechas;
 	        this.fotos = fotos;
 	        this.users = users;
 	        this.id_coment = id_coment;
 	        inflater = (LayoutInflater) context
 	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 	    }

	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return id_coment.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return id_coment.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }


	    public View getView(int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	    	if(convertView==null){
		    	@SuppressWarnings("static-access")
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		    	convertView = inflater.inflate(R.layout.row_comentario, null); 
		    }
		    
		    final TextView comentario = (TextView)convertView.findViewById(R.id.com_comentario);
		    final TextView date = (TextView)convertView.findViewById(R.id.com_date);
		    final ImageView img = (ImageView)convertView.findViewById(R.id.com_img);
		    final TextView emisor = (TextView)convertView.findViewById(R.id.com_emisor);
		    
		    if(fotos.size() != 0 ){	    	
		    	setImage(img, fotos.get(position));
		    	//img.setImageBitmap(fotos.get(position));
		    }
		    if(comentarios.size()!=0){		    	
		    	comentario.setText(comentarios.get(position));
		    }
		    if(fechas.size()!=0){
		    	date.setText(fechas.get(position));
		    }	    
		    if(users.size() != 0){
		    	emisor.setText(users.get(position));
		    }
		    if(id_coment.size() != 0){
		    	 convertView.setTag(position);	    	 
		    }
		    /*
	        convertView.setOnClickListener(new OnClickListener(){        	
				public void onClick(View v) {
					Integer pos = (Integer) v.getTag();
					Intent intent = new Intent(context,Activity_verAlojamiento.class);
					intent.putExtra("idAloj", id_aloj.get(pos));
					context.startActivity(intent);				
				}		
			});  */      
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
