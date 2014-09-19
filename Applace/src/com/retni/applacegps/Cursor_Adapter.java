package com.retni.applacegps;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Cursor_Adapter extends BaseAdapter{
	
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

    public Cursor_Adapter(Context context, List<String> titulos, List<Integer> precios, List<Bitmap> fotos, List<Float> rating, List<Integer> count_rating, List<String> id_aloj) {
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
	    	img.setImageBitmap(fotos.get(position));
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
}
