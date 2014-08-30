package com.retni.applacegps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint({ "NewApi", "InlinedApi" })
public class Fragment_tipoAloj extends Fragment {
	
	private ListView tiposList;
	private String[] tipos;
	String tipoAloj;
	TextView pregunta;
	CharSequence tituloSeccion;
	FrameLayout frame_aloj;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);       
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tipoaloj, container, false);
		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
        tiposList = (ListView) getActivity().findViewById(R.id.list_tipo);
        frame_aloj = (FrameLayout) getActivity().findViewById(R.id.frame_aloj);
        
        tipos = new String[] {"Casa", "Departamento", "Cabaña", "Hostal", "Camping"};
        
        tiposList.setAdapter(new ArrayAdapter<String>(
                getActivity().getActionBar().getThemedContext(),
            android.R.layout.simple_list_item_activated_1, tipos));
        
        tiposList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
     
                switch (position) {
                    case 0:
                        tipoAloj = "casa";                        
                        break;
                    case 1:
                    	tipoAloj = "departamento";                        
                        break;
                    case 2:
                    	tipoAloj = "cabaña";                        
                        break;
                    case 3:
                    	tipoAloj = "hostal";                    	
                    	break;
                    case 4:
                    	tipoAloj = "camping";                    	
                    	break;
                }
             
                Fragment fragment = new Fragment_ubicacion();
                
                Bundle tipoSelected = new Bundle();
                tipoSelected.putString("tipoAloj", tipoAloj);
                fragment.setArguments(tipoSelected);
                     
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit(); 
            }
        });
	}
}
