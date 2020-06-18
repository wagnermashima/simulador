package br.feevale.simulador.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Chamado {

	private Integer nrChamado;
	private LocalDateTime dtAbertura;
	private LocalDateTime dtEntradaBacklog;
	private LocalDateTime dtEntradaDesenvolvimento;
	private LocalDateTime dtSaidaDesenvolvimento;
	private LocalDateTime dtProximoChamado;
	private Integer prioridade;
	private Integer cdCliente;
	
	private Duration tempoEntreChamados;
	private Duration tempoEmEspera;
	private Duration tempoEmDesenvolvimento;
	
	private Desenvolvedor desenvolvedor;
	private Long tempoEmDesenvolvimentoRandom;

	public Integer getNrChamado() {
		return nrChamado;
	}

	public void setNrChamado(Integer nrChamado) {
		this.nrChamado = nrChamado;
	}

	public LocalDateTime getDtAbertura() {
		return dtAbertura;
	}

	public void setDtAbertura(LocalDateTime dtAbertura) {
		this.dtAbertura = dtAbertura;
	}

	public LocalDateTime getDtEntradaDesenvolvimento() {
		return dtEntradaDesenvolvimento;
	}

	public void setDtEntradaDesenvolvimento(LocalDateTime dtEntradaDesenvolvimento) {
		this.dtEntradaDesenvolvimento = dtEntradaDesenvolvimento;
	}

	public LocalDateTime getDtSaidaDesenvolvimento() {
		return dtSaidaDesenvolvimento;
	}

	public void setDtSaidaDesenvolvimento(LocalDateTime dtSaidaDesenvolvimento) {
		this.dtSaidaDesenvolvimento = dtSaidaDesenvolvimento;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public LocalDateTime getDtProximoChamado() {
		return dtProximoChamado;
	}

	public void setDtProximoChamado(LocalDateTime dtProximoChamado) {
		this.dtProximoChamado = dtProximoChamado;
	}

	public Integer getCdCliente() {
		return cdCliente;
	}

	public void setCdCliente(Integer cdCliente) {
		this.cdCliente = cdCliente;
	}

	public void calcularTempoChamado() {
		setTempoEntreChamados(calcularTempo(dtEntradaDesenvolvimento, dtProximoChamado));
		setTempoEmEspera(calcularTempo(dtEntradaDesenvolvimento, dtEntradaBacklog));
		setTempoEmDesenvolvimento(calcularTempo(dtEntradaBacklog, dtSaidaDesenvolvimento));
	}

	private Duration calcularTempo(LocalDateTime primeiraData, LocalDateTime segundaData) {
		try {
			return Duration.between(primeiraData, segundaData);
		} catch (Exception e) {
			return Duration.ZERO;
		}
	}

	public Duration getTempoEmEspera() {
		return tempoEmEspera;
	}
	
	public Double getTempoEmEsperaSeconds() {
		Long seconds = getTempoEmEspera().getSeconds();
		return seconds.doubleValue();
	}

	public void setTempoEmEspera(Duration intervalo) {
		this.tempoEmEspera = intervalo;
	}
	
	public void setTempoEmEspera(Long intervalo) {
		setTempoEmEspera(Duration.ofSeconds(intervalo));
	}

	public String getTempoEmEsperaFormatted() {
		return DurationFormatUtils.formatDuration(getTempoEmEspera().toMillis(), "HH:mm");
	}

	public Desenvolvedor getDesenvolvedor() {
		return desenvolvedor;
	}

	public void setDesenvolvedor(Desenvolvedor desenvolvedor) {
		this.desenvolvedor = desenvolvedor;
	}
	
	public Double getTempoEmDesenvolvimentoSeconds() {
		Long seconds = tempoEmDesenvolvimento.getSeconds();
		return seconds.doubleValue();
	}

	public LocalDateTime getDtEntradaBacklog() {
		return dtEntradaBacklog;
	}

	public void setDtEntradaBacklog(LocalDateTime dtEntradaBacklog) {
		this.dtEntradaBacklog = dtEntradaBacklog;
	}

	public Duration getTempoEmDesenvolvimento() {
		return tempoEmDesenvolvimento;
	}
	
	public void setTempoEmDesenvolvimento(Duration tempoEmDesenvolvimento) {
		this.tempoEmDesenvolvimento = tempoEmDesenvolvimento;
	}

	public Duration getTempoEntreChamados() {
		return tempoEntreChamados;
	}

	public void setTempoEntreChamados(Duration tempoEntreChamados) {
		this.tempoEntreChamados = tempoEntreChamados;
	}
	
	public Double getTempoEntreChamadosSeconds() {
		Long seconds = getTempoEntreChamados().getSeconds();
		return seconds.doubleValue();
	}

	public void setTempoEmDesenvolvimentoRandom(Long tempoEmDesenvolvimentoRandom) {
		this.tempoEmDesenvolvimentoRandom = tempoEmDesenvolvimentoRandom;
	}
	
	public Long getTempoEmDesenvolvimentoRandom() {
		return tempoEmDesenvolvimentoRandom;
	}
	
}
