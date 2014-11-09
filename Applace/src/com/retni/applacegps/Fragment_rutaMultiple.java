package com.retni.applacegps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.signpost.http.HttpResponse;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_rutaMultiple extends Fragment{
	GoogleMap mapa;
	List<LatLng> polyz;
	List<List<LatLng>> polylines = new ArrayList<List<LatLng>>();
    ProgressDialog pDialog;
	
    String startLoc;
    String endLoc;
    LatLng start;
    LatLng end;
	RelativeLayout rutaLayout;
	EditText address;
	Button createRouteButton;
	Button addAddress;
	TextView noDirAdded;
	
	public static List<String> directions = new ArrayList<String>();
	List<String> orderedDirs = new ArrayList<String>();
	
	public Fragment_rutaMultiple() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); 
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.ruta_multiple, container, false);
		return v;
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	                                         .findFragmentById(R.id.google_map);
	    if (f != null) 
	        getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		SupportMapFragment mMapFragment;
        super.onActivityCreated(savedInstanceState);
        
        noDirAdded = (TextView) getActivity().findViewById(R.id.not_dir_added);
        mMapFragment = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.google_map));
        mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.google_map)).getMap();
        Location loc = getMyLocation();
        
        
        if(directions.size() == 0){
        	noDirAdded.setVisibility(View.VISIBLE);
        	mMapFragment.getView().setVisibility(View.GONE);
        	return;
        }
        
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
        
        for(int i = 0; i < directions.size(); i++){
        	//Parseo de String destino a GeoPoint.
			Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext() , Locale.getDefault());     
	        List<Address> address;
	        double latitude = 0;
	        double longitude = 0;
			try {
				address = geoCoder.getFromLocationName(directions.get(i), 1);
				latitude = address.get(0).getLatitude();
		        longitude = address.get(0).getLongitude();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Dibujamos el marker.
			mapa.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(directions.get(i)));
        }
        
        GeoPoint startPoint = new GeoPoint(getMyLocation());
		start = new LatLng(startPoint.getLatitude(), startPoint.getLongitude());
		new GetDirection().execute();
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
		if(directions.size() != 0)
			mapa.setMyLocationEnabled(true);
		//refresh();
		//myLocationOverlay.enableMyLocation();
	//	myLocationOverlay.enableCompass();
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(directions.size() != 0)
			mapa.setMyLocationEnabled(false);
		//myLocationOverlay.disableMyLocation();
	//	myLocationOverlay.disableCompass();
	}
	
	class GetDirection extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Fragment_rutaMultiple.this.getActivity());
            pDialog.setMessage("Cargando ruta. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
        	for(int i = 0; i < directions.size(); i++){
        		orderDirections(directions, start);
	        	//Parseo de String destino a GeoPoint.
				Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext() , Locale.getDefault());     
		        List<Address> address;
		        double latitude = 0;
		        double longitude = 0;
				try {
					address = geoCoder.getFromLocationName(orderedDirs.get(i), 1);
					latitude = address.get(0).getLatitude();
			        longitude = address.get(0).getLongitude();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				end = new LatLng(latitude, longitude);
				
				//Descarga de JSON de la ruta de las direcciones correspondientes
	            String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin=" + Double.toString(start.latitude) + "," + Double.toString(start.longitude) + "&destination=" + Double.toString(end.latitude) + "," + Double.toString(end.longitude) + "&sensor=false&alternatives=true";
	            StringBuilder response = new StringBuilder();
	            try {
	                URL url = new URL(stringUrl);
	                HttpURLConnection httpconn = (HttpURLConnection) url
	                        .openConnection();
	                if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                    BufferedReader input = new BufferedReader(
	                            new InputStreamReader(httpconn.getInputStream()),
	                            8192);
	                    String strLine = null;
	
	                    while ((strLine = input.readLine()) != null) {
	                        response.append(strLine);
	                    }
	                    input.close();
	                }
	
	                String jsonOutput = response.toString();
	
	                JSONObject jsonObject = new JSONObject(jsonOutput);
	
	                // routesArray contains ALL routes
	                JSONArray routesArray = jsonObject.getJSONArray("routes");
	                // Grab the first route
	                JSONObject route = routesArray.getJSONObject(0);
	
	                JSONObject poly = route.getJSONObject("overview_polyline");
	                String polyline = poly.getString("points");
	                polyz = decodePoly(polyline);
	                //Agrega el polyline de la direccion origen a destino correspondiente.
	                polylines.add(polyz);	                
	
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
	            //direccion destino pasa a ser punto de comienzo de viaje.
	            start = new LatLng(latitude, longitude);
	            orderedDirs.clear();
        	}        	
	        return null;

        }

        protected void onPostExecute(String file_url) {
        	for(int i = 0; i < polylines.size(); i++) {
        		for (int j = 0; j < polylines.get(i).size() - 1; j++) {
	                LatLng src = polylines.get(i).get(j);
	                LatLng dest = polylines.get(i).get(j + 1);
	                Polyline line = mapa.addPolyline(new PolylineOptions()
	                        .add(new LatLng(src.latitude, src.longitude),
	                                new LatLng(dest.latitude, dest.longitude))
	                        .width(5).color(Color.CYAN).geodesic(true));
        		}
            }
        	//Se vacian las listas de direcciones y sus polylines.
        	directions.clear();
        	polylines.clear();
            pDialog.dismiss();
        }
    }
	
	//Método para llenar la lista de direcciones para la construcción de ruta óptima.
	public void orderDirections(List<String> directions, LatLng begin){
		
		List<Double> distanceDirs = new ArrayList<Double>();
		
		//Llenado de distancias desde el origen a todos los destinos.
		for(int i = 0; i < directions.size(); i++){
			if(orderedDirs.size() == 0){
				//Parseo de String destino a GeoPoint.
				Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext() , Locale.getDefault());     
		        List<Address> address;
		        double latitude = 0;
		        double longitude = 0;
				try {
					address = geoCoder.getFromLocationName(directions.get(i), 1);
					latitude = address.get(0).getLatitude();
			        longitude = address.get(0).getLongitude();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LatLng latlngDirection = new LatLng(latitude, longitude);
				
				float[] distancia = new float[1];
				Location.distanceBetween(begin.latitude, begin.longitude, latlngDirection.latitude, latlngDirection.longitude, distancia);
				distanceDirs.add((double) distancia[0]);
			}
			else{
				for(int j = 0; j < orderedDirs.size(); j++){
					if(orderedDirs.get(j) != directions.get(i)){
						//Parseo de String destino a GeoPoint.
						Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext() , Locale.getDefault());     
				        List<Address> address;
				        double latitude = 0;
				        double longitude = 0;
						try {
							address = geoCoder.getFromLocationName(directions.get(i), 1);
							latitude = address.get(0).getLatitude();
					        longitude = address.get(0).getLongitude();  
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LatLng latlngDirection = new LatLng(latitude, longitude);
						
						float[] distancia = new float[1];
						Location.distanceBetween(begin.latitude, begin.longitude, latlngDirection.latitude, latlngDirection.longitude, distancia);
						distanceDirs.add((double) distancia[0]);
					}
				}
			}
		}
		
		//Ordenamiento de la lista de distancias (BubbleSort).
		for(int i = distanceDirs.size()-1; i >= 0; i--) {
	        for(int j = 0; j < i; j++) {
	            if(distanceDirs.get(j) > distanceDirs.get(j + 1)) {
	                Double temp = distanceDirs.get(j);
	                distanceDirs.set(j, distanceDirs.get(j + 1));
	                distanceDirs.set(j + 1, temp);
	            }
	        }
	    }
		
		//Llenado de la lista ORDENADA de direcciones para la construccion de ruta optima.
		for(int i = 0; i < distanceDirs.size(); i++){
			for(int j = 0; j < directions.size(); j++){
				//Parseo de String destino a GeoPoint.
				Geocoder geoCoder = new Geocoder(getActivity().getApplicationContext() , Locale.getDefault());     
		        List<Address> address;
		        double latitude = 0;
		        double longitude = 0;
				try {
					address = geoCoder.getFromLocationName(directions.get(j), 1);
					latitude = address.get(0).getLatitude();
			        longitude = address.get(0).getLongitude();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LatLng latlngDirection = new LatLng(latitude, longitude);
				
				float[] distancia = new float[1];
				Location.distanceBetween(begin.latitude, begin.longitude, latlngDirection.latitude, latlngDirection.longitude, distancia);
				if((double) distancia[0] == distanceDirs.get(i)){
					orderedDirs.add(directions.get(j));
					break;
				}
			}
		}
	}

    /* Method to decode polyline points */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
