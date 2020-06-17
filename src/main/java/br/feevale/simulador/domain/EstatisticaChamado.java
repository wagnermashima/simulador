package br.feevale.simulador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.function.Function;

import org.uncommons.maths.statistics.DataSet;

public class EstatisticaChamado {

	private Double media;
	private Double desvioPadrao;
	private Double variancia;
	private Double mediana;
	private Double minimo;
	private Double maximo;

	public EstatisticaChamado(Simulador simulador, Function<Chamado, Double> function) {
		DataSet dataSet = new DataSet();

		simulador.getChamados().forEach(c -> {
			dataSet.addValue(function.apply(c));
		});
		
		setDesvioPadrao(dataSet.getStandardDeviation());
		setMedia(dataSet.getArithmeticMean());
		setVariancia(dataSet.getVariance());
		setMediana(dataSet.getMedian());
		setMinimo(dataSet.getMinimum());
		setMaximo(dataSet.getMaximum());
	}

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public Double getMedia() {
		return media;
	}

	public void setMedia(Double media) {
		Object oldValue = this.media;
		this.media = media;
		changeSupport.firePropertyChange("media", oldValue, media);
	}

	public Double getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(Double desvioPadrao) {
		Object oldValue = this.desvioPadrao;
		this.desvioPadrao = desvioPadrao;
		changeSupport.firePropertyChange("desvioPadrao", oldValue, desvioPadrao);
	}

	public Double getVariancia() {
		return variancia;
	}

	public void setVariancia(Double variancia) {
		Object oldValue = this.variancia;
		this.variancia = variancia;
		changeSupport.firePropertyChange("variancia", oldValue, variancia);
	}

	public Double getMediana() {
		return mediana;
	}

	public void setMediana(Double mediana) {
		Object oldValue = this.mediana;
		this.mediana = mediana;
		changeSupport.firePropertyChange("mediana", oldValue, mediana);
	}

	public Double getMinimo() {
		return minimo;
	}

	public void setMinimo(Double minimo) {
		Object oldValue = this.minimo;
		this.minimo = minimo;
		changeSupport.firePropertyChange("minimo", oldValue, minimo);
	}

	public Double getMaximo() {
		return maximo;
	}

	public void setMaximo(Double maximo) {
		Object oldValue = this.maximo;
		this.maximo = maximo;
		changeSupport.firePropertyChange("maximo", oldValue, maximo);
	}

}