package com.retni.applacegps;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Activity_tabFiltros extends ActionBarActivity{
	
	List<ParseObject> men1;
	List<ParseObject> men2;
	List<ParseObject> men3;
	
	LinearLayout msje_menu;
	Button msje_volver, msje_borrar;
	
	List<ParseObject> convers;
	
	public ArrayList<Double> la = new ArrayList<Double>();
	public ArrayList<Double> lo = new ArrayList<Double>();
	public ArrayList<String> idAloj = new ArrayList<String>();
	public ArrayList<String> tit = new ArrayList<String>();
	public ArrayList<Integer> pre = new ArrayList<Integer>();
	public ArrayList<Float> ranking = new ArrayList<Float>();
	public ArrayList<Integer> countRanking = new ArrayList<Integer>();
	public ArrayList<Bitmap> fot = new ArrayList<Bitmap>();
	public ArrayList<Boolean> est = new ArrayList<Boolean>();
	String titulo, id_aloj;
	Integer precio, count_ratings;
	Boolean estado;
	
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensajes);
		
		Intent in = getIntent();       
        la = (ArrayList<Double>) in.getSerializableExtra("las");
        lo = (ArrayList<Double>) in.getSerializableExtra("los");
        tit = in.getStringArrayListExtra("titulo");
        idAloj = in.getStringArrayListExtra("idAloj");
        pre = in.getIntegerArrayListExtra("precio");
        ranking = (ArrayList<Float>) in.getSerializableExtra("ranking");
        countRanking = in.getIntegerArrayListExtra("countRanking");
        est =(ArrayList<Boolean>) in.getSerializableExtra("estado");
        //fot = (ArrayList<Bitmap>) in.getSerializableExtra("foto");
		/*
		msje_menu = (LinearLayout) findViewById(R.id.msje_menu);
		msje_volver = (Button) findViewById(R.id.msje_volver);
		msje_borrar = (Button) findViewById(R.id.msje_borrar);
		*/
		
		setupTabs(0);
	}
	
	private void setupTabs(Integer delete) {
		
        String uno = Integer.toString(idAloj.size());
        
		ActionBar actionBar = getSupportActionBar();
		if(actionBar.getTabCount()!=0)
			actionBar.removeAllTabs();
		
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);
	    
	    Bundle j = new Bundle();
	    j.putSerializable("las",la);
        j.putSerializable("los", lo);
        j.putSerializable("idAloj", idAloj);
        j.putSerializable("titulo",tit);
        j.putSerializable("precio", pre);
        j.putSerializable("ranking", ranking);
        j.putSerializable("countRanking",countRanking);
        j.putSerializable("estado",est);
        //j.putSerializable("foto", foto);
	    //args.putInt("delete", delete);

	    Tab tab = actionBar.newTab()
	                       .setText("Lista ("+uno+")")
	                       .setTabListener(new TabListener<Fragment_filtroList>(
	                               this, "lista", Fragment_filtroList.class, j));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	                   .setText("Mapa ("+uno+")")
	                   .setTabListener(new TabListener<fragment_filtro>(
	                           this, "mapa", fragment_filtro.class, j));
	    actionBar.addTab(tab);
	    
	    /*
	    if(delete==1){
			msje_menu.setVisibility(View.VISIBLE);
			msje_volver.setOnClickListener(listener);
		}*/		
    }
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.msje_volver) {
				setupTabs(0);
			}
			else if(id == R.id.msje_borrar){
				
			}
		}
	};
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);		
		
		menu.findItem(R.id.como_funciona).setVisible(false);
		menu.findItem(R.id.codiciones).setVisible(false);
		menu.findItem(R.id.politica).setVisible(false);
		menu.findItem(R.id.ayuda).setVisible(false);
		
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_edit).setVisible(false);
		menu.findItem(R.id.action_config).setVisible(false);
		menu.findItem(R.id.action_share).setVisible(false);
		menu.findItem(R.id.action_update).setVisible(false);
		menu.findItem(R.id.action_camara).setVisible(false);
		menu.findItem(R.id.action_delete).setVisible(false);
		menu.findItem(R.id.action_new).setVisible(false);
		
		getSupportActionBar().setTitle("Resultados");
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
	        case R.id.action_delete:	
	        	setupTabs(1);
            	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }	 
	    return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
			case KeyEvent.KEYCODE_BACK:
				startActivity(new Intent(this, Logueado.class));
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
		}
		return super.onKeyDown(keyCode, event);
	}
}
