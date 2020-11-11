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
 * Classe para exibir o Ranking internacional de mortalidade, por periodo selecionado
 * @author Felipe de Jesus
 */
public class JanelaRankingMortalidade {

	private JFrame janelaRankingMortalidade;
	private Container regiaoPrincipal;
	
	/** 
	 * Inicia a janela com o Ranking internacional de Mortalidade.
	 *  
	 * @param janela JFrame de janela anterior para desativar enquanto 
	 * essa tela de ranking estiver executando. Ela é ativada ao fechamento 
	 * dessa tela de ranking de mortalidade.
	 * @param linhas Array de String com linhas no formato "pais!taxa-mortalidade".
	 * Essas linhas serão divididas por "!" e a cada linha o pais e a taxa-mortalidade
	 * são adicionadas em uma linha da tabela
	 */
	public void iniciaJanelaRankingMortalidade(JFrame janela, String[] linhas)
	{
		janelaRankingMortalidade = new JFrame("Ranking Internacional por período de tempo");
		janela.setEnabled(false);
		regiaoPrincipal = janelaRankingMortalidade.getContentPane();
		
		JMenuBar barraExportar = new JMenuBar();
		JMenuItem exportar = new JMenuItem("Exportar...");
		barraExportar.add(exportar);
		janelaRankingMortalidade.add(barraExportar);
		exportar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				Coletor.recebeLocalArquivo(linhas, "mortalidade");
			}
		});
		
		JLabel labelInicial = new JLabel("Mortalidade");
		
		DefaultTableModel modeloTabelaMortalidade = new DefaultTableModel(null, new String[] {"Nº", "Pais", "Taxa"}) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
				
		};
		modeloTabelaMortalidade.setNumRows(0);
		
		JTable tabelaMortalidade = new JTable(modeloTabelaMortalidade);
		
		tabelaMortalidade.setFocusable(false);
		tabelaMortalidade.getTableHeader().setReorderingAllowed(false);
		tabelaMortalidade.getTableHeader().setResizingAllowed(false);
		tabelaMortalidade.setRowSelectionAllowed(false);
		
		tabelaMortalidade.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabelaMortalidade.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabelaMortalidade.getColumnModel().getColumn(2).setPreferredWidth(50);
		
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabelaMortalidade.getColumnModel().getColumn(0).setCellRenderer(centro);
		tabelaMortalidade.getColumnModel().getColumn(1).setCellRenderer(centro);
		tabelaMortalidade.getColumnModel().getColumn(2).setCellRenderer(centro);
		
		/* adiciona os dados da mortalidade em cada linha da tabela de ranking de mortalidade
		 * conteudo da linha: posicao-ranking    pais    taxa-mortalidade
		 * */
		for(int posicaoRanking = 0; posicaoRanking < linhas.length; posicaoRanking++) {
			String[] arrayLinha = linhas[posicaoRanking].split("!");
			modeloTabelaMortalidade.addRow(new Object[] {posicaoRanking+1, arrayLinha[0], arrayLinha[1]});
		}
		
		JScrollPane scrollPaneTabela = new JScrollPane(tabelaMortalidade);
		scrollPaneTabela.setPreferredSize(new Dimension(400, 500));
		
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		labelInicial.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelInicial.setFont(new Font ("Times New Roman", Font.BOLD , 22));
		
		JPanel painelTabelaMortalidade = new JPanel();
		painelTabelaMortalidade.add(scrollPaneTabela);
		
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(labelInicial);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(painelTabelaMortalidade);
		
		janelaRankingMortalidade.dispatchEvent(new WindowEvent(janelaRankingMortalidade, WindowEvent.WINDOW_CLOSING));
		janelaRankingMortalidade.setSize(800, 650);
		janelaRankingMortalidade.setVisible(true);
		
		janelaRankingMortalidade.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janela.setEnabled(true);
			}			
		});
	}
}