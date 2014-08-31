package com.retni.applacegps;

import java.util.List;

import com.parse.ParseFile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
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
    
    private static LayoutInflater inflater = null;

    public Cursor_Adapter(Context context, List<String> titulos, List<Integer> precios, List<Bitmap> fotos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.titulos = titulos;
        this.precios = precios;
        this.fotos = fotos;
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

    @Override
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
	    
	    if(fotos.size()!=0){
	    	
	    	img.setImageBitmap(fotos.get(position));
	    	img.invalidate();
	    }
	    if(titulos.size()!=0){
	    	titulo.setText(titulos.get(position));
	    }
	    if(precios.size()!=0){
	    	precio.setText("$"+precios.get(position).toString());
	    }
	    
	    
        convertView.setOnClickListener(new OnClickListener(){		
			public void onClick(View v) {			
				Intent intent=new Intent(context,Activity_verAlojamiento.class);
				//intent.putExtra("nombre", cursor.getString(cursor.getColumnIndex(from[0])));
				context.startActivity(intent);
			}		
		});
        
        return convertView;
    }
    
}
