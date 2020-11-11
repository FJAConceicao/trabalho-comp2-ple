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
 * Classe para exibir o Ranking internacional de Número ou Crescimento de casos/recuperados/mortos, por periodo selecionado
 * @author Felipe de Jesus 
 */
public class JanelaRankingNumeroCrescimento {

	private JFrame janelaRanking;
	private JPanel painelDeTabelas;
	private Container regiaoPrincipal;
	
	/**
	 * Inicia a janela de Ranking internacional de número ou taxa de crescimento de casos/recuperados/mortos
	 * 
	 * @param janela JFrame de janela anterior para desativar enquanto 
	 * essa tela de ranking estiver executando. Ela é ativada ao fechamento 
	 * dessa tela de ranking de mortalidade.
	 * @param tipoRanking É o tipo de Ranking que será exibido no labelInicial, podendo ser "Número" ou "Crescimento".
	 * @param linhasCasos Array de String com linhas no formato "pais!valor". Essas linhas serão 
	 * divididas por "!" e a cada linha o pais e o valor são adicionadas cada linha da tabela de Casos
	 * @param linhasRecuperados É um array de linhas do mesmo formato citado na linhaCasos. Os dados de 
	 * cada linha são adicionadas em cada linha da tabela de Recuperados.
	 * @param linhasMortos É um array de linhas do mesmo formato citado na linhaCasos/Recuperados. Os dados de 
	 * cada linha são adicionadas em cada linha da tabela de Recuperados.
	 */
	public void iniciaJanelaRanking(JFrame janela, 
									String tipoRanking, //Número ou Crescimento
									String[] linhasCasos,
									String[] linhasRecuperados,
									String[] linhasMortos)
	{
		janelaRanking = new JFrame("Ranking Internacional por período de tempo");
		janelaParaAtivar.setEnabled(false);
		regiaoPrincipal = janelaRanking.getContentPane();
		
		
		JMenuBar barraExportar = new JMenuBar();
		JMenuItem exportar = new JMenuItem("Exportar...");
		barraExportar.add(exportar);
		janelaRanking.add(barraExportar);
		exportar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				Coletor.recebeLocalArquivo(linhasCasos,linhasRecuperados,linhasMortos, tipoRanking);
			}
		});
		
		JLabel labelInicial = new JLabel(tipoRanking + " de casos/recuperados/mortos");
		
		painelDeTabelas = new JPanel(new FlowLayout());
		
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
		
		janelaRanking.dispatchEvent(new WindowEvent(janelaRanking, WindowEvent.WINDOW_CLOSING));
		janelaRanking.setSize(800, 650);
		janelaRanking.setVisible(true);
		
		janelaRanking.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janela.setEnabled(true);
			}			
		});
	}
	
	//
	
	/**
	 * Adiciona tabela ranking na tela de Ranking internacional e coloca os dados do ranking na tabela
	 * 
	 * @param nomeTabela Nome e titulo da tabela, pode ser "Casos", "Recuperados" ou "Mortos"
	 * @param linhas Array de linhas com as linhas de dados para adicionar nas linhas da tabela.
	 */
	private void adicionaTabelaRanking(String nomeTabela, String[] linhas) 
	{
		JLabel labelTituloTabela = new JLabel(nomeTabela);
		labelTituloTabela.setFont(new Font ("Times New Roman", Font.BOLD , 18));
		labelTituloTabela.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		DefaultTableModel modeloTabela = new DefaultTableModel(null, new String[] {"Nº", "Valor", "País"}) {
			
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
		
		tabela.getColumnModel().getColumn(0).setCellRenderer(centro);
		tabela.getColumnModel().getColumn(1).setCellRenderer(centro);
		tabela.getColumnModel().getColumn(2).setCellRenderer(centro);
		
		/*estrutura abaixo para adicionar os dados do tipo de Ranking na tabela*/
		
		/* adiciona os dados da lista de strings de rankings numeros/crescimento em cada linha da tabela
		 * conteudo da linha: posicao-ranking    pais    valor
		 * */
		for(int posicaoRanking = 0; posicaoRanking < linhas.length; posicaoRanking++) {
			String[] arrayLinha = linhas[posicaoRanking].split("!");
			modeloTabela.addRow(new Object[] {posicaoRanking+1, arrayLinha[0], arrayLinha[1]});
		}
		
		JScrollPane scrollPaneTabela = new JScrollPane(tabela);
		scrollPaneTabela.setPreferredSize(new Dimension(250, 500));
		
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		painel.add(labelTituloTabela);
		painel.add(scrollPaneTabela);
		
		painelDeTabelas.add(painel);
	}
}