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

/** 
 * 
 * @author Felipe de Jesus
 */
public class JanelaLocaisMaisProximos {
	
	JFrame janelaLocaisProximos;
	Container regiaoPrincipal;
	
	/** 
	 * 
	 * @param janelaParaAtivar
	 * @param localMaiorCresc
	 * @param locaisMaisProximos
	 */
	public void iniciaJanelaLocaisMaisProximos(JFrame janelaParaAtivar, String localMaiorCresc, String[] locaisMaisProximos)
	{
		janelaLocaisProximos = new JFrame("Locais mais próximos do local com maior crescimento");
		janelaLocaisProximos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar barraExportar = new JMenuBar();
		JMenuItem exportar = new JMenuItem("Exportar...");
		barraExportar.add(exportar);
		
		exportar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				Coletor.recebeLocalArquivo();
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
	
	/** 
	 * 
	 * @param locaisMaisProximos
	 */
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
		for(int numero = 1; numero <= locaisMaisProximos.length; numero++) {
			modeloTabelaLocaisProximos.addRow(new Object[] {numero, locaisMaisProximos[numero]}); //{numero, pais}
		}
		
		/*
		 * Exemplo de preenchimento da tabela com números de 1000 a 1500
		for(int numero = 1000; numero <= 1500; numero++) {
			modeloTabelaLocaisProximos.addRow(new Object[] {numero, numero});
		}
		*/

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