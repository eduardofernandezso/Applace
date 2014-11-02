package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.retni.applacegps.Fragment_conversaciones.Cursor_AdapterConv;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
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
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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

public class Fragment_listaAloj extends Fragment{
	
	ListView list_aloj;
	TextView msje;
	
	List<String> titulos = new ArrayList<String>();
	List<Integer> precios = new ArrayList<Integer>();
	List<Bitmap> fotos = new ArrayList<Bitmap>();
	List<Float> rating = new ArrayList<Float>();
	List<Integer> count_rating = new ArrayList<Integer>();
	List<String> id_aloj = new ArrayList<String>();
	
	List<ParseObject> alojamientos;
	List<ParseObject> alojamientos2;
	
	ProgressBar list_bar;
	
	TransparentProgressDialog pd;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_listaloj, container, false);
		return v;
	}	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);   

        msje = (TextView) getActivity().findViewById(R.id.list_mensaje);
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
        //final ProgressDialog pd1=ProgressDialog.show(this, "Calling Webservice", "Waiting...", true, false);
		list_bar = (ProgressBar) getActivity().findViewById(R.id.list_bar);
		
        new AsyncTask<Void, Void, Void>() {

            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                //list_bar.setVisibility(View.VISIBLE);        
                pd.show();
            }
            
            protected Void doInBackground(Void... params) {
            	
            	Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
                ParseUser user = new ParseUser();
                user = ParseUser.getCurrentUser();
                
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
        		query.whereEqualTo("User", user.getObjectId());
        		query.orderByDescending("_created_at");

        		try {
        			alojamientos = query.find();
        		} catch (ParseException e) {

        		}		
        		
        		int size_aloj=0;
        		size_aloj = alojamientos.size();
        		
        		if (size_aloj == 0){
        			msje.setVisibility(View.VISIBLE);
        		} else{		
        			ParseObject aloj = null;
        			for(int i=0;i<size_aloj; i++){
        				aloj = alojamientos.get(i);
        				titulos.add(aloj.getString("titulo"));
        				precios.add(aloj.getInt("precio"));
        				rating.add((float) aloj.getDouble("calificacion"));
        				count_rating.add(aloj.getInt("count_calificacion"));
        				id_aloj.add(aloj.getObjectId());
        				
        				ParseFile img = aloj.getParseFile("foto");	
        				if(img != null){
        				    img.getDataInBackground(new GetDataCallback() {
        				    	Bitmap bmp = null;
        				        public void done(byte[] data, com.parse.ParseException e) {
        				            if (e == null){
        				                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);	
        				                fotos.add(bmp);
        				            }
        				            else{
        				            	Toast.makeText(getActivity().getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        				            }
        				        }
        				    }); 
        				} else{
        					Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        				}
        			}		
        			
        			list_aloj = (ListView) getActivity().findViewById(R.id.list_aloj);        	          
        		}
                return null;
            }

            protected void onPostExecute(Void result) {
            	//list_bar.setVisibility(View.GONE); 
            	
            	if (pd.isShowing() ) {
        			pd.dismiss();
        		}

                //Custom Adapter for ListView
            	BaseAdapter listAdapter = new Cursor_AdapterList(getActivity(), titulos, precios, fotos, rating, count_rating, id_aloj);
            	list_aloj.setAdapter(listAdapter);  
            }
        }.execute();

    }
	
	
	
	public static class Cursor_AdapterList extends BaseAdapter{
		
		ImageView vis_img1, vis_img2, vis_temp;
		Context context;
	    List<String> titulos;
	    List<Integer> precios;
	    List<Bitmap> fotos;
	    List<Float> rating;
	    List<Integer> count_rating;
	    List<String> id_aloj;
	    int pos = 0;
	    
	    @SuppressWarnings("unused")
		private static LayoutInflater inflater = null;

	    public Cursor_AdapterList(Context context, List<String> titulos, List<Integer> precios, List<Bitmap> fotos, List<Float> rating, List<Integer> count_rating, List<String> id_aloj) {
	        // TODO Auto-generated constructor stub
	        this.context = context;
	        this.titulos = titulos;
	        this.precios = precios;
	        this.fotos = fotos;
	        this.rating = rating;
	        this.count_rating = count_rating;
	        this.id_aloj = id_aloj;
	        inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return titulos.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return titulos.get(position);
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
		    	convertView = inflater.inflate(R.layout.fragment_list_aloj_row, null); 
		    }
	    	
		    final TextView titulo = (TextView)convertView.findViewById(R.id.row_titulo);
		    final TextView precio = (TextView)convertView.findViewById(R.id.row_precio);
		    final ImageView img = (ImageView)convertView.findViewById(R.id.row_img);
		    final RatingBar star = (RatingBar)convertView.findViewById(R.id.row_star);	
		    final TextView count = (TextView)convertView.findViewById(R.id.row_count);
		    
		    if(fotos.size() != 0 ){	   
		    	setImage(img, fotos.get(position));
		    	//img.setImageBitmap(fotos.get(position));
		    }
		    if(titulos.size()!=0){
		    	titulo.setText(titulos.get(position));
		    }
		    if(precios.size()!=0){
		    	precio.setText("$"+precios.get(position).toString());
		    }	    
		    if(rating.size() != 0){
		    	star.setRating(rating.get(position));
		    }
		    if(count_rating.size() != 0){
		    	count.setText(count_rating.get(position).toString());
		    }
		    if(id_aloj.size() != 0){
		    	 convertView.setTag(position);	    	 
		    }
		    
	        convertView.setOnClickListener(new OnClickListener(){        	
				public void onClick(View v) {
					Integer pos = (Integer) v.getTag();
					Intent intent = new Intent(context,Activity_verAlojamiento.class);
					intent.putExtra("idAloj", id_aloj.get(pos));
					context.startActivity(intent);				
				}		
			});        
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
}