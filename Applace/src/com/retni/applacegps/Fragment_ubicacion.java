package com.retni.applacegps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
	EditText direccion;
	
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
        direccion = (EditText) getActivity().findViewById(R.id.direccion);
        
        sig.setOnClickListener(listener);
        
        Bundle bundle = getArguments();
        tipoAlojamiento = (String) bundle.getString("tipoAloj");
        
        pregunta.setText("¿En qué lugar se encuentra tu "+tipoAlojamiento+"?");
        
        getActivity().getActionBar().setTitle("ubicacion");
        
        Fragment_mapa fragment = new Fragment_mapa();  
        
        int i=1;
        
        Bundle tipoSelected = new Bundle();
        tipoSelected.putInt("ubicacion", i);
        fragment.setArguments(tipoSelected);
        
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.frame_mapa, fragment);
        ft.commit(); 
        
        LocationManager milocManager = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
  		LocationListener milocListener = new MiLocationListener();
  		milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000*2, 10, milocListener);
  		
  		Location valor = getMyLocation();
  		if(valor==null){
  			Toast.makeText( getActivity().getApplicationContext(),"No se puede acceder",Toast.LENGTH_SHORT ).show();
  		} else{
  			lat=valor.getLatitude();
			lon=valor.getLongitude();
			
			direccion.setText(lat+", "+lon);
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
}