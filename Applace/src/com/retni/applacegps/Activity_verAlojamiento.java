package com.retni.applacegps;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class Activity_verAlojamiento extends ActionBarActivity{
	
	ViewPager viewPager;
    PagerAdapter adapter;
    int[] fotos;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_veralojamiento);
		
		fotos = new int[] { R.drawable.img01,
                R.drawable.img02, R.drawable.img03,
                R.drawable.img04, R.drawable.img05, R.drawable.img06,
                R.drawable.img07};
		
		//Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager_fotos);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(Activity_verAlojamiento.this, fotos);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);      
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
