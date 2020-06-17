package br.feevale.simulador.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import br.feevale.simulador.domain.Chamado;
import br.feevale.simulador.domain.Desenvolvedor;
import br.feevale.simulador.domain.Simulador;
import br.feevale.simulador.domain.SimuladorThread;
import br.feevale.simulador.domain.RepresentacaoFila;

@SuppressWarnings("serial")
public class FilaFrm extends JFrame {
	
	private PresentationModel<Simulador> model;
	
	private SelectionInList<Chamado> selectionFilaDesenvolvimento;
	private SelectionInList<Desenvolvedor> selectionFilaEmDesenvolvimento;
	private SelectionInList<Chamado> selectionFilaFinalizados;
	
	private JTable tableFilaDesenvolvimento;
	private FilaChamadoTableModel tableModelFilaDesenvolvimento;
	
	private JTable tableFilaEmDesenvolvimento;
	private EmDesenvolvimentoTableModel tableModelEmDesenvolvimento;
	
	private JTable tableFilaChamadosFinalizados;
	private ChamadoFinalizadoTableModel tableModelChamadosFinalizados;
	
	private JButton btnInicializar;

	private SimuladorThread simuladorThread;
	
	private JTextField tfHoraAtual;
	
	public FilaFrm(Simulador simulador) {
		initModel(simulador);
		initComponents();
		initLayout();
		initListeners();
	}


	private void initModel(Simulador simulador) {
		model = new PresentationModel<Simulador>(simulador);
		selectionFilaDesenvolvimento = new SelectionInList<>(model.getModel("filaDesenvolvimento"));
		selectionFilaEmDesenvolvimento = new SelectionInList<>(model.getModel("desenvolvedores"));
		selectionFilaFinalizados = new SelectionInList<>(model.getModel("finalizados"));
		
		tableModelFilaDesenvolvimento = new FilaChamadoTableModel(selectionFilaDesenvolvimento);
		tableModelEmDesenvolvimento = new EmDesenvolvimentoTableModel(selectionFilaEmDesenvolvimento);
		tableModelChamadosFinalizados = new ChamadoFinalizadoTableModel(selectionFilaFinalizados);
	}

	private void initComponents() {
		tableFilaDesenvolvimento = new JTable(tableModelFilaDesenvolvimento);
		tableFilaChamadosFinalizados = new JTable(tableModelChamadosFinalizados);
		tableFilaEmDesenvolvimento = new JTable(tableModelEmDesenvolvimento);
		
		btnInicializar = new JButton("Iniciar");
		btnInicializar.addActionListener((e) -> actionIniciar());
		
		tfHoraAtual = BasicComponentFactory.createTextField(model.getModel("horaAtualFormatted"));
		tfHoraAtual.setEditable(false);
	}

	private void initLayout() {
		setLayout(new BorderLayout());
		add(montaForm(), BorderLayout.CENTER);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1024, 600));
		
		pack();
		
		setVisible(true);
	}
	
	private Component montaForm() {
		FormLayout layout = new FormLayout("100dlu:grow, 5px, 100dlu:grow, 5px, 100dlu:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.appendRow("pref");
		builder.append(btnInicializar);
		builder.append(tfHoraAtual);
		builder.nextLine();
		
		builder.appendRow("fill:100dlu:grow");
		builder.append(new JScrollPane(tableFilaDesenvolvimento));
		builder.append(new JScrollPane(tableFilaEmDesenvolvimento));
		builder.append(new JScrollPane(tableFilaChamadosFinalizados));
		builder.nextLine();
		
		return builder.getPanel();
	}
	
	private void initListeners() {
		model.getModel("horaAtual").addPropertyChangeListener((evt) -> {
			tableModelChamadosFinalizados.fireTableDataChanged();
			tableModelEmDesenvolvimento.fireTableDataChanged();
			tableModelFilaDesenvolvimento.fireTableDataChanged();
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				if (simuladorThread != null) {
					simuladorThread.setContinnue(false);
				}
			}
		});
	}

	private void actionIniciar() {
		if (simuladorThread != null) return;
		
		Thread thread = new Thread(new SimuladorThread(model.getBean()));
		thread.start();
	}

}
