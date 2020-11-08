package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import java.util.ArrayList;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;

public class Dados
{
        private static Dados dados;

        private Dados()
        {

        }

        public static Dados retornaInstancia()
        {
                if (dados == null)
                        dados = new Dados();
                return dados;
        }

        private ArrayList<Pais> paises = new ArrayList<Pais>();
        private ArrayList<Medicao> confirmados = new ArrayList<Medicao>();
        private ArrayList<Medicao> recuperados = new ArrayList<Medicao>();
        private ArrayList<Medicao> mortos = new ArrayList<Medicao>();

        public ArrayList<Pais> getPaises()
        {
                return paises;
        }


        public ArrayList<Medicao> getConfirmados()
        {
                return confirmados;
        }


        public ArrayList<Medicao> getRecuperados()
        {
                return recuperados;
        }


        public ArrayList<Medicao> getMortos()
        {
                return mortos;
        }

}
