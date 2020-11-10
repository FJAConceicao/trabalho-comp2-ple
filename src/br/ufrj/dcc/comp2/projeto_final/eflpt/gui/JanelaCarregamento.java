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

public class JanelaCarregamento {
	
	private JFrame janelaPrincipal;
	private Container regiaoPrincipal;
	private JLabel labelInformativo;
	private JLabel labelPais;
	private JLabel labelGifCarregando;

	private void iniciaTelaCarregamento() {
    	
		janelaPrincipal = new JFrame("Dados COVID-19");
		regiaoPrincipal = janelaPrincipal.getContentPane();
		
    	labelInformativo = new JLabel(); //Texto será inserido com método setaTextoLabelInformativo
    	labelInformativo.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelInformativo.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		labelPais = new JLabel(); //Texto será inserido com método setaPais
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
		
		janelaPrincipal.setSize(600, 220);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaPrincipal.setVisible(true);
	}
	
	// Tipo carregamento: casos, mortes ou recuperados
	public void setaTipoCasoDeCarregamento(StatusCaso status, String tipoDeCarregamento) {
		String mensagemNaTela = "Carregando o número de " + tipoDeCarregamento;
		
		if(status == StatusCaso.CONFIRMADOS || status == StatusCaso.RECUPERADOS)
			mensagemNaTela += " confirmados";
		else
			mensagemNaTela += " confirmadas";
		
		labelInformativo.setText(mensagemNaTela);
	}
	
	public void setaPais(String nomePais) {
		labelPais.setText(nomePais);
	}
	
    /* Pode ser utilizada para mostrar mensagens iniciais ou finais, tipo:
     * 	- Carregamento concluido
     * 	- Iniciando carregamento 
     * */
    public void setaTextoLabelInformativo(String texto) {
    	labelInformativo.setText(texto);
    }
    
    public JanelaCarregamento() {
    	iniciaTelaCarregamento();
    }
    
    /*
    public void close()
    {
    	try {
    		Thread.sleep(1000); //espera 1 seg antes de fechar
    	} catch (Exception e) {} 
    	
    	janelaPrincipal.setVisible(false);
    }
    
    private void resetaRegiaoPrincipal() {
    	janelaPrincipal.getContentPane().removeAll();
    	janelaPrincipal.getContentPane().revalidate();
    	janelaPrincipal.getContentPane().repaint();
    }
    */
}
