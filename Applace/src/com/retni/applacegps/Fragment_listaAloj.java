package com.retni.applacegps;

import java.util.List;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Fragment_listaAloj extends Fragment{
	
	List<ParseObject> alojamientos;
	
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        
		// Override this method to do custom remote calls
		protected Void doInBackground(Void... params) {
			// Gets the current list of todos in sorted order
			//PARA APPLACE DBEERIA SER ---("Alojamiento");
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
			//PARA APPLACE ES 
			Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
	        ParseUser user = new ParseUser();
	        user = ParseUser.getCurrentUser();
	        
			query.whereEqualTo("User", user.getUsername());
			query.orderByDescending("_created_at");

			try {
				alojamientos = query.find();
			} catch (ParseException e) {

			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// Put the list of alojamientos into the list view
			/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					R.layout.list_aloj_row);
            if (alojamientos != null) {
                for (ParseObject Alojamiento : alojamientos) {
                	
                    adapter.add((String) Alojamiento.get("titulo"));
                    adapter.
                }
            }
			setListAdapter(adapter);
			ToDoListActivity.this.progressDialog.dismiss();
			TextView empty = (TextView) findViewById(android.R.id.empty);
			empty.setVisibility(View.VISIBLE);*/
		}
	}
	
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
        
        Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();
        
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
		query.whereEqualTo("User", user.getUsername());
		query.orderByDescending("_created_at");

		try {
			alojamientos = query.find();
		} catch (ParseException e) {

		}
		
		int size_aloj = alojamientos.size();
		
		ParseObject aloj = null;
		for(int i=0;i<size_aloj; i++){
			aloj = alojamientos.get(i);
			titulos.add(aloj.getString("titulo"));
			precios.add(aloj.getInt("precio"));
			
			ParseFile img = aloj.getParseFile("foto");
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
		}		
		     
		list_aloj = (ListView) getActivity().findViewById(R.id.list_aloj);
        list_aloj.setAdapter(new Cursor_Adapter(getActivity(), titulos, precios, fotos));        
	}
}