package com.example.suv2;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainTapHost extends TabActivity {
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tap_principal);
		
	    
		
		 TabHost tabHost = getTabHost();
		 
         tabHost.addTab(tabHost.newTabSpec("Datos Sociales").setIndicator("Datos Sociales",getResources().getDrawable(R.drawable.paloma)).setContent(new Intent(this, Datos_Sociales.class)));         
         tabHost.addTab(tabHost.newTabSpec("Datos Ambientales").setIndicator("Datos Ambientales",getResources().getDrawable(R.drawable.paloma)).setContent(new Intent(this, Datos_Ambientales.class)));
    }
}