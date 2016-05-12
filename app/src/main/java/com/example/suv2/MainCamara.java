package com.example.suv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainCamara extends Activity implements OrientationListener, OnClickListener {
	private static final String TAG = "CamTestActivity";
	private static final String BD_IMAGE_FILE = "/bd";
	Preview preview;
	ImageButton buttonClick;
	ImageButton closeAPP;
	Camera camera;
	Activity act;
	Context ctx;
	LayoutInflater myInflater;
	AlertDialog alertDialog;
	public static String fileName;
	private static Context CONTEXT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ctx = this;
		act = this;
		CONTEXT = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_camara);

		preview = new Preview(this,
				(SurfaceView) findViewById(R.id.surfaceView));
		preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		((FrameLayout) findViewById(R.id.layout)).addView(preview);
		preview.setKeepScreenOn(true);

		Toast.makeText(ctx, "Tome las fotos Necesarias ",
				Toast.LENGTH_LONG).show();
       closeAPP = (ImageButton)findViewById(R.id.cerrar);
		buttonClick = (ImageButton) findViewById(R.id.imgTomarFoto);
		buttonClick.setRotation(-90);
		closeAPP.setEnabled(false);
		buttonClick.setOnClickListener(this);
		closeAPP.setOnClickListener(this);
		buttonClick.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				camera.autoFocus(new AutoFocusCallback() {
					@Override
					public void onAutoFocus(boolean arg0, Camera arg1) {
						// camera.takePicture(shutterCallback, rawCallback,
						// jpegCallback);
					}
				});
				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		int numCams = Camera.getNumberOfCameras();
		if (numCams > 0) {
			try {
				camera = Camera.open(0);
				camera.startPreview();
				preview.setCamera(camera);
				sensorRotar.startListening(this);
			} catch (RuntimeException ex) {
				Toast.makeText(ctx, "La camara no se encuentra activa",
						Toast.LENGTH_LONG).show();
			}
		}
		sensorRotar.startListening(this);
	}

	@Override
	protected void onPause() {

		if (camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	private void resetCam() {
		camera.startPreview();
		preview.setCamera(camera);
	}

	private void refreshGallery(File file) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(Uri.fromFile(file));
		sendBroadcast(mediaScanIntent);
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
			// Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// Log.d(TAG, "onPictureTaken - raw");
		}
	};

	PictureCallback jpegCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			new SaveImageTask().execute(data);
			resetCam();
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

	private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

		@SuppressLint("DefaultLocale") @Override
		protected Void doInBackground(byte[]... data) {
			FileOutputStream outStream = null;
			try {
				//
				fileName= String.format("imagen %d.jpg",
						System.currentTimeMillis());
				File outFile = new File(getAlbumStorageDir(), fileName);
				outStream = new FileOutputStream(outFile);
				outStream.write(data[0]);
				outStream.flush();
				outStream.close();
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length
						+ " to " + outFile.getAbsolutePath());
				refreshGallery(outFile);
				insertarImagenes_bd();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			return null;
		}

	}

	public File getAlbumStorageDir() {
		String extraDirec = "";
		Intent intent = getIntent();
		extraDirec = intent.getStringExtra("Directorio").toString();
		return new File(Environment.getExternalStorageDirectory()+"/DCIM/"
				+ BD_IMAGE_FILE + extraDirec);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensorRotar.isListening()) {
			sensorRotar.stopListening();
		}
	}

	public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onOrientationChanged(float azimuth, float pitch, float roll) {
	}

	@Override
	public void onBottomUp() {
		Log.e("Mensaje", "Abajo");
		buttonClick.setRotation(90);
	}

	@Override
	public void onLeftUp() {
		Log.e("Mensaje", "Izquierda");
		buttonClick.setRotation(180);
	}

	@Override
	public void onRightUp() {
		Log.e("Mensaje", "Derecha");
		buttonClick.setRotation(0);
	}

	@Override
	public void onTopUp() {
		Log.e("Mensaje", "Arriba");
		buttonClick.setRotation(-90);
	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.imgTomarFoto:
			closeAPP.setEnabled(true);
				float get = v.getRotation();
				if (get == -90) {
					Log.e("-90","entro");
					onTopUp();
					camera.takePicture(shutterCallback, rawCallback,
							jpegCallback);
				} else if (get == 0) {
					Log.e("Cero","entro");
					onRightUp();
					camera.takePicture(shutterCallback, rawCallback,
							jpegCallback);
				} else if (get == 90) {
					Log.e("90","entro");
					onBottomUp();
					camera.takePicture(shutterCallback, rawCallback,
							jpegCallback);
				} else if (get == 180) {
					Log.e("180","entro");
					camera.takePicture(shutterCallback, rawCallback,
							jpegCallback);
				}
				break;
		case R.id.cerrar:
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Cerrar Camara");
			alert.setMessage("Desea Salir de la captura de Fotos");
			alert.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						   	finish();
						   	dialog.dismiss();
						}		
					});
			alert.setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
						}
					});
			alertDialog = alert.create();
			alertDialog.show();
			break;

		default:
			break;
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
	public void insertarImagenes_bd(){
		UsuariosSQLiteHelper admin = new UsuariosSQLiteHelper(this, "mapas", null, 1);
		SQLiteDatabase bd=  admin.getWritableDatabase();
		try{
		bd.execSQL("INSERT INTO	imagenes(coord,nombre_i,nombre_carpeta) Values('"+null+"','"+MainCamara.fileName+"','"+MainForm.srt+"')");
		}catch(SQLException e){
			Log.e("Error",e.toString());
		}
	}
}
