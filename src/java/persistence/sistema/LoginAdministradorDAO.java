/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.sistema;

import beans.sistema.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class LoginAdministradorDAO {
    
    private Connection con = Conexao.getConnection();
    
    public Administrador verifica (String login, String senha) {
        
        Administrador adm = new Administrador();
        
        //Para Logar Como Administrador pelo email e senha
        if (login.contains("@")) {
            
            String sqlADM = "select * from administrador where email = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlADM);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {
                    
                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlADM2 = "select nome, email, login, senha, tipo from administrador where email = '"+login+"'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlADM2);
                    ResultSet rs2 = preparador2.executeQuery();
                    
                    while (rs2.next()) {
                        
                        adm.setNome(rs2.getString("nome"));
                        adm.setEmail(rs2.getString("email"));
                        adm.setLogin(rs2.getString("login"));
                        adm.setSenha(rs2.getString("senha"));
                        adm.setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();
                    return adm;
                }

                preparador.close();
                rs.close();

            } catch (SQLException e) {
                System.out.println("Erro - " + e.getMessage());
            }
            return null;
        }
        
        //Para Logar Como Administrador pelo usuario e senha
        else {
            
            String sqlADM = "select * from administrador where login = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlADM);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {

                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlADM2 = "select nome, email, login, senha, tipo from administrador where login = '"+login+"'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlADM2);
                    ResultSet rs2 = preparador2.executeQuery();
                    
                    while (rs2.next()) {
                        
                        adm.setNome(rs2.getString("nome"));
                        adm.setEmail(rs2.getString("email"));
                        adm.setLogin(rs2.getString("login"));
                        adm.setSenha(rs2.getString("senha"));
                        adm.setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();
                    
                    return adm;
                }

                preparador.close();
                rs.close();

            } catch (SQLException e) {
                System.out.println("Erro - " + e.getMessage());
            }
            return null;
        }
    }
}