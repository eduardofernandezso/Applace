/*Este fragmento se encarga de la publicacion de alojamientos*/

package com.retni.applacegps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Fragment_ubicacion extends Fragment {
	
	String tipoAlojamiento;
	double lat, lon;
	TextView pregunta;
	CharSequence tituloSeccion;
	FrameLayout frame_aloj;
	Button sig;
	Button centergps;
	EditText direccion;
	ImageButton buscar;
	/*COSAS DEL MAPA*/
	
	Double lat_save=0.0, lon_save=0.0;
	String dir_save="dirección";
	
	GoogleMap mapa = null;
	MarkerOptions markerOptions;
    LatLng latLng;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);       
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ubicacion, container, false);
		return v;
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	                                         .findFragmentById(R.id.map_ubicacion);
	    if (f != null) 
	        getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	
	@SuppressLint("NewApi")
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
        pregunta = (TextView) getActivity().findViewById(R.id.preg_ubicacion);
        sig = (Button) getActivity().findViewById(R.id.sig);
        centergps = (Button) getActivity().findViewById(R.id.centergps1);
        direccion = (EditText) getActivity().findViewById(R.id.direccion);
        buscar = (ImageButton) getActivity().findViewById(R.id.ubic_buscar);
        
        mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_ubicacion)).getMap();
        //mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        
        Location loc = getMyLocation();
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        } else{
        	GeoPoint startPoint = new GeoPoint(getMyLocation());
        	Double lat, lon;
        	lat = startPoint.getLatitude();
        	lon = startPoint.getLongitude();
        	CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 15);
            mapa.moveCamera(camUpd1);
        }
        
        sig.setOnClickListener(listener);
        //centergps.setOnClickListener(listener_gps);
        
        Bundle bundle = getArguments();
        tipoAlojamiento = (String) bundle.getString("tipoAloj");
        
        pregunta.setText("¿En qué lugar se encuentra tu "+tipoAlojamiento+"?");

        getActivity().getActionBar().setTitle("ubicacion");
        
        Fragment_mapa fragment = new Fragment_mapa();  
        
        int i=1;
        
        Bundle tipoSelected = new Bundle();
        tipoSelected.putInt("ubicacion", i);
        fragment.setArguments(tipoSelected);

        
        // Setting a click event handler for the map
        mapa.setOnMapClickListener(new OnMapClickListener() {
 
            @Override
            public void onMapClick(LatLng arg0) {
 
                // Getting the Latitude and Longitude of the touched location
                latLng = arg0;
 
                // Clears the previously touched position
                mapa.clear();
 
                // Animating to the touched position
                mapa.animateCamera(CameraUpdateFactory.newLatLng(latLng));
 
                // Creating a marker
                markerOptions = new MarkerOptions();
 
                // Setting the position for the marker
                markerOptions.position(latLng);
                
                String addressString="hola";

                try {
                	Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                	List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                	StringBuilder sb = new StringBuilder();
                	if (addresses.size() > 0) {
                		Address address = addresses.get(0);
                		
                		sb.append(address.getAddressLine(0)).append(",\n");
                		sb.append(address.getLocality()).append(",\n");
                		sb.append(address.getCountryName());
                	}

                	addressString = sb.toString();

                	Log.e("Address from lat,long ;", addressString);
                } catch (IOException e) {}
                
                lat_save = latLng.latitude;
                lon_save = latLng.longitude;
                dir_save = addressString;
                direccion.setText(dir_save);
                
                markerOptions.title(addressString);
                //getAddressByGpsCoordinates(latLng.latitude, latLng.longitude);
                // Placing a marker on the touched position
                mapa.addMarker(markerOptions);
 
                // Adding Marker on the touched location with address
                new ReverseGeocodingTask(getActivity().getBaseContext()).execute(latLng);
 
            }
        });
        
        // Setting click event listener for the find button
        buscar.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // Getting the place entered
                String location = direccion.getText().toString();
 
                if(location==null || location.equals("")){
                    Toast.makeText(getActivity().getBaseContext(), "No se ha ingresado ninguna dirección.", Toast.LENGTH_SHORT).show();
                    return;
                }
 
                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
 
                try {
                    // encoding special characters like space in the user input place
                    location = URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
 
                String address = "address=" + location;
 
                String sensor = "sensor=false";
 
                // url , from where the geocoding data is fetched
                url = url + address + "&" + sensor;
 
                // Instantiating DownloadTask to get places from Google Geocoding service
                // in a non-ui thread
                DownloadTask downloadTask = new DownloadTask();
 
                // Start downloading the geocoding places
                downloadTask.execute(url);
            }
        });
        
	}
	
	private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
            br.close();
 
        }catch(Exception e){
            Log.d("Error mientras se descargaba URL", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
 
        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class DownloadTask extends AsyncTask<String, Integer, String>{
 
        String data = null;
 
        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
 
            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserTask parserTask = new ParserTask();
 
            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }
 
    /** A class to parse the Geocoding Places in non-ui thread */
    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        JSONObject jObject;
 
        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){
 
            // Clears all the existing markers
        	mapa.clear();
 
            for(int i=0;i<list.size();i++){
 
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
 
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
 
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
 
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
 
                // Getting name
                String name = hmPlace.get("formatted_address");
 
                LatLng latLng = new LatLng(lat, lng);
                
                lat_save = latLng.latitude;
                lon_save = latLng.longitude;
                dir_save = name;
 
                // Setting the position for the marker
                markerOptions.position(latLng);
 
                // Setting the title for the marker
                markerOptions.title(name);
 
                // Placing a marker on the touched position
                mapa.addMarker(markerOptions);
 
                // Locate the first location
                if(i==0)
                	mapa.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }
	
	private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String>{
        Context mContext;
 
        public ReverseGeocodingTask(Context context){
            super();
            mContext = context;
        }
 
        // Finding address using reverse geocoding
        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;
 
            List<Address> addresses = null;
            String addressText="";
 
            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
 
            if(addresses != null && addresses.size() > 0 ){
                Address address = addresses.get(0);
 
                addressText = String.format("%s, %s, %s",
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                address.getLocality(),
                address.getCountryName());
            }
 
            return addressText;
        }
 
        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(addressText);
 
            // Placing a marker on the touched position
            mapa.addMarker(markerOptions);
 
        }
    }
	
	public void refresh(){
		GeoPoint startPoint = new GeoPoint(getMyLocation());
    	Double lat, lon;
    	lat = startPoint.getLatitude();
    	lon = startPoint.getLongitude();
    	CameraUpdate camUpd1 = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
        mapa.moveCamera(camUpd1);
        //mapa.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Mi posición"));
	}
	
	Location getMyLocation(){
		MiLocationListener loc= new MiLocationListener();
		LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, loc);
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public class MiLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc){
			//refresh();
		}
		
		@Override
		public void onProviderDisabled(String provider){
			Toast.makeText( getActivity().getApplicationContext(),"GpsApplace Desactivado",Toast.LENGTH_SHORT ).show();
		}
		
		@Override
		public void onProviderEnabled(String provider){
			Toast.makeText( getActivity().getApplicationContext(),"GpsApplace Activado",Toast.LENGTH_SHORT ).show();
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
			
		}
	}	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapa.setMyLocationEnabled(true);
		//refresh();
		//myLocationOverlay.enableMyLocation();
	//	myLocationOverlay.enableCompass();
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapa.setMyLocationEnabled(false);
		//myLocationOverlay.disableMyLocation();
	//	myLocationOverlay.disableCompass();
	}
	
	private OnClickListener listener = new OnClickListener(){		//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {			
			switch(v.getId()){			
				case R.id.sig:		//Lanzamiento de una nueva actividad
					Toast.makeText(getActivity().getApplicationContext(), tipoAlojamiento, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), Activity_caracteristicas.class );
					intent.putExtra("lat", lat_save);
					intent.putExtra("lon", lon_save);
					intent.putExtra("dir", dir_save);
					intent.putExtra("tipo_aloj", tipoAlojamiento);
					startActivity(intent);
			}
		}
	};
}