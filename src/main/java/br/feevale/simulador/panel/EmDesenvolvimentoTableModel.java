package br.feevale.simulador.panel;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import br.feevale.simulador.domain.Desenvolvedor;

@SuppressWarnings("serial")
public class EmDesenvolvimentoTableModel extends AbstractTableModel {
	
	private SelectionInList<Desenvolvedor> selection;

	public EmDesenvolvimentoTableModel(SelectionInList<Desenvolvedor> selection) {
		this.selection = selection;
	}

	@Override
	public int getRowCount() {
		return selection.getSize();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Desenvolvedor desenvolvedor = selection.getElementAt(rowIndex);
		switch (columnIndex) {
		case 0: return desenvolvedor.getNrChamado();
		case 1: return desenvolvedor.getDtEntradaDev();
		case 2: return desenvolvedor.getNumero();
		default: return null;
		}
	}
	
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Numero";
		case 1: return "Data entrada dev";
		case 2: return "Desenvolvedor";
		default: return null;
		}
	};

}
