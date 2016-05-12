package com.example.suv2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainPrincipal extends Activity implements OnClickListener {
	Button subir;
	Button crear;
  static	ProgressDialog pDialog ;
	TextView tv, res;
    ProgressDialog progressDialog;
    Button b5;
    MultipartEntity reqEntity,carpeta;
    HttpEntity resEntity;
    String urlString = "http://192.168.10.141/suapp/Lotes/fotos.php";
    String urlStringLote = "http://192.168.10.141/suapp/Lotes/lote.php";
    String urlStringDatosSo = "http://192.168.10.141/suapp/Lotes/datosS.php";
    String urlStringDatosA = "http://192.168.10.141/suapp/Lotes/datosA.php";
    String urlStringImagenes = "http://192.168.10.141/suapp/Lotes/imagenes.php";


    HttpClient client;
    HttpPost post;
    HttpParams param;
    File cLote;
    Button el;
    String baseDir = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/DCIM"+"/bd/";
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_principal);
		//Instanciamos los botonoes
		crear = (Button)findViewById(R.id.btnCrear);
		subir = (Button)findViewById(R.id.subir);
		el = (Button)findViewById(R.id.btnEliminar);
		//Ponemos "escuchadores" para saber cuando se ejecutaran los botones
		crear.setOnClickListener(this);
		subir.setOnClickListener(this);
		el.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnCrear:
			Intent create = new Intent(getApplicationContext(), MainForm.class);
			startActivity(create);
			overridePendingTransition(R.anim.zoom_forward_in,
					R.anim.zoom_forward_out);
			finish();
			break;
		case R.id.subir:
			WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			boolean wifiEnabled = wifiManager.isWifiEnabled();
			if(!wifiEnabled){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Conexion a internet Nulla");
			
				builder.setMessage("Por favor habilite wifi para conexion a internet.");
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
			}else{
			 try{
				 AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Envío de Información");
				
					builder.setMessage("¿Seguro que desea enviar la información?");
					builder.setPositiveButton("Si",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int i) {
	            	cLote = new File(baseDir);
	    			//Verificaci�n de existencia para crear el directorio
	    			if (cLote.exists()) {
	    				//Creamos un objeto de tipo UploaderFoto
	            		UploaderFoto algo = new UploaderFoto ();
	                	algo.execute();
	    			} else {
	    				 Toast.makeText(getApplicationContext(),"No hay información para subir",Toast.LENGTH_SHORT).show();
	    		}	
								}
					});
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									
								}
							});
					
			Dialog alertDialog = builder.create();
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.show();
			 }catch(Exception e){
				 Log.e("",""+e);	
			 }  	
			}
			break;

		case R.id.btnEliminar:
				//insertarImagenes_bd();
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Eliminación de Información");
			
				builder.setMessage("¿Seguro que desea Eliminar la información?");
				builder.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								eliminarDatos a = new eliminarDatos();
								a.execute();
									
							}
				});
				builder.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								
							}
						});
				
		Dialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
			break;
		}
		
	}
	
	//Clase asincrona para subir imaganes
  	class UploaderFoto extends AsyncTask<Void, Void, Void>{

  		
  		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
	  		//Variables locales
  				param = new BasicHttpParams();
  				HttpConnectionParams.setConnectionTimeout(param, 50000);
  				HttpConnectionParams.setSoTimeout(param, 50000);
	  	    	client = new DefaultHttpClient(param);
	  	        post = new HttpPost(urlString);
	  	        carpeta = new MultipartEntity();
	  	        reqEntity = new MultipartEntity();
	  	        ContentBody archivo;
	  	        ContentBody nombreCarpeta;	  	        
	  	        HttpResponse response;
  	        	WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
       
	  	        try
	  	        {
			        if (wifi.isWifiEnabled()){
	  	        	 //Instanciamos un objeto de tipo File y le mandamos la ruta de la carpeta
	  	            File ubicacionCarpetas = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/DCIM" +"/bd/");
	  	            
	  	             //En un arreglo de tipo File igualamos a una lista del objeto previamente instanciado 
	  	            File[] cantidadCarpetas=ubicacionCarpetas.listFiles();
	  	            
	  	            //Iniciamos una condici�n para saber el estado del servidor
	  	            if(StatusLine()!=false){
	  	            	//Recorremos el tama�o del arreglo de tipo File: cantidadCarpetas
	  	            	for(int i=0; i<cantidadCarpetas.length; i++){
	  	            
		  	            		
	  	            		//Igualamos una variable de tipo File al arreglo files en cada posici�n 
	  	            		File ubicacionPorCarpeta = cantidadCarpetas[i];
	  	            		
	  	            		/*En una variable tipo String guardamos la ruta de cada imagen con la
	  	            		   funci�n "getPath()"*/
	  	            		String ubicacionCadaImagen = ubicacionPorCarpeta.getPath();
	  	            		//Obtenemos nombre de cada carpeta con la funci�n getName()
	  	            		String nombreDeCarpeta = new File(ubicacionCadaImagen).getName();
	  	         
	  	            		
	  	            		//Imprimimos en log nombre de la carpeta
	  	            		//Log.e("Carpeta",fileName);
	  	            		
	  	            		nombreCarpeta = new StringBody(new String(nombreDeCarpeta));
	  			    		carpeta.addPart("carpeta", nombreCarpeta);
	  	            		post.setEntity(carpeta);
	  	            		response = client.execute(post);
	  	          
	  	            		
	  			    		//Nueva ruta rearmada
	  		  	            File ubicacionImagenes = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/DCIM" +"/bd/" + nombreDeCarpeta);
	  		  	            
	  		  	            //Nuevo arreglo Files con la direcci�n de cada imagen con sub carpeta
	  		  	            File[] cantidadImagenes = ubicacionImagenes.listFiles();
	  		  	            	for(int j=0; j<cantidadImagenes.length; j++){
	  		  	            		
	  		  	            		
	  		  	            		File ubicacionPorImagen = cantidadImagenes[j];
	  		  	            		
	  		  	            		String ubicacioncadaImagen = ubicacionPorImagen.getPath();
	  		  	            	
	  		  	            	//Condicionamos que solo se seleccionen los archivos con extensi�n ".jpg"
	  		  	            		if(ubicacioncadaImagen.endsWith(".jpg")){ 
	  		  	            			//Imprimimos en el Log la ruta de cada imagen
	  		  	            			//Log.e("Bien","" + novofilePath);
	  		  	            			/*Instanciamos un objeto de tipo Filebody y
	  		  	            			  mandamos como parametros la ruta de cada archivo y como lo va 
	  		  	            			  a manejar (en este caso como imagen)*/
	  		  	            			archivo = new FileBody(new File(ubicacioncadaImagen),"image/jpeg");
	  		  	            			/*En un objeto de tipo MultipartEntity el nombre con 
	  		  	            			 *el que el servidor resivira el archivo y el archivo*/
			  	            			reqEntity.addPart("carpeta", nombreCarpeta);
	  		  	            			reqEntity.addPart("archivo", archivo);
	  		  	            			//Mandamos un POST con el objeto MultipartEntity
	  		  	            			post.setEntity(reqEntity);
	  		  	            			//Ejecutamos el POST
	  		  	            			response = client.execute(post);
	  		  	            			//Conectamos para recibir datos de respuesta
	  		  	            			resEntity = response.getEntity();
	  		  	            			//Creamos el InputStream como su propio nombre indica
	  		  	            			//Luego procesamos el contenido de la respuesta en formato InputStream Buffer y la paso a formato String 
	  		  	            			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	  		  	            			StringBuffer sb = new StringBuffer("");
	  		  	            			String line = "";
	  		  	            			String NL = System.getProperty("line.separator");
	  		  	            			while ((line = in.readLine()) != null) {
	  		  	            				sb.append(line + NL);
	  		  	            			}
	  		  	            			in.close();
	  		  	            		   
	  		  	            			//Respuesta en formato String
	  		  	            			String respsuesta = sb.toString();
	  		  	            			Log.e("Respuesta", respsuesta);
	  		  	            			
	  		  	            			//Cerramos la conexi�n
	  		  	            			client.getConnectionManager().shutdown();
	  		  	            			
	  		  	            			//Creamos de nuevo los objetos
	  		  	            	    	client = new DefaultHttpClient();
	  		  		  	                post = new HttpPost(urlString);
	  		  		  	                reqEntity = new MultipartEntity();
	  		  		  	                carpeta = new MultipartEntity();
	  		  		  	                
	  		  		  	         
	  		  	            			
	  		  	            		}/*FinIF*/else{
	  		  	            					Log.e("No imagen",ubicacioncadaImagen);
	  		  	            	      }
	  		  	            	}
	  	            		
	  	            	}//FinFor
	  	           }/*FinIf*/else{
	  	        	   Log.e("Conexi�n","Conexi�n Caida");
	  	        	   runOnUiThread(new Runnable(){

	  	                   @Override
	  	                   public void run(){
	  	                     //update ui here
	  	        				 Toast.makeText(getApplicationContext(),"Error en la conexi�n del servidor intente m�s tarde",Toast.LENGTH_SHORT).show();
	  	                     // display toast here 
	  	                   }
	  	                });
	  	           }
			     }//FinIf para ver el estado del Wifi
			        else{
			        	runOnUiThread(new Runnable(){

		  	                   @Override
		  	                   public void run(){
		  	                     //update ui here
		  	        				try {
										client.execute(post);
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
		  	                	   Toast.makeText(getApplicationContext(),"Active su wifi o verifique la conexion a su red",Toast.LENGTH_SHORT).show();
		  	                     // display toast here 
		  	                   }
		  	                });
			     }
	  	           //Subir informaci�n a la base de datos
			        UsuariosSQLiteHelper admin = new UsuariosSQLiteHelper(MainPrincipal.this, "mapas", null, 1);
			        SQLiteDatabase db = admin.getReadableDatabase();
			        Cursor a = db.rawQuery("SELECT *  FROM lotes", null);
			        Cursor b = db.rawQuery("SELECT *  FROM datosSociales", null);
			        Cursor c = db.rawQuery("SELECT *  FROM datosAmbientales", null);
			        Cursor d = db.rawQuery("SELECT *  FROM imagenes", null);

			        a.moveToFirst();
			        b.moveToFirst();
			        c.moveToFirst();
			        d.moveToFirst();

			        do {
			        	String coord = a.getString(a.getColumnIndex("coord"));
			        	String codigo = a.getString(a.getColumnIndex("codigoPostal"));
			        	String colonia = a.getString(a.getColumnIndex("colonia"));
			        	String tipoCol = a.getString(a.getColumnIndex("tipoCol"));
			        	String calle_m = a.getString(a.getColumnIndex("calle_manzana"));
			        	String nlote = a.getString(a.getColumnIndex("n_lote"));
			        	String gps_la = a.getString(a.getColumnIndex("gps_latitud"));
			        	String gps_long = a.getString(a.getColumnIndex("gps_longitud"));
			    
				        try{
				        	Log.e("coord",coord);
				        	Log.e("codigo",codigo);
				        	Log.e("colonia",colonia);
				        	Log.e("tipoCol",tipoCol);
				        	Log.e("calle_m",calle_m);
				        	Log.e("nlote",nlote);
				        	Log.e("gps_la",gps_la);
				        	Log.e("gps_long",gps_long);


				     
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(urlStringLote);
							List<NameValuePair> lote = new ArrayList<NameValuePair>();
							lote.add(new BasicNameValuePair("coord",coord));
							lote.add(new BasicNameValuePair("codigo",codigo));
							lote.add(new BasicNameValuePair("colonia",colonia));
							lote.add(new BasicNameValuePair("tipoCol",tipoCol));
							lote.add(new BasicNameValuePair("calle_m",calle_m));
							lote.add(new BasicNameValuePair("nlote",nlote));
							lote.add(new BasicNameValuePair("gps_la",gps_la));
							lote.add(new BasicNameValuePair("gps_long",gps_long));


							httppost.setEntity(new UrlEncodedFormEntity(lote));
							HttpResponse responses = httpclient.execute(httppost);
						    HttpEntity entity = responses.getEntity();
						    String text = EntityUtils.toString(entity);
						    Log.e("mensaje", text);
						    
						    }catch(Exception e){
						    	Log.e("error", e.toString());
						    }
			        	  			
			 
			            
			        } while (a.moveToNext());
			        
			        do {
			        	String cooord = b.getString(b.getColumnIndex("coord"));
			        	String cercania_barrio = b.getString(b.getColumnIndex("cercania_barrio"));
			        	String linea_tel_internet = b.getString(b.getColumnIndex("linea_tel_internet"));
			        	String linea_transporte = b.getString(b.getColumnIndex("linea_transporte"));
			        	String paraderos_autobuses = b.getString(b.getColumnIndex("paraderos_autobuses"));
			        	String recoleccion_basura = b.getString(b.getColumnIndex("recoleccion_basura"));
			        	String cercania_hospital = b.getString(b.getColumnIndex("cercania_hospital"));
			        	String cercania_casetaVigilancia = b.getString(b.getColumnIndex("cercania_casetaVigilancia"));
			        	String cercania_educacionB = b.getString(b.getColumnIndex("cercania_educacionB"));
			        	String cercania_educacionMS = b.getString(b.getColumnIndex("cercania_educacionMS"));
			        	String cercania_educacionS = b.getString(b.getColumnIndex("cercania_educacionS"));
			        	String cercania_mercado = b.getString(b.getColumnIndex("cercania_mercado"));
			        	String cercania_parques = b.getString(b.getColumnIndex("cercania_parques"));
			        	String conexion_drenaje = b.getString(b.getColumnIndex("conexion_drenaje"));
			        	String conexion_electricidad = b.getString(b.getColumnIndex("conexion_electricidad"));
			        	String conexion_tel_internet = b.getString(b.getColumnIndex("conexion_tel_internet"));
			        	String conexion_aguaPotable = b.getString(b.getColumnIndex("conexion_aguaPotable"));
			        	String alumbradoPublico = b.getString(b.getColumnIndex("alumbradoPublico"));
			        	String pavimentacion = b.getString(b.getColumnIndex("pavimentacion"));


			    
				        try{
				        	Log.e("coord",cooord);
				        	Log.e("cercania_barrio",cercania_barrio);
				        	Log.e("linea_tel_internet",linea_tel_internet);
				        	Log.e("linea_transporte",linea_transporte);
				        	Log.e("paraderos_autobuses",paraderos_autobuses);
				        	Log.e("recoleccion_basura",recoleccion_basura);
				        	Log.e("cercania_hospital",cercania_hospital);
				        	Log.e("cercania_casetaVigilancia",cercania_casetaVigilancia);
				        	Log.e("cercania_educacionB",cercania_educacionB);
				        	Log.e("cercania_educacionMS",cercania_educacionMS);
				        	Log.e("cercania_educacionS",cercania_educacionS);
				        	Log.e("cercania_mercado",cercania_mercado);
				        	Log.e("cercania_parques",cercania_parques);
				        	Log.e("conexion_drenaje",conexion_drenaje);
				        	Log.e("conexion_electricidad",conexion_electricidad);
				        	Log.e("conexion_tel_internet",conexion_tel_internet);
				        	Log.e("conexion_aguaPotable",conexion_aguaPotable);
				        	Log.e("alumbradoPublico",alumbradoPublico);
				        	Log.e("pavimentacion",pavimentacion);
				        

				     
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(urlStringDatosSo);
							List<NameValuePair> datosS = new ArrayList<NameValuePair>();
							datosS.add(new BasicNameValuePair("coord",cooord));
							datosS.add(new BasicNameValuePair("cercania_barrio",cercania_barrio));
							datosS.add(new BasicNameValuePair("linea_tel_internet",linea_tel_internet));
							datosS.add(new BasicNameValuePair("linea_transporte",linea_transporte));
							datosS.add(new BasicNameValuePair("paraderos_autobuses",paraderos_autobuses));
							datosS.add(new BasicNameValuePair("recoleccion_basura",recoleccion_basura));
							datosS.add(new BasicNameValuePair("cercania_hospital",cercania_hospital));
							datosS.add(new BasicNameValuePair("cercania_casetaVigilancia",cercania_casetaVigilancia));
							datosS.add(new BasicNameValuePair("cercania_educacionB",cercania_educacionB));
							datosS.add(new BasicNameValuePair("cercania_educacionMS",cercania_educacionMS));
							datosS.add(new BasicNameValuePair("cercania_educacionS",cercania_educacionS));
							datosS.add(new BasicNameValuePair("cercania_mercado",cercania_mercado));
							datosS.add(new BasicNameValuePair("cercania_parques",cercania_parques));
							datosS.add(new BasicNameValuePair("conexion_drenaje",conexion_drenaje));
							datosS.add(new BasicNameValuePair("conexion_electricidad",conexion_electricidad));
							datosS.add(new BasicNameValuePair("conexion_tel_internet",conexion_tel_internet));
							datosS.add(new BasicNameValuePair("conexion_aguaPotable",conexion_aguaPotable));
							datosS.add(new BasicNameValuePair("alumbradoPublico",alumbradoPublico));
							datosS.add(new BasicNameValuePair("pavimentacion",pavimentacion));

							httppost.setEntity(new UrlEncodedFormEntity(datosS));
							HttpResponse responses = httpclient.execute(httppost);
						    HttpEntity entity = responses.getEntity();
						    String text = EntityUtils.toString(entity);
						    Log.e("mensaje", text);
						    
						    }catch(Exception e){
						    	Log.e("error", e.toString());
						    }
			        	  			
			 
			            
			        } while (b.moveToNext());
			       
			        do {
			        	String cooord = c.getString(c.getColumnIndex("coord"));
			        	String ubicadoZona_inundacion = c.getString(c.getColumnIndex("ubicadoZona_inundacion"));
			        	String hundimientos_cavernas = c.getString(c.getColumnIndex("hundimientos_cavernas"));
			        	String rellenos_dS_I_Q = c.getString(c.getColumnIndex("rellenos_dS_I_Q"));
			        	String cercano_dB_pR = c.getString(c.getColumnIndex("cercano_dB_pR"));
			        	String calidad_ambientalTerreno = c.getString(c.getColumnIndex("calidad_ambientalTerreno"));
			        	String cercano_dCombustible = c.getString(c.getColumnIndex("cercano_dCombustible"));
			        	String cercano_estacionesServicio = c.getString(c.getColumnIndex("cercano_estacionesServicio"));
			        	String cercano_ductosCombustibles = c.getString(c.getColumnIndex("cercano_ductosCombustibles"));
			        	String cercano_lineasElectrificacion = c.getString(c.getColumnIndex("cercano_lineasElectrificacion"));
			        	String cercano_limitesCamposAviacion = c.getString(c.getColumnIndex("cercano_limitesCamposAviacion"));
			    
				        try{
				        	Log.e("coord",cooord);
				        	Log.e("ubicadoZona_inundacion",ubicadoZona_inundacion);
				        	Log.e("hundimientos_cavernas",hundimientos_cavernas);
				        	Log.e("rellenos_dS_I_Q",rellenos_dS_I_Q);
				        	Log.e("cercano_dB_pR",cercano_dB_pR);
				        	Log.e("calidad_ambientalTerreno",calidad_ambientalTerreno);
				        	Log.e("cercano_dCombustible",cercano_dCombustible);
				        	Log.e("cercano_estacionesServicio",cercano_estacionesServicio);
				        	Log.e("cercano_ductosCombustibles",cercano_ductosCombustibles);
				        	Log.e("cercano_lineasElectrificacion",cercano_lineasElectrificacion);
				        	Log.e("cercano_limitesCamposAviacion",cercano_limitesCamposAviacion);
	


				     
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(urlStringDatosA);
							List<NameValuePair> datosA = new ArrayList<NameValuePair>();
							datosA.add(new BasicNameValuePair("coord",cooord));
							datosA.add(new BasicNameValuePair("ubicadoZona_inundacion",ubicadoZona_inundacion));
							datosA.add(new BasicNameValuePair("hundimientos_cavernas",hundimientos_cavernas));
							datosA.add(new BasicNameValuePair("rellenos_dS_I_Q",rellenos_dS_I_Q));
							datosA.add(new BasicNameValuePair("cercano_dB_pR",cercano_dB_pR));
							datosA.add(new BasicNameValuePair("calidad_ambientalTerreno",calidad_ambientalTerreno));
							datosA.add(new BasicNameValuePair("cercano_dCombustible",cercano_dCombustible));
							datosA.add(new BasicNameValuePair("cercano_estacionesServicio",cercano_estacionesServicio));
							datosA.add(new BasicNameValuePair("cercano_ductosCombustibles",cercano_ductosCombustibles));
							datosA.add(new BasicNameValuePair("cercano_lineasElectrificacion",cercano_lineasElectrificacion));
							datosA.add(new BasicNameValuePair("cercano_limitesCamposAviacion",cercano_limitesCamposAviacion));
			
							httppost.setEntity(new UrlEncodedFormEntity(datosA));
							HttpResponse responses = httpclient.execute(httppost);
						    HttpEntity entity = responses.getEntity();
						    String text = EntityUtils.toString(entity);
						    Log.e("mensaje", text);
						    
						    }catch(Exception e){
						    	Log.e("error", e.toString());
						    }
			        	  			
			 
			            
			        } while (c.moveToNext());
			        
			        do {
			        	String coooord = d.getString(d.getColumnIndex("coord"));
			        	String nombre_i = d.getString(d.getColumnIndex("nombre_i"));
			        	String nombre_carpeta = d.getString(d.getColumnIndex("nombre_carpeta"));
			        				        try{
				        	Log.e("coord",coooord);
				        	Log.e("nombre_i",nombre_i);
				        	Log.e("nombre_carpeta",nombre_carpeta);
				 


				     
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(urlStringImagenes);
							List<NameValuePair> imagenes = new ArrayList<NameValuePair>();
							imagenes.add(new BasicNameValuePair("coord",coooord));
							imagenes.add(new BasicNameValuePair("nombre_i",nombre_i));
							imagenes.add(new BasicNameValuePair("nombre_carpeta",nombre_carpeta));
							httppost.setEntity(new UrlEncodedFormEntity(imagenes));
							HttpResponse responses = httpclient.execute(httppost);
						    HttpEntity entity = responses.getEntity();
						    String text = EntityUtils.toString(entity);
						    Log.e("mensaje", text);
						    
						    }catch(Exception e){
						    	Log.e("error", e.toString());
						    }
			        	  			
			 
			            
			        } while (d.moveToNext());
			        db.close();
			        
	  	         }catch (UnsupportedEncodingException e) {
	  	          Log.e("UnsupportedEncodingException", e.toString());
	  	         } catch (ClientProtocolException e) {
	  	          Log.e("ClientProtocolException", e.toString());
	  	         } catch (ConnectTimeoutException e) {
	  	          Log.e("CONN TIMEOUT", e.toString());
	  	         } catch (SocketTimeoutException e) {
	  	          Log.e("SOCK TIMEOUT", e.toString());
	  	         } catch (IOException e) {
	  	          Log.e("IOException", e.toString());
	  	         } catch (Exception e) {
	  	          Log.e("OTHER EXCEPTIONS", e.toString());
	  	         }
	  	    
			return null;
		}
  		@Override
		protected void onPreExecute() {
  			super.onPreExecute();
  			pDialog = new ProgressDialog(MainPrincipal.this);
  			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  	        pDialog.setMessage("Subiendo las imagenes");
  	        pDialog.setCanceledOnTouchOutside(false);
  	        pDialog.show();
  		}
  		@Override
		protected void onPostExecute(Void result) {
  			super.onPostExecute(result);
  			pDialog.dismiss();
  		}
  	}
  	
  	//Metodo para saber el estado del servidor
  	public boolean StatusLine(){
  		 try {
  			 //Instanciamos un objeto de tipo URL pasandole la ruta del servidor
             URL url = new URL(urlString);
             //Instanciamos un objeto tipo HttpURLConnection para abrir una conexi�n
             HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
             urlc.setConnectTimeout(10 * 1000);          // Esperamos 10 s.
             //Conectamos al servidor
             urlc.connect();
             
             if (urlc.getResponseCode() == 200) {        // Si la respuesta es igual a 200 hay conexi�n.
                 Log.wtf("Connection", "Success !");
                 return true;
             } else {
                 return false;
             }
             
        	 
         } catch (MalformedURLException e1) {// Por cualquier Cosa! xD
             return false;
         } catch (IOException e) {
             return false;
         }
  		 	
  	
  	}
  	//Metodo para eliminar datos en la base de datos
  	public void eliminar(){
        String lot;
		UsuariosSQLiteHelper admin = new UsuariosSQLiteHelper(this, "mapas", null, 1);
		SQLiteDatabase bd=  admin.getWritableDatabase();

		try{

                String q_i, q_datosS, q_datosA, q_lotes;
                q_lotes ="delete from lotes";
                q_i = "delete from imagenes";
                q_datosS ="delete from datosSociales";
                q_datosA ="delete from datosAmbientales";

				bd.execSQL(q_i);
				bd.execSQL(q_datosS);
				bd.execSQL(q_datosA);
                bd.execSQL(q_lotes);

                bd.close();


		}catch(SQLException e){
			Log.e("Error",e.toString());
		}
	}
  	
  	//Clase asincrona para eliminar carpetas con imagemes
  class eliminarDatos extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainPrincipal.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Eliminando informacion....");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }
	@Override
	protected Void doInBackground(Void... v) {
		// TODO Auto-generated method stub
        eliminar();
		 File ubicacionCarpetas = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/DCIM"+ "/bd/");
           
           //En un arreglo de tipo File igualamos a una lista del objeto previamente instanciado 
          File[] cantidadCarpetas=ubicacionCarpetas.listFiles();
          
			if (!ubicacionCarpetas.isDirectory()) {
				  runOnUiThread(new Runnable(){

 	                   @Override
 	                   public void run(){
 	                     //update ui here
 	        				 Toast.makeText(getApplicationContext(),"No existen datos e imagenes para eliminar....",Toast.LENGTH_LONG).show();
 	                     // display toast here 
 	                   }
 	                });
			} else {
          //Iniciamos una condici�n para saber el estado del servidor
         
          	//Recorremos el tama�o del arreglo de tipo File: cantidadCarpetas
          	for(int i=0; i<cantidadCarpetas.length; i++){
          
	            		
          		//Igualamos una variable de tipo File al arreglo files en cada posici�n 
          		File ubicacionPorCarpeta = cantidadCarpetas[i];
          		
          		/*En una variable tipo String guardamos la ruta de cada imagen con la
          		   funci�n "getPath()"*/
          		String ubicacionCadaImagen = ubicacionPorCarpeta.getPath();
          		//Obtenemos nombre de cada carpeta con la funci�n getName()
          		String nombreDeCarpeta = new File(ubicacionCadaImagen).getName();
       
          	  File ubicacionImagenes = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/DCIM"+ "/bd/" + nombreDeCarpeta);
  	            
  	            //Nuevo arreglo Files con la direcci�n de cada imagen con sub carpeta
  	            File[] cantidadImagenes = ubicacionImagenes.listFiles();
  	            	for(int j=0; j<cantidadImagenes.length; j++){
  	            		
  	            		
  	            		File ubicacionPorImagen = cantidadImagenes[j];
  	            		
  	            			
  	            		ubicacionPorImagen.delete();
  	            		ubicacionPorCarpeta.delete();

  	            	}
  	            	ubicacionImagenes.delete();
  	            	
          	}
          	ubicacionCarpetas.delete();

            runOnUiThread(new Runnable(){

                  @Override
                  public void run(){
                    //update ui here
       				 Toast.makeText(getApplicationContext(),"Se a eliminado Toda la información.",Toast.LENGTH_SHORT).show();
                    // display toast here 
                  }
               });
			}
		
		return null;
	}
	

			@Override
		protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				pDialog.dismiss();
			}
		
  }


}
