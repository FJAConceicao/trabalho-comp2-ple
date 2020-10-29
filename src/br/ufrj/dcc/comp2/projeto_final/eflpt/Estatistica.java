package br.ufrj.dcc.comp2.projeto_final.eflpt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Estatistica 
{
	private String nome;
	private List<Medicao> observacoes;
	
	public void inclui(String nome, Medicao observacao)
	{
		this.nome = nome;
		observacoes = new ArrayList<Medicao>();
		observacoes.add(observacao);
	}
	
	public abstract LocalDate dataInicio();
	
	public abstract LocalDate dataFim();
	
	public abstract void valor();
	
	

}
