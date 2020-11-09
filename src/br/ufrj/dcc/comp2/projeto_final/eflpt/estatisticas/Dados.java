package br.ufrj.dcc.comp2.projeto_final.eflpt.estatisticas;

import java.util.ArrayList;

import br.ufrj.dcc.comp2.projeto_final.eflpt.Medicao;
import br.ufrj.dcc.comp2.projeto_final.eflpt.Pais;

/**
 * Essa classe implementa a central de dados para o cache volátil do programa.
 * Ela é baseada no padrão Singleton.
 * @author Thiago Castro
 */

public class Dados
{
        private static Dados dados;

        private Dados()
        {

        }
        
        /**
         * Retorna a instância única se ela existir. Senão, cria uma nova
         * @return a instância de dados
         */

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
        
        /**
         * Retorna o ArrayList de países
         * @return a lista de países no programa
         */

        public ArrayList<Pais> getPaises()
        {
                return paises;
        }
        
        /**
         * Retorna o ArrayList de confirmados
         * @return a lista de medições de casos confirmados no programa
         */


        public ArrayList<Medicao> getConfirmados()
        {
                return confirmados;
        }
        
        /**
         * Retorna o ArrayList de recuperados
         * @return a lista de medições de casos recuperados no programa
         */


        public ArrayList<Medicao> getRecuperados()
        {
                return recuperados;
        }
        
        /**
         * Retorna o ArrayList de mortos
         * @return a lista de medições de mortos no programa
         */

        public ArrayList<Medicao> getMortos()
        {
                return mortos;
        }

}
