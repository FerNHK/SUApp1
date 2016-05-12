package com.example.suv2;

public interface OrientationListener {

	/**
	 * Cambia la orientación del teléfono
	 * @param azimuth
	 * @param pitch
	 * @param roll
	 */
	public void onOrientationChanged(float azimuth, float pitch, float roll);
	
	/**
	 * Se levanta la parte de adelante del teléfono
	 */
	public void onTopUp();

	/**
	 * Se levanta la parte de atras del teléfono
	 */
	public void onBottomUp();

	/**
	 * Se levanta la parte derecha del teléfono
	 */
	public void onRightUp();

	/**
	 * Se levanta la parte izquierda del teléfono
	 */
	public void onLeftUp();
	
}
