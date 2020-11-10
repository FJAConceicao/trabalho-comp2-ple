package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class JanelaRankingNumeroCrescimento {

	private JFrame janelaRanking;
	private JPanel painelDeTabelas;
	private Container regiaoPrincipal;
	
	public void iniciaJanelaRanking(JFrame janelaParaAtivar, 
									String tipoRanking, //Número ou Crescimento
									String[] linhasCasos,
									String[] linhasRecuperados,
									String[] linhasMortos)
	{
		janelaRanking = new JFrame("Ranking Internacional por período de tempo");
		regiaoPrincipal = janelaRanking.getContentPane();
		
		JMenuBar barraExportar = new JMenuBar();
		JMenuItem exportar = new JMenuItem("Exportar...");
		barraExportar.add(exportar);
		
		exportar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				Coletor.recebeLocalArquivo();
			}
		});
		
		JLabel labelInicial = new JLabel(tipoRanking + " de casos/recuperados/mortos");
		
		painelDeTabelas = new JPanel(new FlowLayout());
		
		//Adicionar tabelas de rankings ao painel de tabelas
		adicionaTabelaRanking("Casos", linhasCasos);
		adicionaTabelaRanking("Recuperados", linhasRecuperados);
		adicionaTabelaRanking("Mortos", linhasMortos);
		
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		labelInicial.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelInicial.setFont(new Font ("Times New Roman", Font.BOLD , 22));
		
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(labelInicial);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(painelDeTabelas);
		
		janelaRanking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaRanking.setSize(800, 650);
		janelaRanking.setVisible(true);
		
		janelaRanking.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janelaParaAtivar.setEnabled(true);
			}			
		});
	}
	
	//
	
	/**
	 * Esse método precisa receber alguma lista de valores {pais, valor} para adicionar na tabela
	 * 
	 * @param nomeLabelTitulo
	 * @param linhas
	 */
	private void adicionaTabelaRanking(String nomeLabelTitulo, String[] linhas) //List<Medicao> medicoes 
	{
		JLabel labelTituloTabela = new JLabel(nomeLabelTitulo);
		labelTituloTabela.setFont(new Font ("Times New Roman", Font.BOLD , 18));
		labelTituloTabela.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		DefaultTableModel modeloTabela = new DefaultTableModel(null, new String[] {"Nº", "Pais", "Valor"}) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
				
		};
		modeloTabela.setNumRows(0);
		
		JTable tabela = new JTable(modeloTabela);
		
		tabela.setFocusable(false);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(false);
		tabela.setRowSelectionAllowed(false);
		
		tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
		
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		//centro.setVerticalAlignment(SwingConstants.CENTER);
		
		tabela.getColumnModel().getColumn(0).setCellRenderer(centro);
		tabela.getColumnModel().getColumn(1).setCellRenderer(centro);
		tabela.getColumnModel().getColumn(2).setCellRenderer(centro);
		
		/*estrutura abaixo para adicionar os dados do tipo de Ranking na tabela*/
		
		/* adiciona os dados da lista de strings de rankings numeros/crescimento em cada linha da tabela
		 * conteudo da linha: posicao-ranking    pais    valor
		 * */
		for(int posicaoRanking = 1; posicaoRanking <= linhas.length; posicaoRanking++) {
			String[] arrayLinha = linhas[posicaoRanking].split(",");
			modeloTabela.addRow(new Object[] {posicaoRanking, arrayLinha[0], arrayLinha[1]});
		}
		
		/*
		//Modelo com lista de Medições --> List<Medicoes> medicoes
		List<Medicao> medicoes = null;
		for(int numero = 1; numero <= linhas.length; numero++) {
			String nomePais = medicoes.get(numero).getPais().getNome();
			int valorCasos = medicoes.get(numero).getCasos();
			modeloTabela.addRow(new Object[] {numero, nomePais, valorCasos});
		}
		*/
		
		JScrollPane scrollPaneTabela = new JScrollPane(tabela);
		scrollPaneTabela.setPreferredSize(new Dimension(250, 500));
		
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		painel.add(labelTituloTabela);
		painel.add(scrollPaneTabela);
		
		//Adiciona painel com titulo e tabela no painel de tabelas
		painelDeTabelas.add(painel);
	}
}