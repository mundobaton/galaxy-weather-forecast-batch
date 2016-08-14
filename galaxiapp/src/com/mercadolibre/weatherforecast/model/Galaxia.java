package com.mercadolibre.weatherforecast.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mercadolibre.weatherforecast.type.PlanetaType;
import com.mercadolibre.weatherforecast.type.SentidoRotacionType;

public class Galaxia {

	private Map<PlanetaType, Planeta> planetas;

	public Galaxia() {
		this.planetas = new HashMap<PlanetaType, Planeta>();
		this.planetas.put(PlanetaType.VULCANOS, new Planeta(0, 10, 5, SentidoRotacionType.ANTIHORARIO));
		this.planetas.put(PlanetaType.FERENGIS, new Planeta(0, 5, 1, SentidoRotacionType.HORARIO));
		this.planetas.put(PlanetaType.BETASOIDES, new Planeta(0, 20, 3, SentidoRotacionType.HORARIO));
		this.planetas.put(PlanetaType.SOL, new Planeta(0, 0, 0, null));
	}

	public void incrementarTiempo(int dias) {
		for (Iterator<Planeta> it = this.planetas.values().iterator(); it.hasNext();) {
			Planeta p = it.next();
			p.avanzarDias(dias);
		}
	}

	public Map<PlanetaType, Planeta> getPlanetas() {
		return planetas;
	}
}
