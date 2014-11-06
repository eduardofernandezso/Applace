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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_mensajes2 extends Fragment{
	
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
        
        Bundle bundle = getArguments();
        Integer delete = bundle.getInt("delete",0);

        list_bar = (ProgressBar) getActivity().findViewById(R.id.list_bar1);
        msje = (TextView) getActivity().findViewById(R.id.list_mens);
        
        list_bar.setVisibility(View.VISIBLE);        
        Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        
        
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensajes");
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("User");
        
		query.whereEqualTo("recibe", user.getEmail());
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
				titulos.add(aloj.getString("envia"));
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
	        list_mens.setAdapter(new Cursor_Adapter2(getActivity(), titulos, mensaje, fotos,fechas,2,id_aloj,delete));        
	        list_bar.setVisibility(View.INVISIBLE);        
		}
	}

}