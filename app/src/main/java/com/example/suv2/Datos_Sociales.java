package com.example.suv2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Datos_Sociales extends Activity implements OnItemSelectedListener{
	Button siguienteDatos2;
	Spinner cercaniaCentro,Telefonica,TransportePublico,Paradero,RecolecionBasura;
	Spinner Hospital,casetaVigilancia,eduacacionB,educacionMS,educacionS;
	Spinner cercaniaMercado,cercaniaParque,conexionDrenaje,conexionElectrica,conexionTelefonica;
	Spinner conexionAgua,alumbradoPublico,pvimentacion;

	public static String cCentro,telefonica,transporte,paradero,recoleccion;
	public static String h_ospital,casetaV,educacion_B,educacion_MS, educacion_S;
	public static String cercaniaM,cercaniaP,conexionD,conexionE, conexionT;
	public static String conexionA,alumbradoP,Pvimentacion;
	@Override
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.datos_sociales);
		
		
		//Asiganos el Id de cada Spinner a una variable
		cercaniaCentro = (Spinner)findViewById(R.id.cercania_centro);
		Telefonica = (Spinner)findViewById(R.id.telefonica);
		TransportePublico = (Spinner)findViewById(R.id.transporte_publico);
		Paradero = (Spinner)findViewById(R.id.paradero);
		RecolecionBasura = (Spinner)findViewById(R.id.recoleccion_basura);
		
		Hospital = (Spinner)findViewById(R.id.hospital);
		casetaVigilancia = (Spinner)findViewById(R.id.caseta_vigilancia);
		eduacacionB = (Spinner)findViewById(R.id.educacion_basica);
		educacionMS = (Spinner)findViewById(R.id.educacion_superiorM);
		educacionS = (Spinner)findViewById(R.id.educacion_superior);
		
		cercaniaMercado = (Spinner)findViewById(R.id.cercania_mercado);
		cercaniaParque = (Spinner)findViewById(R.id.cercania_parque);
		conexionDrenaje = (Spinner)findViewById(R.id.conexion_drenaje);
		conexionElectrica = (Spinner)findViewById(R.id.conexion_electrica);
		conexionTelefonica = (Spinner)findViewById(R.id.conexion_telefonia);
		
		conexionAgua = (Spinner)findViewById(R.id.conexion_agua);
		alumbradoPublico = (Spinner)findViewById(R.id.alumbrado_publico);
		pvimentacion = (Spinner)findViewById(R.id.pavimentacion);
		
		Spinner();



	}
public void Spinner(){
			
	
	try {
		//Llenamos el spinner de cercaniaCentro
		ArrayAdapter<CharSequence> a =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cercaniaCentro.setAdapter(a);
		
		//Llenamos el spinner de Telefonica
		ArrayAdapter<CharSequence> b =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Telefonica.setAdapter(b);
		
		//Llenamos el spinner de TransportePublico
		ArrayAdapter<CharSequence> c =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		TransportePublico.setAdapter(c);
		
		//Llenamos el spinner de Paradero
		ArrayAdapter<CharSequence> d =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Paradero.setAdapter(d);
		
		//Llenamos el spinner de RecolecionBasura
		ArrayAdapter<CharSequence> e =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		e.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		RecolecionBasura.setAdapter(e);
		
		//Llenamos el spinner de Hospital
		ArrayAdapter<CharSequence> f =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		f.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Hospital.setAdapter(a);
		
		//Llenamos el spinner de casetaVigilancia
		ArrayAdapter<CharSequence> g =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		g.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		casetaVigilancia.setAdapter(g);
		
		//Llenamos el spinner de eduacacionB
		ArrayAdapter<CharSequence> h =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		h.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eduacacionB.setAdapter(h);
		
		//Llenamos el spinner de educacionMS
		ArrayAdapter<CharSequence> i =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		i.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		educacionMS.setAdapter(i);
		
		//Llenamos el spinner de educacionS
		ArrayAdapter<CharSequence> j =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		j.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		educacionS.setAdapter(j);
		
		//Llenamos el spinner de cercaniaMercado
		ArrayAdapter<CharSequence> k =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		k.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cercaniaMercado.setAdapter(k);
		
		//Llenamos el spinner de cercaniaParque
		ArrayAdapter<CharSequence> l =  ArrayAdapter.createFromResource(this,R.array.Distancia,android.R.layout.simple_spinner_item);
		l.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cercaniaParque.setAdapter(l);
		
		//Llenamos el spinner de conexionDrenaje
		ArrayAdapter<CharSequence> m =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		conexionDrenaje.setAdapter(m);
		
		//Llenamos el spinner de conexionElectrica
		ArrayAdapter<CharSequence> n =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		n.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		conexionElectrica.setAdapter(n);
		
		//Llenamos el spinner de conexionTelefonica
		ArrayAdapter<CharSequence> p =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		p.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		conexionTelefonica.setAdapter(p);
		
		//Llenamos el spinner de conexionAgua
		ArrayAdapter<CharSequence> q =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		q.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		conexionAgua.setAdapter(q);
		
		//Llenamos el spinner de alumbradoPublico
		ArrayAdapter<CharSequence> r =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		r.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		alumbradoPublico.setAdapter(r);
		
		//Llenamos el spinner de pvimentacion
		ArrayAdapter<CharSequence> s =  ArrayAdapter.createFromResource(this,R.array.Califiacion,android.R.layout.simple_spinner_item);
		s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pvimentacion.setAdapter(s);
	} catch (Exception e) {
		// TODO: handle exception
		Log.e("Error", e.toString());
	}
	
	//Ponemos un escuchador para cuando seleccionamos un spinner 
			cercaniaCentro.setOnItemSelectedListener(this);
			Telefonica.setOnItemSelectedListener(this);
			TransportePublico.setOnItemSelectedListener(this);
			Paradero.setOnItemSelectedListener(this);
			RecolecionBasura.setOnItemSelectedListener(this);
			
			Hospital.setOnItemSelectedListener(this);
			casetaVigilancia.setOnItemSelectedListener(this);
			eduacacionB.setOnItemSelectedListener(this);
			educacionMS.setOnItemSelectedListener(this);
			educacionS.setOnItemSelectedListener(this);
			
			cercaniaMercado.setOnItemSelectedListener(this);
			cercaniaParque.setOnItemSelectedListener(this);
			conexionDrenaje.setOnItemSelectedListener(this);
			conexionElectrica.setOnItemSelectedListener(this);
			conexionTelefonica.setOnItemSelectedListener(this);
			
			conexionAgua.setOnItemSelectedListener(this);
			alumbradoPublico.setOnItemSelectedListener(this);
			pvimentacion.setOnItemSelectedListener(this);
		
			
	}
@Override
public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
        switch (parent.getId()) {
        case R.id.cercania_centro:
        	cCentro= cercaniaCentro.getSelectedItem().toString();
			Log.e("Codigo", "Cercania Centro: "+cCentro);

            break;
            
        case R.id.telefonica:
        	telefonica =  Telefonica.getSelectedItem().toString();
			Log.e("Codigo", "Telefonica: "+telefonica);

        	break;
        
        case R.id.transporte_publico:
        	transporte =  TransportePublico.getSelectedItem().toString();
			Log.e("Codigo", "Transporte Publico: "+transporte);
			
        	break;
        	
        case R.id.paradero:
        	paradero = Paradero.getSelectedItem().toString();
			Log.e("Codigo", "Paradero: "+paradero);
			
        	break;
        case R.id.recoleccion_basura:
        	recoleccion = RecolecionBasura.getSelectedItem().toString();
			Log.e("Codigo", "Recoleccion Basura: "+recoleccion);
        	break;
        
        case R.id.hospital:
        	h_ospital = Hospital.getSelectedItem().toString();
			Log.e("Codigo", "Hospital: "+h_ospital);

            break;
            
        case R.id.caseta_vigilancia:
        	casetaV =  casetaVigilancia.getSelectedItem().toString();
			Log.e("Codigo", "Caseta de Vigilancia: "+casetaV);

        	break;
        
        case R.id.educacion_basica:
        	educacion_B =  eduacacionB.getSelectedItem().toString();
			Log.e("Codigo", "Educacion Basica: "+educacion_B);
			
        	break;
        	
        case R.id.educacion_superiorM:
        	educacion_MS = educacionMS.getSelectedItem().toString();
			Log.e("Codigo", "Educacion Media Superior: "+educacion_MS);
			
        	break;
        case R.id.educacion_superior:
        	educacion_S = educacionS.getSelectedItem().toString();
			Log.e("Codigo", "Educacion Superior: "+educacion_S);
        	break;
        	
        case R.id.cercania_mercado:
        	cercaniaM = cercaniaMercado.getSelectedItem().toString();
			Log.e("Codigo", "Cercania Mercado: "+cercaniaM);

            break;
            
        case R.id.cercania_parque:
        	cercaniaP =  cercaniaParque.getSelectedItem().toString();
			Log.e("Codigo", "Cercania Parque: "+cercaniaP);

        	break;
        
        case R.id.conexion_drenaje:
        	conexionD =  conexionDrenaje.getSelectedItem().toString();
			Log.e("Codigo", "Conexion Drenaje: "+conexionD);
			
        	break;
        	
        case R.id.conexion_electrica:
        	conexionE = conexionElectrica.getSelectedItem().toString();
			Log.e("Codigo", "Conexion Electrica: "+conexionE);
			
        	break;
        case R.id.conexion_telefonia:
        	conexionT = conexionTelefonica.getSelectedItem().toString();
			Log.e("Codigo", "Conexion Telefonia: "+conexionT);
        	break;
        
        case R.id.conexion_agua:
        	conexionA = conexionAgua.getSelectedItem().toString();
			Log.e("Codigo", "Conexion Agua: "+conexionA);

            break;
            
        case R.id.alumbrado_publico:
        	alumbradoP =  alumbradoPublico.getSelectedItem().toString();
			Log.e("Codigo", "Alumbrado Publico: "+alumbradoP);

        	break;
        
        case R.id.pavimentacion:
        	Pvimentacion =  pvimentacion.getSelectedItem().toString();
			Log.e("Codigo", "Pavimentacion: "+Pvimentacion);
			
        	break;

        }
		
	}
	@Override
public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
