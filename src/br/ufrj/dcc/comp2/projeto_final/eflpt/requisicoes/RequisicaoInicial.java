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
				
			        System.err.println("Resposta inválida");
				
			        e.printStackTrace();
				
			    }
		    }
			
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
		   
	   try 
	   {
			
		    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
			
		    int codStatus = resposta.statusCode();
		    
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
				
			        System.err.println("Resposta inválida");
				
			        e.printStackTrace();
				
			    }
		    }
		    else
		    	System.out.println(codStatus);
		    	// Imprimir janela de erro com código de status e encerrar o programa.
			
		} 
			
		catch (IOException e) {
			
		    System.err.println("Problema com a conexão");
		    // Imprimir janela de erro com código de status e encerrar o programa.
		    e.printStackTrace();
			
		} 
			
		catch (InterruptedException e) {
			
		    System.err.println("Requisição interrompida");
		    // Imprimir janela de erro com código de status e encerrar o programa.
		    e.printStackTrace();
			
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
		   
		   try 
		   {
				
			    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
				
			    int codStatus = resposta.statusCode();
			    
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
					
				        System.err.println("Resposta inválida");
				        // Imprimir janela de erro com código de status e encerrar o programa.
				        e.printStackTrace();
					
				    }
			    }
			    else
			    	System.out.println(codStatus);
			    // Imprimir janela de erro com código de status e encerrar o programa.
				
			} 
				
			catch (IOException e) {
				
			    System.err.println("Problema com a conexão");
			    // Imprimir janela de erro com código de status e encerrar o programa.
			    e.printStackTrace();
				
			} 
				
			catch (InterruptedException e) {
				
			    System.err.println("Requisição interrompida");
			    // Imprimir janela de erro com código de status e encerrar o programa.
			    e.printStackTrace();
				
			}
	   }
   }
}

