/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class ExcluirTabelasTeste {
    
    private Connection con = Conexao.getConnection();
    
    public void excluirTabelaAdministrador() {
        
        String sql = "DROP TABLE ADMINISTRADOR";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaMedico() {
        
        String sql = "DROP TABLE MEDICO";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaCrianca() {
        
        String sql = "DROP TABLE CRIANCA";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaConsulta() {
        
        String sql = "DROP TABLE CONSULTA";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaEstudante() {
        
        String sql = "DROP TABLE ESTUDANTE";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaHistorico() {
        
        String sql = "DROP TABLE HISTORICO";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void excluirTabelaMae() {
        
        String sql = "DROP TABLE MAE";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
}