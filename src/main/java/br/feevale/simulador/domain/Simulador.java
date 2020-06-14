package br.feevale.simulador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulador {

	private List<Chamado> chamados = new ArrayList<>();
	private TipoValorAleatorio tipoValor;
	private Integer nrVezesSimular;
	private Integer escalaUmaHora = 300;
	private Integer nrDesenvolvedores = 10;
	
	private List<Desenvolvedor> desenvolvedores = new ArrayList<>();

	private List<Chamado> filaDesenvolvimento = new ArrayList<Chamado>();
	private List<Chamado> finalizados = new ArrayList<Chamado>();
	private List<Chamado> emDesenvolvimento = new ArrayList<Chamado>();
	
	private LocalDateTime horaAtual;
	private String horaAtualFormatted;

	private boolean pause = false;

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		Object oldValue = this.chamados;
		this.chamados = chamados;
		changeSupport.firePropertyChange("chamados", oldValue, chamados);
	}

	public TipoValorAleatorio getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValorAleatorio tipoValor) {
		Object oldValue = this.tipoValor;
		this.tipoValor = tipoValor;
		changeSupport.firePropertyChange("tipoValor", oldValue, tipoValor);
	}

	public Integer getNrVezesSimular() {
		return nrVezesSimular;
	}

	public void setNrVezesSimular(Integer nrVezesSimular) {
		Object oldValue = this.nrVezesSimular;
		this.nrVezesSimular = nrVezesSimular;
		changeSupport.firePropertyChange("nrVezesSimular", oldValue, nrVezesSimular);
	}

	public Integer getEscalaUmaHora() {
		return escalaUmaHora;
	}

	public void setEscalaUmaHora(Integer escalaUmaHora) {
		Object oldValue = this.escalaUmaHora;
		this.escalaUmaHora = escalaUmaHora;
		changeSupport.firePropertyChange("escalaUmaHora", oldValue, escalaUmaHora);
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		Object oldValue = this.pause;
		this.pause = pause;
		changeSupport.firePropertyChange("pause", oldValue, pause);
	}

	public List<Chamado> getFilaDesenvolvimento() {
		return filaDesenvolvimento;
	}

	public void setFilaDesenvolvimento(List<Chamado> filaDesenvolvimento) {
		Object oldValue = this.filaDesenvolvimento;
		this.filaDesenvolvimento = filaDesenvolvimento;
		changeSupport.firePropertyChange("filaDesenvolvimento", oldValue, filaDesenvolvimento);
	}

	public List<Chamado> getFinalizados() {
		return finalizados;
	}

	public void setFinalizados(List<Chamado> finalizados) {
		Object oldValue = this.finalizados;
		this.finalizados = finalizados;
		changeSupport.firePropertyChange("finalizados", oldValue, finalizados);
	}

	public List<Chamado> getEmDesenvolvimento() {
		return emDesenvolvimento;
	}

	public void setEmDesenvolvimento(List<Chamado> emDesenvolvimento) {
		Object oldValue = this.emDesenvolvimento;
		this.emDesenvolvimento = emDesenvolvimento;
		changeSupport.firePropertyChange("emDesenvolvimento", oldValue, emDesenvolvimento);
	}

	public Integer getNrDesenvolvedores() {
		return nrDesenvolvedores;
	}

	public void setNrDesenvolvedores(Integer nrDesenvolvedores) {
		Object oldValue = this.nrDesenvolvedores;
		this.nrDesenvolvedores = nrDesenvolvedores;
		changeSupport.firePropertyChange("nrDesenvolvedores", oldValue, nrDesenvolvedores);
	}

	public void start() {
		chamados.sort((o1, o2) -> o1.getDtEntradaDesenvolvimento().compareTo(o2.getDtEntradaDesenvolvimento()));
		
		horaAtual = chamados.stream().findFirst().get().getDtEntradaDesenvolvimento();
		
		if (!desenvolvedores.isEmpty()) {
			desenvolvedores.clear();
		}
		
		for (int i = 0; i < nrDesenvolvedores; i++) {
			Desenvolvedor desenvolvedor = new Desenvolvedor();
			desenvolvedor.setNumero(i);
			
			desenvolvedores.add(desenvolvedor);
		}
	}

	public LocalDateTime getHoraAtual() {
		return horaAtual;
	}

	public void setHoraAtual(LocalDateTime horaAtual) {
		Object oldValue = this.horaAtual;
		this.horaAtual = horaAtual;
		changeSupport.firePropertyChange("horaAtual", oldValue, horaAtual);
		setHoraAtualFormatted(horaAtual.toString());
	}

	public List<Desenvolvedor> getDesenvolvedores() {
		return desenvolvedores;
	}

	public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
		Object oldValue = this.desenvolvedores;
		this.desenvolvedores = desenvolvedores;
		changeSupport.firePropertyChange("desenvolvedores", oldValue, desenvolvedores);
	}

	public void transfereChamadosTerminados() {
		if (emDesenvolvimento.isEmpty()) return;
		
		for (Desenvolvedor desenvolvedor : desenvolvedores) {
			Chamado chamado = desenvolvedor.getChamado();
			if (chamado == null) continue;
			
			if (chamado.getDtSaidaDesenvolvimento().isBefore(horaAtual) || chamado.getDtSaidaDesenvolvimento().isEqual(horaAtual)) {
				desenvolvedor.setChamado(null);
				
				emDesenvolvimento.remove(chamado);
				finalizados.add(chamado);
			}
		}
	
	}

	public void transfereFilaDesenvolvimento() {
		for (Iterator<Chamado> iterator = chamados.iterator(); iterator.hasNext();) {
			Chamado chamado = iterator.next();
			
			if (chamado.getDtEntradaDesenvolvimento().isBefore(horaAtual) || chamado.getDtEntradaDesenvolvimento().isEqual(horaAtual)) {
				
				iterator.remove();
				
				filaDesenvolvimento.add(chamado);
				
			} else if (chamado.getDtEntradaDesenvolvimento().isAfter(horaAtual)) {
				break;
			}
		}
	}

	public void transfereEmDesenvolvimento() {
		if (desenvolvedores.isEmpty()) return;
		if (filaDesenvolvimento.isEmpty()) return;
		
		for (Desenvolvedor desenvolvedor : desenvolvedores) {
			if (desenvolvedor.getChamado() == null) {
				Chamado chamado = filaDesenvolvimento.stream().filter(c -> c.getDtSaidaDesenvolvimento() != null).findFirst().orElse(null);
				if (chamado == null) break;

				desenvolvedor.setChamado(chamado);

				filaDesenvolvimento.remove(chamado);
				emDesenvolvimento.add(chamado);
			}
		}
	}

	public void nextHour() {
		setHoraAtual(horaAtual.plusHours(1));
	}

	public String getHoraAtualFormatted() {
		return horaAtualFormatted;
	}

	public void setHoraAtualFormatted(String horaAtualFormatted) {
		Object oldValue = this.horaAtualFormatted;
		this.horaAtualFormatted = horaAtualFormatted;
		changeSupport.firePropertyChange("horaAtualFormatted", oldValue, horaAtualFormatted);
	}

}
