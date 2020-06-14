package br.feevale.simulador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Desenvolvedor {

	private Integer numero;

	private Chamado chamado;

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		Object oldValue = this.numero;
		this.numero = numero;
		changeSupport.firePropertyChange("numero", oldValue, numero);
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		Object oldValue = this.chamado;
		this.chamado = chamado;
		changeSupport.firePropertyChange("chamado", oldValue, chamado);
	}

	public Object getNrChamado() {
		return chamado == null ? null : chamado.getNrChamado();
	}

	public Object getDtEntradaDev() {
		return chamado == null ? null : chamado.getDtEntradaDesenvolvimento();
	}

}
