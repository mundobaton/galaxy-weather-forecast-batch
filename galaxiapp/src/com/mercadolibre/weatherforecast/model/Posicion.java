package com.mercadolibre.weatherforecast.model;

/**
 * 
 * Posicion del planeta expresado en coordenadas polares
 *
 */
public class Posicion {

	private final double radio;
	private double radianes;
	private Coordenada coordCartesiana;

	public Posicion(double x, double y) {
		this.radianes = Math.atan2(y, x);
		this.radio = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		recalcularPosicion();
	}

	public double getRadianes() {
		return radianes;
	}

	public void setRadianes(double radianes) {
		this.radianes = radianes;
	}

	public double getAngulo() {
		return Math.toDegrees(radianes);
	}

	public double getRadio() {
		return radio;
	}

	public void recalcularPosicion() {
		this.coordCartesiana = new Coordenada(this.radio * Math.cos(this.radianes), this.radio * Math.sin(radianes));
	}

	public Coordenada getCoordCartesiana() {
		return coordCartesiana;
	}

}
