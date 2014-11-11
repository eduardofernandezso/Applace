package com.retni.applacegps;

import java.util.List;

import com.parse.DeleteCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Activity_tabmensajes extends TabActivity 
{
			List<ParseObject> men1;
			List<ParseObject> men2;
			List<ParseObject> men3;
            /** Called when the activity is first created. */
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.mensajes_tab);

                    // create the TabHost that will contain the Tabs
                    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

                    Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
                    ParseUser user = new ParseUser();
                    user = ParseUser.getCurrentUser();                    
                    
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Mensajes");
                    query.whereEqualTo("envia", user.getEmail());
                    
                    
                    try {
            			men1 = query.find();
            		} catch (ParseException e) {

            		}
                    int a = men1.size();
                    String uno = Integer.toString(a);
                    
                    ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Mensajes");
                    query2.whereEqualTo("recibe", user.getEmail());
                    try {
            			men2 = query2.find();
            		} catch (ParseException e) {

            		}
                    int b = men2.size();
                    String dos = Integer.toString(b);
                    
                    //ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Mensajes");
                    
                    TabSpec tab1 = tabHost.newTabSpec("First Tab");
                    TabSpec tab2 = tabHost.newTabSpec("Second Tab");
                   // TabSpec tab3 = tabHost.newTabSpec("Third tab");

                   // Set the Tab name and Activity
                   // that will be opened when particular Tab will be selected
                    tab1.setIndicator("enviados ("+uno+")");
                    tab1.setContent(new Intent(this,Enviados.class));
                    
                    tab2.setIndicator("recibidos ("+dos+")");
                    tab2.setContent(new Intent(this,Recibidos.class));
/*
                    tab3.setIndicator("no leidos");
                    tab3.setContent(new Intent(this,Enviados.class));
  */                  
                    /** Add the tabs  to the TabHost to display. */
                    tabHost.addTab(tab1);
                    tabHost.addTab(tab2);
                    //tabHost.addTab(tab3);

            }
            
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
        		menu.findItem(R.id.action_new).setVisible(false);
        		menu.findItem(R.id.action_camara).setVisible(false);
        		menu.findItem(R.id.action_delete).setVisible(true);
        		menu.findItem(R.id.action_fav).setVisible(false);
        		menu.findItem(R.id.action_fav_no).setVisible(false);
        		
        		return true;
        	}
            
            @Override
        	public boolean onOptionsItemSelected(MenuItem item) {
        		switch(item.getItemId())
        	    {
        	        case R.id.action_delete:
        	        	Toast.makeText( getApplicationContext(),"Eliminando mensaje...",Toast.LENGTH_SHORT ).show();
        	        	//Intent i = new Intent(Activity_tabmensajes.this,Logueado.class);
                    	break;
        	        default:
        	            return super.onOptionsItemSelected(item);
        	    }
        	 
        	    return true;
        	}
} 
