package br.feevale.simulador.panel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jgoodies.binding.list.SelectionInList;

import br.feevale.simulador.domain.fila.AnaliseChamados;

@SuppressWarnings("serial")
public class AnaliseChamadoTableModel extends AbstractTableModel {
	
	private SelectionInList<AnaliseChamados> selection;

	public AnaliseChamadoTableModel(SelectionInList<AnaliseChamados> selection) {
		this.selection = selection;
	}

	@Override
	public int getRowCount() {
		return selection.getSize();
	}

	@Override
	public int getColumnCount() {
		List<AnaliseChamados> list = selection.getList();
		if (list == null || list.isEmpty()) return 4;
		AnaliseChamados analise = list.get(0);
		if (analise == null || analise.getResultadoMetrica().isEmpty()) return 4;
		return analise.getResultadoMetrica().values().size() + 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AnaliseChamados analise = selection.getElementAt(rowIndex);
		if (analise == null) return null;
		
		if (columnIndex == 0) {
			return analise.getTipo();
		} else if (columnIndex == 1) {
			return analise.getNrDesenvolvedores();
		} else if (columnIndex > 1  && columnIndex < getColumnCount() -2) {
			return analise.getResultadoMetrica().get(columnIndex - 2);
		} else if (columnIndex == getColumnCount() - 2) {
			return analise.getMedia();
		} else if (columnIndex == getColumnCount() - 1) {
			return analise.getDesvioPadrao();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Tipo valor";
		} else if (column == 1) {
			return "Nr. desenvolvedores";
		} else  if (column == getColumnCount() - 2) {
			return "Media";
		} else if (column == getColumnCount() - 1) {
			return "Desvio Padrao";
		}
		return String.valueOf(column);
	}

}
