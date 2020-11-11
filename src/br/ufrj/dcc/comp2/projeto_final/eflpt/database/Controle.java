package br.ufrj.dcc.comp2.projeto_final.eflpt.database;

import javax.swing.JOptionPane;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.JanelaCarregamento;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicaoInicial;
import br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes.RequisicoesUpdates;

/**
 * Essa classe implementa o controle do banco de dados. Isto é, a integridade dos arquivos
 * e as atualizações dos mesmos.
 * @author Thiago Castro
 */

public class Controle
{
	private ArquivoBase arqs = new ArquivoBase();
	private Dados d = Dados.retornaInstancia();
	private JanelaCarregamento jc = new JanelaCarregamento();
	
	/**
	 * Carregamento inicial do banco de dados.
	 * Se qualquer arquivo tiver a integridade prejudicada, é necessário baixar novos dados.
	 * @param r instância de RequisicaoInicial utilizada para solicitar os dados à API
	 */
	
	public void verificador(RequisicaoInicial r)
	{
		jc.iniciaTelaCarregamento();
		JanelaCarregamento.setaTextoLabelInformativo("Recuperando informações do banco de dados ...");
		
		if (!arqs.abreArquivoConfirmados() || !arqs.abreArquivoMortos() || !arqs.abreArquivoRecuperados())
		{
			// Mostra mensagem de warning na tela
			String mensagem = "Erro na abertura de um dos arquivos, realizando novos downloads ...";		
			JOptionPane.showMessageDialog(JanelaCarregamento.getJanelaPrincipal(),
										  mensagem,
										  "Abertura de arquivos",
										  JOptionPane.WARNING_MESSAGE);
			
			//Abrir tela de download de requisições
			JanelaCarregamento.setaTextoLabelInformativo("Iniciando download de requisições ...");
			
			JanelaCarregamento.setaTextoLabelInformativo("Baixando informações de países ...");
			r.requisitarPaises(d);
			
			JanelaCarregamento.setaTipoCasoDeInfoLabelInformativo(StatusCaso.CONFIRMADOS, "Carregando", "casos");
			r.requisitarConfirmados(d);
			
			JanelaCarregamento.setaTipoCasoDeInfoLabelInformativo(StatusCaso.MORTOS, "Carregando", "mortes");
			r.requisitarMortes(d);
			
			JanelaCarregamento.setaTipoCasoDeInfoLabelInformativo(StatusCaso.RECUPERADOS, "Carregando", "recuperados");
			r.requisitarRecuperados(d);
		}
		
		//Mostrar carregamento concluido e fechar tela de carregamento
		jc.mostraConcluidoFechaTela();
	}
		
	
	/**
	 * Verifica atualizações e realiza se houver
	 * @param tipo o tipo do caso para ser solicitado na URL
	 * @param status o status do caso para carregar o ArrayList correto da classe Dados
	 * @param slug o slug do país para ser utilizado na URL
	 * @param ultimaMedicao a data da última medição presente no arquivo
	 * @param pais o país que sofreu a medição
	 */
	
	public void realizaAtualizacoes(String tipo, StatusCaso status, String slug, String ultimaMedicao, Pais pais)
	{
		RequisicoesUpdates atualizador = new RequisicoesUpdates();
		atualizador.realizaOperacoesAtualizacao(tipo, slug, ultimaMedicao, d, status, pais);	
	}
}