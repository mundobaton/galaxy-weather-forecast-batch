package com.mercadolibre.weatherforecast.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mercadolibre.weatherforecast.core.shape.ShapeHelper;
import com.mercadolibre.weatherforecast.model.Galaxia;
import com.mercadolibre.weatherforecast.model.Planeta;
import com.mercadolibre.weatherforecast.model.shape.Recta;
import com.mercadolibre.weatherforecast.model.shape.Triangulo;
import com.mercadolibre.weatherforecast.type.PlanetaType;

public class WeatherForecast {

	private final static String OPTIMAS = "cond_optimas";
	private final static String SEQUIA = "sequia";
	private final static String LLUVIA = "lluvia";
	private final static String OTRO = "otro";

	private Galaxia gal;
	private WeatherForecastHelper wfh;
	private WeatherBatchHelper wbh;

	public WeatherForecast() {
		this.gal = new Galaxia();
		this.wfh = new WeatherForecastHelper();
		this.wbh = new WeatherBatchHelper();
	}

	public boolean tieneCondicionesSequia() {
		return wfh.tieneCondicionesSequia();
	}

	public void insertarResultados(int dias) throws IOException {
		String clima = null;
		for (int i = 1; i <= dias; i++) {
			gal.incrementarTiempo(1);
			clima = obtenerClima();
			if (clima != null) {
				this.wbh.putData(i, clima);
			}
		}
	}

	public void obtenerResultados(int dias) {
		Map<String, Integer> results = new HashMap<String, Integer>();
		String clima = null;
		for (int i = 1; i <= dias; i++) {
			gal.incrementarTiempo(1);
			clima = obtenerClima();
			if (clima != null) {
				results.put(clima, results.get(clima) == null ? 1 : results.get(clima) + 1);
			}
		}
		for (Iterator<String> it = results.keySet().iterator(); it.hasNext();) {
			String k = it.next();
			System.out.println(">> La cantidad de periodos de " + k + " es " + results.get(k));
		}
		System.out.println(">> El dia de pico de lluvia es el dia " + wfh.diaMaximaLluvia());
	}

	private String obtenerClima() {
		if (wfh.tieneCondicionesOptimas()) {
			return OPTIMAS;
		} else if (wfh.tieneCondicionesSequia()) {
			return SEQUIA;
		} else if (wfh.tieneCondicionesLluvia()) {
			return LLUVIA;
		} else {
			return OTRO;
		}
	}

	public static void main(String[] args) throws IOException {
		WeatherForecast wf = new WeatherForecast();
		int dias = 3650; // 10 * 365
		if (args[0].equals("obtenerResultados")) {
			wf.obtenerResultados(dias);
		} else if (args[0].equals("insertarResultados")) {
			wf.insertarResultados(dias);
		} else {
			System.out.println("Comando no encontrado");
		}
	}

	class WeatherBatchHelper {
		private final static String ENDPOINT = "http://localhost:9200/weatherforecast/dia";
		private final static String AGENT = "user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
		private final static String ACCEPT_LANG = "en-US,en;q=0.5";
		private final static String ACCEPT = "application/json";

		public void putData(int dia, String clima) throws IOException {
			URL url = new URL(ENDPOINT + "/" + dia);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("User-Agent", AGENT);
			con.setRequestProperty("Accept-Language", ACCEPT_LANG);
			con.setRequestProperty("Accept", ACCEPT);

			String val = "{\"dia\":\"" + dia + "\",\"clima\":\"" + clima + "\"}";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			dos.writeBytes(val);
			dos.flush();
			dos.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println("Dia : " + dia + " Response: " + response);
		}
	}

	class WeatherForecastHelper {

		double perimetroAcum = 0;
		int diaMaxPerimetro = 0;

		public boolean tieneCondicionesSequia() {
			Planeta p1 = gal.getPlanetas().get(PlanetaType.VULCANOS);
			Planeta p2 = gal.getPlanetas().get(PlanetaType.BETASOIDES);
			Planeta p3 = gal.getPlanetas().get(PlanetaType.FERENGIS);
			Planeta sol = gal.getPlanetas().get(PlanetaType.SOL);

			Recta r = new Recta(p2.getPosicion().getCoordCartesiana(), p3.getPosicion().getCoordCartesiana());
			return ShapeHelper.puntoPerteneceRecta(p1.getPosicion().getCoordCartesiana(), r)
					&& ShapeHelper.puntoPerteneceRecta(sol.getPosicion().getCoordCartesiana(), r);
		}

		public boolean tieneCondicionesOptimas() {
			Planeta p1 = gal.getPlanetas().get(PlanetaType.VULCANOS);
			Planeta p2 = gal.getPlanetas().get(PlanetaType.BETASOIDES);
			Planeta p3 = gal.getPlanetas().get(PlanetaType.FERENGIS);
			Planeta sol = gal.getPlanetas().get(PlanetaType.SOL);

			Recta r = new Recta(p2.getPosicion().getCoordCartesiana(), p3.getPosicion().getCoordCartesiana());
			return ShapeHelper.puntoPerteneceRecta(p1.getPosicion().getCoordCartesiana(), r)
					&& !ShapeHelper.puntoPerteneceRecta(sol.getPosicion().getCoordCartesiana(), r);
		}

		public boolean tieneCondicionesLluvia() {
			Planeta p1 = gal.getPlanetas().get(PlanetaType.VULCANOS);
			Planeta p2 = gal.getPlanetas().get(PlanetaType.BETASOIDES);
			Planeta p3 = gal.getPlanetas().get(PlanetaType.FERENGIS);
			Planeta sol = gal.getPlanetas().get(PlanetaType.SOL);

			Triangulo t = new Triangulo(p1.getPosicion().getCoordCartesiana(), p2.getPosicion().getCoordCartesiana(),
					p3.getPosicion().getCoordCartesiana());
			boolean result = ShapeHelper.puntoPerteneceTriangulo(sol.getPosicion().getCoordCartesiana(), t);
			if (result) {
				if (t.perimetro() > perimetroAcum) {
					perimetroAcum = t.perimetro();
					diaMaxPerimetro = p1.getEdad();
				}
			}
			return result;
		}

		public int diaMaximaLluvia() {
			return this.diaMaxPerimetro;
		}
	}

}
