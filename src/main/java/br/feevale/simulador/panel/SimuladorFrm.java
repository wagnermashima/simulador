package br.feevale.simulador.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import br.feevale.simulador.domain.Chamado;
import br.feevale.simulador.domain.ChamadoExcelBuilder;
import br.feevale.simulador.domain.Simulador;
import br.feevale.simulador.domain.TipoValorAleatorio;

@SuppressWarnings("serial")
public class SimuladorFrm extends JFrame {
	
	private JFileChooser fileChooser;
	private JButton btnCarregarRegistros;
	private JComboBox<TipoValorAleatorio> cobTipoValor;
	private PresentationModel<Simulador> model;
	
	private JTable table;
	private ChamadoTableModel tableModel;
	
	private SelectionInList<Chamado> selectionChamados;
	
	public SimuladorFrm() {
		initModel();
		initComponent();
		initLayout();
	}

	private void initModel() {
		Simulador bean = new Simulador();
		bean.setNrVezesSimular(3);

		model = new PresentationModel<Simulador>(bean);
		
		selectionChamados = new SelectionInList<Chamado>(model.getModel("chamados"));
		
		tableModel = new ChamadoTableModel(selectionChamados);
	}

	@SuppressWarnings("unchecked")
	private void initComponent() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel file","xls", "xlsx"));
		
		btnCarregarRegistros = new JButton("Carregar Registros");
		btnCarregarRegistros.addActionListener((e) -> actionCarregarRegistros());
		
		cobTipoValor = new JComboBox<TipoValorAleatorio>();
		cobTipoValor.setModel(new ComboBoxAdapter<>(TipoValorAleatorio.values(), model.getModel("tipoValor")));
		
		table = new JTable(tableModel);
	}

	private void actionCarregarRegistros() {
		int showOpenDialog = fileChooser.showOpenDialog(this);
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) return;
		
		File selectedFile = fileChooser.getSelectedFile();
		ChamadoExcelBuilder builder = new ChamadoExcelBuilder(selectedFile);

		model.getBean().getChamados().clear();

		List<Chamado> chamados = builder.build();
		
		model.getBean().getChamados().addAll(chamados);
		
		tableModel.fireTableDataChanged();
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		add(montaForm(), BorderLayout.CENTER);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		
		pack();
		
		setVisible(true);
	}

	private Component montaForm() {
		FormLayout layout = new FormLayout("pref, 5px, 100dlu:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.appendRow("pref");
		builder.append(btnCarregarRegistros);
		builder.nextLine();
		
		builder.appendRow("fill:100dlu:grow");
		builder.append(new JScrollPane(table), builder.getColumnCount());
		builder.nextLine();
		
		return builder.getPanel();
	}

}
