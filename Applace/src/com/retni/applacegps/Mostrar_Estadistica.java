package com.retni.applacegps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;


import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Mostrar_Estadistica extends Activity{
	
 Button ingresostotales;

 String id_aloj;
 int precio_aloj;
 int mes, dia;
 int ganancia_tot;
 List<ParseObject> disp;
 ProgressBar cargando;
 ArrayList <Integer> Vector = new ArrayList <Integer>();
 ArrayList <Integer> Vector2 = new ArrayList <Integer>();
 ArrayList <Integer> ganancia = new ArrayList <Integer>();
 public double DatoX, DatoY;
 
 
 
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar);
  
        
        
        
        //cargando = (ProgressBar) findViewById(R.id.carg_est);
 
        
        for(int j=0; j<12 ;j++){
        	ganancia.add(0);
        }
        
        Bundle bundle = getIntent().getExtras();
        id_aloj=bundle.getString("idAloj");
        precio_aloj=bundle.getInt("precio",0);
        
        Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
       /*
        new AsyncTask<Void, Void, Void>(){
        	protected void onPreExecute(){
        		//cargando.setVisibility(View.VISIBLE);
        		super.onPreExecute();
        	}*/

			//@Override
			//protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				ParseUser user = new ParseUser();
		        user = ParseUser.getCurrentUser();
		        
		        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Disponibilidad");
				query.whereEqualTo("id_alojamiento", id_aloj);
				query.orderByDescending("_created_at");

				try {
					disp = query.find();
				} catch (ParseException e) {

				}		
				
				int size_disp=0;
				size_disp = disp.size();
				
				if (size_disp == 0){
					//msje.setVisibility(View.VISIBLE);
				} else{		
					ParseObject aloj = null;
					ganancia_tot = precio_aloj*size_disp;
					for(int i=0;i<size_disp; i++){
						aloj = disp.get(i);
						mes = aloj.getDate("fecha_ocupada").getMonth();
						//dia = aloj.getDate("fecha_ocupada").getDay();
						int temp=0;
						switch(mes){
							case 0:
								temp=ganancia.get(0);
								ganancia.set(0, temp+precio_aloj);
								break;
							case 1:
								temp=ganancia.get(1);
								ganancia.set(1, temp+precio_aloj);
								break;
							case 2:
								temp=ganancia.get(2);
								ganancia.set(2, temp+precio_aloj);
								break;
							case 3:
								temp=ganancia.get(3);
								ganancia.set(3, temp+precio_aloj);
								break;
							case 4:
								temp=ganancia.get(4);
								ganancia.set(4, temp+precio_aloj);
								break;
							case 5:
								temp=ganancia.get(5);
								ganancia.set(5, temp+precio_aloj);
								break;
							case 6:
								temp=ganancia.get(6);
								ganancia.set(6, temp+precio_aloj);
								break;
							case 7:
								temp=ganancia.get(7);
								ganancia.set(7, temp+precio_aloj);
								break;
							case 8:
								temp=ganancia.get(8);
								ganancia.set(8, temp+precio_aloj);
								break;
							case 9:
								temp=ganancia.get(9);
								ganancia.set(9, temp+precio_aloj);
								break;
							case 10:
								temp=ganancia.get(10);
								ganancia.set(10, temp+precio_aloj);
								break;
							case 11:
								temp=ganancia.get(11);
								ganancia.set(11, temp+precio_aloj);
								break;
						}
					}
						//Toast.makeText( getApplicationContext(),"mes "+i+":" +mes,Toast.LENGTH_SHORT ).show();
						
					}

			//	return null;
			//}
			
			//protected void onPostExecute(Void result){
				
				for(int k=0; k<12;k++){
					//Vector.add(k);
					Vector.add(ganancia.get(k));					
				}
				Vector2.add(ganancia.get(0)+ganancia.get(1)+ganancia.get(2));
				Vector2.add(ganancia.get(3)+ganancia.get(4)+ganancia.get(5));
				Vector2.add(ganancia.get(6)+ganancia.get(7)+ganancia.get(8));
				Vector2.add(ganancia.get(9)+ganancia.get(10)+ganancia.get(11));
				
								
				//cargando.setVisibility(View.GONE);
				/*
				Intent in = new Intent(Mostrar_Estadistica.this,PieGraph.class);
				in.putExtra("ingresos", Vector);
				startActivity(in);*/
				
			}
        
       // }.execute();
    
        
    public void pieGraphHandler (View view)
    {
  	
    	PieGraph pie = new PieGraph();
    	pie.getvector(Vector2);
    	Intent lineIntent = pie.getIntent(this);
    	
    	//lineIntent.putExtra("in", Vector);
        startActivity(lineIntent);
    }
    public void barGraphHandler (View view)
    {
    	BarGraph bar = new BarGraph();
    	bar.getvector(Vector);
    	Intent lineIntent = bar.getIntent(this);
        startActivity(lineIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
    	
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
  



}