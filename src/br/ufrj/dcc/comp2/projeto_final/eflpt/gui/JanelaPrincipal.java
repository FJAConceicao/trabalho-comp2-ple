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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import br.ufrj.dcc.comp2.projeto_final.eflpt.database.ArquivoBase;

<<<<<<< HEAD
=======
/**
 * Essa classe implementa a janela principal do programa.
 * Ela fornece os botões para solicitação dos rankings.
 * @author Thiago Castro
 *
 */


>>>>>>> d1652ad... Javadoc do programa criado
public class JanelaPrincipal 
{
	private JFrame janelaPrincipal = new JFrame("Dados COVID-19");
	private JLabel msgBemVindo = new JLabel("Criador de rankings internacionais de COVID-19");
	
	/**
	 * Inicia a janela principal, contendo os botões para criação dos rankings.
	 */
	
	public void iniciaJanelaPrincipal()
	{
		Container regiaoPrincipal = janelaPrincipal.getContentPane();
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		msgBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
		msgBemVindo.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		JPanel painel = new JPanel();
		adicionaBotoes(painel, janelaPrincipal);
		regiaoPrincipal.add(msgBemVindo);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(50,50)));
		regiaoPrincipal.add(painel);
				
		janelaPrincipal.setSize(800, 600);
		janelaPrincipal.setVisible(true);
		
		janelaPrincipal.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				ArquivoBase arqs = new ArquivoBase();
				
				arqs.salvaArquivoConfirmados();
				arqs.salvaArquivoMortos();
				arqs.salvaArquivoRecuperados();
				
				janelaPrincipal.setEnabled(true);
			}	
		});
	}
	
	/**
	 * Adiciona os botões na janela principal
	 * @param painel o painel que contém os botões
	 * @param janelaPrincipal a janela principal
	 */
	
	public void adicionaBotoes(JPanel painel, JFrame janelaPrincipal)
	{
		JButton rankingPrincipal = new JButton("Ranking internacional casos/recuperados/mortos por período");
		JButton rankingCrescimento = new JButton("Ranking internacional de crescimento de casos/recuperados/mortos por período");
		JButton rankingMortalidade = new JButton("Ranking internacional de mortalidade por período");
		JButton locaisProximosMaior = new JButton("Locais mais próximos do local com maior crescimento de casos confirmados em um período de tempo, até um raio r (km)");
		
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		
		rankingPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
		rankingCrescimento.setAlignmentX(Component.CENTER_ALIGNMENT);
		rankingMortalidade.setAlignmentX(Component.CENTER_ALIGNMENT);
		locaisProximosMaior.setAlignmentX(Component.CENTER_ALIGNMENT);
<<<<<<< HEAD
		exportarRankings.setAlignmentX(Component.CENTER_ALIGNMENT);
=======
>>>>>>> d1652ad... Javadoc do programa criado
		
		geraEventoReceptorPeriodo(rankingPrincipal);
		geraEventoReceptorPeriodo(rankingCrescimento);
		geraEventoReceptorPeriodo(rankingMortalidade);
		geraEventoReceptorRaio(locaisProximosMaior);
		
		painel.add(rankingPrincipal);
		painel.add(Box.createRigidArea(new Dimension(50,50)));
		painel.add(rankingCrescimento);
		painel.add(Box.createRigidArea(new Dimension(50,50)));
		painel.add(rankingMortalidade);
		painel.add(Box.createRigidArea(new Dimension(50,50)));
		painel.add(locaisProximosMaior);
		painel.add(Box.createRigidArea(new Dimension(50,50)));
<<<<<<< HEAD
		painel.add(exportarRankings);
		painel.add(Box.createRigidArea(new Dimension(50,50)));
=======
		
>>>>>>> d1652ad... Javadoc do programa criado
	}
	
	/**
	 * Adiciona ação aos botões que geram ranking que
	 * necessitam apenas de um intervalo de data
	 * @param botao o botão que abre a janela de receber data
	 */
	
	public void geraEventoReceptorPeriodo(JButton botao)
	{
		botao.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				abreJanelaRecebePeriodo(janelaPrincipal, null);				
			}			
		});
	}
	
	/**
	 * Adiciona ação ao botão que gera o ranking de local
	 * mais próximo ao local com maior taxa de crescimento
	 * @param botao o botão que abre a janela de receber raio
	 */
	
	public void geraEventoReceptorRaio(JButton botao)
	{
		botao.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				abreJanelaRecebeRaio(janelaPrincipal);				
			}			
		});
	}
	
	/**
	 * Adiciona ação de abrir a janela de salvar como para salvar o ranking gerado
	 * @param botao o botão que abrirá a janela de salvamento
	 */
	
	public void geraEventoReceptorLocalArquivo(JButton botao)
	{
		botao.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				abreJanelaRecebeLocaisArquivos(janelaPrincipal);				
			}			
		});
	}
	
<<<<<<< HEAD
=======
	/**
	 * Abre o diálogo que recebe as duas datas usadas para gerar um ranking
	 * @param janelaPrincipal a janela principal
	 * @param raio o raio caso seja o ranking de local mais próximo
	 */
	
	
>>>>>>> d1652ad... Javadoc do programa criado
	@SuppressWarnings("serial")
	public void abreJanelaRecebePeriodo(JFrame janelaPrincipal, Integer raio)
	{
		janelaPrincipal.setEnabled(false);
		JDialog janela = new JDialog();
		janela.setLocation(200, 200);
		Container regiaoPrincipal = janela.getContentPane();
		janela.setSize(500, 150);
		janela.setResizable(false);
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		regiaoPrincipal.add(painel);
		
		JLabel msgPrimeiraData = new JLabel("Digite a data de ínicio da busca (dd/mm/aaaa): ");
		msgPrimeiraData.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel msgSegundaData = new JLabel("Digite a data de fim da busca (dd/mm/aaaa): ");
		msgSegundaData.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton botaoOk = new JButton("OK");
		
		JTextField primeiraData = new JTextField();
		primeiraData.setColumns(10);
		primeiraData.setMaximumSize(new Dimension(200,50));
		primeiraData.setDocument(new PlainDocument()
		{
			@Override
			public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			    if (str == null)
			      return;

			    if ((getLength() + str.length()) <= 10) {
			      super.insertString(offset, str, attr);
			    }
			  }
			
		});
		
		JTextField segundaData = new JTextField();
		segundaData.setColumns(10);
		segundaData.setMaximumSize(new Dimension(200,50));
		segundaData.setDocument(new PlainDocument()
		{
			@Override
			public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			    if (str == null)
			      return;

			    if ((getLength() + str.length()) <= 10) {
			      super.insertString(offset, str, attr);
			    }
			  }
			
		});
		
		botaoOk.setAlignmentX(Component.CENTER_ALIGNMENT);
		painel.add(msgPrimeiraData);
		painel.add(primeiraData);
		painel.add(msgSegundaData);
		painel.add(segundaData);
		JPanel regiaoBotao = new JPanel();
		regiaoBotao.add(Box.createHorizontalGlue());
		regiaoBotao.add(botaoOk);
		painel.add(regiaoBotao);
		
		botaoOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (raio == null)
					Coletor.converteData(primeiraData, segundaData, janela);
				else
					Coletor.converteData(primeiraData, segundaData, janela, raio);
			}
			
		});
		
		janela.setVisible(true);
		
		janela.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janelaPrincipal.setEnabled(true);
			}			
		});		
	}
	
	/**
	 * Abre o diálogo para receber o raio utilizado no ranking de local mais próximo
	 * @param janelaPrincipal a janela principal do programa
	 */
	
	@SuppressWarnings("serial")
	public void abreJanelaRecebeRaio(JFrame janelaPrincipal)
	{
		janelaPrincipal.setEnabled(false);
		JDialog janela = new JDialog();
		janela.setLocation(200, 200);
		Container regiaoPrincipal = janela.getContentPane();
		janela.setSize(500, 150);
		janela.setResizable(false);
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		regiaoPrincipal.add(painel);
		
		JLabel msgRaio = new JLabel("Digite o raio (máx.: 6371): ");
		msgRaio.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton botaoOk = new JButton("OK");
		
		JTextField raio = new JTextField();
		raio.setColumns(5);
		raio.setMaximumSize(new Dimension(200,50));
		raio.setDocument(new PlainDocument()
		{
			@Override
			public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			    if (str == null)
			      return;

			    if ((getLength() + str.length()) <= 5) {
			      super.insertString(offset, str, attr);
			    }
			  }
			
		});
		
		botaoOk.setAlignmentX(Component.CENTER_ALIGNMENT);
		painel.add(msgRaio);
		painel.add(raio);
		JPanel regiaoBotao = new JPanel();
		regiaoBotao.add(Box.createHorizontalGlue());
		regiaoBotao.add(botaoOk);
		painel.add(regiaoBotao);
		
		botaoOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (Coletor.verificaRaio(raio, janela))
					abreJanelaRecebePeriodo(janelaPrincipal, Integer.parseInt(raio.getText()));
			}
			
		});
		
		janela.setVisible(true);
		
		janela.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) {
				
				janelaPrincipal.setEnabled(true);
			}			
		});		
	}
	
	/**
	 * Abre a janela de salvar como para receber o local de salvamento
	 * @param janelaPrincipal a janela principal do programa
	 */
	
	public void abreJanelaRecebeLocaisArquivos(JFrame janelaPrincipal)
	{
		Coletor.recebeLocalArquivo();
		
	}
}