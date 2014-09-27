package com.retni.applacegps;


import java.util.ArrayList;
import java.util.List;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
	
	private MapView myOpenMapView;
	private MapController myMapController;	
	ArrayList<OverlayItem> anotherOverlayItemArray;	
	MyLocationOverlay myLocationOverlay = null;
	List<ParseObject> alojamientos;

	
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
                

        myOpenMapView = (MapView) getActivity().findViewById(R.id.mapView);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(14);
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
				String titulo;
				int precio;
				double latit, longi;
				ParseObject aloj = null;
				for(int i=0 ; i < size_aloj ; i++){
					aloj = alojamientos.get(i);
					titulo = aloj.getString("titulo");
					precio = aloj.getInt("precio");
					latit = aloj.getDouble("dir_latitud");
					longi = aloj.getDouble("dir_longitud");
					
					GeoPoint punto = new GeoPoint(latit, longi);
					
					agregar_punto(titulo, precio, punto);
				}
			} 
        }
		
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(getActivity());
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
        
        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationOverlay(getActivity(), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationOverlay);
        myOpenMapView.postInvalidate(); 
	}
	
	public void agregar_punto(String tit, int pre, GeoPoint p){	
		
        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        anotherOverlayItemArray.add(new OverlayItem(tit, ""+pre, new GeoPoint(p)));
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

		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			Toast.makeText(getActivity(), 
					item.mDescription + "\n"
					+ item.mTitle + "\n"
					+ item.mGeoPoint.getLatitudeE6() + " : " + item.mGeoPoint.getLongitudeE6(), 
					Toast.LENGTH_LONG).show();
			return true;
		}
    	
    };
	
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
		myLocationOverlay.enableCompass();		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();

	}	
}