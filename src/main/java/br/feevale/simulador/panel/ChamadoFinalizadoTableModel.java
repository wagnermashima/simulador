package br.feevale.simulador.panel;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import br.feevale.simulador.domain.Chamado;

@SuppressWarnings("serial")
public class ChamadoFinalizadoTableModel extends AbstractTableModel {
	
	private SelectionInList<Chamado> selection;

	public ChamadoFinalizadoTableModel(SelectionInList<Chamado> selection) {
		this.selection = selection;
	}

	@Override
	public int getRowCount() {
		return selection.getSize();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Chamado chamado = selection.getElementAt(rowIndex);
		switch (columnIndex) {
		case 0: return chamado.getNrChamado();
		case 1: return chamado.getDtSaidaDesenvolvimento();
		default: return null;
		}
	}
	
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Numero";
		case 1: return "Data saida dev";
		default: return null;
		}
	};

}
