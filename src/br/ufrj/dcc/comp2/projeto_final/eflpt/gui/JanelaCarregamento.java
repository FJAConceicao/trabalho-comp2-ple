package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;

public class JanelaCarregamento {
	
	private static JFrame janelaCarregamento;
	private Container regiaoPrincipal;
	private JLabel labelInformativo;
	private static JLabel labelPais;
	private JLabel labelGifCarregando;

	public void iniciaTelaCarregamento() {
		
		janelaCarregamento = new JFrame("Dados COVID-19");
		regiaoPrincipal = janelaCarregamento.getContentPane();
		
    	labelInformativo = new JLabel("Iniciando carregamento de dados do programa ...");
    	labelInformativo.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelInformativo.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		labelPais = new JLabel(""); //Texto será inserido com método setaPais
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
		
		centralizarTela();
		
		janelaCarregamento.setUndecorated(true);
		janelaCarregamento.setSize(600, 220);
		janelaCarregamento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaCarregamento.setVisible(true);
		
		esperaSegundos(3000); //espera 3 seg antes de continuar
	}
	
	// Tipo carregamento: casos, mortes ou recuperados
	public void setaTipoCasoDeCarregamento(StatusCaso status, String tipoDeCarregamento) {
		String mensagemNaTela = "Carregando o número de " + tipoDeCarregamento;
		
		if(status == StatusCaso.CONFIRMADOS || status == StatusCaso.RECUPERADOS)
			mensagemNaTela += " confirmados";
		else
			mensagemNaTela += " confirmadas";
		
		labelInformativo.setText(mensagemNaTela);
		labelPais.setText("");
	}
	
	public static void setaPais(String nomePais) {
		labelPais.setText(nomePais);
	}
	
    /* Pode ser utilizada para mostrar mensagens iniciais ou finais, tipo:
     * 	- Carregamento concluido
     * 	- Iniciando carregamento 
     * */
    public void setaTextoLabelInformativo(String texto) {
    	labelInformativo.setText(texto);
    }
    
    public static JFrame getJanelaPrincipal() {
    	return janelaCarregamento;
    }
    
    public void mostraConcluidoFechaTela() {
    	setaTextoLabelInformativo("Carregamento concluido!");
    	
    	labelPais.setText("Abrindo o programa ...");
    	
    	esperaSegundos(5000); //espera 2 seg antes de fechar
    	
    	janelaCarregamento.dispatchEvent(new WindowEvent(janelaCarregamento, WindowEvent.WINDOW_CLOSING));
    }
    
    private void esperaSegundos(int milissegundos) {
    	try {
    		Thread.sleep(milissegundos);
    	} catch (Exception e) {}
    }
    
    private void centralizarTela() {
    	Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension dw = janelaCarregamento.getSize();
    	
    	janelaCarregamento.setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
    }
}