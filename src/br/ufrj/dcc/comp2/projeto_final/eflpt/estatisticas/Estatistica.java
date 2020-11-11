package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe é uma abstração de uma estatística no programa.
 * É implementada conforme o status de um caso.
 * @author Thiago Castro
 */

public abstract class Estatistica 
{
	private String nome;
	private List<Medicao> observacoes;
	
	/**
	 * Inclui uma medição na lista de observações
	 * @param nome o nome da medição
	 * @param observacao a medição a ser adicionada
	 */
	
	public void inclui(String nome, Medicao observacao)
	{
		this.nome = nome;
		observacoes = new ArrayList<Medicao>();
		observacoes.add(observacao);
	}
	
	/**
	 * Retorna o nome desta medição
	 * @return
	 */
	
	public String getNome()
	{
		return nome;
	}
	
	public abstract LocalDate dataInicio();
	
	public abstract LocalDate dataFim();
	
	public abstract float valor();
	
	public abstract void setDataInicio(LocalDate inicio);
	
	public abstract void setDataFim(LocalDate fim);

}
