
package com.example.suv2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper{

    //Sentencia SQL para crear la tabla de Usuarios\
    String sqlCreate = "CREATE TABLE IF NOT EXISTS colonias(d_codigo INTEGER(5) NOT NULL, d_asenta VARCHAR(46), d_tipo_asenta VARCHAR(21))";
    String lotes = "CREATE TABLE IF NOT EXISTS lotes(coord VARCHAR(2000) PRIMARY KEY NOT NULL, codigoPostal VARCHAR(10) NOT NULL, colonia VARCHAR(46) NOT NULL, tipoCol VARCHAR(21) NOT NULL, calle_manzana VARCHAR(40) NOT NULL, n_lote VARCHAR(1000) NOT NULL, gps_latitud VARCHAR(600) NOT NULL, gps_longitud VARCHAR(600) NOT NULL)";
  
    String datosS = "CREATE TABLE IF NOT EXISTS datosSociales(sociales INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,coord VARCHAR(2000) NOT NULL, cercania_barrio VARCHAR(30) NOT NULL, linea_tel_internet VARCHAR(30) NOT NULL, " +
    		"linea_transporte VARCHAR(30) NOT NULL,paraderos_autobuses VARCHAR(30) NOT NULL, recoleccion_basura VARCHAR(30) NOT NULL, " +
    		"cercania_hospital VARCHAR(30) NOT NULL, cercania_casetaVigilancia VARCHAR(30) NOT NULL, cercania_educacionB VARCHAR(30) NOT NULL," +
    		"cercania_educacionMS VARCHAR(30) NOT NULL, cercania_educacionS VARCHAR(30) NOT NULL,cercania_mercado VARCHAR(30) NOT NULL, " +
    		"cercania_parques VARCHAR(30) NOT NULL,conexion_drenaje VARCHAR(30) NOT NULL, conexion_electricidad VARCHAR(30) NOT NULL," +
    		"conexion_tel_internet VARCHAR(30) NOT NULL, conexion_aguaPotable VARCHAR(30) NOT NULL,alumbradoPublico VARCHAR(30) NOT NULL, " +
    		"pavimentacion VARCHAR(30) NOT NULL,FOREIGN KEY(coord) REFERENCES lotes(coord))";
   
    String datosA = "CREATE TABLE IF NOT EXISTS datosAmbientales(ambientales INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,coord VARCHAR(2000) NOT NULL, ubicadoZona_inundacion VARCHAR(30) NOT NULL, hundimientos_cavernas VARCHAR(30) NOT NULL, rellenos_dS_I_Q VARCHAR(30) NOT NULL, " +
    		"cercano_dB_pR VARCHAR(30) NOT NULL, calidad_ambientalTerreno VARCHAR(30) NOT NULL, cercano_dCombustible VARCHAR(30) NOT NULL, cercano_estacionesServicio VARCHAR(30) NOT NULL, cercano_ductosCombustibles VARCHAR(30) NOT NULL," +
    		"cercano_lineasElectrificacion VARCHAR(30) NOT NULL, cercano_limitesCamposAviacion VARCHAR(30) NOT NULL,FOREIGN KEY(coord) REFERENCES lotes(coord))";
    
    String imagenes = "CREATE TABLE IF NOT EXISTS imagenes(imagenes INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,coord VARCHAR(2000) NULL, nombre_i VARCHAR(40) NOT NULL, nombre_carpeta VARCHAR(40) NOT NULL,FOREIGN KEY(coord) REFERENCES lotes(coord))";

    String rutas_i = "CREATE TABLE IF NOT EXISTS rutas_i(rutas INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ruta VARCHAR(50) NOT NULL)";

    
    public UsuariosSQLiteHelper(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci\'f3n de la tabla\
        db.execSQL(sqlCreate);
        db.execSQL(lotes);
        db.execSQL(datosS);
        db.execSQL(datosA);
        db.execSQL(imagenes);
        db.execSQL(rutas_i);
        


        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77000,'Chetumal Centro','Colonia')"); 
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77008,'Secretaria de Hacienda y Credito Publico','Gran usuario')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77009,'Palacio de Gobierno del Estado de Quintana Roo','Gran usuario')"); 
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Naval','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Adolfo Lopez Mateos','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Francisco J Mujica INFONAVIT','Unidad habitacional')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Isabel Tenorio','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Bugambilias','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77010,'Del Mar I y II (Infonavit)','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77011,'Centro Sct Quintana Roo','Gran usuario')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77012,'Venustiano Carranza','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77012,'Casitas','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77013,'David G Gutierrez Ruiz','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Villas Kinichna','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Unidad Antorchista','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Los Arcos','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Infonavit Santa Maria','Unidad habitacional')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Oxtankah','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Centenario','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Andara','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77014,'Pacto Obrero Campesino','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77015,'Constituyentes','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77015,'Del Sol','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77015,'Industrial','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77016,'Leona Vicario','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77016,'Martinez Ross','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77017,'Ley Federal de Agua','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77017,'SAHOP','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77018,'5 de Abril','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77018,'Avancemos Juntos','Colonia')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77018,'Reforma','Fraccionamiento')");
        db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77019,'Del Bosque','Colonia')");
    	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77020,'Carlos Riva Palacio','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77020,'Rafael E Melgar FOVISSSTE','Unidad habitacional')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77020,'Gonzalo Guerrero','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77023,'Infonavit Enrique Ramirez y Ramirez','Unidad habitacional')");
      	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77023,'Miraflores II','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77024,'Cedros','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77024,'Fovissste (II Etapa)','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77025,'Sutage INFONAVIT','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77025,'Guadalupe Victoria','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77025,'Forjadores de Quintana Roo','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77025,'Los Almendros','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77025,'Milenio','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77026,'El Sol','Fraccionamiento')");
     	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77026,'Jardines','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77026,'Tumben Cuxtal','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77026,'Fovissste  IV Etapa','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77027,'Miraflores','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77028,'8 de Octubre','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77028,'8 de Octubre','Ampliacion')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77028,'Plutarco Elias Calles','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77029,'Los Monos','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77029,'Lagunitas','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77029,'Bosques del Lago','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77030,'Campestre','Fraccionamiento')");
      	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77034,'Framboyanes','Colonia')");
     	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77035,'Italia','Colonia')");
     	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77036,'Josefa Ortiz de Dominguez','Colonia')");
     	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77037,'Aserradero','Colonia')");
     	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77037,'Taxistas','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77037,'Benito Juarez','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77038,'20 de Noviembre','Colonia')");
      	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77039,'Magisterial','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77040,'Santa Isabel Cereso','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77041,'Cereso Chetumal','Gran usuario')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77048,'Chetumal (Internacional de Chetumal)','Aeropuerto')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77049,'FOVISSSTE 5a Etapa','Unidad habitacional')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77049,'Estatuto Juridico','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Infonavit Alvaro Obregon','Unidad habitacional')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Infonavit Hermanos Flores Magon','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Cascada I','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Residencial la Herradura','Residencial')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Aeropuerto','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Fovissste (I Etapa)','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77050,'Cascadas II','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77053,'Lomas del Caribe','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77059,'Infonavit Villas Chetumal','Unidad habitacional')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77060,'Militar','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77060,'Electricistas','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77070,'Infonavit Aron Merino Fernandez','Unidad habitacional')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'Las Brisas','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'Fovissste VI Etapa (Tampico)','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'Bahia','Fraccionamiento')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'Zona de Granjas','Colonia')");
       	db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'Residencial Caribe','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77079,'FOVISSSTE 6a Etapa','Unidad habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77080,'Infonavit Fidel Velazquez','Unidad habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77082,'Kilometro 5','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77082,'Jardines de Payo Obispo','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77083,'Payo Obispo IV','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77083,'Tamalcab','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77083,'Residencial Chetumal IV','Residencial')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77083,'Nuevo Progreso','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77083,'Payo Obispo I y II','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77084,'Maya Real','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77084,'Infonavit Emancipacion','Unidad habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77084,'Payo Obispo','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77085,'Hacienda Chetumal','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'El Encanto','Conjunto habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Lazaro Cardenas','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Las Americas','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Las Americas II','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Proterritorio VII','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'La Esperanza','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Las Arboledas I','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Residencial Arboledas','Residencial')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Proterritorio Ampliacion I','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Las Americas III','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Bicentenario','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Las Arboledas II','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Territorio Federal de Quintana Roo','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Infonavit 17 de Octubre','Unidad habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Andres Quintana Roo','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Residencial Chetumal I y II (2004)','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Nueva Generacion','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Proterritorio VIII','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Solidaridad','Unidad habitacional')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Comite Proterritorio','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77086,'Caribe','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77087,'Sian Kaan','Fraccionamiento')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77090,'Plutarco Elias Calles','Colonia')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77098,'Barrio Bravo','Barrio')");
		db.execSQL("INSERT INTO colonias(d_codigo,d_asenta,d_tipo_asenta) VALUES (77099,'Primera Legislatura','Colonia')");
        db.execSQL("INSERT INTO rutas_i(ruta) VALUES ('localhost/suapp/Lotes/fotos/')"); 

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqu\'ed utilizamos directamente la opci\'f3n de\
        //      eliminar la tabla anterior y crearla de nuevo vac\'eda con el nuevo formato.\
        //      Sin embargo lo normal ser\'e1 que haya que migrar datos de la tabla antigua\
        //      a la nueva, por lo que este m\'e9todo deber\'eda ser m\'e1s elaborado.\
 
        //Se elimina la version anterior de la tabla\
        db.execSQL("DROP TABLE IF EXISTS colonias");
        db.execSQL("DROP TABLE IF EXISTS lotes");
        db.execSQL("DROP TABLE IF EXISTS datosS");
        db.execSQL("DROP TABLE IF EXISTS datosA");
        db.execSQL("DROP TABLE IF EXISTS imagenes");
        db.execSQL("DROP TABLE IF EXISTS rutas_i");

       //Se crea la nueva version de la tabla\
        db.execSQL(sqlCreate);
    }
    

    
  }