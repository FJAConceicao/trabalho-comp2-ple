package br.ufrj.dcc.comp2.projeto_final.eflpt.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.MensagensDeErro;

/**
 * Essa classe controla os arquivos do banco de dados que conectam o cache não volátil com o volátil.
 * Os dados ficam na raiz do programa na pasta database.
 * Ao salvar, se a pasta database não existir, ela é criada
 * @author Thiago Castro
 */

public class ArquivoBase 
{
	private final String separador = File.separator;
	
	private Dados central = Dados.retornaInstancia();
	
	/**
	 * Verifica se a pasta database existe
	 * @return true se existe ou se foi criada com sucesso, false se não foi possível criar
	 */
	
	public boolean verificaExistenciaPastaDataBase()
	{
		File f = new File(".." + separador + "database");
		
		try
		{
			if (!f.exists())
			{
				f.mkdir();
				return true;
			}
		}
		
		catch (SecurityException e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Verifica a existência do arquivo fornecido
	 * @param localArquivo o local do arquivo
	 * @return true se existe, false senão
	 */
	
	public boolean verificaExistenciaArquivo(String localArquivo)
	{
		File f = new File(localArquivo);
		try
		{
			@SuppressWarnings({ "unused", "resource" })
			FileReader fr = new FileReader(f);
		}
		catch (FileNotFoundException e) 
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Abre o arquivo fornecido e carrega no banco de dados do programa.
	 * Ao final do carregamento de um país específico, se a última data
	 * for anterior a data atual, é verificado se existe atualizações
	 * pelo método realizaAtualizacoes da classe Controle
	 * @param nomeArquivo o nome do arquivo(confirmed.tsv, deaths.tsv, recovered.tsv)
	 * @param status o tipo de caso
	 * @return true se foi possível ler o arquivo, false c.c
	 */
	
	public boolean abreArquivosBase(String nomeArquivo, StatusCaso status)
	{
		String local = ".." + separador + "database" + separador + nomeArquivo;
		Controle atualizador = new Controle();
		
		if (!verificaExistenciaArquivo(local)) {
			MensagensDeErro.mostraMensagemDeErro("Arquivo " + nomeArquivo + " não encontrado.",
												 "Abertura de arquivo");
			return false;
		}
		
		String aux = "";
		
		String nome_pais;
		String codigo;
		String slug;
		float latitude;
		float longitude;
		LocalDateTime momento;
		int casos;
		ArrayList<Medicao> tipoDados;
		String tipo;
		momento = LocalDateTime.now(); // apenas para inicializar
		
		if (status == StatusCaso.CONFIRMADOS)
		{
			tipoDados = central.getConfirmados();
			tipo = "confirmed";
		}
		
		else if (status == StatusCaso.MORTOS)
		{
			tipoDados = central.getMortos();		
			tipo = "deaths";
		}
		
		else
		{
			tipoDados = central.getRecuperados();			
			tipo = "recovered";
		}
		
		try
		{
			FileInputStream f = new FileInputStream(local);
			InputStreamReader is = new InputStreamReader(f, "UTF-8");
			BufferedReader leitor = new BufferedReader(is);
			
			leitor.readLine(); // Lê a linha cabeçalho
			
			aux = leitor.readLine();
			
			Pais paisAtual = null;
			
			while (leitor.ready())
			{
				nome_pais = aux.split("\t")[0];
				codigo = aux.split("\t")[1];
				slug = aux.split("\t")[2];
				latitude = Float.parseFloat(aux.split("\t")[3]);
				longitude = Float.parseFloat(aux.split("\t")[4]);
				paisAtual = new Pais(nome_pais, codigo, slug, latitude, longitude);
				central.getPaises().add(new Pais(paisAtual));
				
				while (aux != null && paisAtual.getNome().intern() == aux.split("\t")[0].intern())
				{					
					momento = LocalDateTime.parse(aux.split("\t")[5]);
					casos = Integer.parseInt(aux.split("\t")[6]);
					
					tipoDados.add(new Medicao(new Pais(paisAtual), momento, casos, status));
					aux = leitor.readLine();
				}
				
				LocalDateTime agora = LocalDateTime.now();
				LocalTime meiaNoite = LocalTime.MIDNIGHT;
				
				/* Verifica se o último dia somado a um não está igual ao dia atual e é anterior a ele*/
				
				boolean igualdadeDia = momento.plusDays(1).isBefore(LocalDateTime.of(agora.toLocalDate(), meiaNoite))
						&& !momento.plusDays(1).equals(LocalDateTime.of(agora.toLocalDate(), meiaNoite));
				
				if (igualdadeDia)
				{
					atualizador.realizaAtualizacoes(tipo, status, paisAtual.getSlug(), momento.plusDays(1).toString() + ":00Z", paisAtual);
				}
					
			}
			
			leitor.close();		
		}
		catch (FileNotFoundException e)
		{
			central.getPaises().clear();
			return false;			
		}
		catch(IOException e)
		{
			central.getPaises().clear();
			return false;
		}		
		
		return true;
	}
	
	/**
	 * Abre o arquivo confirmed.tsv usando o método abreArquivosBase
	 * @return true se tudo ocorreu corretamente, false senão
	 */
	
	public boolean abreArquivoConfirmados()
	{
		return abreArquivosBase("confirmed.tsv", StatusCaso.CONFIRMADOS);
	}
	
	/**
	 * Abre o arquivo deaths.tsv usando o método abreArquivosBase
	 * @return true se tudo ocorreu corretamente, false senão
	 */
	
	public boolean abreArquivoMortos()
	{
		return abreArquivosBase("deaths.tsv", StatusCaso.MORTOS);
	}
	
	/**
	 * Abre o arquivo recovered.tsv usando o método abreArquivosBase
	 * @return true se tudo ocorreu corretamente, false senão
	 */
	
	public boolean abreArquivoRecuperados()
	{
		return abreArquivosBase("recovered.tsv", StatusCaso.RECUPERADOS);
	}
	
	/**
	 * Salva o arquivo passado como argumento na pasta database.
	 * @param nomeArquivo o nome do arquivo
	 * @param tipo o tipo de caso
	 * @return true se foi possível salvar, false senão
	 */
	
	public boolean salvaArquivosBase(String nomeArquivo, StatusCaso tipo)
	{
		String local = ".." + separador + "database" + separador + nomeArquivo;
		
		if (!verificaExistenciaPastaDataBase()) {
			// Imprimir erro ao salvar banco de dados
			MensagensDeErro.mostraMensagemDeErro("Banco de dados não encontrado.",
												 "Abertura do banco de dados");
			return false;
		}
		
		String nome_pais;
		String codigo;
		String slug;
		float latitude;
		float longitude;
		
		ArrayList<Medicao> tipoDados;
		Medicao linha;
		
		try
		{
			FileOutputStream f = new FileOutputStream(local);
			PrintStream impressora = new PrintStream(f);
			
			impressora.println("nome_pais\tcodigo\tslug\tlatitude\tlongitude\tmomento\tcasos\tstatus");
			
			if (tipo == StatusCaso.CONFIRMADOS)
			{
				tipoDados = central.getConfirmados();				
			}
			
			else if (tipo == StatusCaso.MORTOS)
			{
				tipoDados = central.getMortos();				
			}
			
			else
			{
				tipoDados = central.getRecuperados();				
			}
			
			Iterator<Medicao> iterador = tipoDados.iterator();
			Pais paisAtual;
			
			while (iterador.hasNext())
			{
				linha = iterador.next();
				paisAtual = linha.getPais();
				nome_pais = paisAtual.getNome();
				codigo = paisAtual.getCodigo();
				slug = paisAtual.getSlug();
				latitude = paisAtual.getLatitude();
				longitude = paisAtual.getLongitude();
				impressora.print(nome_pais + "\t" + codigo + "\t" + slug + "\t" + latitude + "\t" + longitude + "\t"
						+ linha.getMomento().toString() + "\t" + linha.getCasos() + "\t" + linha.getStatus().toString() + "\n");
				
			}
			
			
			impressora.close();
		}
		catch (FileNotFoundException e)
		{
			MensagensDeErro.mostraMensagemDeErro("Arquivo " + nomeArquivo + " não encontrado.",
												 "Abertura de arquivo");
			return false;			
		}	
		
		return true;
	}
	
	/**
	 * Salva o arquivo confirmed.tsv utilizando o método salvaArquivosBase
	 * @return true se foi possível salvar, false senão
	 */
	
	public boolean salvaArquivoConfirmados()
	{
		return salvaArquivosBase("confirmed.tsv", StatusCaso.CONFIRMADOS);		
	}
	
	/**
	 * Salva o arquivo deaths.tsv utilizando o método salvaArquivosBase
	 * @return true se foi possível salvar, false senão
	 */
	
	public boolean salvaArquivoMortos()
	{
		return salvaArquivosBase("deaths.tsv", StatusCaso.MORTOS);		
	}	
	
	/**
	 * Salva o arquivo recovered.tsv utilizando o método salvaArquivosBase
	 * @return true se foi possível salvar, false senão
	 */
	
	public boolean salvaArquivoRecuperados()
	{
		return salvaArquivosBase("recovered.tsv", StatusCaso.RECUPERADOS);		
	}
	
	
	
	

}
