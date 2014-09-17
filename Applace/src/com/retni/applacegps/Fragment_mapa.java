package com.retni.applacegps;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;

import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class Fragment_mapa extends Fragment{
	
	Button centergps;
	private MapView myOpenMapView;
	private MapController myMapController;
	ArrayList<OverlayItem> anotherOverlayItemArray;
	MyLocationNewOverlay myLocationOverlay = null;
	List<ParseObject> alojamientos;
	
	public ArrayList<Double> la = new ArrayList<Double>();
	public ArrayList<Double> lo = new ArrayList<Double>();
	
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
        
        //Se verifica si el fragmento es cargado para Fragment_ubicacion
        Bundle bundle = getArguments();
        int valor = 0;
        if(bundle != null){
        	valor = bundle.getInt("ubicacion");
        } 
        /*
        la = (ArrayList<Double>) bundle.getSerializable("las");
        lo = (ArrayList<Double>) bundle.getSerializable("los");  
        int flag = bundle.getInt("flag", -1);
        
        if(flag!=-1){
        	Toast.makeText( getActivity().getApplicationContext(),"entre al flag",Toast.LENGTH_SHORT ).show();
        	for(int i=0 ; i<la.size() ; i++){
        	GeoPoint punto = new GeoPoint(la.get(i), lo.get(i));
			
			agregar_punto("titulo", "id", punto);
        	}
        }*/
        
        
        myOpenMapView = (MapView) getActivity().findViewById(R.id.mapView);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(14);
        centergps = (Button) getActivity().findViewById(R.id.centergps1);
        centergps.setOnClickListener(listener_gps);
        myOpenMapView.setMultiTouchControls(true);
        Location loc = getMyLocation();
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        else {
        	GeoPoint startPoint = new GeoPoint(getMyLocation());
            myMapController.setCenter(startPoint);
        }        

        if(valor != 1){
 	       
	        Parse.initialize(getActivity(), "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");        
	        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Alojamiento");
	
			try {
				alojamientos = query.find();
			} catch (ParseException e) {
				Toast.makeText( getActivity().getApplicationContext(),"Error.",Toast.LENGTH_SHORT ).show();
			}
			
			int size_aloj = 0;
			size_aloj = alojamientos.size();
			
			if (size_aloj == 0){
				Toast.makeText( getActivity().getApplicationContext(),"No hay datos para cargar.",Toast.LENGTH_SHORT ).show();
			}
			else{
				String titulo, id_aloj;
				int precio;
				double latit, longi;
				ParseObject aloj = null;
				for(int i=0 ; i < size_aloj ; i++){
					aloj = alojamientos.get(i);
					titulo = aloj.getString("titulo");
					precio = aloj.getInt("precio");
					latit = aloj.getDouble("dir_latitud");
					longi = aloj.getDouble("dir_longitud");
					id_aloj = aloj.getObjectId();
					
					GeoPoint punto = new GeoPoint(latit, longi);
					
					agregar_punto(titulo, id_aloj, punto);
				}
			} 
        }        
        
        
        
        

        
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(getActivity());
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
        
        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationNewOverlay(getActivity(), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationOverlay);
        myOpenMapView.postInvalidate();

        /*
        MinimapOverlay miniMapOverlay = new MinimapOverlay(getActivity(), myOpenMapView.getTileRequestCompleteHandler());
        miniMapOverlay.setZoomDifference(5);
        miniMapOverlay.setHeight(200);
        miniMapOverlay.setWidth(200);
        myOpenMapView.getOverlays().add(miniMapOverlay);
        */

        
	}
	
	public void agregar_punto(String tit, String id, GeoPoint p){	
		
        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        anotherOverlayItemArray.add(new OverlayItem(tit, id, new GeoPoint(p)));
        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
        	= new ItemizedIconOverlay<OverlayItem>(
        			getActivity(), anotherOverlayItemArray, myOnItemGestureListener);
        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
	}
	
	
	OnItemGestureListener<OverlayItem> myOnItemGestureListener
    = new OnItemGestureListener<OverlayItem>(){

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}
		Intent intent = null;
		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			
			String id = item.getSnippet();
			Intent i = new Intent(getActivity(), Activity_verAlojamiento.class);
			i.putExtra("idAloj", id);
			startActivity(i);
			return true;
			/*
			Toast.makeText(getActivity(), 
					item.getSnippet() + "\n"
					+ item.getTitle() + "\n"
					+ item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(), 
					Toast.LENGTH_LONG).show();
			*/
		}
    	
    };
	
	private OnClickListener listener_gps = new OnClickListener(){
		@Override
		public void onClick(View v){
			GeoPoint startPoint = new GeoPoint(getMyLocation());
			myMapController.setCenter(startPoint);
			Toast.makeText(getActivity(), "Centrando mapa", Toast.LENGTH_SHORT).show();					
		}
	};
    
    /*click en mapa*/

    

    
    /*click en mapa*/
    
	Location getMyLocation(){
		LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public class MiLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc){
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
		myLocationOverlay.enableMyLocation();
	//	myLocationOverlay.enableCompass();
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationOverlay.disableMyLocation();
	//	myLocationOverlay.disableCompass();
	}
	
}
