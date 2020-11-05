package br.ufrj.dcc.comp2.projeto_final.eflpt;

import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.JanelaPrincipal;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicaoInicial;

public class Main {

	public static void main(String[] args) 
	{
		 Dados d = Dados.retornaInstancia();
         RequisicaoInicial i = new RequisicaoInicial();
		 i.requisitarPaises(d);
         i.requisitarConfirmados(d);
         i.requisitarMortes(d);
         i.requisitarRecuperados(d);

         for (var t : d.getConfirmados())
         {
                 Pais atual = t.getPais();
                 System.out.println(atual.getNome() + "\t" + atual.getSlug() + "\t" + atual.getCodigo()
                 + "\t" + atual.getLatitude() +"\t" + atual.getLongitude() + "\t"
                                 + t.getMomento().toString() + "\t" + t.getCasos() + "\t" + t.getStatus());
         }

         for (var t : d.getMortos())
         {
                 Pais atual = t.getPais();
                 System.out.println(atual.getNome() + "\t" + atual.getSlug() + "\t" + atual.getCodigo()
                 + "\t" + atual.getLatitude() +"\t" + atual.getLongitude() + "\t"
                                 + t.getMomento().toString() + "\t" + t.getCasos() + "\t" + t.getStatus());
         }

         for (var t : d.getRecuperados())
         {
                 Pais atual = t.getPais();
                 System.out.println(atual.getNome() + "\t" + atual.getSlug() + "\t" + atual.getCodigo()
                 + "\t" + atual.getLatitude() +"\t" + atual.getLongitude() + "\t"
                                 + t.getMomento().toString() + "\t" + t.getCasos() + "\t" + t.getStatus());
         }
         
         JanelaPrincipal janela = new JanelaPrincipal();
         janela.iniciaJanelaPrincipal();


	}

}
