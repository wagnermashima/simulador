package br.feevale.simulador.domain;

import java.time.Duration;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class SimuladorUtil {
	
	public static String formatHoras(Double segundos) {
		return DurationFormatUtils.formatDuration(Duration.ofSeconds(segundos.longValue()).toMillis(), "HH:mm");
	}

}
