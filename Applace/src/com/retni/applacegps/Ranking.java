package com.retni.applacegps;
/*
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    int[] fotos;
    LayoutInflater inflater;
 
    public ViewPagerAdapter(Context context, int[] fotos) {
        this.context = context;
        this.fotos = fotos;
    }
 
    @Override
    public int getCount() {
        return fotos.length;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
 
        // Declare Variables
        ImageView imgfotos;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_fotos, container,
                false);
 
        // Locate the ImageView in viewpager_item.xml
        imgfotos = (ImageView) itemView.findViewById(R.id.fotos2);
        // Capture position and set to the ImageView
        imgfotos.setImageResource(fotos[position]);
 
        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }
}*/
