package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Esta classe exporta os rankings gerados para CSV e TSV.
 * @author edrods
 *
 */
public class Exportadora {
		
	
	
	
		/**
		 * Este método gera o arquivo CSV para taxa de letalidade e locais mais próximos.
		 * @param arquivo Caminho do arquivo
		 * @param ranking Valores a serem adicionados 
		 */
		public static void geraArquivoCSV(File arquivo, String[] ranking) {
			try{
				File arq = arquivo;
				FileOutputStream saida = new FileOutputStream(arq);
				PrintStream ps = new PrintStream(saida);
			
				ps.print("Nº" + "," + "Valor" + "," + "País" + "\n");
				int posicao = 1;
				
				for(String i : ranking) {
					String[]valores =  i.split("!");
					ps.print(posicao+"º,"+ valores[0]+","+ valores[1]+"\n");
					posicao ++;
				}
				
				
				
				
			} catch(IOException e) {
				
				System.out.println("Erro ao gerar a consulta em CSV");
			}
			
		}
		
		/**
		 * Este método gera o arquivo CSV para os casos de número de casos, taxa de crescimento e mortos.
		 * @param arquivo Caminho do arquivo.
		 * @param ranking Casos Ranking de casos
		 * @param ranking Recuperados Ranking de recuperados
		 * @param ranking Mortos Ranking de mortos
		 */
		public static void geraArquivoCSV(File arquivo, String[] rankingCasos,String[] rankingRecuperados,String[] rankingMortos ) {
			try{
				File arq = arquivo;
				FileOutputStream saida = new FileOutputStream(arq);
				PrintStream ps = new PrintStream(saida);
			
				ps.print("Nº" + "," + "Valor" + "," + "País");
				int posicao = 1;
				
				for(String i : rankingCasos) {
					String[]valores =  i.split("!");
					ps.print(posicao+"º,"+ valores[0]+","+ valores[1]+"\n");
					posicao ++;
				}
				
				
			} catch(IOException e) {
				
				System.out.println("Erro ao gerar a consulta em CSV");
			}
			
		}
		/**
		 * Este método gera o arquivo TSV para taxa de letalidade e locais mais próximos.
		 * @param arquivo O caminho do arquivo.
		 * @param ranking O ranking utilizado para escrever o arquivo.
		 */
		public static void geraArquivoTSV(File arquivo, String[] ranking) {
			
			try{
				File arq = arquivo;
				FileOutputStream saida = new FileOutputStream(arq);
				PrintStream ps = new PrintStream(saida);
			
				ps.print("Nº" + "\t" + "Valor" + "\t" + "País");
				int posicao = 1;
				
				for(String i : ranking) {
					String[]valores =  i.split("!");
					ps.print(posicao+"º\t"+ valores[0]+"\t"+ valores[1]+"\n");
					posicao ++;
				}
				
			
			}catch(IOException e) {
				
				System.out.println("Erro ao gerar a consulta em TSV");
			}
			
		}
		
		/**
		 * Este método gera os arquivos TSV para os casos de número de casos, taxa de crescimento e mortos.
		 * @param arquivo Caminho do arquivo.
		 * @param rankingCasos Casos Ranking de casos
		 * @param rankingRecuperados Ranking de recuperados
		 * @param rankingMortos Ranking de mortos
		 */
		public static void geraArquivoTSV(File arquivo, String[] rankingCasos,String[] rankingRecuperados,String[] rankingMortos ) {
			
			try{
				File arq = arquivo;
				FileOutputStream saida = new FileOutputStream(arq);
				PrintStream ps = new PrintStream(saida);
			
				ps.print("Nº" + "\t" + "Valor" + "\t" + "País");
				int posicao = 1;
				
				for(String i : rankingCasos) {
					String[]valores =  i.split("!");
					ps.print(posicao+"º\t"+ valores[0]+"\t"+ valores[1]+"\n");
					posicao ++;
				}
				
			
			}catch(IOException e) {
				
				System.out.println("Erro ao gerar a consulta em TSV");
			}
			
		}
}
