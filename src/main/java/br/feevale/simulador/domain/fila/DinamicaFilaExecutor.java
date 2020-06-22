package br.feevale.simulador.domain.fila;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.feevale.simulador.domain.Chamado;

public class DinamicaFilaExecutor {

	private List<MovimentoFila> movimentos;

	public DinamicaFilaExecutor(List<Chamado> chamados) {
		this.movimentos = new ArrayList<>();

		for (Chamado chamado : chamados) {
			if(chamado.getDtEntradaDesenvolvimento() != null) { 
				movimentos.add(MovimentoFila.entrada(chamado));
			}
			
			if(chamado.getDtSaidaDesenvolvimento() != null) { 
				movimentos.add(MovimentoFila.saida(chamado));
			}
		}

		Collections.sort(movimentos);
	}

	public void execute() {

		List<LocalDateTime> horasExibir = getHorasExibir();

		for (LocalDateTime horaExibir : horasExibir) {

			List<MovimentoFila> movimentosHora = filtraMovimentosHora(movimentos, horaExibir);

			if (!movimentosHora.isEmpty()) {

				List<MovimentoFila> entradas = movimentosHora.stream().filter(MovimentoFila::isEntrada)
						.collect(Collectors.toList());
				List<MovimentoFila> saidas = movimentosHora.stream().filter(MovimentoFila::isSaida)
						.collect(Collectors.toList());

				System.out.println(horaExibir);

				System.out.println("Entradas ("
						+ entradas.size() + ") : -> [" + entradas.stream()
								.map(mov -> mov.getChamado().getNrChamado().toString()).collect(Collectors.joining(","))
						+ "]");

				System.out.println("Saidas ("
						+ saidas.size() + ") : -> [" + saidas.stream()
								.map(mov -> mov.getChamado().getNrChamado().toString()).collect(Collectors.joining(","))
						+ "]");

				List<MovimentoFila> movimentosAteHora = movimentos.stream()
						.filter(mov -> horaExibir.isAfter(mov.getData().withMinute(0).withSecond(0))
								|| horaExibir.equals(mov.getData().withMinute(0).withSecond(0)))
						.collect(Collectors.toList());
				long entradasCount = movimentosAteHora.stream().filter(MovimentoFila::isEntrada).count();
				long saidasCount = movimentosAteHora.stream().filter(MovimentoFila::isSaida).count();

				System.out.println("Fila -> " + (entradasCount - saidasCount));

				System.out.println("-------------------------");
			}

		}
	}

	private List<MovimentoFila> filtraMovimentosHora(List<MovimentoFila> movimentos, LocalDateTime horaExibir) {
		return movimentos.stream().filter(mov -> mov.getData().withMinute(0).withSecond(0).equals(horaExibir))
				.collect(Collectors.toList());
	}

	private List<LocalDateTime> getHorasExibir() {

		Comparator<LocalDateTime> comparator = new Comparator<LocalDateTime>() {

			@Override
			public int compare(LocalDateTime o1, LocalDateTime o2) {
				return o1.compareTo(o2);
			}
		};

		LocalDateTime dtInicial = movimentos.stream().map(MovimentoFila::getData).min(comparator).get();
		LocalDateTime dtFinal = movimentos.stream().map(MovimentoFila::getData).max(comparator).get();

		LocalDateTime dtInicialAjustada = dtInicial.withMinute(0).withSecond(0);
		LocalDateTime dtFinalAjustada = dtFinal.withMinute(0).withSecond(0);

		LocalDateTime dataAtual = dtInicialAjustada;

		List<LocalDateTime> result = new ArrayList<>();

		while (dataAtual.isBefore(dtFinalAjustada)) {
			result.add(dataAtual);
			dataAtual = dataAtual.plusHours(1);
		}

		Collections.sort(result);
		return result;
	}

}
