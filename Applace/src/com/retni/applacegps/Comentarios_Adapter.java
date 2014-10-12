package com.retni.applacegps;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Comentarios_Adapter extends BaseAdapter{
	
	Context context;
    List<String> comentarios;
    List<String> fechas;
    List<Bitmap> fotos;
    List<String> users;
    List<String> id_coment;
    
    ImageView vis_temp;
    int pos = 0;
    
   // @SuppressWarnings("unused")
	private static LayoutInflater inflater = null;

    public Comentarios_Adapter(Context context, List<String> comentarios, List<String> fechas, List<Bitmap> fotos, List<String> users, List<String> id_coment) {
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
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
	    	@SuppressWarnings("static-access")
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	    	convertView = inflater.inflate(R.layout.row_comentario, null); 
	    }
    	
	    TextView comentario = (TextView)convertView.findViewById(R.id.com_comentario);
	    TextView date = (TextView)convertView.findViewById(R.id.com_date);
	    ImageView img = (ImageView)convertView.findViewById(R.id.com_img);
	    TextView emisor = (TextView)convertView.findViewById(R.id.com_emisor);
	    
	    if(fotos.size() != 0 ){	    	
	    	//img.setImageBitmap(fotos.get(position));
	    	//setImage(img, fotos.get(position));
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
	    
        convertView.setOnClickListener(new OnClickListener(){        	
			public void onClick(View v) {
				Integer pos = (Integer) v.getTag();
				Intent intent = new Intent(context,Activity_verAlojamiento.class);
				intent.putExtra("id_coment", id_coment.get(pos));
				context.startActivity(intent);				
			}		
		});        
		
        return convertView;
	}
}
