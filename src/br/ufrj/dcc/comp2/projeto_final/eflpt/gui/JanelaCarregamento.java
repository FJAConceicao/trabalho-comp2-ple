package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;

/** 
 * Classe que simula o carregamento de arquivos do banco de dados, downloads de dados 
 * da API e atualização de dados da base de dados caso necessário
 * @author Felipe de Jesus
 */
public class JanelaCarregamento {
	
	private static JFrame janelaCarregamento;
	private Container regiaoPrincipal;
	private static JLabel labelInformativo;
	private static JLabel labelPais;
	private JLabel labelGifCarregando;

	/** 
	 * Inicia a tela de Carregamento. Contém um label informativo que o programa usa frequentemente
	 * para informar o que está sendo carregado/atualizado, um label para o nome do país, o qual está
	 * sofrendo operação do label informativo. E além disso, contém um gif de carregando que fica girando
	 * até o fechamento da tela de carregamento.
	 */
	public void iniciaTelaCarregamento() {
		
		janelaCarregamento = new JFrame("Dados COVID-19");
		regiaoPrincipal = janelaCarregamento.getContentPane();
		
    	labelInformativo = new JLabel("Iniciando carregamento de dados do programa ...");
    	labelInformativo.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelInformativo.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		labelPais = new JLabel(" "); //Texto será inserido com método setaPais
		labelPais.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPais.setFont(new Font ("Times New Roman", Font.BOLD , 20));
		
		labelGifCarregando = new JLabel(new ImageIcon("dados/carregando.gif"));
    	labelGifCarregando.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(labelInformativo);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(20,20)));
		regiaoPrincipal.add(labelPais);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(10,10)));
		regiaoPrincipal.add(labelGifCarregando);
		
		JanelaPrincipal.centralizarTela(janelaCarregamento);
		
		janelaCarregamento.setUndecorated(true);
		janelaCarregamento.setSize(600, 220);
		janelaCarregamento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaCarregamento.setVisible(true);
		
		esperaSegundos(3000); //espera 3 seg antes de continuar
	}
	
	/** 
	 * Atualiza o texto do labelInformativo, caso esteja ocorrendo atualização ou carregamento de dados.
	 * 
	 * @param status StatusCaso para definição da palavra ao final do texto informativo.
	 * Podendo ser "confirmados", em caso de status igual a StatusCaso.CONFIRMADOS ou StatusCaso.RECUPERADOS.
	 * E "confirmadas", em caso de status igual a StatusCaso.MORTOS.
	 * @param tipoDeTarefa Simboliza o que está ocorrendo. Pode ser: "Carregando" ou "Atualizando".
	 * @param tipoDeCaso É o tipo de casos que está sendo realizada. Pode ser: "casos", "mortes" ou "recuperados".
	 */
	public static void setaTipoCasoDeInfoLabelInformativo(StatusCaso status, String tipoDeTarefa, String tipoDeCaso) {
		String mensagemNaTela = tipoDeTarefa + " o número de " + tipoDeCaso;
		
		if(status == StatusCaso.CONFIRMADOS || status == StatusCaso.RECUPERADOS)
			mensagemNaTela += " confirmados";
		else
			mensagemNaTela += " confirmadas";
		
		labelInformativo.setText(mensagemNaTela);
		labelPais.setText(" ");
	}
	
	/** 
	 * Atualiza o nome do país no labelPais, através do método setText.
	 * @param nomePais String com o nome do país para ser atualizado.
	 */
	public static void setaPais(String nomePais) {
		labelPais.setText(nomePais);
	}
	
	/** 
	 * Utilizada para exibir textos informativos de acordo com o que está ocorrendo.
	 * Basicamente recebe um texto e atualiza o texto do LabelInformativo.
	 * @param texto String com o texto para ser atualizado no label
	 */
    public static void setaTextoLabelInformativo(String texto) {
    	labelInformativo.setText(texto);
    }
    
    /** 
	 * Método get para retornar a janela de carregamento
	 * @return JFrame retorna o JFrame da tela de carregamento
	 */
    public static JFrame getJanelaPrincipal() {
    	return janelaCarregamento;
    }
    
    /** 
	 * Atualiza o texto do labelInformativo com o texto "Carregamento concluido!".
	 * Atualiza o texto do labelPais com o texto "Abrindo o programa ...".
	 * Chama o método esperaSegundos e passa 3000. Assim o programa espera 3 segundos antes prosseguir.
	 * Chama o método dispose() do JFrame de janelaCarregamento para que o programa não seja encerrado.
	 */
    public void mostraConcluidoFechaTela() {
    	setaTextoLabelInformativo("Carregamento concluido!");
    	
    	labelPais.setText("Abrindo o programa ...");
    	
    	esperaSegundos(3000); //espera 3 seg antes de fechar
    	
    	janelaCarregamento.dispose();
    }
    
    /** 
     * Espera uma quantidade de tempo em milissegundos.
	 * @param milissegundos quantidade de tempo para o programa esperar antes de continuar.
	 */
    public static void esperaSegundos(int milissegundos) {
    	try {
    		Thread.sleep(milissegundos);
    	} catch (Exception e) {
    		//Ignora quantidade de tempo
    	}
    }
}