package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Estatistica;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Exportadora;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.TotalCasos;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.TotalMortos;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.TotalRecuperados;

/**
 * Essa classe implementa a recepção e o controle de dados oriundos da GUI.
 * Esses dados são utilizados na criação dos rankings
 * @author Thiago Castro
 */


public class Coletor
{
	/**
	 * Modularização do switch do método converteData
	 * @param numero o identificador do ranking a ser chamado
	 * @param inicio a data ínicio
	 * @param fim a data fim
	 */
	
	public static void geradorRanking(int numero, LocalDate inicio, LocalDate fim, JFrame janelaPrincipal)
	{
		TotalMortos mortos = new TotalMortos();
		TotalCasos casos = new TotalCasos();
		TotalRecuperados recuperados = new TotalRecuperados();
		setDatas(mortos, fim, inicio);
		setDatas(casos, fim, inicio);
		setDatas(recuperados, fim, inicio);
		
		
		
		
		switch(numero)
		{
			case 1:
				 String[] rankingCasos = casos.rankingCasos();
				 String[] rankingMortos = mortos.rankingMortos();
				 String[] rankingRecuperados = recuperados.rankingRecuperados();
				 JanelaRankingNumeroCrescimento janela = new JanelaRankingNumeroCrescimento();
				 janela.iniciaJanelaRanking(janelaPrincipal, "Número", rankingCasos, rankingRecuperados, rankingMortos);
				 
				break;
			case 2:
				String[] rankingCasosCrescimento = casos.rankingCasosCrescimento();
				String[] rankingMortosCrescimento = mortos.rankingMortosCrescimento();
				String[] rankingRecuperadosCrescimento = recuperados.rankingRecuperadosCrescimento();	
				JanelaRankingNumeroCrescimento janelaCrescimento = new JanelaRankingNumeroCrescimento();
				janelaCrescimento.iniciaJanelaRanking(janelaPrincipal, "Crescimento", rankingCasosCrescimento, rankingRecuperadosCrescimento, rankingMortosCrescimento);
				break;
			case 3:
				String[] rankingCasosMortalidade = casos.rankingMortalidade();
				JanelaRankingMortalidade janelaMortalidade = new JanelaRankingMortalidade();
				janelaMortalidade.iniciaJanelaRankingMortalidade(janelaPrincipal, rankingCasosMortalidade);
				break;
		}
	}
	
	private static  void setDatas(Estatistica objeto, LocalDate fim, LocalDate inicio) {
			objeto.setDataFim(fim);
			objeto.setDataInicio(inicio);
		
	}

	/**
	 * Verifica se as datas para receber um período são corretas.
	 * @param data1 a data início do período
	 * @param data2 a data fim do período
	 * @param campo o campo de texto para ser recuperada a janela original
	 * @return true se elas são corretas, false c.c.
	 */
	public static boolean verificaDatas(LocalDate data1, LocalDate data2, JTextField campo)
	{	
		LocalDate dataLimite = LocalDate.now();
		if (data1.isAfter(data2)) // Verifica se a data início vem antes da data fim
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												 "A primeira data vem depois da segunda data.",
												 "Erro ao receber data");
			
			return false;
		}
		
		if (data1.isAfter(dataLimite) || data2.isAfter(dataLimite)) // Verifica se uma das duas datas está no futuro
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
					 							 "Uma das datas está no futuro.",
					 							 "Erro ao receber data");
			
			return false;
		}
		
		if (data1.isBefore(LocalDate.of(2019, 11, 17))) // Verifica se a primeira data vem antes do primeiro caso de COVID no mundo
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												 "A primeira data vem antes do primeiro caso de COVID-19 no mundo",
												 "Erro ao receber data");

			return false;
		}
		if(data1.equals(dataLimite) || data2.equals(dataLimite)) // Verifica se o usuário está pedindo dados que ainda não foram atualizados
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												"Os dados de hoje ainda não foram atualizados, espere o intervalo de 1 dia para fazer esta consulta",
												"Erro ao receber data");

			return false;
		
		}
		
		
		if(data2.equals(dataLimite.minusDays(2))) //Verifica se o usuário está pedindo dados que ainda não foram atualizados
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												"Os dados de hoje ainda não foram atualizados, espere o intervalo de 1 dia para fazer esta consulta",
												"Erro ao receber data");

			return false;
		}
		
		
		
		return true;
	}
	
	/**
	 * Recebe as datas em formato de texto para conversão em LocalDate
	 * @param primeiraData a data início
	 * @param segundaData a data fim
	 * @param ranking o ranking que vai ser gerado(na ordem dos botões: 1, 2, 3)
	 * @param janela a janela principal do programa
	 */
	
	public static void converteData(JTextField primeiraData, JTextField segundaData, int ranking, JDialog janela, JFrame janelaPrincipal)
	{
		LocalDate inicio;
		LocalDate fim;
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try
		{
			inicio = LocalDate.parse(primeiraData.getText(), formatador);
			fim = LocalDate.parse(segundaData.getText(), formatador);
		}
		catch (DateTimeParseException e)
		{
			MensagensDeErro.mostraMensagemDeErro(primeiraData.getParent(),
												 "Alguma data é invalida.",
												 "Erro ao receber data");
			
			return;
		}
		
		if (verificaDatas(inicio, fim, primeiraData))
		{
			janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
			Coletor.geradorRanking(ranking, inicio, fim, janelaPrincipal);
		}
	}
	
	/**
	 * Recebe as datas em formato de texto para conversão em LocalDate
	 * e o raio
	 * @param primeiraData a data início
	 * @param segundaData a data fim
	 * @param janela a janela principal do programa
	 * @param raio o valor do raio
	 */
	
	public static void converteData(JTextField primeiraData, JTextField segundaData, JDialog janela, int raio, JFrame janelaPrincipal)
	{
		LocalDate inicio;
		LocalDate fim;
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try
		{
			inicio = LocalDate.parse(primeiraData.getText(), formatador);
			fim = LocalDate.parse(segundaData.getText(), formatador);
		}
		catch (DateTimeParseException e)
		{
			MensagensDeErro.mostraMensagemDeErro(primeiraData.getParent(),
												 "Alguma data é invalida.",
												 "Erro ao receber data");

			return;
			
		}
		
		if (verificaDatas(inicio, fim, primeiraData))
		{
			janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
			TotalCasos locaisProximos = new TotalCasos();
			setDatas(locaisProximos,fim, inicio);
			JanelaLocaisMaisProximos janelaRaio = new JanelaLocaisMaisProximos();
			String[] paises = locaisProximos.rankingCasosCrescimento();
			String pais = paises[0].split("!")[1];
			String[] locais = locaisProximos.rankingRaio(raio, pais);
			janelaRaio.iniciaJanelaLocaisMaisProximos(janelaPrincipal,pais,locais);
		}
	}
	
	
	/**
	 * Verifica se o raio está entre 0 e 6371
	 * @param raio o raio fornecido
	 * @param janela a janela principal
	 * @return true se está, false c.c.
	 */
	
	public static boolean verificaRaio(JTextField raio, JDialog janela)
	{
		int valor;
		
		try
		{
			valor = Integer.parseInt(raio.getText());
		}
		
		catch (NumberFormatException e)
		{
			MensagensDeErro.mostraMensagemDeErro(raio.getParent(),
					 							 "Digite um número válido",
					 							 "Erro ao receber raio");

			return false;
		}
		
		if (valor > 6371 || valor <= 0)
		{
			JOptionPane.showMessageDialog(raio.getParent(),
										  "Digite um número maior que 0 e menor que 6371.",
										  "Erro ao receber raio",
										  JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
		
		janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
		
		return true;
	}
	
	/**
	 * Recebe o local para o salvamento do arquivo de ranking.
	 * Esse método abre uma janela padrão de salvar como do sistema.
	 */

	public static void recebeLocalArquivo(String[] ranking, String tipoRanking)
	{
		File arquivo;
		JFileChooser verArqSistema = new JFileChooser();
		FileNameExtensionFilter filtroCsv = new FileNameExtensionFilter("CSV (separado por vírgulas)", "csv");
		FileNameExtensionFilter filtroTsv = new FileNameExtensionFilter("Texto (separado por tabulações)", "tsv");
		JDialog salvarArquivo = new JDialog();
		verArqSistema.setAcceptAllFileFilterUsed(false);
		verArqSistema.setFileFilter(filtroCsv);
		verArqSistema.setFileFilter(filtroTsv);
		int confirmar = verArqSistema.showSaveDialog(salvarArquivo);
		
		if (confirmar == JFileChooser.APPROVE_OPTION)
		{
			arquivo = verArqSistema.getSelectedFile();	
			
			if (arquivo.getName().toLowerCase().endsWith(".csv"))
			{
				verificaTipoRanking(tipoRanking, ranking, "csv", arquivo);
				// Chamar método para salvar os rankings
			}
			else if (arquivo.getName().toLowerCase().endsWith(".tsv"))
			{
				verificaTipoRanking(tipoRanking, ranking, "tsv", arquivo);
				// Chamar método para salvar os rankings
			}
			else
			{
				MensagensDeErro.mostraMensagemDeErro(verArqSistema.getParent(),
													 "Apenas arquivos .tsv e .csv são suportados",
													 "Erro ao receber local arquivo");
			}
		}
	}
	
	/**
	 * Verifica qual consulta será exportado e chama o método correposdente
	 * @param tipoRanking O tipo de consulta que foi realizada
	 * @param 
	 */
	public static void verificaTipoRanking(String tipoRanking, String[] ranking, String tipoArquivo, File arquivo) {
		switch(tipoRanking) {
		case "mortalidade":
			if(tipoArquivo.equalsIgnoreCase("csv")) 
			{
				Exportadora.geraArquivoCSV(arquivo, ranking);
			} else 
			{
				Exportadora.geraArquivoTSV(arquivo, ranking);
			}
			break;
		case "raio":
			if(tipoArquivo.equalsIgnoreCase("csv")) 
			{
				Exportadora.geraArquivoCSV(arquivo, ranking);
			} else
			{
				Exportadora.geraArquivoTSV(arquivo, ranking);
			}
			
			break;
		}
	}
	
	public static void recebeLocalArquivo(String[] rankingCasos, String[] rankingRecuperados, String[] rankingMortos, String tipoRanking) {
		File arquivo;
		JFileChooser verArqSistema = new JFileChooser();
		FileNameExtensionFilter filtroCsv = new FileNameExtensionFilter("CSV (separado por vírgulas)", "csv");
		FileNameExtensionFilter filtroTsv = new FileNameExtensionFilter("Texto (separado por tabulações)", "tsv");
		JDialog salvarArquivo = new JDialog();
		verArqSistema.setAcceptAllFileFilterUsed(false);
		verArqSistema.setFileFilter(filtroCsv);
		verArqSistema.setFileFilter(filtroTsv);
		int confirmar = verArqSistema.showSaveDialog(salvarArquivo);
		
		if (confirmar == JFileChooser.APPROVE_OPTION)
		{
			arquivo = verArqSistema.getSelectedFile();	
			
			if (arquivo.getName().toLowerCase().endsWith(".csv"))
			{
				verificaTipoRanking(rankingCasos, rankingRecuperados, rankingMortos, tipoRanking, "csv", arquivo);
				// Chamar método para salvar os rankings
			}
			else if (arquivo.getName().toLowerCase().endsWith(".tsv"))
			{
				verificaTipoRanking(rankingCasos, rankingRecuperados, rankingMortos, tipoRanking, "tsv", arquivo);
				// Chamar método para salvar os rankings
			}
			else
			{
				MensagensDeErro.mostraMensagemDeErro(verArqSistema.getParent(),
													 "Apenas arquivos .tsv e .csv são suportados",
													 "Erro ao receber local arquivo");
			}
		}
	}
	
	/**
	 * 
	 * @param tipo 
	 */
	public static void verificaTipoRanking(String[] rankingCasos, String[] rankingRecuperados, String[] rankingMortos,String tipoRanking, String tipoArquivo, File arquivo) {
		switch(tipoRanking) {
		case "Crescimento":
			if(tipoArquivo.equalsIgnoreCase("csv")) 
			{
				Exportadora.geraArquivoCSV(arquivo, rankingCasos, rankingRecuperados, rankingMortos);
			} else
			{
				Exportadora.geraArquivoTSV(arquivo, rankingCasos, rankingRecuperados, rankingMortos);
			}
			
			break;
		case "Número":
			if(tipoArquivo.equalsIgnoreCase("csv")) 
			{
				Exportadora.geraArquivoCSV(arquivo, rankingCasos, rankingRecuperados, rankingMortos);
			} else
			{
				Exportadora.geraArquivoTSV(arquivo, rankingCasos, rankingRecuperados, rankingMortos);
			}
			
			break;
		
		}
	}
	
	

	
}