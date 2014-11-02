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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_mensajes extends Fragment{
	
	ListView list_mens;
	TextView msje;
	
	List<String> titulos = new ArrayList<String>();
	List<String> mensaje = new ArrayList<String>();
	List<Bitmap> fotos = new ArrayList<Bitmap>();
	List<String> fechas = new ArrayList<String>();
	List<Float> rating = new ArrayList<Float>();
	List<Integer> count_rating = new ArrayList<Integer>();
	List<String> id_aloj = new ArrayList<String>();
	
	List<ParseObject> mensajes;
	List<ParseObject> users;
	List<ParseObject> alojamientos2;
	
	ProgressBar list_bar;
	
	LinearLayout msje_menu;
	Button msje_volver, msje_borrar;
	
	BaseAdapter listAdapter;
	
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

        list_bar = (ProgressBar) getActivity().findViewById(R.id.list_bar1);
        msje = (TextView) getActivity().findViewById(R.id.list_mens);
        
        list_bar.setVisibility(View.VISIBLE);
        
        msje_menu = (LinearLayout) getActivity().findViewById(R.id.msje_menu);
		msje_volver = (Button) getActivity().findViewById(R.id.msje_volver);
		msje_borrar = (Button) getActivity().findViewById(R.id.msje_borrar);
        
        Bundle bundle = getArguments();
        Integer delete = bundle.getInt("delete",0);
        
        if(delete==1){
			msje_menu.setVisibility(View.VISIBLE);
			msje_volver.setOnClickListener(listener);
		}

        Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        
        
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensajes");
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("User");
		query.whereEqualTo("envia", user.getEmail());
		query.orderByDescending("_created_at");

		try {
			mensajes = query.find();
		} catch (ParseException e) {

		}
		
		try {
			users = query.find();
		} catch (ParseException e) {}
		
		int size_aloj=0;
		size_aloj = mensajes.size();
		
		if (size_aloj == 0){
			msje.setVisibility(View.VISIBLE);
		} else{		
			ParseObject aloj = null;
			for(int i=0;i<size_aloj; i++){
				aloj = mensajes.get(i);
				titulos.add(aloj.getString("recibe"));
				mensaje.add(aloj.getString("mensaje"));
				fechas.add(aloj.getUpdatedAt().toGMTString());
				id_aloj.add(aloj.getObjectId());
				
				ParseFile img = aloj.getParseFile("Foto");	
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
			     
			list_mens = (ListView) getActivity().findViewById(R.id.list_men);
	        list_mens.setAdapter(new Cursor_Adapter2(getActivity(), titulos, mensaje, fotos,fechas,1,id_aloj, delete));        
	        list_bar.setVisibility(View.INVISIBLE);        
		}
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.msje_volver) {
				Intent intent = getActivity().getIntent();
				getActivity().finish();
				startActivity(intent);
			}
			else if(id == R.id.msje_borrar){
				/*
				for (int i = 0; i < listAdapter.getCount(); i++)
                {
                    Mensaje mensaje = listAdapter.getItem(i);
                    if (mensaje.isChecked())
                    {
                        Toast.makeText(getActivity(),
                                mensaje.getidMensaje() + " is Checked!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                */
			}
		}
	};
	
	private static class Mensaje
    {
        private String idMensaje = "";
        private boolean checked = false;

        public Mensaje()
        {
        }

        public Mensaje(String idMensaje)
        {
            this.idMensaje = idMensaje;
        }

        public Mensaje(String idMensaje, boolean checked)
        {
            this.idMensaje = idMensaje;
            this.checked = checked;
        }

        public String getidMensaje()
        {
            return idMensaje;
        }

        public void setidMensaje(String idMensaje)
        {
            this.idMensaje = idMensaje;
        }

        public boolean isChecked()
        {
            return checked;
        }

        public void setChecked(boolean checked)
        {
            this.checked = checked;
        }

        public String toString()
        {
            return idMensaje;
        }

        public void toggleChecked()
        {
            checked = !checked;
        }
    }
	
	/*
	public static class Cursor_Adapter3 extends BaseAdapter{
		
		CheckBox borrar;
		TextView titulo, precio;
		ImageView img;
		
		Context context;
	    List<String> titulos;
	    List<String> mensajes;
	    List<Bitmap> fotos;
	    List<String> fechas;
	    int id;
	    List<String> id_aloj;
	    int pos = 0;
	    Integer delete;
	    
	    @SuppressWarnings("unused")
		private static LayoutInflater inflater = null;

	    public Cursor_Adapter3(Context context, List<String> titulos, List<String> mensajes, List<Bitmap> fotos,List<String> fechas,int id, List<String> id_aloj, Integer delete) {
	        // TODO Auto-generated constructor stub
	        this.context = context;
	        this.titulos = titulos;
	        this.mensajes = mensajes;
	        this.fotos = fotos;
	        this.fechas = fechas;
	        this.id_aloj = id_aloj;
	        this.id = id;
	        this.delete = delete;
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
	    	
	    	borrar = (CheckBox)convertView.findViewById(R.id.msje_delete);
	    	titulo = (TextView)convertView.findViewById(R.id.row_titulo1);
		    precio = (TextView)convertView.findViewById(R.id.row_precio1);
		    img = (ImageView)convertView.findViewById(R.id.row_img1);
		    
		    borrar.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    CheckBox cb = (CheckBox) v;
                    Mensaje mensaje = (Mensaje) cb.getTag();
                    mensaje.setChecked(cb.isChecked());
                }
            });

		    if(delete == 1){
		    	borrar.setVisibility(View.VISIBLE);
		    }
		    
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
	}*/
	
	
	//***************************************************************************************
	/*
	public static class Cursor_Adapter3 extends ArrayAdapter<Mensaje>{
		
		private LayoutInflater inflater;

        public Cursor_Adapter3(Context context, List<Mensaje> mensajeList)
        {
            super(context, R.layout.simplerow, R.id.rowTextView, mensajeList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // Planet to display
        	Mensaje mensaje = (Mensaje) this.getItem(position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.simplerow, null);

                // Find the child views.
                textView = (TextView) convertView
                        .findViewById(R.id.rowTextView);
                checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

                // Optimization: Tag the row with it's child views, so we don't
                // have to
                // call findViewById() later when we reuse the row.
                convertView.setTag(new PlanetViewHolder(textView, checkBox));

                // If CheckBox is toggled, update the planet it is tagged with.
                checkBox.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        CheckBox cb = (CheckBox) v;
                        Mensaje mensaje = (Mensaje) cb.getTag();
                        mensaje.setChecked(cb.isChecked());
                    }
                });
            }
            // Reuse existing row view
            else
            {
                // Because we use a ViewHolder, we avoid having to call
                // findViewById().
                PlanetViewHolder viewHolder = (PlanetViewHolder) convertView
                        .getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            checkBox.setTag(mensaje);

            // Display planet data
            checkBox.setChecked(mensaje.isChecked());
            textView.setText(mensaje.getidMensaje());

            return convertView;
        }	
	}
	*/
}

