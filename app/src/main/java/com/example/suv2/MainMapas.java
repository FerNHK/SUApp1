package com.example.suv2;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainMapas extends Activity implements OnMapClickListener,
		OnMapLongClickListener, OnMarkerClickListener, android.view.View.OnClickListener {
    private static String Marcadores ="";
    private static String pMarcador ="";
    private static String glat ="";
    private static String glong ="";
    
	final int RQS_GooglePlayServices = 1;
	private static GoogleMap myMap;
	boolean markerClicked;
	Location myLocation;
	
	ProgressDialog pDialog;
	PolygonOptions polygonops = null;
	Polygon polygon = null;
	
	TextView tvLocInfo;
	Button borrar;
	Button buscar;
	Button guardar;
	MainForm selectores;
	MainCamara cam;
	String b,a,c,d,e,f;
    int conv =0; 
	 String cadenaNueva="";
	 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapas);
		borrar = (Button) findViewById(R.id.btnLimpiarMapa);
		//buscar = (Button) findViewById(R.id.btnBuscarLoc);
		guardar = (Button)findViewById(R.id.btnGuardar);
		//buscar.setEnabled(false);
		tvLocInfo = (TextView) findViewById(R.id.tex);
		//buscar.setOnClickListener(this);
		borrar.setOnClickListener(this);
		guardar.setOnClickListener(this);
	
		a = MainForm.seleccion_codigo;
		b = MainForm.noLote;
		c = MainForm.calle;
		d = MainForm.srt;
		e = MainForm.seleccion_colonia;
		f = MainForm.seleccion_tipo;

		


		Log.e("",a);
		Log.e("", b);
		Log.e("",c);
		Log.e("", d);
		Log.e("",e);
		Log.e("", f);
	
		setUpMap();
	}
//Metodo principal que inicializa los mapas 
	public void setUpMap() {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		
		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		} else {
		//Verifica si no hay mapas almacenados previamente 
			if (myMap == null) {
				myMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();
				if (myMap != null) {
					myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					myMap.setMyLocationEnabled(true);

				}
			}
//Obtencion del control fragment
			FragmentManager myfrag = getFragmentManager();
			MapFragment myFragment = (MapFragment) myfrag
					.findFragmentById(R.id.map);
//Asicnaci�n del control fragment a los mapas .
			myMap = myFragment.getMap();
//Tipo de mapa que se visualizara en el mapa
			myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			myMap.setMyLocationEnabled(true);
		
//Inicializacion de escuchadores para el mapa: Click en el mapa, click en los marcadores  y mostrar ventana de informacion en los markers
			myMap.setOnMapClickListener(this);
			myMap.setOnMapLongClickListener(this);
			myMap.setOnMarkerClickListener(this);
			
			markerClicked = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.m, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			String LicenseInfo = GooglePlayServicesUtil
					.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
			AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(
					MainMapas.this);
			LicenseDialog.setTitle("Legal Notices");
			LicenseDialog.setMessage(LicenseInfo);
			LicenseDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        if(myMap==null){
        	setUpMap();
        }
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			Toast.makeText(getApplicationContext(),
					"isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG)
					.show();
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}

	}

	@Override
	/**Al momento de dar clicks en e mapa ejecutara lo siguiente **/
	public void onMapClick(LatLng point) {
		//Obtiene el punto donde se efectuo el click y desplegara los valores Latitud y Longitud
		//en un texView
		tvLocInfo.setText(point.toString());
		//Efectura el movimiento de la camra al momento de dar click en una nueva posicion en el mapa.
		myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
		//
		markerClicked = false;
		//el Boton de busqueda se habilita.
		//buscar.setEnabled(true);
	}

	@Override
	/**Se efectura un click por un largo tiempo y ejecuta lo siguiente.**/
	public void onMapLongClick(LatLng point) {
		//Muestra la latitud y longitud de dode se esta efectuando el click y se visializa en un
		//TextView.
		if(myMap != null || checkConnectivity()==true){
			tvLocInfo.setText("New marker added@" + point.toString());
			
			        myMap.addMarker(new MarkerOptions()
					.position(point)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
					.draggable(false)
					.title("Marcador Posicion del punto:  " + point.toString()));
	
			//Se almacenan los marcadores en una variable global para proximo almacenamiento en la base de datos.
			Marcadores += "["+point.latitude +  "," + point.longitude+"]"+","; 
			
			Log.e("Guardando", Marcadores);
			
			markerClicked = false;
		}else{
			Toast.makeText(getApplicationContext(),"No tiene una buena recepción a internet ... Imposible crear el marcador....Espere a la conexion", Toast.LENGTH_SHORT).show();
		}
		
	}
	private boolean checkConnectivity()
    {
        boolean enabled = true;
 
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
         
        if ((info == null || !info.isConnected() || !info.isAvailable()))
        {
            enabled = false;
        }
        return enabled;         
    }

	@Override
	/**Al efectuar un click en cualquier marcador.**/
	public boolean onMarkerClick(Marker mark) {
		// TODO Auto-generated method stub
		Log.e("MEsaje markador", mark.getId());
		
		//si se efectua el click en un marcador 
		if (markerClicked) {
			//si existe poligono asignado al marcador eliminalo.
			if (polygon != null) {
				//eliminacion de poligono
				polygon.remove();
				polygon = null;
			}
			//se asigna un pologono en la posicion del marcador.
			polygonops.add(mark.getPosition());
			//Colorea el borde del poligono de color Negro.
			polygonops.strokeColor(Color.BLACK);
			//Rellena el interior del poligono en color azul
			polygonops.fillColor(Color.BLUE);
			//Agrega el pologono al mapa.
			polygon = myMap.addPolygon(polygonops);
		} else {
			if (polygon != null) {
				polygon.remove();
				polygon = null;
			}
			polygonops = new PolygonOptions().add(mark.getPosition());
			markerClicked = true;
		}
		return true;

	}

	

	@Override
	/**Evento click para los botones.**/
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//Si se presiona el boton de limpiar.
		case R.id.btnLimpiarMapa:
			   Marcadores ="";
			   glat ="";
			   glong ="";
			   pMarcador="";
			   myMap.clear();   
			   break;
		case R.id.btnGuardar:
			Log.e("Guardando",Marcadores);
			if(Marcadores != null && Marcadores != ""){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("¡¡Guardar!!");
				builder.setIcon(R.drawable.ic_launcher);
				builder.setMessage("¿Desea Guardar toda la información obtenida?");
				builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						    insertarBd();
					        Toast.makeText(getApplicationContext(), "Se han Guardado los datos", Toast.LENGTH_SHORT).show();
					       	Intent p = new Intent(getApplicationContext(), MainPrincipal.class);
							startActivity(p);
							finish();
							Marcadores="";
					        glat="";
					        glong="";
					        MainForm.noLote = "";
							MainForm.calle = "";
							
					}
				});
				builder.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
				Dialog alertDialog = builder.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.show();
			}
			else {
				Toast.makeText(this, "No se encuentran Marcadores en el mapa", Toast.LENGTH_SHORT).show();
			}
			break;
			
		
		}
	}
    
	public void insertarBd(){
		UsuariosSQLiteHelper admin = new UsuariosSQLiteHelper(this, "mapas", null, 1);
		SQLiteDatabase bd=  admin.getWritableDatabase();
		StringBuilder ex = new StringBuilder(Marcadores);
		glat = String.valueOf(myMap.getMyLocation().getLatitude());
		glong =String.valueOf(myMap.getMyLocation().getLongitude());
		
		Log.e("Primer Marker", pMarcador);
		    a = MainForm.seleccion_codigo;
			b = MainForm.noLote;
			c = MainForm.calle;
			d = MainForm.srt;
			e = MainForm.seleccion_colonia;
			f = MainForm.seleccion_tipo;
			 
         pMarcador = Marcadores.substring(0,Marcadores.indexOf(']')+1); 
	     
		 conv= ex.lastIndexOf(",");
		 ex.replace(conv, conv+1, "");
		 cadenaNueva = ex.toString()+","+pMarcador;
		 
		 Log.e("Coordenadas", cadenaNueva);
		 
		    
		try{
		bd.execSQL("INSERT INTO lotes(coord,codigoPostal,colonia,tipoCol,calle_manzana,n_lote,gps_latitud,gps_longitud) VALUES('"+cadenaNueva+"','"+
				a+"','"+ e +"','"+f+"','"+c+"','"+b+"','"+glat+"','"+glong+"')");
	
		bd.execSQL("INSERT INTO datosSociales (coord,cercania_barrio,linea_tel_internet,linea_transporte,"  +
				 "paraderos_autobuses,recoleccion_basura,cercania_hospital,cercania_casetaVigilancia,cercania_educacionB,"+
				 "cercania_educacionMS,cercania_educacionS,cercania_mercado,cercania_parques,conexion_drenaje,conexion_electricidad,"+
				 "conexion_tel_internet,conexion_aguaPotable,alumbradoPublico,pavimentacion)" +
				 "VALUES ('"
				          +cadenaNueva+"','"	
				          +Datos_Sociales.cCentro+ "', '"
				          
				          +Datos_Sociales.telefonica  + "','" 
				          +Datos_Sociales.transporte+ "','" 
				          +Datos_Sociales. paradero+ "','" 
				          +Datos_Sociales.recoleccion+"','"
				          +Datos_Sociales.h_ospital+ "','" 
				          +Datos_Sociales.casetaV + "','"
				          +Datos_Sociales.educacion_B+ "','"
				          +Datos_Sociales.educacion_MS +"','"
				          +Datos_Sociales.educacion_S +"','" 
				          +Datos_Sociales.cercaniaM+ "','"
				          +Datos_Sociales.cercaniaP + "','" 
				          +Datos_Sociales.conexionD + "','" 
				          +Datos_Sociales.conexionE +"','" 
				          +Datos_Sociales.conexionT + "','" 
				          +Datos_Sociales.conexionA + "','"
				          +Datos_Sociales.alumbradoP+"','"
				          +Datos_Sociales.Pvimentacion +"') ");
		
		
		bd.execSQL("INSERT INTO datosAmbientales(coord,ubicadoZona_inundacion,hundimientos_cavernas,rellenos_dS_I_Q,"+
				   	"cercano_dB_pR, calidad_ambientalTerreno,cercano_dCombustible,cercano_estacionesServicio,cercano_ductosCombustibles,cercano_lineasElectrificacion,cercano_limitesCamposAviacion"+
				    ")VALUES('"
				   	           +cadenaNueva+"','" 
				   	           +Datos_Ambientales.zonaI+"','"
				   	           
				               +Datos_Ambientales.hinundacionC +"','"
				   	           +Datos_Ambientales.D_sechos+"','"
				               +Datos_Ambientales.cercaniaB+"','"
				   	           +Datos_Ambientales.calidadA+"','"
				               +Datos_Ambientales.depositoC +"','"
				   	           +Datos_Ambientales.estacionesS +"','"
				               +Datos_Ambientales.cercanoD +"','"
				   	           +Datos_Ambientales.lineasT+"','"
				               +Datos_Ambientales.camposA  +"')");
				
		
	
		bd.execSQL("UPDATE imagenes set coord='"+cadenaNueva+"' where nombre_carpeta='"+ MainForm.srt+"'");
	     
				}catch(SQLException  e){
					Log.e("BD ERROR", e.toString());
				}

		}
	
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
        if(myMap != null){
        	myMap.clear();
        	myMap = null;
            Marcadores ="";
            glat ="";
        	glong ="";
        }
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	Toast.makeText(this, "Boton Desabilitado", Toast.LENGTH_SHORT).show();
        	Log.e("Mesanje", "Back Presionado");
        	return true;
        }

        return super.onKeyDown(keyCode, event);
    }

	
}
