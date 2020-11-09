package br.ufrj.dcc.comp2.projeto_final.eflpt;

import br.ufrj.dcc.comp2.projeto_final.eflpt.database.Controle;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.JanelaPrincipal;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicaoInicial;

public class Main {

	public static void main(String[] args) 
	{
		 Controle c = new Controle();
		 Dados d = Dados.retornaInstancia();
         RequisicaoInicial i = new RequisicaoInicial();
         c.verificador(i);
         
         JanelaPrincipal janela = new JanelaPrincipal();
         janela.iniciaJanelaPrincipal();

	}

}
