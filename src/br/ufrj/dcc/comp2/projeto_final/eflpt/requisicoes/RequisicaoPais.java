package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;



public class RequisicaoPais{
    protected String URL;
    public RequisicaoPais(String slug) {
        URL = "https://api.covid19api.com/dayone/country/"+slug+"/status/";
//        recebeConfirmados(URL+"confirmed");
//        recebeMortes(URL+"deaths");
//        recebeRecuperados(URL+"recovered");
    }

}

