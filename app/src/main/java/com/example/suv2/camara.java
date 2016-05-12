package com.example.suv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;





public class camara extends Activity implements View.OnClickListener,OrientationListener {
    private SurfaceView preview=null;
    public static String fileName;
    private SurfaceHolder previewHolder=null;
    private Camera camera=null;
    private boolean inPreview=false;
    private boolean cameraConfigured=false;
    ImageButton closeAPP;
    AlertDialog alertDialog;
    private static final String BD_IMAGE_FILE = "/bd";
    ImageButton a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        closeAPP = (ImageButton)findViewById(R.id.cerrar);

        a = (ImageButton)findViewById(R.id.imgTomarFoto);
        a.setRotation(-90);
        a.setOnClickListener(this);
        closeAPP.setEnabled(false);
        a.setOnClickListener(this);
        closeAPP.setOnClickListener(this);

        preview=(SurfaceView)findViewById(R.id.surfaceView);
        previewHolder=preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        a.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                camera.autoFocus(new Camera.AutoFocusCallback() {
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
    public void onResume() {
        super.onResume();

        camera=Camera.open(0);

        if (camera == null) {
            camera=Camera.open();
        }

        startPreview();
    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;

        super.onPause();
    }



    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result=size;
                }
                else {
                    int resultArea=result.width * result.height;
                    int newArea=size.width * size.height;

                    if (newArea > resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private Camera.Size getSmallestPictureSize(Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (result == null) {
                result=size;
            }
            else {

            }
        }

        return(result);
    }

    private void initPreview(int width, int height) {
        if (camera != null && previewHolder.getSurface() != null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback",
                        "Exception in setPreviewDisplay()", t);
                Toast.makeText(this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size=getBestPreviewSize(width, height, parameters);
                Camera.Size pictureSize=getSmallestPictureSize(parameters);

                if (size != null && pictureSize != null) {
                    parameters.setPreviewSize(size.width, size.height);
                    parameters.setPictureSize(pictureSize.width,
                            pictureSize.height);
                    parameters.setPictureFormat(ImageFormat.JPEG);
                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera != null) {
            camera.startPreview();
            inPreview=true;
        }
    }

    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaceChanged()
        }

        public void surfaceChanged(SurfaceHolder holder, int format,
                                   int width, int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };

    Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new SavePhotoTask().execute(data);
            camera.startPreview();
            inPreview=true;
        }
    };


    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.imgTomarFoto:
                closeAPP.setEnabled(true);
                float get = v.getRotation();
                if (get == -90) {
                    onTopUp();
                    camera.takePicture(null, null,
                            photoCallback);
                } else if (get == 0) {
                    onRightUp();
                    camera.takePicture(null, null,
                            photoCallback);
                } else if (get == 90) {
                    onBottomUp();
                    camera.takePicture(null, null,
                            photoCallback);
                } else if (get == 180) {
                    camera.takePicture(null, null,
                            photoCallback);
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
            bd.execSQL("INSERT INTO	imagenes(coord,nombre_i,nombre_carpeta) Values('"+null+"','"+camara.fileName+"','"+MainForm.srt+"')");
        }catch(SQLException e){
            Log.e("Error",e.toString());
        }
    }
    public File getAlbumStorageDir() {
        String extraDirec = "";
        Intent intent = getIntent();
        extraDirec = intent.getStringExtra("Directorio").toString();
        return new File(Environment.getExternalStorageDirectory()+"/DCIM/"
                + BD_IMAGE_FILE + extraDirec);
    }

    class SavePhotoTask extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... data) {
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
                Log.d("EW", "onPictureTaken - wrote bytes: " + data.length
                        + " to " + outFile.getAbsolutePath().toString());
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
    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }
    @Override
    public void onOrientationChanged(float azimuth, float pitch, float roll) {
    }

    @Override
    public void onBottomUp() {
        Log.e("Mensaje", "Abajo");
        a.setRotation(90);
    }

    @Override
    public void onLeftUp() {
        Log.e("Mensaje", "Izquierda");
        a.setRotation(180);
    }

    @Override
    public void onRightUp() {
        Log.e("Mensaje", "Derecha");
        a.setRotation(0);
    }

    @Override
    public void onTopUp() {
        Log.e("Mensaje", "Arriba");
        a.setRotation(-90);
    }
}
