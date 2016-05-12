package com.example.suv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Datos_Ambientales extends Activity implements OnClickListener,OnItemSelectedListener{
	Button siguienteDatos5;
	public static String D_sechos,cercaniaB,calidadA,depositoC,estacionesS;
	public static String zonaI, hinundacionC;
	public static String cercanoD,lineasT,camposA;

	Spinner d_sechos,cercanoBasura,calidad_Ambiental,depositoCombustibles,estacionesServicios;
	Spinner zonaInundacion,hinundamientoCaverna;
	Spinner cercanoDuctos,lineasTension,camposAviasion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.datos_ambientales);
		siguienteDatos5 = (Button) findViewById(R.id.datos_ambientales);
		siguienteDatos5.setOnClickListener(this);
		
		//Asiganos el Id de cada Spinner a una variable
		d_sechos = (Spinner)findViewById(R.id.desechos);
		cercanoBasura = (Spinner)findViewById(R.id.cercano_depositosBasura);
		calidad_Ambiental = (Spinner)findViewById(R.id.calidadAmbiental);
		depositoCombustibles = (Spinner)findViewById(R.id.deposito_combustible);
		estacionesServicios = (Spinner)findViewById(R.id.estaciones_servicios);
		
		zonaInundacion = (Spinner)findViewById(R.id.zona_inundacion);
		hinundamientoCaverna = (Spinner)findViewById(R.id.hundimento_caverna);
		
		cercanoDuctos = (Spinner)findViewById(R.id.cercano_ductosCombustibles);
		lineasTension = (Spinner)findViewById(R.id.lineas_altaTension);
		camposAviasion = (Spinner)findViewById(R.id.campos_aviasion);
				
		Spinner();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.datos_ambientales:
			LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			boolean wifiEnabled = wifiManager.isWifiEnabled();
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
					&& conectadoRedMovil() == false/*
									 * !lm.isProviderEnabled(LocationManager.
									 * NETWORK_PROVIDER)
									 */) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Servicio de Localizacion Inactivo");

				builder.setMessage("Por favor habilitar el gps para iniciar su localizacion");
				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								// Show location settings when the user
								// acknowledges the alert dialog

								Intent intent = new Intent(
										Settings.ACTION_SETTINGS);
								startActivity(intent);
							}
						});
				Dialog alertDialog = builder.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.show();
			} else {
				Intent a = new Intent(getApplicationContext(), MainMapas.class);
				startActivity(a);
				overridePendingTransition(R.anim.zoom_forward_in,
						R.anim.zoom_forward_out);
				finish();
			}
					break;

		}
	}
	
	protected Boolean conectadoRedMovil(){
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
		NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info != null) {
		if (info.isConnected()) {
		return true;
		}
		}
		}
		return false;
		}


/*	Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    context.startActivity(myIntent);
	*/
	
	
	public void Spinner(){
		
		
		try {
			
			//Llenamos el spinner de zonaInundacion
			ArrayAdapter<CharSequence> a =  ArrayAdapter.createFromResource(this,R.array.Valoracion,android.R.layout.simple_spinner_item);
			a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			zonaInundacion.setAdapter(a);
			
			//Llenamos el spinner de hinundamientoCaverna
			ArrayAdapter<CharSequence> b =  ArrayAdapter.createFromResource(this,R.array.SioNo,android.R.layout.simple_spinner_item);
			b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			hinundamientoCaverna.setAdapter(b);
			
			//Llenamos el spinner de d_sechos
			ArrayAdapter<CharSequence> c =  ArrayAdapter.createFromResource(this,R.array.SioNo,android.R.layout.simple_spinner_item);
			c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			d_sechos.setAdapter(c);
			
			//Llenamos el spinner de cercanoBasura
			ArrayAdapter<CharSequence> d =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cercanoBasura.setAdapter(d);
			
			//Llenamos el spinner de calidad_Ambiental
			ArrayAdapter<CharSequence> e =  ArrayAdapter.createFromResource(this,R.array.Ambiental,android.R.layout.simple_spinner_item);
			e.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			calidad_Ambiental.setAdapter(e);
			
			//Llenamos el spinner de depositoCombustibles
			ArrayAdapter<CharSequence> f =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			f.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			depositoCombustibles.setAdapter(f);
			
			//Llenamos el spinner de estacionesServicios
			ArrayAdapter<CharSequence> g =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			g.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			estacionesServicios.setAdapter(g);
			
			//Llenamos el spinner de cercanoDuctos
			ArrayAdapter<CharSequence> h =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			h.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cercanoDuctos.setAdapter(h);
			
			//Llenamos el spinner de lineasTension
			ArrayAdapter<CharSequence> i =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			i.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			lineasTension.setAdapter(i);
			
			//Llenamos el spinner de camposAviasion
			ArrayAdapter<CharSequence> j =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
			j.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			camposAviasion.setAdapter(j);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error", e.toString());
		}
		
		//Ponemos un escuchador para cuando seleccionamos un spinner 
			d_sechos.setOnItemSelectedListener(this);
			cercanoBasura.setOnItemSelectedListener(this);
			calidad_Ambiental.setOnItemSelectedListener(this);
			depositoCombustibles.setOnItemSelectedListener(this);
			estacionesServicios.setOnItemSelectedListener(this);
			
			zonaInundacion.setOnItemSelectedListener(this);
			hinundamientoCaverna.setOnItemSelectedListener(this);
			
			cercanoDuctos.setOnItemSelectedListener(this);
			lineasTension.setOnItemSelectedListener(this);
			camposAviasion.setOnItemSelectedListener(this);
			
				
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
	        switch (parent.getId()) {
	        
	        case R.id.zona_inundacion:
	        	zonaI = zonaInundacion.getSelectedItem().toString();
				Log.e("Codigo", "Zona de Inundacion: "+zonaI);
				
	        	break;
	        case R.id.hundimento_caverna:
	        	hinundacionC = hinundamientoCaverna.getSelectedItem().toString();
				Log.e("Codigo", "Hinundamiento : "+hinundacionC);
	        	break;
	        	
	        case R.id.desechos:
	        	D_sechos = d_sechos.getSelectedItem().toString();
				Log.e("Codigo", "Desechos: "+D_sechos);

	            break;
	            
	        case R.id.cercano_depositosBasura:
	        	cercaniaB =  cercanoBasura.getSelectedItem().toString();
				Log.e("Codigo", "Cercano deposito Basura: "+cercaniaB);

	        	break;
	        
	        case R.id.calidadAmbiental:
	        	calidadA =  calidad_Ambiental.getSelectedItem().toString();
				Log.e("Codigo", "Calidad Ambiental: "+calidadA);
				
	        	break;
	        	
	        case R.id.deposito_combustible:
	        	depositoC = depositoCombustibles.getSelectedItem().toString();
				Log.e("Codigo", "Deposito Combustibles: "+depositoC);
				
	        	break;
	        case R.id.estaciones_servicios:
	        	estacionesS = estacionesServicios.getSelectedItem().toString();
				Log.e("Codigo", "Estaciones de Servicio : "+estacionesS);
	        	break;
	        
	        case R.id.cercano_ductosCombustibles:
	        	cercanoD = cercanoDuctos.getSelectedItem().toString();
				Log.e("Codigo", "Ductos: "+cercanoD);

	            break;
	            
	        case R.id.lineas_altaTension:
	        	lineasT =  lineasTension.getSelectedItem().toString();
				Log.e("Codigo", "Lineas de Alta Tension: "+lineasT);

	        	break;
	        
	        case R.id.campos_aviasion:
	        	camposA =  camposAviasion.getSelectedItem().toString();
				Log.e("Codigo", "Campos de Aviasion: "+camposA);
				
	        	break;
	        }
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
}
