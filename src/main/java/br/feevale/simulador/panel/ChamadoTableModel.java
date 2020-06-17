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
		return 6;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Numero";
		case 1: return "Data entrada dev";
		case 2: return "Data entrada backlog";
		case 3: return "Tempo em espera";
		case 4: return "Cliente";
		case 5: return "Prioridade";
		default: return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Chamado chamado = selection.getElementAt(rowIndex);
		switch (columnIndex) {
		case 0: return chamado.getNrChamado();
		case 1: return chamado.getDtEntradaDesenvolvimento();
		case 2: return chamado.getDtEntradaBacklog();
		case 3: return chamado.getTempoEmEsperaFormatted();
		case 4: return chamado.getCdCliente();
		case 5: return chamado.getPrioridade();
		default: return null;
		}
	}

}
