package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.*;
import javax.swing.*;

public class JanelaCarregamentoRecuperados
{	
	JFrame janelaPrincipal = new JFrame("Dados COVID-19");
	JLabel loading = new JLabel(new ImageIcon("loading.gif"));
	Container regiaoPrincipal = janelaPrincipal.getContentPane();

    public void refresh(int numeroPais)
    {
    	janelaPrincipal.getContentPane().removeAll();
    	janelaPrincipal.getContentPane().revalidate();
    	janelaPrincipal.getContentPane().repaint();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Carregando o n�mero de recuperados do ");
    	sb.append(numeroPais);
    	sb.append("� pa�s...");
    	JLabel msg = new JLabel(sb.toString());
    	
    	janelaPrincipal.setSize(600, 150);
    	
    	loading.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		msg.setAlignmentX(Component.CENTER_ALIGNMENT);
		msg.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		regiaoPrincipal.add(msg);
		regiaoPrincipal.add(loading);
		regiaoPrincipal.add(Box.createRigidArea(new Dimension(50,50)));
		
		janelaPrincipal.setVisible(true);
		
		//try { Thread.sleep(100); } catch (Exception e) {}
    }
    
    public void concluido()
    {
    	janelaPrincipal.getContentPane().removeAll();
    	janelaPrincipal.getContentPane().revalidate();
    	janelaPrincipal.getContentPane().repaint();
    	
    	JLabel msgConcluido = new JLabel("Carregamento conclu�do.");  
    	
    	janelaPrincipal.setSize(600, 150);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		regiaoPrincipal.setLayout(new BoxLayout(regiaoPrincipal, BoxLayout.Y_AXIS));
		
		msgConcluido.setAlignmentX(Component.CENTER_ALIGNMENT);
		msgConcluido.setFont(new Font ("Times New Roman", Font.BOLD , 22));

		regiaoPrincipal.add(msgConcluido);

		janelaPrincipal.setVisible(true);
    }
    
    public void close()
    {
    	try { Thread.sleep(1000); } catch (Exception e) {} //espera 1 seg antes de fechar
    	janelaPrincipal.setVisible(false);
    }
}