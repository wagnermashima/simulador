package br.feevale.simulador.domain;

import java.time.LocalDateTime;

public class Chamado {

	private Integer nrChamado;
	private LocalDateTime dtAbertura;
	private LocalDateTime dtEntradaDesenvolvimento;
	private LocalDateTime dtSaidaDesenvolvimento;
	private LocalDateTime dtProximoChamado;
	private Integer prioridade;
	private Integer cdCliente;

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

	public String getIntervalo() {
		// TODO Auto-generated method stub
		return null;
	}

}
