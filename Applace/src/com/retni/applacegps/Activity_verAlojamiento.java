package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_verAlojamiento extends ActionBarActivity{
	
	ViewPager viewPager;
    PagerAdapter adapter;
    String id_aloj = "jaja";
    TextView vis_tit, vis_estado, vis_rating_count, vis_precio, vis_descrip;
    RatingBar vis_rating;
    List<Bitmap> fotitos = new ArrayList<Bitmap>();
    int[] fotos;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_veralojamiento);
		
		fotos = new int[] { R.drawable.img01,
                R.drawable.img02, R.drawable.img03,
                R.drawable.img04, R.drawable.img05, R.drawable.img06,
                R.drawable.img07};
		
		vis_tit = (TextView) findViewById(R.id.vis_tit);
		vis_estado = (TextView) findViewById(R.id.vis_estado);
		vis_rating = (RatingBar) findViewById(R.id.vis_rating);
		vis_rating_count = (TextView) findViewById(R.id.vis_count_rating);
		vis_precio = (TextView) findViewById(R.id.vis_precio);
		vis_descrip = (TextView) findViewById(R.id.vis_descrip);
		
		Bundle bundle = getIntent().getExtras();
		id_aloj=bundle.getString("idAloj");
		Toast.makeText( getApplicationContext(),id_aloj,Toast.LENGTH_SHORT ).show();
		
        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
		query.whereEqualTo("objectId", id_aloj);
		
		List<ParseObject> car = null;
		try {
			car = query.find();
		} catch (ParseException e) {

		}
		
		if(car.size()!=0){
			ParseObject caract = car.get(0);
			vis_tit.setText(caract.getString("titulo"));
			if(caract.getBoolean("estado")==true){
				vis_estado.setText("Disponible");
			} else if(caract.getBoolean("estado")==false){
				vis_estado.setText("No disponible");
			}
			
			vis_rating.setRating((float) caract.getDouble("calificacion"));
			vis_rating_count.setText(""+caract.getInt("count_calificacion"));
			vis_precio.setText("$"+caract.getInt("precio"));
			vis_descrip.setText(caract.getString("descripcion"));
			
			ParseFile img = caract.getParseFile("foto");	
			if(img != null){
			    img.getDataInBackground(new GetDataCallback() {
			    	Bitmap bmp = null;
			        public void done(byte[] data, com.parse.ParseException e) {
			            if (e == null){
			                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);			                
			                fotitos.add(bmp);
			            }
			            else{
			            	//Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			            }
			        }
			    }); 
			} else{
				//Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
			}
			viewPager = (ViewPager) findViewById(R.id.pager_fotos);
	        adapter = new ViewPagerAdapter(Activity_verAlojamiento.this, fotos);
	        viewPager.setAdapter(adapter);
			
		}			
		else {
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}