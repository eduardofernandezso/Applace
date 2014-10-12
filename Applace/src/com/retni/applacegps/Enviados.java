package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class Enviados extends ActionBarActivity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = Enviados.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
		Fragment fragmento = new Fragment_mensajes();
		ft.replace(R.id.content_frame, fragmento);
        ft.commit(); 
	}
}
