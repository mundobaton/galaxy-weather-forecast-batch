package com.mercadolibre.weatherforecast.model.shape;

import com.mercadolibre.weatherforecast.model.Coordenada;

public class Recta {
	
	private final Coordenada origen;
	private final Coordenada destino;
	
	public Recta(Coordenada p1, Coordenada p2) {
		this.origen = p1;
		this.destino = p2;
	}
	
	public Coordenada getOrigen() {
		return origen;
	}
	
	public Coordenada getDestino() {
		return destino;
	}
}
