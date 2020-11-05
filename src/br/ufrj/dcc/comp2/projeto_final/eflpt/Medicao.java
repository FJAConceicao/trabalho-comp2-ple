package br.ufrj.dcc.comp2.projeto_final.eflpt;

import java.time.LocalDateTime;

public class Medicao
{
	private Pais pais;
    private LocalDateTime momento;
    private int casos;
    private StatusCaso status;

    public Medicao(Pais pais, LocalDateTime momento, int casos, StatusCaso status)
    {
       this.pais = new Pais(pais.getNome(), pais.getCodigo(), pais.getSlug(), pais.getLatitude(), pais.getLongitude());
       this.momento = momento;
       this.casos = casos;
       this.status = status;
    }
    
    public Medicao(Medicao m)
    {
    	this.pais = new Pais(m.getPais());
    	this.momento = m.getMomento();
        this.casos = m.getCasos();
        this.status = m.getStatus();
    	
    }

    public Pais getPais()
    {
    	return new Pais(pais.getNome(), pais.getCodigo(), pais.getSlug(), pais.getLatitude(), pais.getLongitude());
    }

	public LocalDateTime getMomento()
	{
		return momento;		
	}
	
	public int getCasos()
	{
		return casos;
	}
	
	public StatusCaso getStatus()
	{
		return status;
	}
	

}
