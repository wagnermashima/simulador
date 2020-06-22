package br.feevale.simulador.domain;

import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.DiscreteUniformGenerator;
import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.PoissonGenerator;

public class NumberGeneratorFactory {
	
	public static NumberGenerator<? extends Number> build(EstatisticaChamado estatisticas, TipoValorAleatorio tipo) {
		Random random = new MersenneTwisterRNG();
		if (tipo == TipoValorAleatorio.UNIFORME) {
			return new DiscreteUniformGenerator(estatisticas.getMinimo().intValue(), estatisticas.getMaximo().intValue(), random);
		} else if (tipo == TipoValorAleatorio.EXPONENCIAL) {
			return new ExponentialGenerator(estatisticas.getMedia(), random);
		} else if (tipo == TipoValorAleatorio.NORMAL) {
			return new GaussianGenerator(estatisticas.getMedia(), estatisticas.getDesvioPadrao(), random);
		} else if (tipo == TipoValorAleatorio.POISSON) {
			return new PoissonGenerator(estatisticas.getVariancia(), random);
		}
		throw new RuntimeException("Deu galho");
	}

}
