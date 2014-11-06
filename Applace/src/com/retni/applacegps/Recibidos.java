package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class Recibidos extends ActionBarActivity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = Recibidos.this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
		Fragment fragment = new Fragment_mensajes2();
		ft.replace(R.id.content_frame, fragment);
        ft.commit(); 
	}
}
