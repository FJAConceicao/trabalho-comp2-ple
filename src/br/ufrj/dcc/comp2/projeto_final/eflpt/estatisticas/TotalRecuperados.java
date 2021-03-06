package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;

/**
 * Esta classe gera os rankings referentes a Recuperados.
 * @author edrods
 *
 */
public class TotalRecuperados extends Estatistica{
	
	
	private Dados d = Dados.retornaInstancia();	
	private ArrayList<Medicao> numRecuperados = d.getRecuperados();
	private LocalDate dataFim;
	private LocalDate dataInicio;
	
	/**
	 * Este método gera o ranking internacional no número de recuperados
	 * @return ranking Uma String[] com o nome do pais e seu respectivo valor.
	 */
	public String[] rankingRecuperados() {
		
		StringBuilder sb = new StringBuilder();
		String pais = null;
		int casos = 0;
		String[] ranking = null;
		
		TreeMap<Integer,String> rankingRecuperados = new TreeMap<>(Collections.reverseOrder());
		
		for(Medicao m : numRecuperados) {
			if(m.getMomento().toLocalDate().equals(dataFim)) {
				rankingRecuperados.put(m.getCasos(), m.getPais().getNome());
			}
		}
		
		for(Map.Entry<Integer,String> entry : rankingRecuperados.entrySet()) {
			pais = entry.getValue();
			casos = entry.getKey();
			sb.append(pais).append("!").append(casos).append(";");
		}
		ranking = sb.toString().split(";");
		
		return ranking;
	}
	
	/**
	 * Este método gera o ranking da taxa de crescimento do número de recuperados.
	 * @return ranking Uma String[] com o nome do pais e seu respectivo valor.
	 */
	public String[] rankingRecuperadosCrescimento() {
		
		int casosIniciais = 0;
		int casosFinais = 0;
		int taxaCrescimento =  0;
		String[] ranking = null;

		StringBuilder sb = new StringBuilder();
		
		TreeMap<String,Integer> recuperadosInicial = new TreeMap<>();
		TreeMap<String,Integer> recuperadosFinal = new TreeMap<>();
		
		TreeMap<Integer,String> rankingCrescimento = new TreeMap<>(Collections.reverseOrder());
		
		for(Medicao m : numRecuperados) {
			if(m.getMomento().toLocalDate().equals(dataFim)) {
				recuperadosFinal.put(m.getPais().getNome(), m.getCasos());
			}
			if(m.getMomento().toLocalDate().equals(dataInicio)) {
				recuperadosInicial.put(m.getPais().getNome(), m.getCasos());
			}
		}
		
		
		
		for(Map.Entry<String,Integer> entry : recuperadosInicial.entrySet()){
			
			for(Map.Entry<String, Integer> mep : recuperadosFinal.entrySet()){
				
				if(entry.getKey().equals(mep.getKey())) {
					casosIniciais = entry.getValue();
					casosFinais = mep.getValue();
					if(casosIniciais == 0 && casosFinais != 0){
						taxaCrescimento = casosFinais;
					}
					else if(casosFinais == 0) {
						taxaCrescimento = 0;
					}
					else{
						taxaCrescimento = ((casosFinais - casosIniciais)/casosIniciais)*100;
					}
					rankingCrescimento.put(taxaCrescimento, entry.getKey());
					}
				}
			
			}
		
		for(Map.Entry<Integer,String> entry : rankingCrescimento.entrySet()) {
			sb.append(entry.getKey()).append("%").append("!").append(entry.getValue()).append(";");
		}
		ranking = sb.toString().split(";");
		
		return ranking;
	}
	
	
	/**
	 * Este método fornece o valor da dataInicio escolhida pelo usuário.
	 * @return dataInicio Data fornecida pelo usuário
	 */
	@Override
	public LocalDate dataInicio() {
		
		return dataInicio;
	}
	
	/**
	 * Este método fornece o valor da dataFim escolhida pelo usuário.
	 * @return dataFim Data fornecida pelo usuário
	 */
	@Override
	public LocalDate dataFim() {
		
		return dataFim;
	}
	
	/**
	 * Este método fornece o valor.
	 * @return valor Variável valor 
	 */
	@Override
	public float valor() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Este método atualiza a dataFim.
	 * 
	 */
	public void setDataFim(LocalDate fim) {
		dataFim = fim;
	}
	
	/**
	 * Este método atualiza a dataInicio.
	 * 
	 */
	public void setDataInicio(LocalDate inicio) {
	 dataInicio = inicio;
	}
}
