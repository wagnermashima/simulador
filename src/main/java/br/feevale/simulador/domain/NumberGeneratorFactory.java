package br.feevale.simulador.domain;

import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.DiscreteUniformGenerator;
import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.PoissonGenerator;

public class NumberGeneratorFactory {
	
	public static NumberGenerator<? extends Number> build(EstatisticaChamado estatisticas, TipoValorAleatorio tipo) {
		if (tipo == TipoValorAleatorio.UNIFORME) {
			return new DiscreteUniformGenerator(estatisticas.getMinimo().intValue(), estatisticas.getMaximo().intValue(), new Random());
		} else if (tipo == TipoValorAleatorio.EXPONENCIAL) {
			return new ExponentialGenerator(estatisticas.getMedia(), new Random());
		} else if (tipo == TipoValorAleatorio.NORMAL) {
			return new GaussianGenerator(estatisticas.getMedia(), estatisticas.getDesvioPadrao(), new Random());
		} else if (tipo == TipoValorAleatorio.POISSON) {
			return new PoissonGenerator(estatisticas.getMedia(), new Random());
		}
		throw new RuntimeException("Deu galho");
	}

}
