package com.retni.applacegps;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

public class PieGraph {

	public ArrayList<Integer> Vector = new ArrayList<Integer>();
	private final int ORANGE = 0xFFFF3300;
	
	public void getvector(ArrayList<Integer> a){
		int i;
		for(i=0;i<4;i++)Vector.add(a.get(i));
	}
	public Intent getIntent(Context context) {
		
		//Intent in = getIntent(context);
		//Vector = in.getIntegerArrayListExtra("in");
		
		
		int[] values = { Vector.get(0), Vector.get(1), Vector.get(2), Vector.get(3) };
		
		CategorySeries series = new CategorySeries("Pie Graph");

		int aux =0;
		for (int value : values) {
			if(aux==0)series.add("Verano", value);
			if(aux==1)series.add("Otoño", value);
			if(aux==2)series.add("Invierno", value);
			if(aux==3)series.add("Primavera", value);
		    aux=aux+1;
		}

		int[] colors = new int[] { Color.parseColor("#009900"),  Color.parseColor("#FF9933"),  Color.parseColor("#00CCFF"), Color.parseColor("#FF33CC") };

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			
			//r.setChartValuesTextSize(1000);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("GANANCIA POR ESTACIONES");
		
		renderer.setAxesColor(20);
		renderer.setChartTitleTextSize(45);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setLabelsTextSize(25);
		//renderer.setLegendHeight(2);
		renderer.setLegendTextSize(25);
		
		
		renderer.setZoomButtonsVisible(true);

		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Estaciones");
		return intent;
		
		
		//Toast.makeText( getApplicationContext(),"El mejor momento para arrendar tu vivienda es:" "+i" "+aux" ,Toast.LENGTH_LONG ).show();
	}
	
}
