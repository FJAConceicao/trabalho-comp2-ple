package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * 
 * @author Felipe de Jesus
 *
 */
public class MensagensDeErro 
{
	/**
	 * 
	 * @param mensagemErro
	 * @param tipoErro
	 */
	public static void mostraMensagemDeErro(String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(null, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 * @param componenteParent
	 * @param mensagemErro
	 * @param tipoErro
	 */
	public static void mostraMensagemDeErro(Component componenteParent, String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(componenteParent, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 * @param componenteParent
	 * @param mensagemErro
	 * @param codigoStatus
	 * @param tipoErro
	 */
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
