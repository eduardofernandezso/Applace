package com.retni.applacegps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.location.Geocoder;


@SuppressWarnings({ "deprecation", "unused" })
public class Fragment_mapaRuta extends Fragment{
	private MapView myOpenMapView;
	private MapController myMapController;
	private GeoPoint startPoint;
	
	RelativeLayout rutaLayout;
	EditText endRoute;
	Button createRouteButton;
	
	ArrayList<OverlayItem> anotherOverlayItemArray;
	
	MyLocationOverlay myLocationOverlay = null;
	
	public Fragment_mapaRuta() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); 
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mapa_ruta, container, false);
		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
        myOpenMapView = (MapView) getActivity().findViewById(R.id.mapView);
        rutaLayout = (RelativeLayout)getActivity().findViewById(R.id.layout_ruta);
        endRoute = (EditText)getActivity().findViewById(R.id.endPoint);
        createRouteButton = (Button)getActivity().findViewById(R.id.createRouteButton);
        
        createRouteButton.setOnClickListener(listener_ruta);
        
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(14);
        Location loc = getMyLocation();
        myOpenMapView.setMultiTouchControls(true);
        
        
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        else {
        	startPoint = new GeoPoint(getMyLocation());
            myMapController.setCenter(startPoint);
        }        
        

        
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(getActivity());
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
        
        //Add MyLocationOverlay
        myLocationOverlay = new MyLocationOverlay(getActivity(), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationOverlay);
        myOpenMapView.postInvalidate();

        /*
        MinimapOverlay miniMapOverlay = new MinimapOverlay(getActivity(), myOpenMapView.getTileRequestCompleteHandler());
        miniMapOverlay.setZoomDifference(5);
        miniMapOverlay.setHeight(200);
        miniMapOverlay.setWidth(200);
        myOpenMapView.getOverlays().add(miniMapOverlay);*/
        
        
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
					Toast.LENGTH_LONG).show();*/
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
	
	private android.view.View.OnClickListener listener_ruta = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String endAt = endRoute.getText().toString();
			GeoPoint start = new GeoPoint(getMyLocation());
			//Toast.makeText(myOpenMapView.getContext(), startPoint.toString(), Toast.LENGTH_SHORT).show();
			
			//Parseo de String destino a GeoPoint.
			Geocoder geoCoder = new Geocoder(myOpenMapView.getContext(), Locale.getDefault());     
	        List<Address> address;
	        double latitude = 0;
	        double longitude = 0;
			try {
				address = geoCoder.getFromLocationName(endAt, 1);
				latitude = address.get(0).getLatitude();
		        longitude = address.get(0).getLongitude();  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			
			StrictMode.setThreadPolicy(policy);
			
			//Construcción de la ruta.
	        RoadManager roadManager = new OSRMRoadManager();
	        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
	        waypoints.add(start);
	        GeoPoint endPoint = new GeoPoint(latitude, longitude);
            waypoints.add(endPoint);
            
            Road road = roadManager.getRoad(waypoints);
            /*if (road.mStatus != Road.STATUS_OK){
                Toast.makeText(myOpenMapView.getContext(), "Error when loading the road - status="+road.mStatus, Toast.LENGTH_SHORT).show();
                return;
            }*/
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road, myOpenMapView.getContext());
            roadOverlay.setColor(Color.CYAN);
            myOpenMapView.getOverlays().add(roadOverlay);
            myOpenMapView.invalidate();
		}
	};	
}
