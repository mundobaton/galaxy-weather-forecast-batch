package com.mercadolibre.weatherforecast.model;

import com.mercadolibre.weatherforecast.type.SentidoRotacionType;

public class Planeta {

	private Posicion posicion;
	private final Movimiento movimiento;
	private int edad = 0;

	public Planeta(double coordX, double coordY, double vel, SentidoRotacionType sentido) {
		this.posicion = new Posicion(coordX, coordY);
		this.movimiento = new Movimiento(vel, sentido);
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void avanzarDias(int dias) {
		// posInicial + V.T
		posicion.setRadianes(posicion.getRadianes() + (movimiento.getVelocidad() * dias
				* (movimiento.getSentido() == SentidoRotacionType.HORARIO ? -1 : 1)));
		posicion.recalcularPosicion();
		
		edad = edad + dias;
	}
}
