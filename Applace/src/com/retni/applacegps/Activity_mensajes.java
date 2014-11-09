package com.retni.applacegps;

import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
public class Activity_mensajes extends ActionBarActivity{
	
	List<ParseObject> men1;
	List<ParseObject> men2;
	List<ParseObject> men3;
	
	LinearLayout msje_menu;
	Button msje_volver, msje_borrar;
	
	List<ParseObject> convers;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mensajes);
		/*
		msje_menu = (LinearLayout) findViewById(R.id.msje_menu);
		msje_volver = (Button) findViewById(R.id.msje_volver);
		msje_borrar = (Button) findViewById(R.id.msje_borrar);
		*/
		
		setupTabs(0);
	}
	
	private void setupTabs(Integer delete) {		
		
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
        ParseUser user = new ParseUser();
        user = ParseUser.getCurrentUser();                    
        
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensajes");
        query.whereEqualTo("envia", user.getEmail());

        ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Conversacion");
        query3.whereEqualTo("id_user1", user.getObjectId());
		query3.orderByDescending("_created_at");

		try {
			convers = query3.find();
		} catch (ParseException e) {}
        
        int c = convers.size();
        String tres = Integer.toString(c);
        if(c == 0){
        	ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>("Conversacion");
            query4.whereEqualTo("id_user2", user.getObjectId());
    		query4.orderByDescending("_created_at");
    		
    		try {
    			convers = query4.find();
    		} catch (ParseException e) {}
            
            c = convers.size();
            tres = Integer.toString(c);
        }        
		
        try {
			men1 = query.find();
		} catch (ParseException e) {

		}
        int a = men1.size();
        String uno = Integer.toString(a);
        
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Notificacion");
        query2.whereEqualTo("id_user", user.getObjectId());
        try {
			men2 = query2.find();
		} catch (ParseException e) {

		}
        int b = men2.size();
        String dos = Integer.toString(b);
        
		ActionBar actionBar = getSupportActionBar();
		if(actionBar.getTabCount()!=0)
			actionBar.removeAllTabs();
		
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);
	    
	    Bundle args = new Bundle();
	    args.putInt("delete", delete);

	    Tab tab = actionBar.newTab()
	                       .setText("conversaciones ("+tres+")")
	                       .setTabListener(new TabListener<Fragment_conversaciones>(
	                               this, "conversaciones", Fragment_conversaciones.class, args));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	                   .setText("notificaciones ("+dos+")")
	                   .setTabListener(new TabListener<Fragment_notificaciones>(
	                           this, "notificaciones", Fragment_notificaciones.class, args));
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
		menu.findItem(R.id.action_delete).setVisible(true);
		menu.findItem(R.id.action_new).setVisible(false);
		
		getSupportActionBar().setTitle("Mis Mensajes");
		
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