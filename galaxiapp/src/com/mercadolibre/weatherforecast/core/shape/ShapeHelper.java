package com.mercadolibre.weatherforecast.core.shape;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.mercadolibre.weatherforecast.model.Coordenada;
import com.mercadolibre.weatherforecast.model.shape.Recta;
import com.mercadolibre.weatherforecast.model.shape.Triangulo;

public class ShapeHelper {

	public static boolean puntoPerteneceRecta(Coordenada p1, Recta r) {
		Line2D line = new Line2D.Double(r.getOrigen().getX(), r.getOrigen().getY(), r.getDestino().getX(),
				r.getDestino().getY());
		return line.ptLineDist(new Point2D.Double(p1.getX(), p1.getY())) == 0;

	}

	public static boolean puntoPerteneceTriangulo(Coordenada p1, Triangulo t) {
		Path2D path = new Path2D.Double();
		path.moveTo(t.getP1().getX(), t.getP1().getY());
		path.lineTo(t.getP2().getX(), t.getP2().getY());
		path.lineTo(t.getP3().getX(), t.getP3().getY());
		path.closePath();

		return path.contains(new Point2D.Double(p1.getX(), p1.getY()));
	}
}
