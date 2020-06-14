package br.feevale.simulador.panel;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import br.feevale.simulador.domain.Chamado;
import br.feevale.simulador.domain.Desenvolvedor;

@SuppressWarnings("serial")
public class FilaChamadoTableModel extends AbstractTableModel {
	
	private SelectionInList<Chamado> selection;

	public FilaChamadoTableModel(SelectionInList<Chamado> selection) {
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
		case 1: return chamado.getDtEntradaDesenvolvimento();
		default: return null;
		}
	}
	
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Numero";
		case 1: return "Data entrada dev";
		default: return null;
		}
	};

}
