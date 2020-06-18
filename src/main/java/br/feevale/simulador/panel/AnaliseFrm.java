package br.feevale.simulador.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import br.feevale.simulador.domain.AnaliseFilter;
import br.feevale.simulador.domain.Chamado;
import br.feevale.simulador.domain.EstatisticaChamado;
import br.feevale.simulador.domain.GeradorChamado;
import br.feevale.simulador.domain.RepresentacaoFila;
import br.feevale.simulador.domain.Simulador;
import br.feevale.simulador.domain.TipoValorAleatorio;
import br.feevale.simulador.domain.fila.AnaliseChamados;

@SuppressWarnings("serial")
public class AnaliseFrm extends JFrame {

	private PresentationModel<Simulador> model;
	private PresentationModel<AnaliseFilter> modelFilter;
	private PresentationModel<EstatisticaChamado> modelEstatistica;
	
	private SelectionInList<AnaliseChamados> selectionAnalise;
	
	private JTextField tfNrVezes;
	private JTextField tfNrDesenvolvedores;
	private JComboBox<TipoValorAleatorio> cobTipoValor;
	
	private JTextField tfMedia;
	private JTextField tfDesvioPadrao;
	
	private JButton btnSimular;
	
	private JTable tableAnalise;
	private AnaliseChamadoTableModel tableModel;

	public AnaliseFrm(PresentationModel<Simulador> model) {
		this.model = model;
		initModel();
		initComponents();
		initLayout();
		EstatisticaChamado estatisticaChamado = new EstatisticaChamado(model.getBean(), Chamado::getTempoEmEsperaSeconds);
		modelEstatistica.setBean(estatisticaChamado);
	}

	private void initModel() {
		modelEstatistica = new PresentationModel<EstatisticaChamado>();
		
		AnaliseFilter filter = new AnaliseFilter();
		filter.setNrVezesSimular(10);
		filter.setTipoValor(TipoValorAleatorio.NORMAL);
		modelFilter = new PresentationModel<AnaliseFilter>(filter);
		
		selectionAnalise = new SelectionInList<AnaliseChamados>(new ArrayList<>());
	}

	private void initComponents() {
		tfMedia = BasicComponentFactory.createFormattedTextField(modelEstatistica.getModel("media"), new DecimalFormat("#,###.00"));
		tfMedia.setEditable(false);
		
		tfDesvioPadrao = BasicComponentFactory.createFormattedTextField(modelEstatistica.getModel("desvioPadrao"), new DecimalFormat("#,###.00"));
		tfDesvioPadrao.setEditable(false);
		
		cobTipoValor = new JComboBox<>(new ComboBoxAdapter(TipoValorAleatorio.values(), modelFilter.getModel("tipoValor")));
		tfNrVezes = BasicComponentFactory.createIntegerField(modelFilter.getModel("nrVezesSimular"));
		
		tfNrDesenvolvedores = BasicComponentFactory.createIntegerField(model.getModel("nrDesenvolvedores"));
		
		tableModel = new AnaliseChamadoTableModel(selectionAnalise);
		tableAnalise = new JTable(tableModel);
		
		btnSimular = new JButton("Simular");
		btnSimular.addActionListener((e) -> actionSimular());
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		add(montaFormDadosOriginais(), BorderLayout.NORTH);
		add(montaForm(), BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		
		pack();
		
		setVisible(true);
	}
	
	private Component montaFormDadosOriginais() {
		FormLayout layout = new FormLayout("pref, 5px, 80dlu, 5px, pref, 5px, 80dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.border(BorderFactory.createTitledBorder("Original"));
		
		builder.appendRow("pref");
		builder.append("Media:", tfMedia);
		builder.append("Desvio padrao", tfDesvioPadrao);
		builder.nextLine();
		
		return builder.getPanel();
	}

	private Component montaForm() {
		FormLayout layout = new FormLayout("pref, 5px, 100dlu, 5px, pref, 5px, 50dlu, 5px, pref, 5px, 50dlu, 1dlu:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.border(BorderFactory.createTitledBorder("Simular"));
		
		builder.appendRow("pref");
		builder.append("Tipo:", cobTipoValor);
		builder.append("Nr. vezes", tfNrVezes);
		builder.append("Nr. desenvolvedores", tfNrDesenvolvedores);
		builder.nextLine();
		
		builder.appendRow("pref");
		builder.append(btnSimular);
		builder.nextLine();
		
		builder.appendRow("fill:100dlu:grow");
		builder.append(new JScrollPane(tableAnalise), builder.getColumnCount());
		builder.nextLine();
		
		return builder.getPanel();
	}

	public void actionSimular() {
		AnaliseFilter filter = modelFilter.getBean();
		
		AnaliseChamados analise = new AnaliseChamados();
		analise.setTipo(filter.getTipoValor());
		analise.setNrDesenvolvedores(model.getBean().getNrDesenvolvedores());
		for (int i = 0; i < filter.getNrVezesSimular(); i++) {
			
			GeradorChamado geradorChamado = new GeradorChamado(model.getBean(), filter.getTipoValor());
			Simulador simulador = new Simulador();
			simulador.setChamados(geradorChamado.gerar());
			
			RepresentacaoFila representacaoFila = new RepresentacaoFila(simulador);
			representacaoFila.simular();
			
			EstatisticaChamado estatisticas = new EstatisticaChamado(simulador, Chamado::getTempoEmEsperaSeconds);
			
			analise.getResultadoMetrica().put(i, estatisticas.getMedia());
		}
		selectionAnalise.getList().add(analise);
		tableModel.fireTableStructureChanged();
		tableModel.fireTableDataChanged();
	}

}
