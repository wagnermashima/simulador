package br.feevale.simulador.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.uncommons.maths.number.NumberGenerator;

public class GeradorChamado {

	private Simulador simulador;
	private NumberGenerator<? extends Number> tempoEntreChamadosGenerator;
	private NumberGenerator<? extends Number> tempoDesenvolvimentoGenerator;

	public GeradorChamado(Simulador simulador, TipoValorAleatorio tipoValor) {
		this.simulador = simulador;
		this.tempoEntreChamadosGenerator = NumberGeneratorFactory.build(new EstatisticaChamado(simulador, Chamado::getTempoEntreChamadosSeconds), tipoValor);
		this.tempoDesenvolvimentoGenerator = NumberGeneratorFactory.build(new EstatisticaChamado(simulador, Chamado::getTempoEmDesenvolvimentoSeconds), tipoValor);
	}
	
	public List<Chamado> gerar() {
		List<Chamado> novosChamados = new ArrayList<Chamado>();
		LocalDateTime dataBase = simulador.getChamados().stream().findFirst().get().getDtEntradaDesenvolvimento();
		for (Chamado chamado : simulador.getChamados()) {
			try {
				Chamado novoChamado = new Chamado();
				BeanUtils.copyProperties(novoChamado, chamado);
				
				dataBase = dataBase.plus(getTempoEntreChamadosRandom());
				novoChamado.setDtEntradaDesenvolvimento(dataBase);
				novoChamado.setTempoEmDesenvolvimento(getTempoEmDesenvolvimentoRandom());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return novosChamados;
	}

	private Duration getTempoEntreChamadosRandom() {
		return Duration.ofSeconds(tempoEntreChamadosGenerator.nextValue().longValue());
	}
	
	private Duration getTempoEmDesenvolvimentoRandom() {
		return Duration.ofSeconds(tempoDesenvolvimentoGenerator.nextValue().longValue());
	}
	
}
