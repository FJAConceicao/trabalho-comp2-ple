package br.ufrj.dcc.comp2.projeto_final.eflpt.requisicoes;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;

public class Requisicao {
    protected HttpClient cliente;
    protected HttpRequest requisicao;

    public Requisicao(String URL)
    {
        cliente = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        requisicao = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
    }
}
