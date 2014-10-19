package com.retni.applacegps;

import java.io.ByteArrayOutputStream;
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

public class Cursor_Adapter2 extends BaseAdapter{
	
	Context context;
    List<String> titulos;
    List<String> mensajes;
    List<Bitmap> fotos;
    List<String> fechas;
    int id;
    List<String> id_aloj;
    int pos = 0;
    
    @SuppressWarnings("unused")
	private static LayoutInflater inflater = null;

    public Cursor_Adapter2(Context context, List<String> titulos, List<String> mensajes, List<Bitmap> fotos,List<String> fechas,int id, List<String> id_aloj) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.titulos = titulos;
        this.mensajes = mensajes;
        this.fotos = fotos;
        this.fechas = fechas;
        this.id_aloj = id_aloj;
        this.id = id;
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
	    	convertView = inflater.inflate(R.layout.fragment_mensajes_row, null); 
	    }
    	
	    final TextView titulo = (TextView)convertView.findViewById(R.id.row_titulo1);
	    final TextView precio = (TextView)convertView.findViewById(R.id.row_precio1);
	    final ImageView img = (ImageView)convertView.findViewById(R.id.row_img1);

	    
	    if(fotos.size() != 0 ){	    	
	    	img.setImageBitmap(fotos.get(position));
	    }
	    if(titulos.size()!=0){
	    	titulo.setText(titulos.get(position));
	    	convertView.setTag(position);
	    }
	    if(mensajes.size()!=0){
	    	precio.setText(mensajes.get(position));
	    }
	  
	   
	    
        convertView.setOnClickListener(new OnClickListener(){        	
			public void onClick(View v) {
				Integer pos = (Integer) v.getTag();
				Intent intent = new Intent(context,Activity_verMensaje.class);
				
				//Bitmap b = fotos.get(pos); // your bitmap
				//ByteArrayOutputStream bs = new ByteArrayOutputStream();
				//b.compress(Bitmap.CompressFormat.PNG, 50, bs);
				intent.putExtra("usuario", titulos.get(pos));
				intent.putExtra("mensaje",mensajes.get(pos));
				intent.putExtra("imagen", fotos.get(pos));
				intent.putExtra("fecha",fechas.get(pos));
				intent.putExtra("ide", id_aloj.get(pos));
				if(id==1)intent.putExtra("id", 1);
				else if(id==2)intent.putExtra("id", 2);
				//intent.putExtra("byteArray", bs.toByteArray());
				context.startActivity(intent);

				
	
			}		
		});        
        return convertView;
    }    
}