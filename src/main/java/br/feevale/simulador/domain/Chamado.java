package br.feevale.simulador.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Chamado {

	private Integer nrChamado;
	private LocalDateTime dtAbertura;
	private LocalDateTime dtEntradaDesenvolvimento;
	private LocalDateTime dtSaidaDesenvolvimento;
	private LocalDateTime dtProximoChamado;
	private Integer prioridade;
	private Integer cdCliente;
	private Duration intervalo;

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

	public void calcularIntervalo() {
		try {
			setIntervalo(Duration.between(dtSaidaDesenvolvimento, dtProximoChamado));
		} catch (Exception e) {
			setIntervalo(Duration.ZERO);
		}
	}

	public Duration getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Duration intervalo) {
		this.intervalo = intervalo;
	}
	
	public void setIntervalo(Long intervalo) {
		setIntervalo(Duration.ofSeconds(intervalo));
	}

	public String getIntervaloFormatted() {
		return DurationFormatUtils.formatDuration(getIntervalo().toMillis(), "HHH:mm");
	}

}
