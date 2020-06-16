package br.feevale.simulador.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.uncommons.maths.number.NumberGenerator;

public class GeradorChamado {

	private Simulador simulador;
	private NumberGenerator<? extends Number> intervalorGenerator;
	private NumberGenerator<? extends Number> tempoDesenvolvimentoGenerator;

	public GeradorChamado(Simulador simulador) {
		this.simulador = simulador;
		this.intervalorGenerator = NumberGeneratorFactory.build(new EstatisticaChamado(simulador, Chamado::getIntervaloSeconds), simulador.getTipoValor());
		this.tempoDesenvolvimentoGenerator = NumberGeneratorFactory.build(new EstatisticaChamado(simulador, Chamado::getTempoDesenvolvimento), simulador.getTipoValor());
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
		return Duration.ofSeconds(intervalorGenerator.nextValue().longValue());
	}
	
}
