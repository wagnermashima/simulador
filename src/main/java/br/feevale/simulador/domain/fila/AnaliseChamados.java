package br.feevale.simulador.domain.fila;

import java.util.HashMap;
import java.util.Map;

import org.uncommons.maths.statistics.DataSet;

public class AnaliseChamados {
	
	private final Map<Integer, Double> resultadoMetrica = new HashMap<>();
	private Double media;
	private Double desvioPadrao;
	private DataSet dataSet;

	public Map<Integer, Double> getResultadoMetrica() {
		return resultadoMetrica;
	}

	public Double getMedia() {
		if (resultadoMetrica.isEmpty()) return null;
		if (media != null) return media;

		DataSet dataSet = getDataSet();
		media = dataSet.getArithmeticMean();
		
		return media;
	}
	
	public Double getDesvioPadrao() {
		if (resultadoMetrica.isEmpty()) return null;
		if (desvioPadrao != null) return desvioPadrao;
		
		DataSet dataSet = getDataSet();
		desvioPadrao = dataSet.getStandardDeviation();
		
		return desvioPadrao;
	}
	
	private DataSet getDataSet() {
		if (dataSet == null) {
			dataSet = new DataSet();
			for (Double result : resultadoMetrica.values()) {
				dataSet.addValue(result);
			}
		}
		return dataSet;
	}

}
