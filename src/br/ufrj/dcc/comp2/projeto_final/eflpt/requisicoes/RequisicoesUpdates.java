package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class RequisicoesUpdates 
{
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
			        	System.out.println("Atualização:" + pais.getNome() + "\t" + casos);  
				        
			        }
			    } 
				
			    catch (ParseException e) {
				
			        System.err.println("Resposta inválida");
				
			        e.printStackTrace();
				
			    }
		    }
		    else
		    	System.out.println(codStatus);
			
		} 
			
		catch (IOException e) {
			
		    System.err.println("Problema com a conexão");
			
		    e.printStackTrace();
			
		} 
			
		catch (InterruptedException e) {
			
		    System.err.println("Requisição interrompida");
			
		    e.printStackTrace();
			
		}

	}

}
