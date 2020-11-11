package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Classe para exibir mensagens de erro e fechar o programa de acordo com a situação
 * @author Felipe de Jesus
 *
 */
public class MensagensDeErro 
{
	/**
	 * Recebe uma mensagem e um tipo de erro. Esses dados são passados para um JOptionPane com 
	 * o parentComponent igual a null e messageType sendo OptionPane.ERROR_MESSAGE. E ele exibe a mensagem 
	 * de erro na tela. 
	 * 
	 * @param mensagemErro Mensagem de erro para ser exibida
	 * @param tipoErro Tipo de erro que é mostrado no titulp da janela.
	 */
	public static void mostraMensagemDeErro(String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(null, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Recebe um componenteParent, uma mensagem e um tipo de erro.
	 * Esses dados são passados para JOptionPane.showMessageDialog(), que exibe o erro na tela.
	 * 
	 * @param componenteParent Componente mãe da tela de erro. Assim o erro é exibido dentro da componente mãe
	 * @param mensagemErro Mensagem de erro para ser exibida
	 * @param tipoErro Tipo de erro que é mostrado no titulp da janela.
	 */
	public static void mostraMensagemDeErro(Component componenteParent, String mensagemErro, String tipoErro) {
		JOptionPane.showMessageDialog(componenteParent, mensagemErro, tipoErro, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Mostra mensagem de erro e encerra o programa.
	 * 
	 * @param componenteParent Componente mãe da tela de erro. Assim o erro é exibido dentro da componente mãe
	 * @param mensagemErro Mensagem de erro para ser exibida
	 * @param codigoStatus Código de status gerado da requisição http
	 * @param tipoErro Tipo de erro que é mostrado no titulp da janela.
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
