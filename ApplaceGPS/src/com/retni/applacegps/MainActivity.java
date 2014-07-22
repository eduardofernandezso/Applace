package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends FragmentActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        launch_home();
    }
	
	private void launch_home() {//identificamos y cargamos el fragmento
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main"))==null){
			ft.add(R.id.frame_ini, new Fragment_inicio(),"main"); //en caso que sea null cargamos uno nuevo en el frame y con el tag "main"
		}
		else{
			if(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main")).isDetached()){
				ft.attach(((Fragment_inicio) getSupportFragmentManager().findFragmentByTag("main")));//en caso que no sea null, cargamos el que ya existe
			}
		}
		ft.commit();
	}
}
