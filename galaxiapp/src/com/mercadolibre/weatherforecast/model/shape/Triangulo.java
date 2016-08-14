package com.mercadolibre.weatherforecast.model.shape;

import com.mercadolibre.weatherforecast.model.Coordenada;

public class Triangulo {

	private final Coordenada p1;
	private final Coordenada p2;
	private final Coordenada p3;

	public Triangulo(Coordenada p1, Coordenada p2, Coordenada p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public Coordenada getP1() {
		return p1;
	}

	public Coordenada getP2() {
		return p2;
	}

	public Coordenada getP3() {
		return p3;
	}

	public double perimetro() {
		double ab = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
		double bc = Math.sqrt(Math.pow(p2.getX() - p3.getX(), 2) + Math.pow(p2.getY() - p3.getY(), 2));
		double ac = Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) + Math.pow(p1.getY() - p3.getY(), 2));

		return ab + bc + ac;
	}

}
