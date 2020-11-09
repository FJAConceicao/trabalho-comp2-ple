package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;
import br.ufrj.dcc.comp2.projeto_final.eflpt.StatusCaso;
import br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas.Dados;
import br.ufrj.dcc.comp2.projeto_final.eflpt.gui.MensagensDeErro;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class RequisicaoInicial
{
   public void requisitarPaises(Dados d)
   {
	   HttpClient cliente = HttpClient.newBuilder()
               .version(Version.HTTP_2)
               .followRedirects(Redirect.ALWAYS)
               .build();



	   HttpRequest requisicaoPais = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.covid19api.com/countries"))
                    .build();
	   Pais paisAtual;
	   try 
	   {
			
		    HttpResponse<String> resposta = cliente.send(requisicaoPais, HttpResponse.BodyHandlers.ofString());
			
		    int codStatus = resposta.statusCode();
		    
		    if (codStatus >= 200 && codStatus < 300)
		    {
			    try 
			    {
			        JSONArray paises = (JSONArray) new JSONParser().parse(resposta.body());
			        
			        for (Object pais : paises)
			        {				
			            String nomePais = (String)	((JSONObject) pais).get("Country");
			            String slugPais = (String)	((JSONObject) pais).get("Slug");
			            paisAtual = new Pais(nomePais, slugPais);
						requisitarInformacoesPais(paisAtual);
			            d.getPaises().add(new Pais(paisAtual));
			        }				
			    } 
				
			    catch (ParseException e) {
			    	MensagensDeErro.mostraMensagemDeErro("Resposta inválida", "Erro de requisição");				
			    }
		    }
			
		} 
			
		catch (IOException e) {
			MensagensDeErro.mostraMensagemDeErro("Problema com a conexão", "Erro de requisição");
		} 
			
		catch (InterruptedException e) {
			MensagensDeErro.mostraMensagemDeErro("Requisição interrompida", "Erro de requisição");
		}	   
   }
   
   public void requisitarInformacoesPais(Pais paisAtual)
   {
	   String codigo;
       float latitude;
       float longitude;
       
       HttpClient cliente = HttpClient.newBuilder()
	               .version(Version.HTTP_2)
	               .followRedirects(Redirect.ALWAYS)
	               .build();
       
	
       HttpRequest requisicao;
       
       if (paisAtual.getSlug().intern() == "united-states")
       
    	   requisicao = HttpRequest.newBuilder()
	                    .uri(URI.create("https://api.covid19api.com/live/country/" + paisAtual.getSlug() + "/status/confirmed"))
	                    .build();
       else
    	   requisicao = HttpRequest.newBuilder()
           .uri(URI.create("https://api.covid19api.com/country/" + paisAtual.getSlug() + "/status/confirmed"))
           .build();
		   
       int codStatus = 0;
	   try 
	   {
			
		    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
			
		    codStatus = resposta.statusCode();
		    
		    if (codStatus >= 200 && codStatus < 300)
		    {
			    try 
			    {
			        JSONArray casosPais = (JSONArray) new JSONParser().parse(resposta.body());
			        
				    Iterator<JSONArray> iterador = casosPais.iterator();
				        
			        if (iterador.hasNext())
			        {
				        Object linha = iterador.next();
				        
				        codigo = (String)	((JSONObject) linha).get("CountryCode");
				        latitude = Float.parseFloat((String)	((JSONObject) linha).get("Lat"));
				        longitude = Float.parseFloat((String)	((JSONObject) linha).get("Lon"));
				        paisAtual.setaInfo(codigo, latitude, longitude);
				        System.out.println(paisAtual.getNome());
			        }
			    }
			    
			    catch (ClassCastException d)
			    {
			    	
			    }
				
			    catch (ParseException e) {
			    	MensagensDeErro.mostraMensagemDeErro("Resposta inválida", "Erro de requisição");
			    }
		    }
		    else {
		    	
		    	// Imprimir janela de erro com código de status e encerrar o programa.
		    	MensagensDeErro.mostraErroEncerraPrograma(null, 
						  								  "", //Inserir mensagem de erro aqui
						  								  codStatus,
						  								  "Erro de requisição");
		    }
		}
	   
		catch (IOException e) {
			
			// Imprimir janela de erro com código de status e encerrar o programa.
			MensagensDeErro.mostraErroEncerraPrograma(null, 
													  "Problema com a conexão",
													  codStatus,
													  "Erro de requisição");
		} 
			
		catch (InterruptedException e) {
			
			// Imprimir janela de erro com código de status e encerrar o programa.
			MensagensDeErro.mostraErroEncerraPrograma(null, 
					  								  "Requisição interrompida",
					  								  codStatus,
					  								  "Erro de requisição");
		}
   }

   
   public void requisitarConfirmados(Dados d)
   { 
	   StatusCaso confirmado = StatusCaso.CONFIRMADOS;
	   realizaOperacoesTipo("confirmed", d, confirmado);
   }
   
   public void requisitarMortes(Dados d)
   {
	   StatusCaso mortes = StatusCaso.MORTOS;
	   realizaOperacoesTipo("deaths", d, mortes);
   }
   
   public void requisitarRecuperados(Dados d)
   {
	   
	   StatusCaso recuperados = StatusCaso.RECUPERADOS;
	   realizaOperacoesTipo("recovered", d, recuperados);
   }
   
   public void realizaOperacoesTipo(String tipo, Dados d, StatusCaso status)
   {
	   Iterator<Pais> i = d.getPaises().iterator();
	   Pais paisAtual;
	   ArrayList<Medicao> tipoDados;
       LocalDateTime momento;
       long casos;
	   
	   if (status == StatusCaso.CONFIRMADOS)
		   tipoDados = d.getConfirmados();
	   
	   else if (status == StatusCaso.MORTOS)
		   tipoDados = d.getMortos();
	   
	   else
		   tipoDados = d.getRecuperados();
	   
	   while (i.hasNext())
	   {
		   paisAtual = i.next();
		   
		   HttpClient cliente = HttpClient.newBuilder()
	               .version(Version.HTTP_2)
	               .followRedirects(Redirect.ALWAYS)
	               .build();
	
		   HttpRequest requisicao = HttpRequest.newBuilder()
	                    .uri(URI.create("https://api.covid19api.com/total/country/" + paisAtual.getSlug() + "/status/" + tipo))
	                    .build();
		   
		   int codStatus = 0;
		   try 
		   {
				
			    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
				
			    codStatus = resposta.statusCode();
			    
			    if (codStatus >= 200 && codStatus < 300)
			    {
				    try 
				    {
				        JSONArray casosPais = (JSONArray) new JSONParser().parse(resposta.body());
				        
				        Iterator<JSONArray> iterador = casosPais.iterator();
				        
				        if (iterador.hasNext())
				        {
					        Object linha = iterador.next();
					        
					        momento = LocalDateTime.parse(((String) ((JSONObject) linha).get("Date")).replace("Z", ""));
					        casos = Long.parseLong(String.valueOf(( ((JSONObject) linha).get("Cases"))));
					     	        
					        
					        tipoDados.add(new Medicao(new Pais(paisAtual), momento, (int) casos, status));
					        
					        
					        while (iterador.hasNext())	
					        {
					        	linha = iterador.next();
					        	momento = LocalDateTime.parse(((String) ((JSONObject) linha).get("Date")).replace("Z", ""));
					        	casos = Long.parseLong(String.valueOf(( ((JSONObject) linha).get("Cases"))));
					        	tipoDados.add(new Medicao(new Pais(paisAtual), momento, (int) casos, status));
					        	System.out.println(paisAtual.getNome() + "\t" + casos);
					        	
					        }
				        }
				    } 
					
				    catch (ParseException e) {
				    	
				    	// Imprimir janela de erro com código de status e encerrar o programa.
				    	MensagensDeErro.mostraErroEncerraPrograma(null, 
				    											  "Resposta inválida",
				    											  codStatus,
				    											  "Erro de requisição");
				    	
				    }
			    }
			    else {
			    	
			    	// Imprimir janela de erro com código de status e encerrar o programa.
			    	MensagensDeErro.mostraErroEncerraPrograma(null, 
							  								  "", //Inserir mensagem de erro aqui
							  								  codStatus,
							  								  "Erro de requisição");
			    	
			    }
			} 
				
			catch (IOException e) {
				
				// Imprimir janela de erro com código de status e encerrar o programa.
				MensagensDeErro.mostraErroEncerraPrograma(null, 
														  "Problema com a conexão",
														  codStatus,
														  "Erro de requisição");
			} 
				
			catch (InterruptedException e) {
				
				// Imprimir janela de erro com código de status e encerrar o programa.
				MensagensDeErro.mostraErroEncerraPrograma(null, 
														  "Requisição interrompida",
														  codStatus,
														  "Erro de requisição");
				
			}
	   }
   }
}