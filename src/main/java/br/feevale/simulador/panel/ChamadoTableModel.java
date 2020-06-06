package br.feevale.simulador.panel;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import br.feevale.simulador.domain.Chamado;

@SuppressWarnings("serial")
public class ChamadoTableModel extends AbstractTableModel {
	
	private SelectionInList<Chamado> selection;

	public ChamadoTableModel(SelectionInList<Chamado> selection) {
		this.selection = selection;
	}

	@Override
	public int getRowCount() {
		return selection.getSize();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Numero";
		case 1: return "Data entrada dev";
		case 2: return "Data saida dev";
		case 3: return "Data prox chamado";
		case 4: return "Intervalo";
		case 5: return "Cliente";
		case 6: return "Prioridade";
		default: return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Chamado chamado = selection.getElementAt(rowIndex);
		switch (columnIndex) {
		case 0: return chamado.getNrChamado();
		case 1: return chamado.getDtEntradaDesenvolvimento();
		case 2: return chamado.getDtSaidaDesenvolvimento();
		case 3: return chamado.getDtProximoChamado();
		case 4: return chamado.getIntervalo();
		case 5: return chamado.getCdCliente();
		case 6: return chamado.getPrioridade();
		default: return null;
		}
	}

}
