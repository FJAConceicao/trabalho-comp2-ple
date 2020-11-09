package br.ufrj.dcc.comp2.projeto_final.eflpt;

/**
 * Essa classe representa a entidade país, que guarda informações de um país que sofre medição
 *
 * @author Thiago Castro
 *
 */

public class Pais 
{
	private String nome;
	private String codigo;
	private String slug;
	private float latitude;
	private float longitude;
	
	/**
	 * Construtor baseado no diagrama UML
	 * @param nome o nome do país
	 * @param codigo o código na API do COVID 19
	 * @param slug o slug do país na API do COVID 19
	 * @param latitude a latitude do país
	 * @param longitude a latitude do país
	 */
	
	public Pais(String nome, String codigo, String slug, float latitude, float longitude)
	{
		this.nome = nome;
		this.codigo = codigo;
		this.slug = slug;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Construtor de país usado para copiar outra instância de país
	 * @param pais o país usado para cópia
	 */
	
	public Pais(Pais pais)
	{
		this.nome = pais.getNome();
		this.codigo = pais.getCodigo();
		this.slug = pais.getSlug();
		this.latitude = pais.getLatitude();
		this.longitude = pais.getLongitude();
	}
	
	/**
	 * Construtor de país que recebe nome e slug para solicitação posterior na API
	 * @param nome o nome do país
	 * @param slug o slug do país na API
	 */
	public Pais(String nome,String slug)
	{
		this.nome = nome;
		this.slug = slug;
	}
	
	/**
	 * Retorna o nome do país desta instância
	 * @return o nome do país desta instância
	 */
	
	public String getNome()
	{
		return nome;
	}
	
	/**
	 * Retorna o código desse país na API
	 * @return o código do país
	 */
	
	public String getCodigo()
	{
		return codigo;
	}
	
	/**
	 * Retorna o slug desse país na API
	 * @return o slug desse país
	 */
	
	public String getSlug()
	{
		return slug;
	}
	
	/**
	 * Retorna a latitude desse país
	 * @return a latitude do país
	 */
	
	public float getLatitude()
	{
		return latitude;
	}
	
	/**
	 * Retorna a longitude desse país
	 * @return a longitude desse país
	 */
	
	public float getLongitude()
	{
		return longitude;
	}
	
	/**
	 * Configura esta instância ser complementada com código do país na API, latitude e longitude
	 * @param codigo o código do país na API
	 * @param latitude a latitude do país
	 * @param longitude a longitude do país
	 */

	public void setaInfo(String codigo, float latitude, float longitude)
	{
		this.codigo = codigo;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
