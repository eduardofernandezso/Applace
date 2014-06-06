package com.retni.gpsactivity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
//import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.os.Build;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.SimpleLocationOverlay;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

public class MainActivity extends Activity {
	
	TextView ubicacion; 
	private MapView mapView;
	private MapController mapController;
	double lat=0;
	double lon=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ubicacion = (TextView)findViewById(R.id.textView1);
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setTileSource(TileSourceFactory.MAPNIK);
		mapView.setMultiTouchControls(true);
		mapView.setBuiltInZoomControls(true);
		
		mapController = (MapController) mapView.getController();
		mapController.setZoom(12);
		
		LocationManager milocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener milocListener = new MiLocationListener();
		milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
		
		GeoPoint myLocation = new GeoPoint(lat,lon);
		
		SimpleLocationOverlay myLocationOverlay = new SimpleLocationOverlay(this);
		mapView.getOverlays().add(myLocationOverlay);
		mapController.setCenter(myLocation);
		myLocationOverlay.setLocation(myLocation);	
	}

	public class MiLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			String coordenadas = "Mis coordenadas son: " + "Latitud = " + lat + " Longitud = " + lon;
			ubicacion.setText(coordenadas);
		}
		
		@Override
		public void onProviderDisabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
		}
		
		@Override
		public void onProviderEnabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
