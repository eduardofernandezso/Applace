package com.retni.gpsactivity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
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

public class MainActivity extends Activity {
	
	
	TextView ubicacion; 
	//double u1=0;
	//double u2=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ubicacion = (TextView)findViewById(R.id.textView1);
		
		//ubicacion.setText("hola");
		LocationManager milocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener milocListener = new MiLocationListener();
		milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
		
		//ubicacion.setText(u1+","+u2);
	}

	public class MiLocationListener implements LocationListener
	{
		public void onLocationChanged(Location loc){
			loc.getLatitude();
			loc.getLongitude();
			String coordenadas = "Mis coordenadas son: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
			ubicacion.setText(coordenadas);
			Toast.makeText( getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
		}
		
		public void onProviderDisabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
		}
		
		public void onProviderEnabled(String provider){
			Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
		}
		
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
