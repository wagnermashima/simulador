package br.feevale.simulador.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.uncommons.maths.number.NumberGenerator;

public class GeradorChamado {

	private Simulador simulador;
	private NumberGenerator<? extends Number> tempoEntreChamadosGenerator;
	private NumberGenerator<? extends Number> tempoDesenvolvimentoGenerator;
	private EstatisticaChamado estatisticasEntreChamados;
	private EstatisticaChamado estatisticasTempoEmDev;

	public GeradorChamado(Simulador simulador, TipoValorAleatorio tipoValor) {
		this.simulador = simulador;
		this.estatisticasEntreChamados = new EstatisticaChamado(simulador, Chamado::getTempoEntreChamadosSeconds);
		this.estatisticasTempoEmDev = new EstatisticaChamado(simulador, Chamado::getTempoEmDesenvolvimentoSeconds);
		this.tempoEntreChamadosGenerator = NumberGeneratorFactory.build(estatisticasEntreChamados, tipoValor);
		this.tempoDesenvolvimentoGenerator = NumberGeneratorFactory.build(estatisticasTempoEmDev, tipoValor);
	}
	
	public List<Chamado> gerar() {
		List<Chamado> novosChamados = new ArrayList<Chamado>();
		LocalDateTime dataBase = simulador.getChamados().stream().findFirst().get().getDtEntradaDesenvolvimento();
		for (Chamado chamado : simulador.getChamados()) {
			try {
				Chamado novoChamado = new Chamado();
				BeanUtils.copyProperties(novoChamado, chamado);
				
				dataBase = dataBase.plusSeconds(getTempoEntreChamadosRandom());
				novoChamado.setDtEntradaDesenvolvimento(dataBase);
				novoChamado.setTempoEmDesenvolvimentoRandom(getTempoEmDesenvolvimentoRandom());
				
				novosChamados.add(novoChamado);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return novosChamados;
	}

	private Long getTempoEntreChamadosRandom() {
		Long x = -1l;

		long maximo = estatisticasEntreChamados.getMaximo().longValue();
		long minimo = estatisticasEntreChamados.getMinimo().longValue();
		boolean cagado = maximo < minimo;
		
		while (x > maximo || x < minimo) {
			x = tempoEntreChamadosGenerator.nextValue().longValue();
		}
		
		return x;
	}
	
	private Long getTempoEmDesenvolvimentoRandom() {
		
		Long x = -1l;

		long maximo = estatisticasTempoEmDev.getMaximo().longValue();
		long minimo = estatisticasTempoEmDev.getMinimo().longValue();
		boolean cagado = maximo < minimo;
		
		while (x > maximo || x < minimo) {
			x = tempoDesenvolvimentoGenerator.nextValue().longValue();
		}
		
		return x;
	}
	
}
