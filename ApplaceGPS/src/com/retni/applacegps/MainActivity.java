package com.retni.applacegps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.SimpleLocationOverlay;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

public class MainActivity extends Activity {
	
	TextView ubicacion;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ubicacion = (TextView)findViewById(R.id.ubicacion);
		
		MapView mapView = (MapView) findViewById(R.id.mapView);
		mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
		mapView.setMultiTouchControls(true);
		IMapController mapController = mapView.getController();
		mapController.setZoom(18);
		
		Location valor = getMyLocation();
		ubicacion.setText("Latitud: "+valor.getLatitude()+" y Longitud: "+valor.getLongitude());
				
		GeoPoint myLocation = new GeoPoint(getMyLocation());
				
		SimpleLocationOverlay myLocationOverlay = new SimpleLocationOverlay(this);
		mapView.getOverlays().add(myLocationOverlay);
 
		mapController.setCenter(myLocation);
		myLocationOverlay.setLocation(myLocation);
	}
	
	Location getMyLocation(){
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
