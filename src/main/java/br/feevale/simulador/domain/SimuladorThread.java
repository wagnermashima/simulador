package br.feevale.simulador.domain;

public class SimuladorThread implements Runnable {
	
	private Simulador simulador;
	
	private boolean continnue = true;
	
	public SimuladorThread(Simulador simulador) {
		this.simulador = simulador;
	}
	
	public synchronized void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		simulador.start();
		while (continnue) {
			if (simulador.isPause()) continue;
			
			simulador.transfereChamadosTerminados();
			
			simulador.transfereFilaDesenvolvimento();
			
			simulador.transfereEmDesenvolvimento();
			
			simulador.nextHour();
			
			continnue = simulador.getChamados().size() > 0;
			
			try {
				Thread.sleep(simulador.getEscalaUmaHora().longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setContinnue(boolean continnue) {
		this.continnue = continnue;
	}
}
