package br.feevale.simulador.panel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;

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
	
	private SelectionInList<AnaliseChamados> selectionInList;
	
	private JTextField tfNrVezes;
	private JComboBox<TipoValorAleatorio> cobTipoValor;
	
	private JTextField tfMedia;
	private JTextField tfDesvioPadrao;
	
	private JButton btnSimular;

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
		
		selectionInList = new SelectionInList<AnaliseChamados>(new ArrayList<>());
	}

	private void initComponents() {
		tfMedia = BasicComponentFactory.createFormattedTextField(model.getModel("media"), new DecimalFormat("#,###.00"));
		tfDesvioPadrao = BasicComponentFactory.createFormattedTextField(model.getModel("desvioPadrao"), new DecimalFormat("#,###.00"));
		
		cobTipoValor = new JComboBox<>(new ComboBoxAdapter(TipoValorAleatorio.values(), modelFilter.getModel("tipoValor")));
		tfNrVezes = BasicComponentFactory.createIntegerField(model.getModel("nrVezes"));
	}

	private void initLayout() {
		
	}
	
	public void actionSimular() {
		AnaliseFilter filter = modelFilter.getBean();

		GeradorChamado geradorChamado = new GeradorChamado(model.getBean(), filter.getTipoValor());
		
		for (int i = 0; i < filter.getNrVezesSimular(); i++) {
			
			Simulador simulador = new Simulador();
			simulador.setChamados(geradorChamado.gerar());
			
			RepresentacaoFila representacaoFila = new RepresentacaoFila(simulador);
			representacaoFila.simular();
			
			EstatisticaChamado estatisticas = new EstatisticaChamado(simulador, Chamado::getTempoEmEsperaSeconds);
			
			AnaliseChamados analise = new AnaliseChamados();
			analise.getResultadoMetrica().put(i, estatisticas.getMedia());
			
			selectionInList.getList().add(analise);
		}
	}

}
