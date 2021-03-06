package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.JanelaCarregamento;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.MensagensDeErro;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Essa classe realiza as requisições para as atualizações
 * @author Thiago Castro
 * @author Pedro Henrique
 */

public class RequisicoesUpdates 
{
	/**
	 * Realiza as requisições de atualização da última data registrada na medição
	 * até o dia atual do computador
	 * @param tipo o tipo de caso para ser fornecido na URL
	 * @param slug o slug do país a ser atualizado
	 * @param ultimaData a última data da medição acrescida de um dia
	 * @param d a instância de dados para receber as atualizações
	 * @param status o status do caso
	 * @param pais o país a ser atualizado
	 */
	
	public void realizaOperacoesAtualizacao(String tipo, String slug, String ultimaData, Dados d, StatusCaso status, Pais pais)
	{		
	   ArrayList<Medicao> tipoDados;
	   
	   if (status == StatusCaso.CONFIRMADOS)
		   tipoDados = d.getConfirmados();
	   
	   else if (status == StatusCaso.MORTOS)
		   tipoDados = d.getMortos();
	   
	   else
		   tipoDados = d.getRecuperados();
	   
	   String dataAtual = LocalDateTime.now().toString();
	   
	   HttpClient cliente = HttpClient.newBuilder()
               .version(Version.HTTP_2)
               .followRedirects(Redirect.ALWAYS)
               .build();

	   HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.covid19api.com/total/country/" + slug + "/status/" + tipo + "?from=" + ultimaData + "&to=" + dataAtual + "Z"))
                    .build();
	   
	   try 
	   {
			
		    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
			
		    int codStatus = resposta.statusCode();
		    
		    if (codStatus >= 200 && codStatus < 300)
		    {
			    try 
			    {
			        JSONArray casosPeriodo = (JSONArray) new JSONParser().parse(resposta.body());
			        
			        Object linha;
			        LocalDateTime momento;
			        long casos;
			        
			        Iterator<JSONArray> iterador = casosPeriodo.iterator();
			        
			        while (iterador.hasNext())
			        {
			        	linha = iterador.next();
			        	momento = LocalDateTime.parse(((String) ((JSONObject) linha).get("Date")).replace("Z", ""));
			        	casos = Long.parseLong(String.valueOf(( ((JSONObject) linha).get("Cases"))));
			        
				    
			        	tipoDados.add(new Medicao(new Pais(pais), momento, (int) casos, status));
			        	JanelaCarregamento.setaPais(pais.getNome());
				        JanelaCarregamento.esperaSegundos(1000);
			        }
			        
			        
			    } 
				
			    catch (ParseException e) {
			    	MensagensDeErro.mostraMensagemDeErro(JanelaCarregamento.getJanelaPrincipal(),
			    										 String.format("Erro ao atualizar (País: %s)", pais.getNome()),
			    										 "Erro de atualização");
			    }
		    }
		    else {
		    	MensagensDeErro.mostraMensagemDeErro(JanelaCarregamento.getJanelaPrincipal(),
		    										 "Ocorreu um erro durante a requisição.\n"
		    										 + "Código HTTP: " + codStatus,
		    										 "Erro de atualização");
		    }
		} 
			
		catch (IOException e) {
			MensagensDeErro.mostraMensagemDeErro(JanelaCarregamento.getJanelaPrincipal(),
												 "Problema com a conexão",
												 "Erro de atualização");
		} 
			
		catch (InterruptedException e) {
			MensagensDeErro.mostraMensagemDeErro(JanelaCarregamento.getJanelaPrincipal(),
												 "Requisição interrompida",
												 "Erro de atualização");
		}
	}
}