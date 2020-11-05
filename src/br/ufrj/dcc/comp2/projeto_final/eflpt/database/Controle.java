package br.ufrj.dcc.comp2.projeto_final.eflpt.database;

import java.time.LocalDateTime;
import java.util.ArrayList;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicaoInicial;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicoesUpdates;

public class Controle
{
	private ArquivoBase arqs = new ArquivoBase();
	private Dados d = Dados.retornaInstancia();
	
	public void verificador(RequisicaoInicial r)
	{
		if (!arqs.abreArquivoConfirmados() || !arqs.abreArquivoMortos() || !arqs.abreArquivoRecuperados())
		{
			// Imprime erro na tela e abre tela de download de requisições
			
			System.out.println("Erro na abertura de um dos arquivos, realizando novos downloads");			
			r.requisitarPaises(d);
			r.requisitarConfirmados(d);
			r.requisitarMortes(d);
			r.requisitarRecuperados(d);
		}
		
	}
	
	public void realizaAtualizacoes(String tipo, StatusCaso status, String slug, String ultimaMedicao, ArrayList<Medicao> lista, Pais pais)
	{
		RequisicoesUpdates atualizador = new RequisicoesUpdates();
		ArrayList<Medicao> resultado;
		resultado = atualizador.realizaOperacoesAtualizacao(tipo, slug, ultimaMedicao, d, status, pais);
		
		if (!resultado.isEmpty() && resultado != null)
		{
			for (Medicao m : resultado)
			{
				lista.add(new Medicao(m));
			}
		}
		
	}
	
	
	
	
	
	

}
