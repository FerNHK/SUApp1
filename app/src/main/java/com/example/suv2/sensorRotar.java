package com.example.suv2;

import android.content.Context;

import java.util.List;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.example.suv2.OrientationListener;


public class sensorRotar {
		
	//DESCOMENTAR PARA USAR SIN EMULADOR
	private static SensorManager sensorManager;
	
	
	private static OrientationListener listener;
	
	
	private static boolean running = false;
	
	/** Lados del teléfono */
	enum Side {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	/**
	 * Retorna verdadero si el manager está escuchando cambios en la orientación
	 */
	public static boolean isListening() {
		return running;
	}
	
	
	public static void stopListening() {
		running = false;
		try {
			if (sensorManager != null && sensorEventListener != null) {
				sensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) {}
	}
	
		
	/**
	 * Registra un listener y comienza a escuchar
	 */
	public static void startListening(
			//DESCOMENTAR PARA USAR SIN SIMULADOR
	OrientationListener orientationListener) {
		sensorManager = (SensorManager) MainCamara.getContext().getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(
				Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			running = sensorManager.registerListener(
					sensorEventListener, sensor, 
					SensorManager.SENSOR_DELAY_NORMAL);
			listener = orientationListener;
		}			
	}


	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {
		
		/** El lado que está actualmente arriba */
		private Side currentSide = null;
		private Side oldSide = null;
		private float azimuth;
		private float pitch;
		private float roll;
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			
			azimuth = event.values[0]; 	// azimuth
			pitch = event.values[1]; 	// pitch
			roll = event.values[2];		// roll
			
			if (pitch < -45 && pitch > -135) {
				// top side up
				currentSide = Side.TOP;
			} else if (pitch > 45 && pitch < 135) {
				// bottom side up
				currentSide = Side.BOTTOM;
			} else if (roll > 45) {
				// right side up
				currentSide = Side.RIGHT;
			} else if (roll < -45) {
				// left side up
				currentSide = Side.LEFT;
			}
			
			if (currentSide != null && !currentSide.equals(oldSide)) {
				switch (currentSide) {
					case TOP : 
						listener.onTopUp();
						break;
					case BOTTOM : 
						listener.onBottomUp();
						break;
					case LEFT: 
						listener.onLeftUp();
						break;
					case RIGHT: 
						listener.onRightUp();
						break;
				}
				oldSide = currentSide;
			}
			
			listener.onOrientationChanged(azimuth, pitch, roll);
		}
		
	};
}