package com.retni.applacegps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.osmdroid.util.GeoPoint;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.retni.applacegps.Fragment_googlemaps.MyMarker;
import com.retni.applacegps.Fragment_listaAloj.Cursor_AdapterList;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class fragment_filtro extends Fragment{
	
	GoogleMap mapa = null;
	List<ParseObject> alojamientos;
	MarkerOptions markerOptions;
    LatLng latLng;
    ParseFile img = null;
    String id_alojamiento;
    int size_aloj = 0;
    String titulo, id_aloj;
	Integer precio, count_ratings;
	Boolean estado;
	double latit, longi;
	Float ratings;
	Bitmap foto;
    GeoPoint punto = null;
    Marker marker;
    TransparentProgressDialog pd;
    List<Bitmap> fotosi = new ArrayList<Bitmap>();
    List<ParseFile> fotos = new ArrayList<ParseFile>();
    
    public ArrayList<Double> la = new ArrayList<Double>();
	public ArrayList<Double> lo = new ArrayList<Double>();
	public ArrayList<String> idAloj = new ArrayList<String>();
	public ArrayList<String> tit = new ArrayList<String>();
	public ArrayList<Integer> pre = new ArrayList<Integer>();
	public ArrayList<Double> ranking = new ArrayList<Double>();
	public ArrayList<Integer> countRanking = new ArrayList<Integer>();
	public ArrayList<Bitmap> fot = new ArrayList<Bitmap>();
	public ArrayList<Boolean> est = new ArrayList<Boolean>();
    
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
	
	public fragment_filtro() {
		// TODO Auto-generated constructor stub
	}
	
	public class MyMarker
	{
	    private String mLabel;
	    private ParseFile mIcon;
	    private Double mLatitude;
	    private Double mLongitude;
	    private Boolean mEstado;
	    private Integer mPrecio, mCount_rating;
	    private Float mRating;
	    private String id_aloja;
	    private Bitmap mFoto;

	    public MyMarker(Bitmap foto, String label, Double latitude, Double longitude, Boolean estado, Integer precio, Float rating, Integer count_rating, String id_aloja)
	    {
	    	this.mFoto = foto;
	        this.mLabel = label;
	        this.mLatitude = latitude;
	        this.mLongitude = longitude;
	        //this.mIcon = icon;
	        this.mEstado = estado;
	        this.mPrecio = precio;
	        this.mCount_rating = count_rating;
	        this.mRating = rating;
	        this.id_aloja = id_aloja;
	    }
	    public Bitmap getmFoto()
	    {
	        return mFoto;
	    }

	    public void setmFoto(Bitmap mFoto)
	    {
	        this.mFoto = mFoto;
	    }

	    public String getmLabel()
	    {
	        return mLabel;
	    }

	    public void setmLabel(String mLabel)
	    {
	        this.mLabel = mLabel;
	    }

	    public ParseFile getmIcon()
	    {
	        return mIcon;
	    }

	    public void setmIcon(ParseFile icon)
	    {
	        this.mIcon = icon;
	    }

	    public Double getmLatitude()
	    {
	        return mLatitude;
	    }

	    public void setmLatitude(Double mLatitude)
	    {
	        this.mLatitude = mLatitude;
	    }

	    public Double getmLongitude()
	    {
	        return mLongitude;
	    }

	    public void setmLongitude(Double mLongitude)
	    {
	        this.mLongitude = mLongitude;
	    }
	    
	    public void setmEstado(Boolean b){
	    	this.mEstado = b;
	    }
	    
	    public Boolean getmEstado()
	    {
	        return mEstado;
	    }
	    
	    public void setmPrecio(Integer precio){
	    	this.mPrecio = precio;
	    }
	    
	    public Integer getmPrecio()
	    {
	        return mPrecio;
	    }
	    
	    public void setmCount_rating(Integer count_rating){
	    	this.mCount_rating = count_rating;
	    }
	    
	    public Integer getmCount_rating()
	    {
	        return mCount_rating;
	    }
	    
	    public void setmRating(Float rating){
	    	this.mRating = rating;
	    }
	    
	    public Float getmRating()
	    {
	        return mRating;
	    }
	    
	    public String getmId()
	    {
	        return id_aloja;
	    }

	    public void setmId(String id_aloja)
	    {
	        this.id_aloja = id_aloja;
	    }
	}	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); 
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_googlemaps, container, false);
		return v;
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    SupportMapFragment f = (SupportMapFragment) getFragmentManager()
	                                         .findFragmentById(R.id.map);
	    if (f != null) 
	        getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState); 
        
        mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        pd = new TransparentProgressDialog(getActivity(), R.drawable.prog_applace);
        
        Bundle bundle = getArguments();        
        la = (ArrayList<Double>) bundle.getSerializable("las");
        lo = (ArrayList<Double>) bundle.getSerializable("los");
        tit = (ArrayList<String>) bundle.getSerializable("titulo");
        idAloj = (ArrayList<String>) bundle.getSerializable("idAloj");
        pre = (ArrayList<Integer>) bundle.getSerializable("precio");
        ranking = (ArrayList<Double>) bundle.getSerializable("ranking");
        countRanking = (ArrayList<Integer>) bundle.getSerializable("countRanking");
        est = (ArrayList<Boolean>) bundle.getSerializable("estado");
        //fot = (ArrayList<Bitmap>) bundle.getSerializable("foto");
        
        Location loc = getMyLocation();
        if (loc == null){
        	Toast.makeText( getActivity().getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        } else{
        	GeoPoint startPoint = new GeoPoint(getMyLocation());
        	Double lat, lon;
        	lat = startPoint.getLatitude();
        	lon = startPoint.getLongitude();
        	CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 15);
            mapa.moveCamera(camUpd1);
        }
		
		if (idAloj.size()==0){
			Toast.makeText( getActivity().getApplicationContext(),"No hay datos para cargar.",Toast.LENGTH_SHORT ).show();
		} else{
			Drawable myDrawable = getResources().getDrawable(R.drawable.img_defecto);
	    	Bitmap fo = ((BitmapDrawable) myDrawable).getBitmap();
	    	mMarkersHashMap = new HashMap<Marker, MyMarker>();
			for(int h=0; h<idAloj.size() ;h++){				
				//fot.add(fo);				
        		mMyMarkersArray.add(new MyMarker(fo, tit.get(h), la.get(h), lo.get(h), est.get(h), pre.get(h), ranking.get(h).floatValue(), countRanking.get(h), idAloj.get(h)));
        	}
			Toast.makeText(getActivity().getApplicationContext(), mMyMarkersArray.size()+": tamaño", Toast.LENGTH_SHORT).show();
			plotMarkers(mMyMarkersArray);
		}
	
	}
	
private class TransparentProgressDialog extends Dialog {
		
		private ImageView iv;
			
		public TransparentProgressDialog(Context context, int resourceIdOfImage) {
			super(context, R.style.TransparentProgressDialog);
	        	WindowManager.LayoutParams wlmp = getWindow().getAttributes();
	        	wlmp.gravity = Gravity.CENTER_HORIZONTAL;
	        	getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			iv = new ImageView(context);
			iv.setImageResource(resourceIdOfImage);
			layout.addView(iv, params);
			addContentView(layout, params);
		}
			
		@Override
		public void show() {
			super.show();
			RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(Animation.INFINITE);
			anim.setDuration(3000);					
			iv.setAnimation(anim);
			iv.startAnimation(anim);
		}
	}
	
	private void plotMarkers(final ArrayList<MyMarker> markers){
		if(markers.size() > 0){
	        for (final MyMarker myMarker : markers){
	        	MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
	        	
	        	if(myMarker.getmEstado()==true)
	        		markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	        	else if(myMarker.getmEstado()==false)
	        		markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

	            Marker currentMarker = mapa.addMarker(markerOption);
	            mMarkersHashMap.put(currentMarker, myMarker);
	            //id_alojamiento = myMarker.getmId();
	            currentMarker.setTitle(myMarker.getmId());
	            
		        mapa.setInfoWindowAdapter(new MarkerInfoWindowAdapter());	            
		        mapa.setOnInfoWindowClickListener(listener);
			    //currentMarker.showInfoWindow();		            
	        }
		}
	}
	
	private OnInfoWindowClickListener listener = new OnInfoWindowClickListener(){		

		@Override
		public void onInfoWindowClick(Marker arg0) {
			// TODO Auto-generated method stub
			id_alojamiento = arg0.getTitle();
			Intent intent = new Intent(getActivity(), Activity_verAlojamiento.class);
			intent.putExtra("idAloj", idAloj);
			startActivity(intent);	
			//Toast.makeText(getActivity().getApplicationContext(), id_alojamiento+"", Toast.LENGTH_SHORT).show();
		}
	};
	
	public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
	    public MarkerInfoWindowAdapter(){
	    	
	    }
	    
	    @Override
	    public View getInfoContents(final Marker marker) {
	        
	        View v  = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_aloj_row, null);
	        final MyMarker myMarker = mMarkersHashMap.get(marker);
	        
	        final TextView titulo = (TextView)v.findViewById(R.id.row_titulo);
		    final TextView precio = (TextView)v.findViewById(R.id.row_precio);
		    final ImageView imgh = (ImageView)v.findViewById(R.id.row_img);
		    final RatingBar star = (RatingBar)v.findViewById(R.id.row_star);	
		    final TextView count = (TextView)v.findViewById(R.id.row_count);
		    
		    
		    titulo.setText(myMarker.getmLabel());
	        precio.setText("$"+myMarker.getmPrecio());
	        star.setRating(myMarker.getmRating());
	        count.setText(myMarker.getmCount_rating().toString());  
	        //imgh.setImageBitmap(myMarker.getmFoto());

	        return v;
	    }

	    @Override
	    public View getInfoWindow(Marker marker){
	    	
	    	return null;
	    }
	}
	
	public void refresh(){
		GeoPoint startPoint = new GeoPoint(getMyLocation());
    	Double lat, lon;
    	lat = startPoint.getLatitude();
    	lon = startPoint.getLongitude();
    	CameraUpdate camUpd1 = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
        mapa.moveCamera(camUpd1);
        //mapa.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("Mi posición"));
	}
	
	Location getMyLocation(){
		MiLocationListener loc= new MiLocationListener();
		LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, loc);
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public class MiLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc){
			//refresh();
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
		mapa.setMyLocationEnabled(true);
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapa.setMyLocationEnabled(false);
	}
}
