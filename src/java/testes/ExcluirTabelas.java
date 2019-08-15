/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 *
 * @author Mayk Willians
 */
public class ExcluirTabelas {
    
    public static void main (String[] args) {
        
        new ExcluirTabelasTeste().excluirTabelaAdministrador();
        new ExcluirTabelasTeste().excluirTabelaMedico();
        new ExcluirTabelasTeste().excluirTabelaCrianca();
        new ExcluirTabelasTeste().excluirTabelaConsulta();
        new ExcluirTabelasTeste().excluirTabelaEstudante();
        new ExcluirTabelasTeste().excluirTabelaMae();
        new ExcluirTabelasTeste().excluirTabelaHistorico();
    }
}