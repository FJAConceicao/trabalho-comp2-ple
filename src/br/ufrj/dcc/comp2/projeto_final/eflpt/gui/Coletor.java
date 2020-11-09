package br.ufrj.dcc.comp2.projeto_final.eflpt.gui;

import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Coletor
{
	public static boolean verificaDatas(LocalDate data1, LocalDate data2, JTextField campo)
	{
		if (data1.isAfter(data2))
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												 "A primeira data vem depois da segunda data.",
												 "Erro ao receber data");
			
			return false;
		}
		
		if (data1.isAfter(LocalDate.now()) || data2.isAfter(LocalDate.now()))
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
					 							 "Uma das datas está no futuro.",
					 							 "Erro ao receber data");
			
			return false;
		}
		
		if (data1.isBefore(LocalDate.of(2019, 11, 17)))
		{
			MensagensDeErro.mostraMensagemDeErro(campo.getParent(),
												 "A primeira data vem antes do primeiro caso de COVID-19 no mundo",
												 "Erro ao receber data");

			return false;
		}
		
		return true;
	}
	
	public static void converteData(JTextField primeiraData, JTextField segundaData, JDialog janela)
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
			// Chamar o método para criar o ranking.
		}
	}
	
	public static void converteData(JTextField primeiraData, JTextField segundaData, JDialog janela, int raio)
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
			// Chamar o método para criar o ranking dos locais mais próximos.
		}
	}
	
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

	public static void recebeLocalArquivo()
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
				arquivo = new File(arquivo.getAbsoluteFile().toString());
				// Chamar método para salvar os rankings
			}
			else if (arquivo.getName().toLowerCase().endsWith(".tsv"))
			{
				arquivo = new File(arquivo.getAbsoluteFile().toString());
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
}