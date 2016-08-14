package com.mercadolibre.weatherforecast.model;

import com.mercadolibre.weatherforecast.type.SentidoRotacionType;

public class Movimiento {

	private final double velocidad; // expresado en radianes/dia
	private final SentidoRotacionType sentido;

	public Movimiento(double vel, SentidoRotacionType sentidoRot) {
		this.velocidad = Math.toRadians(vel);
		this.sentido = sentidoRot;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public SentidoRotacionType getSentido() {
		return sentido;
	}

}
