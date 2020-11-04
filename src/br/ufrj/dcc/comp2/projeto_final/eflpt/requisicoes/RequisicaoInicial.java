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
import java.util.Iterator;

public class RequisicaoInicial
{
   public void requisitarPaises(Dados d)
   {
	   HttpClient cliente = HttpClient.newBuilder()
               .version(Version.HTTP_2)
               .followRedirects(Redirect.ALWAYS)
               .build();



	   HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.covid19api.com/countries"))
                    .build();
	   
	   try 
	   {
			
		    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
			
		    int codStatus = resposta.statusCode();
		    
		    if (codStatus >= 200 || codStatus < 300)
		    {
			    try 
			    {
			        JSONArray paises = (JSONArray) new JSONParser().parse(resposta.body());
			        
			        for (Object pais : paises) 
			        {				
			            String nomePais = (String)	((JSONObject) pais).get("Country");
			            String slugPais = (String)	((JSONObject) pais).get("Slug");
			            d.getPaises().add(new Pais(nomePais, slugPais));	
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
   
   public void requisitarConfirmados(Dados d)
   {
	   Iterator<Pais> i = d.getPaises().iterator();     
	   StatusCaso confirmado = StatusCaso.CONFIRMADOS;
	   realizaOperacoesTipo("confirmed", i, d, confirmado);
   }
   
   public void requisitarMortes(Dados d)
   {
	   Iterator<Pais> i = d.getPaises().iterator();
	   StatusCaso mortes = StatusCaso.MORTOS;
	   realizaOperacoesTipo("deaths", i, d, mortes);
   }
   
   public void requisitarRecuperados(Dados d)
   {
	   Iterator<Pais> i = d.getPaises().iterator();
	   StatusCaso recuperados = StatusCaso.RECUPERADOS;
	   realizaOperacoesTipo("recovered", i, d, recuperados);
   }
   
   public void realizaOperacoesTipo(String tipo, Iterator<Pais> i, Dados d, StatusCaso status)
   {
	   Pais paisAtual;
	   
	   while (i.hasNext())
	   {
		   paisAtual = i.next();
		   
		   
		   HttpClient cliente = HttpClient.newBuilder()
	               .version(Version.HTTP_2)
	               .followRedirects(Redirect.ALWAYS)
	               .build();
	
		   HttpRequest requisicao = HttpRequest.newBuilder()
	                    .uri(URI.create("https://api.covid19api.com/total/dayone/country/" + paisAtual.getSlug() + "/status/" + tipo))
	                    .build();
		   
		   try 
		   {
				
			    HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
				
			    int codStatus = resposta.statusCode();
			    
			    if (codStatus >= 200 || codStatus < 300)
			    {
				    try 
				    {
				        JSONArray casosPais = (JSONArray) new JSONParser().parse(resposta.body());
				        
				        Iterator<JSONArray> iterador = casosPais.iterator();
				        
				        if (iterador.hasNext())
				        {
				        
					        Object linha = iterador.next();
					        
					        String codigo = (String)	((JSONObject) linha).get("Country Code");
					        float latitude = Float.parseFloat((String)	((JSONObject) linha).get("Lat"));
					        float longitude = Float.parseFloat((String)	((JSONObject) linha).get("Lon"));
					        LocalDateTime momento = LocalDateTime.parse(((String) ((JSONObject) linha).get("Date")).replace("Z", ""));
					        long casos = Long.parseLong(String.valueOf(( ((JSONObject) linha).get("Cases"))));
					        
					        paisAtual.setaInfo(codigo, latitude, longitude);			        
					        
					        d.getConfirmados().add(new Medicao(paisAtual, momento, (int) casos, status));
					        
					        while (iterador.hasNext())	
					        {
					        	linha = iterador.next();
					        	momento = LocalDateTime.parse(((String) ((JSONObject) linha).get("Date")).replace("Z", ""));
					        	casos = Long.parseLong(String.valueOf(( ((JSONObject) linha).get("Cases"))));
					        	d.getConfirmados().add(new Medicao(paisAtual, momento, (int) casos, status));
					        	System.out.println(paisAtual.getNome() + "\t" + casos);
					        }
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
}

