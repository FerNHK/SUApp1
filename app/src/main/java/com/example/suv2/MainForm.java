package com.example.suv2;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainForm extends Activity implements OnClickListener,OnItemSelectedListener {
	Button guardar;
	ImageButton agregar;
	ImageButton tomarFoto;
	EditText nombreCarpeta;
	File dc;
	File cLote;
	Spinner spCodigo,spColonia,spTipoColonia;
	int verificar_spinner = 0;
	
	boolean verificar = false;
	String dcImagen = 	Environment.getExternalStorageDirectory() + "/DCIM/";
	String baseDir = "/bd/";
	
	AlertDialog alertDialog;
	final Context context = this;
	UsuariosSQLiteHelper bd;
	EditText edCalle;
	EditText lote;
	
	public static String calle;
	public static String noLote;
	public static String loteCarpeta;
	public static String srt ;
	public static String seleccion_codigo,seleccion_colonia, seleccion_tipo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_datos);

		guardar = (Button) findViewById(R.id.btndatosPredio);
		tomarFoto = (ImageButton) findViewById(R.id.imgTakePhoto);
		edCalle = (EditText)findViewById(R.id.edtCalle);
		lote = (EditText)findViewById(R.id.edtLoteNo);
		guardar.setEnabled(false);
		guardar.setOnClickListener(this);
		tomarFoto.setOnClickListener(this);	
		
		//Obtenemos cada spinner 
		spCodigo = (Spinner)findViewById(R.id.codigoPostal);
		spColonia = (Spinner)findViewById(R.id.colonia);
		spTipoColonia = (Spinner)findViewById(R.id.tipoColonia);

		Spinner();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btndatosPredio:
		
			
			calle = edCalle.getText().toString();
			noLote =lote.getText().toString();
			
			if(calle.equals("")|| noLote.equals("")  ){
				Toast.makeText(getApplicationContext(), "Verifique la información",Toast.LENGTH_LONG).show();
			 }else {
                 Intent a = new Intent(getApplicationContext(), MainTapHost.class);
				 startActivity(a);
				overridePendingTransition(R.anim.zoom_forward_in,
						R.anim.zoom_forward_out);	
				finish();
					
			 }
			
			break;

		case R.id.imgTakePhoto:
		    dc = new File(dcImagen);
		    
		    if(dc.isDirectory()){
		    	Toast.makeText(getApplicationContext(), "SI existe DCIM",
						Toast.LENGTH_LONG).show();
		  	cLote = new File(dcImagen+baseDir);
			if (!cLote.isDirectory()) {
				directory();
			} else {
				Toast.makeText(getApplicationContext(), "SI existe BD",
						Toast.LENGTH_LONG).show();
			}
			final AlertDialog.Builder alert = new AlertDialog.Builder(context);

			alert.setTitle("Crear Carpeta");
			alert.setMessage("Nombre de la carpeta: ");
			nombreCarpeta = new EditText(this);
			alert.setView(nombreCarpeta);
			
			alert.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							srt = nombreCarpeta.getEditableText()
									.toString();
						     if ((checkNombreCarpeta(srt) == true)) {
								Toast.makeText(getApplicationContext(),
										"Se creo la Carpeta" + ": " + srt,
										Toast.LENGTH_LONG).show();
								
								loteCarpeta = dcImagen+baseDir + srt;
								cLote = new File(loteCarpeta);
								cLote.mkdirs();
								cam(srt);
								dialog.dismiss();
								verificar = true;
							} else {
								Toast.makeText(getApplicationContext(),
										"Verificar el nombre" + ": " + srt,
										Toast.LENGTH_LONG).show();
								dialog.cancel();
							}

						}
					});
			alert.setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							nombreCarpeta.setText("");

						}
					});
			alertDialog = alert.create();
			alertDialog.show();
			break;
			}else{
				Toast.makeText(getApplicationContext(), "No se encuentra la ruta /DCIM/ en el celular", Toast.LENGTH_SHORT).show();
			}
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

	public void directory() {
		
		File wallpaperDirectory = new File(dcImagen+baseDir);
		// have the object build the directory structure, if needed.
		wallpaperDirectory.mkdirs();
	}

	public void cam(String srt) {
		String albumDir= "";
		albumDir = "/"+srt+"/";
		Intent intent = new Intent(getApplicationContext(), camara.class);
		intent.putExtra("Directorio", albumDir );
		Toast.makeText(getApplicationContext(), albumDir , Toast.LENGTH_LONG).show();
		this.startActivity(intent);
	   guardar.setEnabled(true);
	}
	public boolean checkNombreCarpeta(String carpeta) {
		if (carpeta.equals("")) {
			Log.e("Error",
					"El campo de nombre carpeta Vacio o nullo.. verifique");
			return false;
		} else {
			return true;
		}
	}
	
	public void Spinner(){
		
		//Arreglos
		ArrayList<String> c_postal = new ArrayList<String>();

		
		try {
			
			//Creamos la base de datos
			bd = new UsuariosSQLiteHelper(this, "mapas", null, 1);
		
			//Abrimos la base de datos en modo de lectura
			SQLiteDatabase db = bd.getReadableDatabase();
			
			//Qwery de busqueda
			Cursor codigo = db.rawQuery("SELECT DISTINCT d_codigo FROM colonias", null);
			
			//Mientras haya algo en el cursor
			while(codigo.moveToNext()){
				codigo.getString(0);
				c_postal.add(codigo.getString(0));
			}
			
			Log.e("c_postal", c_postal.toString());
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error",e.toString());
		}
			//Adapter Para Codigo Postal
			ArrayAdapter<String> adapta = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,c_postal);
			adapta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCodigo.setAdapter(adapta);

			
			//Ponemos un escuchador para cuando seleccionamos un codigo postal
			spCodigo.setOnItemSelectedListener(this);
			spColonia.setOnItemSelectedListener(this);
			spTipoColonia.setOnItemSelectedListener(this);

			
			bd.close();
			
			
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
        switch (parent.getId()) {
        case R.id.codigoPostal:
			//Guardamos el codigo postal en una variable de tipo String

			seleccion_codigo= spCodigo.getSelectedItem().toString();// Tenemos error porque el selector del spinner da null (reparar xD)
			
			//Arreglos
			ArrayList<String> Colonia = new ArrayList<String>();
			ArrayList<String> TipoColonia = new ArrayList<String>();
			
			try {
				
				SQLiteDatabase db = bd.getReadableDatabase();
				Cursor colonia = db.rawQuery("SELECT DISTINCT d_asenta FROM colonias WHERE d_codigo = "+seleccion_codigo, null);
				Cursor tipoColonia = db.rawQuery("SELECT DISTINCT d_tipo_asenta FROM colonias WHERE d_codigo = "+seleccion_codigo, null);
				
				
				while(colonia.moveToNext()){
					colonia.getString(0);
					Colonia.add(colonia.getString(0));
				}
				
				while(tipoColonia.moveToNext()){
					tipoColonia.getString(0);
					TipoColonia.add(tipoColonia.getString(0));
				}
				
				
				Log.e("Codigo", seleccion_codigo);
				
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Error",e.toString());
			}
			
				//Adapter para Colonia
				ArrayAdapter<String> adapta_colonia= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Colonia);
				adapta_colonia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spColonia.setAdapter(adapta_colonia);
				
				//Adapter para Tipo de Colonia
				ArrayAdapter<String> adapta_tipoColonia= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,TipoColonia);
				adapta_tipoColonia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spTipoColonia.setAdapter(adapta_tipoColonia);
				bd.close();
            break;
            
        case R.id.colonia:
        	seleccion_colonia= spColonia.getSelectedItem().toString();// Tenemos error porque el selector del spinner da null (reparar xD)
			Log.e("Colonia", seleccion_colonia);
        	break;
        
        case R.id.tipoColonia:
			seleccion_tipo= spTipoColonia.getSelectedItem().toString();// Tenemos error porque el selector del spinner da null (reparar xD)
        	Log.e("Tipo", seleccion_tipo);
        	break;
            
        }
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	

	
	
}