package br.feevale.simulador.domain;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.uncommons.maths.number.NumberGenerator;

public class ExponentialNumberGenerator implements NumberGenerator<Double> {
	
	private ExponentialDistribution exponentialDistribution;

	public ExponentialNumberGenerator(EstatisticaChamado estatistica) {
		exponentialDistribution = new ExponentialDistribution(estatistica.getRate());
	}

	@Override
	public Double nextValue() {
		return exponentialDistribution.sample();
	}

}
