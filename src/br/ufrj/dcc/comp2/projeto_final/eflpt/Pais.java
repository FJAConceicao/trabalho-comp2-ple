package br.ufrj.dcc.comp2.projeto_final.eflpt;

public class Pais 
{
	private String nome;
	private String codigo;
	private String slug;
	private float latitude;
	private float longitude;
	
	public Pais(String nome, String codigo, String slug, float latitude, float longitude)
	{
		this.nome = nome;
		this.codigo = codigo;
		this.slug = slug;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Pais()
	{
		
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	public String getSlug()
	{
		return slug;
	}
	
	public float getLatitude()
	{
		return latitude;
	}
	
	public float getLongitude()
	{
		return longitude;
	}

}
