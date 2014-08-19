package com.retni.applacegps;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.SimpleLocationOverlay;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Fragment_mapa extends Fragment{
	
	private MapView mapView;
	private IMapController mapController;
	double lat=0;
	double lon=0;
	
	public Fragment_mapa(){
	
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);                  
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mapa, container, false);
		
		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
  	    
  		LocationManager milocManager = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
  		LocationListener milocListener = new MiLocationListener();
  		milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
  		
  		//ubicacion = (TextView)findViewById(R.id.ubicacion);
  		
  		mapView = (MapView) getActivity().findViewById(R.id.mapView);
  		mapView.setTileSource(TileSourceFactory.MAPNIK);
  		mapView.setMultiTouchControls(true);
  		mapView.setBuiltInZoomControls(true);
  		mapController = mapView.getController();	
  		mapController.setZoom(16);
  			
  		Location valor = getMyLocation();
  		if(valor==null){
  			Toast.makeText( getActivity().getApplicationContext(),"No se puede acceder",Toast.LENGTH_SHORT ).show();
  		}
  		else{
  			milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
  			//ubicacion.setText("Latitud: "+valor.getLatitude()+" y Longitud: "+valor.getLongitude());
  			do{	
  				GeoPoint myLocation = new GeoPoint(getMyLocation());
  					
  				SimpleLocationOverlay myLocationOverlay = new SimpleLocationOverlay(getActivity().getApplicationContext());
  				mapView.getOverlays().add(myLocationOverlay);
  		 
  				mapController.setCenter(myLocation);
  				myLocationOverlay.setLocation(myLocation);
  				lat=valor.getLatitude();
  				lon=valor.getLongitude();
  				
  			}
  			while(lat!=valor.getLatitude() || lon!=valor.getLongitude());
  		}		      
	}
	
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
			Toast.makeText( getActivity().getApplicationContext(),"Cambio ubicacion",Toast.LENGTH_SHORT ).show();
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
}
