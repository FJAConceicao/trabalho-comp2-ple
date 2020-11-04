package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;

import com.google.gson.Gson; 
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;

import java.util.TreeMap;

public class RequisicaoInicial extends Requisicao{
    TreeMap<String, Pais> listaPaises;

    public RequisicaoInicial() {
        super("https://api.covid19api.com/countries");

        listaPaises = new TreeMap<>();
        //String[] linhas = lejson;
        //foreach linha
        String nome = "";
        String slug = "";
        listaPaises.put(nome, new Pais(nome, slug));

    }
}

