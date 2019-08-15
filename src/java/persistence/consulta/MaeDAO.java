/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.consulta;

import beans.consulta.Mae;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class MaeDAO {
    
    private Connection con = Conexao.getConnection();
    
    public void cadastrar(Mae mae) {
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO MAE (nome, cpf, telefone, chavemedico, chavecrianca) values (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            
            preparador.setString(1, mae.getNome());
            preparador.setString(2, mae.getCpf());
            preparador.setString(3, mae.getTelefone());
            preparador.setString(4, mae.getChaveMedico());
            preparador.setString(5, mae.getChaveCrianca());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("Mãe cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void alterar(String cpf) {
        
        
    }
    
    public void excluir(String cpf) {
        
        
    }
}