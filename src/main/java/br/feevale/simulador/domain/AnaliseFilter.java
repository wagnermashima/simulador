package br.feevale.simulador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AnaliseFilter {

	private TipoValorAleatorio tipoValor;
	private Integer nrVezesSimular;

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
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

}
