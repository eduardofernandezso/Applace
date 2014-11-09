package com.retni.applacegps;

import java.text.DateFormat.Field;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.retni.applacegps.R.color;

public class Activity_disp extends ActionBarActivity {	
	
	CalendarView cal;	
	String mail1, mail2;
	String idAloj;
	Integer mes, dia;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disp);
		
		cal = (CalendarView) findViewById(R.id.disp_cal);	
		
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		Intent in = getIntent();		
		idAloj = in.getStringExtra("idAloj");
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Disponibilidad");
		query.whereEqualTo("id_alojamiento", idAloj);
		
		List<ParseObject> cari = null;
		try {
			cari = query.find();
		} catch (ParseException e) {

		}
		if(cari.size()!=0){
			ParseObject disp = null;
			for(int h=0;h<cari.size();h++){
				disp = cari.get(h);
				dia = disp.getDate("fecha_ocupada").getDay();
				mes = disp.getDate("fecha_ocupada").getMonth();
				cal.setDateTextAppearance(color.applace_color);
				
			}
		}
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
		menu.findItem(R.id.action_camara).setVisible(false);
		menu.findItem(R.id.action_delete).setVisible(false);
		menu.findItem(R.id.action_new).setVisible(false);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
	    {
	        case R.id.action_delete:	        	
            	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 
	    return true;
	}			
}
