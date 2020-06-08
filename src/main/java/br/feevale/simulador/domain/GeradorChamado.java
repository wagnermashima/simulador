package br.feevale.simulador.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.uncommons.maths.number.NumberGenerator;

public class GeradorChamado<T extends Number> {

	private Simulador simulador;
	private NumberGenerator<T> generator;

	public GeradorChamado(Simulador simulador, NumberGenerator<T> generator) {
		this.simulador = simulador;
		this.generator = generator;
		/*
		EstatisticaChamado estatisticas = new EstatisticaChamado(simulador);
		if (simulador.getTipoValor() == TipoValorAleatorio.UNIFORME) {
			generator = new DiscreteUniformGenerator(estatisticas.getMinimo().intValue(), estatisticas.getMaximo().intValue(), new Random());
		} else if (simulador.getTipoValor() == TipoValorAleatorio.EXPONENCIAL) {
			generator = new ExponentialGenerator(estatisticas.getMedia(), new MersenneTwister());
		} else if (simulador.getTipoValor() == TipoValorAleatorio.NORMAL) {
			generator = new GaussianGenerator(estatisticas.getMedia(), estatisticas.getDesvioPadrao(), new MersenneTwister());
		}
		*/
	}
	
	public List<Chamado> gerar() {
		List<Chamado> novosChamados = new ArrayList<Chamado>();
		simulador.getChamados().forEach(c -> {
			try {
				Chamado novoChamado = new Chamado();
				BeanUtils.copyProperties(novoChamado, c);
				
				novoChamado.setIntervalo(getIntervaloRandom());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return novosChamados;
	}

	private Duration getIntervaloRandom() {
		return Duration.ofSeconds(generator.nextValue().longValue());
	}
	
}
