package br.feevale.simulador.domain;

public enum TipoValorAleatorio {
	
	UNIFORME("Uniforme")
	, NORMAL("Normal")
	, EXPONENCIAL("Exponencial")
	, POISSON("Poisson")
	;
	
	private String meaning;

	private TipoValorAleatorio(String meaning) {
		this.meaning = meaning;
	}
	
	@Override
	public String toString() {
		return meaning;
	}

}
