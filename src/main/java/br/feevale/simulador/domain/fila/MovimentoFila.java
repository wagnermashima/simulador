package br.feevale.simulador.domain.fila;

import java.time.LocalDateTime;

import br.feevale.simulador.domain.Chamado;

public class MovimentoFila implements Comparable<MovimentoFila>{
	
	private Chamado chamado;
	private LocalDateTime data;
	private EntradaSaida entradaSaida;

	private MovimentoFila(Chamado chamado, LocalDateTime data, EntradaSaida entradaSaida) {
		this.chamado = chamado;
		this.data = data;
		this.entradaSaida = entradaSaida;
	}

	public static MovimentoFila saida(Chamado chamado) {
		return new MovimentoFila(chamado, chamado.getDtSaidaDesenvolvimento(), EntradaSaida.Saida);
	}

	public static MovimentoFila entrada(Chamado chamado) {
		return new MovimentoFila(chamado, chamado.getDtEntradaDesenvolvimento(), EntradaSaida.Entrada);
	}

	public Chamado getChamado() {
		return chamado;
	}

	public LocalDateTime getData() {
		return data;
	}

	public EntradaSaida getEntradaSaida() {
		return entradaSaida;
	}
	
	public boolean isEntrada() {
		return EntradaSaida.Entrada.equals(getEntradaSaida());
	}
	
	public boolean isSaida() {
		return EntradaSaida.Saida.equals(getEntradaSaida());
	}
	
	private enum EntradaSaida {
		Entrada, Saida
	}

	@Override
	public int compareTo(MovimentoFila o) {
		return getData().compareTo(o.getData());
	}

}
