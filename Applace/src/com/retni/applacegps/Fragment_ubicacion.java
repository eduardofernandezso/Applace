/*Este fragmento se encarga de la publicacion de alojamientos*/

package com.retni.applacegps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
	/*COSAS DEL MAPA*/
	private MapView myOpenMapView;
	private MapController myMapController;
	
	ArrayList<OverlayItem> anotherOverlayItemArray;
	
	MyLocationOverlay myLocationOverlay = null;
	/*COSAS DEL MAPA*/
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);       
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ubicacion, container, false);
		return v;
	}
	
	@SuppressLint("NewApi")
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
        pregunta = (TextView) getActivity().findViewById(R.id.preg_ubicacion);
        sig = (Button) getActivity().findViewById(R.id.sig);
        centergps = (Button) getActivity().findViewById(R.id.centergps1);
        direccion = (EditText) getActivity().findViewById(R.id.direccion);
        
       /* sig.setOnClickListener(listener);
        centergps.setOnClickListener(listener_gps);
        */
        Bundle bundle = getArguments();
        tipoAlojamiento = (String) bundle.getString("tipoAloj");
        
        pregunta.setText("¿En qué lugar se encuentra tu "+tipoAlojamiento+"?");
 
        getActivity().getActionBar().setTitle("Ubicacion");

        Fragment fragment = new Fragment_googlemaps();       

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.frame_mapa, fragment);
        ft.commit(); 

        /*
        myOpenMapView = (MapView) getActivity().findViewById(R.id.mapView);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(14);
        Location loc = getMyLocation();
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        else {
        	GeoPoint startPoint = new GeoPoint(getMyLocation());
            myMapController.setCenter(startPoint);
        }

        
        LocationManager milocManager = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
  		LocationListener milocListener = new MiLocationListener();
  		milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
  		

  		Location valor = getMyLocation();
  		
  		if(valor==null){
  			Toast.makeText( getActivity().getApplicationContext(),"No se puede acceder",Toast.LENGTH_SHORT ).show();
  		} else{
  			lat=valor.getLatitude();
			lon=valor.getLongitude();
			//direccion.setText("Ingrese dirección del"+tipoAlojamiento);
  		}
	}
	
	OnItemGestureListener<OverlayItem> myOnItemGestureListener
    = new OnItemGestureListener<OverlayItem>(){

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			/*Toast.makeText(getActivity(), 
					item.mDescription + "\n"
					+ item.mTitle + "\n"
					+ item.mGeoPoint.getLatitudeE6() + " : " + item.mGeoPoint.getLongitudeE6(), 
					Toast.LENGTH_LONG).show();
			return true;
		}
    };*/
	}
    /*
	Location getMyLocation(){
		LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public class MiLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc){
			//String coordenadas = "Mis coordenadas son: " + "Latitud = " + lat + " Longitud = " + lon;
			//ubicacion.setText(coordenadas);
			//Toast.makeText( getActivity().getApplicationContext(),"Cambio ubicacion",Toast.LENGTH_SHORT ).show();
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
	
	private OnClickListener listener = new OnClickListener(){		//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {			
			switch(v.getId()){			
				case R.id.sig:		//Lanzamiento de una nueva actividad
					Toast.makeText(getActivity().getApplicationContext(), tipoAlojamiento, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), Activity_caracteristicas.class );
					intent.putExtra("lat", lat);
					intent.putExtra("lon", lon);
					intent.putExtra("tipo_aloj", tipoAlojamiento);
					startActivity(intent);
			}
		}
	};
	


	private OnClickListener listener_gps = new OnClickListener(){
		@Override
		public void onClick(View v){
			String dir = direccion.getText().toString();
			
			//Parseo de String destino a GeoPoint.
			Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());    
	        List<Address> address;
			try {
				address = geoCoder.getFromLocationName(dir, 1);
				lat = address.get(0).getLatitude();
		        lon = address.get(0).getLongitude();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			/*
			//Toast.makeText(getActivity(), String.valueOf(lat) + " " + String.valueOf(lon), Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "Centrando en la posición ingresada de tu alojamiento", Toast.LENGTH_SHORT).show();
			
	        anotherOverlayItemArray = new ArrayList<OverlayItem>();
	        anotherOverlayItemArray.add(new OverlayItem(
	        		"Alojamiento", "Ubicación del ", new GeoPoint(lat,lon)));
	        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
	        	= new ItemizedIconOverlay<OverlayItem>(
	        			getActivity(), anotherOverlayItemArray, myOnItemGestureListener);
	        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
	        GeoPoint Pointdept = new GeoPoint(lat,lon);

	        myMapController.setCenter(Pointdept);
	        
			
		}
	};*/
}