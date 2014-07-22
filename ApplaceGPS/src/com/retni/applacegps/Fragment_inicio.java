package com.retni.applacegps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockFragment;

public class Fragment_inicio extends Fragment{
	
	Button facebook;
	Button iniCorreo;
	Button regCorreo;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.inicio, container, false);
		return v;
	}	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
        facebook = (Button) getActivity().findViewById(R.id.facebook);
		iniCorreo = (Button) getActivity().findViewById(R.id.iniCorreo);
		regCorreo = (Button) getActivity().findViewById(R.id.regCorreo);
		
		facebook.setOnClickListener(listener);
		iniCorreo.setOnClickListener(listener);
		regCorreo.setOnClickListener(listener);
	}
	
	private OnClickListener listener = new OnClickListener(){		//Mismo listener para multiples botones
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			int id = v.getId();
			if (id == R.id.facebook) {
				intent = new Intent(getActivity(), Logueado.class);
				startActivity(intent);
			} else if (id == R.id.iniCorreo) {
				intent = new Intent(getActivity(), iniciarSesion_correo.class);
				startActivity(intent);
			} else if (id == R.id.regCorreo) {
				intent = new Intent(getActivity(), Logueado.class);
				startActivity(intent);
			}
		}
	};
}
