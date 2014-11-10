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
import com.parse.SaveCallback;
import com.retni.applacegps.Activity_verAlojamiento.ViewPagerAdapter1;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_detalleFoto extends ActionBarActivity{
	
	private final int PICKER = 1;
	private int currentPic = 0;
	private Gallery picGallery;
	private ImageView picView;
	private PicAdapter imgAdapt;
	TransparentProgressDialog pd;
	String idAloj, titulo="Galería";
	ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    ArrayList<String> com = new ArrayList<String>();
    TextView coment, tit;
	int pos;
    
	TextView new_receptor;
	EditText new_mensaje;
	ParseFile img;
	Button new_enviar;
	String men;
	String id_user_receptor, id_user_emisor, name_receptor, mail_receptor;
	
	String mail1, mail2;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detallefoto);
		
		Intent in = getIntent();		
		idAloj = in.getStringExtra("idAloj");
		titulo = in.getStringExtra("titulo");
		pos = in.getIntExtra("pos", 0);
		pd = new TransparentProgressDialog(this, R.drawable.prog_applace);
		
		picView = (ImageView) findViewById(R.id.picture);
		coment = (TextView) findViewById(R.id.det_com);
		tit = (TextView) findViewById(R.id.det_tit);
		picGallery = (Gallery) findViewById(R.id.gallery);
		
		tit.setText("Galería de '"+titulo+"'");
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		
		new AsyncTask<Void, Void, Void>() {

            protected void onPreExecute() {
                super.onPreExecute();       
                pd.show();      
                imgAdapt = new PicAdapter(Activity_detalleFoto.this,imgs,com);
            }
            
            protected Void doInBackground(Void... params) {
            	
            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Fotos");
        		query.whereEqualTo("id_alojamiento", idAloj);
        		
        		List<ParseObject> aa = null;
        		try {
        			aa = query.find();
        		} catch (ParseException e) {}
        		
        		if(aa.size()!=0){
        			for(int i=0;i<aa.size();i++){
        				ParseObject hola = aa.get(i);
        				com.add(hola.getString("comentario"));
        				imgAdapt.notifyDataSetChanged();
        				img = hola.getParseFile("foto");
        			    if(img != null){
        				    img.getDataInBackground(new GetDataCallback() {
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
        				                imgAdapt.notifyDataSetChanged();
        				            }
        				            else{
        				            	Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        				            }
        				        }
        				    }); 
        			    }
        			}
        		}	
        		
            	return null;
            }

            protected void onPostExecute(Void result) {            	
            	if (pd.isShowing() ) {
            		pd.dismiss();
        		}        		
        		picGallery.setAdapter(imgAdapt);
        		
            }
        }.execute();
        
        
		
		picGallery.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        picView.setImageBitmap(imgAdapt.getPic(position));
		        coment.setText(imgAdapt.getCom(position));
		    }
		});
	}
	
	public class PicAdapter extends BaseAdapter {
		int defaultItemBackground;
		private Context galleryContext;
		
		ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
	    ArrayList<String> com = new ArrayList<String>();
		
		public PicAdapter(Context c, ArrayList<Bitmap> imgs, ArrayList<String> com) {
		    galleryContext = c;
		    this.imgs = imgs;
		    this.com = com;
		   
		    TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
		    
		    defaultItemBackground = styleAttrs.getResourceId(
		        R.styleable.PicGallery_android_galleryItemBackground, 0);
		    
		    styleAttrs.recycle();
		     
		}

		@Override
		public int getCount() {
		    return imgs.size();
		}

		@Override
		public Object getItem(int position) {
		    return position;
		}

		@Override
		public long getItemId(int position) {
		    return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
		    //create the view
		    ImageView imageView = new ImageView(galleryContext);
		    //specify the bitmap at this position in the array
		    imageView.setImageBitmap(imgs.get(position));
		    //set layout options
		    imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
		    //scale type within view area
		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		    //set default gallery item background
		    imageView.setBackgroundResource(defaultItemBackground);
		    //return the view
		    return imageView;
		}
		/*
		public void addPic(Bitmap newPic)
		{
		    //set at currently selected index
		    imageBitmaps[currentPic] = newPic;
		}*/
		//return bitmap at specified position for larger display
		public Bitmap getPic(int posn)
		{
		    //return bitmap at posn index
		    return imgs.get(posn);
		}
		public String getCom(int posn)
		{
		    //return bitmap at posn index
		    return com.get(posn);
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

					
			}
			/*else if(id == R.id.ini_link){
				Intent intent = new Intent(Activity_registro.this, Activity_iniciarSesion.class );
				startActivity(intent);
			}*/
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
}
