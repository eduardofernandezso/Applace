package com.retni.applacegps;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.googlecode.charts4j.AxisLabels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph{

	public ArrayList<Integer> Vector = new ArrayList<Integer>();
	
	public void getvector(ArrayList<Integer> a){
		int i;
		for(i=0;i<12;i++)Vector.add(a.get(i));
	}	
	public Intent getIntent(Context context) 
	{	
		// Bar 1
		int[] y = { Vector.get(0), Vector.get(2), Vector.get(4), Vector.get(6), Vector.get(8), Vector.get(10)};
		//int[] y = { Vector.get(0), Vector.get(1), Vector.get(2), Vector.get(3), Vector.get(4), Vector.get(5), Vector.get(6), Vector.get(7), Vector.get(8), Vector.get(9), Vector.get(10), Vector.get(11)};
		CategorySeries series = new CategorySeries("MESES IMPARES");
		for (int i = 0; i < y.length; i++) {
			series.add("Bar " + (i+1), y[i]);
		}
		
		// Bar 2
		int[] y2 = { Vector.get(1), Vector.get(3), Vector.get(5), Vector.get(7), Vector.get(9), Vector.get(11)};
		CategorySeries series2 = new CategorySeries("MESES PARES");
		for (int i = 0; i < y.length; i++) {
			series2.add("Bar " + (i+1), y2[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		dataset.addSeries(series2.toXYSeries());

		// This is how the "Graph" itself will look like
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("GANANCIAS POR MES");
		
		//mRenderer.setBackgroundColor(20);
		mRenderer.setChartValuesTextSize(1000);
		mRenderer.setChartTitleTextSize(45);
		mRenderer.setXTitle("MESES");
		mRenderer.setYTitle("INGRESOS");
		mRenderer.setAxesColor(Color.WHITE);
		mRenderer.setLabelsColor(Color.parseColor("#C0C0C0"));
		//mRenderer.setLegendHeight(2);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
	    
		// Customize bar 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
	    renderer.setDisplayChartValues(true);
	    renderer.setChartValuesSpacing((float) 0.5);
	    renderer.setChartValuesTextSize(20);
	    renderer.setLineWidth(40);
	    renderer.setColor(Color.parseColor("#FF0033"));
	    
	    
	   // mRenderer.set
	    mRenderer.addSeriesRenderer(renderer);
	    
	
	    // Customize bar 2
	    XYSeriesRenderer renderer2 = new XYSeriesRenderer();
	    renderer2.setColor(Color.parseColor("#00CCFF"));
	    renderer2.setDisplayChartValues(true);
	    //renderer.setChartValuesSpacing((float) 0.5);
	    mRenderer.addSeriesRenderer(renderer2);
	 
		Intent intent = ChartFactory.getBarChartIntent(context, dataset,mRenderer, Type.DEFAULT);
		return intent;
	}

}
