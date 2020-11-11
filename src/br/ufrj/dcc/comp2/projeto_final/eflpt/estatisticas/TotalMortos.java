package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;

/**
 * Esta classe gera os rankings referentes a Mortos(rip).
 * @author edrods
 *
 */
public class TotalMortos extends Estatistica {

	private Dados d = Dados.retornaInstancia();	
	private ArrayList<Medicao> numMortos = d.getMortos();
	private LocalDate dataFim;
	private LocalDate dataInicio;
	
	/**
	 * Este método gera o ranking internacional do número de mortos.
	 * @return ranking Uma String[] com o nome do pais e seu respectivo valor.
	 */
	public String[] rankingMortos() {
		
		
		StringBuilder sb = new StringBuilder();
		String pais = null;
		int casos = 0;
		
		
		String[] ranking = null;
		
		TreeMap<Integer,String> mortos = new TreeMap<>(Collections.reverseOrder());
		
		for(Medicao m : numMortos) {
			if(m.getMomento().toLocalDate().equals(dataFim)) {
				mortos.put(m.getCasos(), m.getPais().getNome());
			}
		}
		
		for(Map.Entry<Integer,String> entry : mortos.entrySet()) {
			pais = entry.getValue();
			casos = entry.getKey();
			sb.append(pais).append("!").append(casos).append(";");
		}
		
		ranking = sb.toString().split(";");
		
		return ranking;
		
	}
	
	/**
	 * Este método gera o ranking da taxa de crescimento do número de mortos.
	 * @return ranking Uma String[] com o nome do pais e seu respectivo valor.
	 */
	public String[] rankingMortosCrescimento() {
		int casosIniciais = 0;
		int casosFinais = 0;
		int taxaCrescimento =  0;
		String[] ranking = null;
		
		
		StringBuilder sb = new StringBuilder();
		
		TreeMap<String,Integer> mortosInicial = new TreeMap<>();
		TreeMap<String,Integer> mortosFinal = new TreeMap<>();
		
		TreeMap<Integer,String> rankingCrescimento = new TreeMap<>(Collections.reverseOrder());
		
		for(Medicao m : numMortos) {
			if(m.getMomento().toLocalDate().equals(dataFim)) {
				mortosFinal.put(m.getPais().getNome(), m.getCasos());
			}
			if(m.getMomento().toLocalDate().equals(dataInicio)) {
				mortosInicial.put(m.getPais().getNome(), m.getCasos());
			}
		}
		

		for(Map.Entry<String,Integer> entry : mortosInicial.entrySet()){
			
			for(Map.Entry<String, Integer> mep : mortosFinal.entrySet()){
				
				if(entry.getKey().equals(mep.getKey())) {
					casosIniciais = entry.getValue();
					casosFinais = mep.getValue();
					if(casosIniciais == 0 && casosFinais != 0) {
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
