package br.feevale.simulador.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import br.feevale.simulador.domain.Simulador;
import br.feevale.simulador.domain.TipoValorAleatorio;

@SuppressWarnings("serial")
public class SimuladorFrm extends JFrame {
	
	private JFileChooser fileChooser;
	private JButton btnCarregarRegistros;
	private JComboBox<TipoValorAleatorio> cobTipoValor;
	private PresentationModel<Simulador> model;
	
	public SimuladorFrm() {
		initModel();
		initComponent();
		initLayout();
	}

	private void initModel() {
		Simulador bean = new Simulador();
		bean.setNrVezesSimular(3);

		model = new PresentationModel<Simulador>(bean);
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
		
	}

	private void actionCarregarRegistros() {
		int showOpenDialog = fileChooser.showOpenDialog(this);
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) return;
		
		File selectedFile = fileChooser.getSelectedFile();
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
		FormLayout layout = new FormLayout("pref");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.append(btnCarregarRegistros);
		return builder.getPanel();
	}

}
