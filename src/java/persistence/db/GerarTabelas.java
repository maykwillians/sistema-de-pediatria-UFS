/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.db;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Mayk Willians
 */
@ManagedBean (name = "db")
public class GerarTabelas {
    
    public void criarTabelas() {
        
        new TabelaCrianca().criarTabela();
        new TabelaEstudante().criarTabela();
        new TabelaMae().criarTabela();
        new TabelaMedico().criarTabela();
        new TabelaConsulta().criarTabela();
        new TebelaAdministrador().criarTabela();
        new TabelaHistorico().criarTabela();
    }
}