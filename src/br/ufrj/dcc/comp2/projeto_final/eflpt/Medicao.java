package br.ufrj.dcc.comp2.projeto_final.eflpt;

import java.time.LocalDateTime;

/**
 * Essa classe representa a entidade de medição, que guarda informações de casos de COVID 19
 * em um país, com data, número de casos e o status específico.
 * 
 * @author Thiago Castro
 */

public class Medicao
{
	private Pais pais;
    private LocalDateTime momento;
    private int casos;
    private StatusCaso status;
    
    /**
     * Construtor de medição com base no diagrama UML
     * @param pais	o país da medição	
     * @param momento	a data da medição
     * @param casos	o número de casos
     * @param status	o tipo de caso
     */

    public Medicao(Pais pais, LocalDateTime momento, int casos, StatusCaso status)
    {
       this.pais = new Pais(pais.getNome(), pais.getCodigo(), pais.getSlug(), pais.getLatitude(), pais.getLongitude());
       this.momento = momento;
       this.casos = casos;
       this.status = status;
    }
    
    /**
     * Construtor de medição para cópia de outra medição
     * @param m	a medição a ser copiada
     */
    
    public Medicao(Medicao m)
    {
    	this.pais = new Pais(m.getPais());
    	this.momento = m.getMomento();
        this.casos = m.getCasos();
        this.status = m.getStatus();
    	
    }
    
    /**
     * Retorna o país desta medição
     * @return o país desta medição
     */

    public Pais getPais()
    {
    	return new Pais(pais.getNome(), pais.getCodigo(), pais.getSlug(), pais.getLatitude(), pais.getLongitude());
    }
    
    /**
     * Retorna a data desta medição
     * @return a data desta medição
     */

	public LocalDateTime getMomento()
	{
		return momento;		
	}
	
	/**
	 * Retorna o número de casos desta medição
	 * @return o número de casos desta medição
	 */
	
	public int getCasos()
	{
		return casos;
	}
	
	/**
	 * Retorna o tipo de status desta medição
	 * @return o status da medição
	 */
	
	public StatusCaso getStatus()
	{
		return status;
	}
	

}
