package com.retni.applacegps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class transitiva extends Fragment {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);  
    	
        Intent intent = new Intent(getActivity().getApplication(), Logueado.class);
        startActivity(intent);
        
        	
                   
	}
}