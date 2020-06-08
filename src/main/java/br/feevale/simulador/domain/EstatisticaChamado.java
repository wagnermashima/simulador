package br.feevale.simulador.domain;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.uncommons.maths.statistics.DataSet;

public class EstatisticaChamado {

	private AggregateSummaryStatistics summaryStatistics;
	private DataSet dataSet;

	public EstatisticaChamado(Simulador simulador) {
		dataSet = new DataSet();
		
		SummaryStatistics prototypeStatistics = new SummaryStatistics();
		
		simulador.getChamados().forEach(c -> {
			prototypeStatistics.addValue(c.getIntervalo().getSeconds());
			dataSet.addValue(c.getIntervalo().getSeconds());
		});
		
		summaryStatistics = new AggregateSummaryStatistics(prototypeStatistics);
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
	
}