package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MensagensDeErro 
{
	public static void mostraMensagemDeErro(String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(null, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostraMensagemDeErro(Component componenteParent, String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(componenteParent, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostraErroEncerraPrograma(Component componenteParent, String mensagemErro,
												 int codigoStatus, String tipoErro) {
		
		String mensagemDeErro = String.format("%s (Código: %d).\n\nO programa será encerrado!", mensagemErro, codigoStatus);
		
		if(componenteParent == null)
			mostraMensagemDeErro(mensagemDeErro, tipoErro);
		else
			mostraMensagemDeErro(componenteParent, mensagemDeErro, tipoErro);
		
		System.exit(1); //encerra o programa
	}
}
