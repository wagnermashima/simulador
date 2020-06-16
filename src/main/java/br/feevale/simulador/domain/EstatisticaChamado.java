package br.feevale.simulador.domain;

import java.util.function.Function;

import org.uncommons.maths.statistics.DataSet;

public class EstatisticaChamado {

	private DataSet dataSet;

	public EstatisticaChamado(Simulador simulador, Function<Chamado, Double> function) {
		dataSet = new DataSet();
		
		simulador.getChamados().forEach(c -> {
			dataSet.addValue(function.apply(c));
		});
	}
	
	public Double getMedia() {
		return dataSet.getArithmeticMean();
	}
	
	public Double getDesvioPadrao() {
		return dataSet.getStandardDeviation();
	}
	
	public Double getVariancia() {
		return dataSet.getVariance();
	}
	
	public Double getMediana() {
		return dataSet.getMedian();
	}
	
	public Double getMinimo() {
		return dataSet.getMinimum();
	}
	
	public Double getMaximo() {
		return dataSet.getMaximum();
	}
	
}