package com.retni.applacegps;


import java.util.ArrayList;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
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
        
        myOpenMapView = (MapView) getActivity().findViewById(R.id.mapView);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(14);
        Location loc = getMyLocation();
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        else {
        	GeoPoint startPoint = new GeoPoint(getMyLocation());
            myMapController.setCenter(startPoint);
        }        
        
        //--- Create Another Overlay for multi marker
        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        anotherOverlayItemArray.add(new OverlayItem(
        		"Universidad Tecnica Federico Santa Maria", "USM", new GeoPoint(-33.49066,-70.61899)));
        anotherOverlayItemArray.add(new OverlayItem(
        		"Acá como", "Carrito de los churrascos", new GeoPoint(-33.50868, -70.64477)));
        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
        	= new ItemizedIconOverlay<OverlayItem>(
        			getActivity(), anotherOverlayItemArray, myOnItemGestureListener);
        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
        //---
        
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(getActivity());
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
        
        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationOverlay(getActivity(), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationOverlay);
        myOpenMapView.postInvalidate();

        
        MinimapOverlay miniMapOverlay = new MinimapOverlay(getActivity(), myOpenMapView.getTileRequestCompleteHandler());
        miniMapOverlay.setZoomDifference(5);
        miniMapOverlay.setHeight(200);
        miniMapOverlay.setWidth(200);
        myOpenMapView.getOverlays().add(miniMapOverlay);
        

        
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
