package br.feevale.simulador.domain.fila;

import java.util.HashMap;
import java.util.Map;

import org.uncommons.maths.statistics.DataSet;

import br.feevale.simulador.domain.TipoValorAleatorio;

public class AnaliseChamados {
	
	private final Map<Integer, Double> resultadoMetrica = new HashMap<>();
	private TipoValorAleatorio tipo;
	private Integer nrDesenvolvedores;
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

	public TipoValorAleatorio getTipo() {
		return tipo;
	}

	public void setTipo(TipoValorAleatorio tipo) {
		this.tipo = tipo;
	}

	public Integer getNrDesenvolvedores() {
		return nrDesenvolvedores;
	}

	public void setNrDesenvolvedores(Integer nrDesenvolvedores) {
		this.nrDesenvolvedores = nrDesenvolvedores;
	}

}
