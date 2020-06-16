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
import br.feevale.simulador.domain.EstatisticaChamado;
import br.feevale.simulador.domain.Simulador;
import br.feevale.simulador.domain.SimuladorUtil;
import br.feevale.simulador.domain.TipoValorAleatorio;
import br.feevale.simulador.domain.fila.DinamicaFilaExecutor;

@SuppressWarnings("serial")
public class SimuladorFrm extends JFrame {
	
	private JFileChooser fileChooser;
	private JButton btnCarregarRegistros;
	private JButton btnEstatisticas;
	private JButton btnTeste;
	private JComboBox<TipoValorAleatorio> cobTipoValor;
	private PresentationModel<Simulador> model;
	
	private JTable table;
	private ChamadoTableModel tableModel;
	
	private SelectionInList<Chamado> selectionChamados;
	private JButton btnDinamica;
	
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
		
		btnEstatisticas = new JButton("Estatisticas");
		btnEstatisticas.addActionListener((e) -> actionEstatisticas());
		
		btnDinamica = new JButton("Exec. Dinamica");
		btnDinamica.addActionListener((e) -> actionExecutarDinamica());
		
		btnTeste = new JButton("TESTE");
		btnTeste.addActionListener((e) -> actionExecutarTeste());
		
		cobTipoValor = new JComboBox<TipoValorAleatorio>();
		cobTipoValor.setModel(new ComboBoxAdapter<>(TipoValorAleatorio.values(), model.getModel("tipoValor")));
		
		table = new JTable(tableModel);
	}

	private void actionExecutarTeste() {
		FilaFrm filaFrm = new FilaFrm(model.getBean());
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
	
	private void actionEstatisticas() {
		EstatisticaChamado estatistica = new EstatisticaChamado(model.getBean(), Chamado::getIntervaloSeconds);
		System.out.println(String.format("MEDIA %s", SimuladorUtil.formatHoras(estatistica.getMedia())));
		System.out.println(String.format("MEDIANA %s", SimuladorUtil.formatHoras(estatistica.getMediana())));
		System.out.println(String.format("DESVIO PADRAO %s", SimuladorUtil.formatHoras(estatistica.getDesvioPadrao())));
		System.out.println(String.format("VARIANCIA %s", SimuladorUtil.formatHoras(estatistica.getVariancia())));
	}
	
	private void actionExecutarDinamica() {
		new DinamicaFilaExecutor(model.getBean().getChamados()).execute();
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
		
		builder.appendRow("pref");
		builder.append(btnEstatisticas);
		builder.nextLine();
		
		builder.appendRow("pref");
		builder.append(btnDinamica);
		builder.nextLine();
		
		builder.appendRow("pref");
		builder.append(btnTeste);
		builder.nextLine();
		
		builder.appendRow("fill:100dlu:grow");
		builder.append(new JScrollPane(table), builder.getColumnCount());
		builder.nextLine();
		
		return builder.getPanel();
	}

}
