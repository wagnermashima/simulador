package br.feevale.simulador.domain;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class EstatisticaChamado {

	private Simulador simulador;
	private AggregateSummaryStatistics statistics;

	public EstatisticaChamado(Simulador simulador) {
		this.simulador = simulador;
		
		SummaryStatistics prototypeStatistics = new SummaryStatistics();
		
		statistics = new AggregateSummaryStatistics(prototypeStatistics);
		
	}
	
	
}
