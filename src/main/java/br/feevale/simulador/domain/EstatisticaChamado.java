package br.feevale.simulador.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.function.Function;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.uncommons.maths.statistics.DataSet;

public class EstatisticaChamado {

	private Double media;
	private String mediaFormatted;
	private Double desvioPadrao;
	private String desvioPadraoFormatted;
	private Double variancia;
	private Double mediana;
	private Double minimo;
	private Double maximo;

	public EstatisticaChamado(Simulador simulador, Function<Chamado, Double> function) {
		DataSet dataSet = new DataSet();

		simulador.getChamados().forEach(c -> {
			dataSet.addValue(function.apply(c) * 1000);
		});
		
		setDesvioPadrao(Math.abs(dataSet.getStandardDeviation()));
		setMedia(Math.abs(dataSet.getArithmeticMean()));
		setVariancia(Math.abs(dataSet.getVariance()));
		setMediana(Math.abs(dataSet.getMedian()));
		setMinimo(Math.abs(dataSet.getMinimum()));
		setMaximo(Math.abs(dataSet.getMaximum()));
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
		setMediaFormatted(DurationFormatUtils.formatDuration(media.longValue(), "HH:mm"));
	}

	public Double getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(Double desvioPadrao) {
		Object oldValue = this.desvioPadrao;
		this.desvioPadrao = desvioPadrao;
		changeSupport.firePropertyChange("desvioPadrao", oldValue, desvioPadrao);
		setDesvioPadraoFormatted(DurationFormatUtils.formatDuration(desvioPadrao.longValue(), "HH:mm"));
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

	public String getDesvioPadraoFormatted() {
		return desvioPadraoFormatted;
	}

	public void setDesvioPadraoFormatted(String desvioPadraoFormatted) {
		Object oldValue = this.desvioPadraoFormatted;
		this.desvioPadraoFormatted = desvioPadraoFormatted;
		changeSupport.firePropertyChange("desvioPadraoFormatted", oldValue, desvioPadraoFormatted);
	}

	public String getMediaFormatted() {
		return mediaFormatted;
	}

	public void setMediaFormatted(String mediaFormatted) {
		Object oldValue = this.mediaFormatted;
		this.mediaFormatted = mediaFormatted;
		changeSupport.firePropertyChange("mediaFormatted", oldValue, mediaFormatted);
	}

}