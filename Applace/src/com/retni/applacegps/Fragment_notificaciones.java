package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.retni.applacegps.Fragment_conversaciones.Cursor_AdapterConv;

public class Fragment_notificaciones extends Fragment{	

	ProgressBar list_bar;
	
	LinearLayout msje_menu;
	Button msje_volver, msje_borrar;
	int sinmsje=0;
	int delete;
	List<ParseObject> convers, convers4, date;
	List<ParseUser> otro;
	String idMio="mio", idOtro="otro";
	List<String> nomOtro = new ArrayList<String>();
	List<String> idOtros = new ArrayList<String>();
	List<Bitmap> fotOtro = new ArrayList<Bitmap>();
	//List<String> fecha = new ArrayList<String>();
	//List<String> id_conv = new ArrayList<String>();
	
	List<String> fecha = new ArrayList<String>();
	List<String> id_not = new ArrayList<String>();
	List<String> id_aloj = new ArrayList<String>();
	List<String> notificacion = new ArrayList<String>();
	
	ListView list_conv;
	TextView msje;
	TransparentProgressDialog pd;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mensajes, container, false);

		return v;
	}	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);          
        
        //msje = (TextView) getActivity().findViewById(R.id.list_mens);
        //msje_menu = (LinearLayout) getActivity().findViewById(R.id.msje_menu);
		//msje_volver = (Button) getActivity().findViewById(R.id.msje_volver);
		//msje_borrar = (Button) getActivity().findViewById(R.id.msje_borrar);
		
		pd = new TransparentProgressDialog(getActivity(), R.drawable.prog_applace);
        
        updateList();
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
	
	private void updateList() {
        // TODO Auto-generated method stub		
        new AsyncTask<Void, Void, Void>() {

            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                pd.show();
                
            }
            
            protected Void doInBackground(Void... params) {
            	Bundle bundle = getArguments();
                delete = bundle.getInt("delete",0);
                
                if(delete==1){
        			msje_menu.setVisibility(View.VISIBLE);
        			//msje_volver.setOnClickListener(listener);
        		}
                
                Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
                ParseUser user = new ParseUser();
                user = ParseUser.getCurrentUser();
                
                idMio = user.getObjectId();
                
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Notificacion");
                query.whereEqualTo("id_user", idMio);
        		query.orderByDescending("_created_at");
            		
        		try {
        			convers = query.find();
        		} catch (ParseException e) {}            
                
        		if(convers.size() == 0){
        			//No tiene notificaciones
        			sinmsje = 1;        			
        		} else{	        
        			ParseObject conv = null;
        			for(int i=0 ; i<convers.size() ; i++){
        				conv = convers.get(i);        				
        				id_not.add(conv.getObjectId());	//lista de id_notificacion	
        				id_aloj.add(conv.getString("id_alojamiento"));
        				notificacion.add(conv.getString("notificacion"));
        				fecha.add(conv.getUpdatedAt().toLocaleString());        		        
        			}
        			list_conv = (ListView) getActivity().findViewById(R.id.list_men);
        		}                
                return null;
            }

            protected void onPostExecute(Void result) {
            	if(pd.isShowing())
            		pd.dismiss();
            	if(sinmsje == 1){
            		//msje.setVisibility(View.VISIBLE);
            	} else if(sinmsje == 0){
            		BaseAdapter listAdapter=new Cursor_AdapterConv(getActivity(), notificacion, fecha, id_aloj, id_not, delete);
                    list_conv.setAdapter(listAdapter);
            	}            	
            }
        }.execute();
    }
	
	public static class Cursor_AdapterConv extends BaseAdapter{
		
		ImageView vis_img1, vis_img2, vis_temp;		
		Context context;
		List<String> notificacion;
	    List<String> fechas;
	    List<String> id_aloj;
	    List<String> id_not;
	    //int id;
	    //int pos = 0;
	    Integer delete;
	    
	    @SuppressWarnings("unused")
		private static LayoutInflater inflater = null;

	    public Cursor_AdapterConv(Context context, List<String> notificacion, List<String> fechas, List<String> id_aloj, List<String> id_not, Integer delete) {
	        // TODO Auto-generated constructor stub
	        this.context = context;
	        this.notificacion = notificacion;
	        this.fechas = fechas;
	        this.id_aloj = id_aloj;
	        this.id_not = id_not;
	        this.delete = delete;
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return id_not.size();
	    }
	   
	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return id_not.get(position);
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
		    	convertView = inflater.inflate(R.layout.fragment_notificacion_row, null);
		    }
	    	
	    	//final CheckBox borrar = (CheckBox)convertView.findViewById(R.id.msje_delete);
	    	final TextView notifi = (TextView)convertView.findViewById(R.id.not_text);
		    final TextView fecha = (TextView)convertView.findViewById(R.id.not_date);
		    /*
		    if(delete == 1){		    	
		    	borrar.setVisibility(View.VISIBLE);
		    }*/
		    
		    if(notificacion.size()!=0){
		    	notifi.setText(notificacion.get(position));
		    	convertView.setTag(position);
		    }
		    if(fechas.size()!=0){
		    	fecha.setText(fechas.get(position));
		    }
		  
		    
	        convertView.setOnClickListener(new OnClickListener(){        	
				public void onClick(View v) {	
					/*
					Integer pos = (Integer) v.getTag();
					Vibrator h = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        	h.vibrate(25);
					Intent intent = new Intent(context,Activity_conversacion.class);
					intent.putExtra("idConv", id_conv.get(pos));
					intent.putExtra("nomOtro", nomOtros.get(pos));
					intent.putExtra("fecha", fechas.get(pos));
					intent.putExtra("idOtros", idOtros.get(pos));
					intent.putExtra("fotOtro", Bitmap.createScaledBitmap(fotOtros.get(pos), 200, 200, false));
					context.startActivity(intent);	
					*/
				}		
			});        
	        return convertView;
	    } 	
	}
}
