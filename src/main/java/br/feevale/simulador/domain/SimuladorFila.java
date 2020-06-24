package br.feevale.simulador.domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimuladorFila {
	
	private Simulador simulador;
	
	private boolean continnue = true;
	
	public SimuladorFila(Simulador simulador) {
		this.simulador = simulador;
	}
	
	public void simular() {
		simulador.start();
		while (continnue) {
			simulador.transfereChamadosTerminados();
			
			simulador.transfereFilaDesenvolvimento();
			
			transfereEmDesenvolvimento();
			
			simulador.nextHour();
			
			continnue = simulador.getChamados().size() > 0;
		}
		
		simulador.resetChamados();
	}
	
	private void transfereEmDesenvolvimento() {
		if (simulador.getDesenvolvedores().isEmpty()) return;
		if (simulador.getFilaDesenvolvimento().isEmpty()) return;
		
		for (Desenvolvedor desenvolvedor : simulador.getDesenvolvedores()) {
			if (desenvolvedor.getChamado() == null) {
				Chamado chamado = simulador.getFilaDesenvolvimento().stream().filter(c -> c.getDtSaidaDesenvolvimento() != null).findFirst().orElse(null);
				if (chamado == null) break;
				
				LocalDateTime horaAtual = simulador.getHoraAtual();
				chamado.setDtEntradaBacklog(horaAtual);
				chamado.setTempoEmEspera(Duration.between(chamado.getDtEntradaDesenvolvimento(), horaAtual));
				
				desenvolvedor.setChamado(chamado);

				simulador.getFilaDesenvolvimento().remove(chamado);
				simulador.getEmDesenvolvimento().add(chamado);
			}
		}
	}
}
