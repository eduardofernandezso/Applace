package com.retni.applacegps;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class posiciones {
	        //Constante de Posición del marcador
		public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432); 
	        //Constante de Opciones de Polilínea.
		public static final PolylineOptions POLILINEA = new PolylineOptions()
	                               .add(new LatLng(41.40347, 2.17432))
	                               .add(new LatLng(41.40691, 2.16864)) 
	                               .add(new LatLng(41.40364, 2.16437));

}
