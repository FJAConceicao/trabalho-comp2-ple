package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JanelaLocaisMaisProximos {
	
	private JFrame janelaLocaisProximos;
	private Container regiaoPrincipal;
	
	public void iniciaJanelaLocaisMaisProximos(JFrame janelaParaAtivar, String localMaiorCresc, String[] locaisMaisProximos)
	{
		janelaLocaisProximos = new JFrame("Locais mais próximos do local com maior crescimento");
		janelaParaAtivar.setEnabled(false);
		janelaLocaisProximos.dispatchEvent(new WindowEvent(janelaLocaisProximos, WindowEvent.WINDOW_CLOSING));
		
		JMenuBar barraExportar = new JMenuBar();
		JMenuItem exportar = new JMenuItem("Exportar...");
		barraExportar.add(exportar);
		janelaLocaisProximos.add(barraExportar);
		exportar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				Coletor.recebeLocalArquivo(locaisMaisProximos, "raio");
			}
		});
		
		regiaoPrincipal = janelaLocaisProximos.getContentPane();
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		JLabel labelLocalMaiorCrescimento = new JLabel("Local com maior crescimento: " + localMaiorCresc);
		labelLocalMaiorCrescimento.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelLocalMaiorCrescimento.setFont(new Font ("Times New Roman", Font.BOLD , 20));
		
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(labelLocalMaiorCrescimento);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		
		adicionaTabela(locaisMaisProximos);
		
		janelaLocaisProximos.setSize(800, 650);
		janelaLocaisProximos.setVisible(true);
		
		janelaLocaisProximos.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janelaParaAtivar.setEnabled(true);
			}			
		});
	}
	
	public void adicionaTabela(String[] locaisMaisProximos) {

		JLabel labelTabela = new JLabel("Locais mais próximos");
		labelTabela.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelTabela.setFont(new Font ("Times New Roman", Font.BOLD , 18));
		
		//Construir tabela
		DefaultTableModel modeloTabelaLocaisProximos = new DefaultTableModel(null, new String[] {"Nº", "Local"}) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
				
		};
		modeloTabelaLocaisProximos.setNumRows(0);
		
		JTable tabelaLocaisProximos = new JTable(modeloTabelaLocaisProximos);
		
		tabelaLocaisProximos.setFocusable(false);
		tabelaLocaisProximos.getTableHeader().setReorderingAllowed(false);
		tabelaLocaisProximos.getTableHeader().setResizingAllowed(false);
		tabelaLocaisProximos.setRowSelectionAllowed(false);
		
		tabelaLocaisProximos.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabelaLocaisProximos.getColumnModel().getColumn(1).setPreferredWidth(150);
		
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabelaLocaisProximos.getColumnModel().getColumn(0).setCellRenderer(centro);
		tabelaLocaisProximos.getColumnModel().getColumn(1).setCellRenderer(centro);
		
		/* adiciona locais mais próximos na tabela baseado em uma lista de strings
		* conteudo da linha: posicao-ranking    pais    taxa-mortalidade
		 * */
		for(int numero = 0; numero < locaisMaisProximos.length; numero++) {
			modeloTabelaLocaisProximos.addRow(new Object[] {numero+1, locaisMaisProximos[numero].split("!")[0]}); //{numero, pais}
		}
		
		

		JScrollPane scrollPaneTabela = new JScrollPane(tabelaLocaisProximos);
		scrollPaneTabela.setPreferredSize(new Dimension(400, 500));
		
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
		painelPrincipal.add(labelTabela);
		painelPrincipal.add(scrollPaneTabela);
		
		JPanel painelAuxiliar = new JPanel();
		painelAuxiliar.add(painelPrincipal);
		
		regiaoPrincipal.add(painelAuxiliar);
	}
}
