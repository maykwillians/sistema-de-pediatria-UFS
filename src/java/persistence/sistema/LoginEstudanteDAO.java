/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.sistema;

import beans.consulta.Estudante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class LoginEstudanteDAO {
    
    private Connection con = Conexao.getConnection();
    
    public Estudante verifica(String login, String senha) {
        
        Estudante estudante = new Estudante();
        
        //Para Logar Como Estudante pelo email e senha
        if (login.contains("@")) {
            
            String sqlAluno = "select * from estudante where email = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlAluno);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {

                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlEstudante2 = "select nome, email, login, senha, matricula, tipo from estudante where email = '"+login+"'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlEstudante2);
                    ResultSet rs2 = preparador2.executeQuery();
                    
                    while (rs2.next()) {
                        
                        estudante.setNome(rs2.getString("nome"));
                        estudante.setEmail(rs2.getString("email"));
                        estudante.setLogin(rs2.getString("login"));
                        estudante.setSenha(rs2.getString("senha"));
                        estudante.setMatricula(rs2.getString("matricula"));
                        estudante.setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();
                    
                    return estudante;
                }

                preparador.close();
                rs.close();

            } catch (SQLException e) {
                System.out.println("Erro - " + e.getMessage());
            }
            return null;
        }
        
        //Para Logar Como Estudante pelo usuario e senha
        else {
            
            String sqlAluno = "select * from estudante where login = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlAluno);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {

                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlEstudante2 = "select nome, email, login, senha, matricula, tipo from estudante where login = '"+login+"'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlEstudante2);
                    ResultSet rs2 = preparador2.executeQuery();
                    
                    while (rs2.next()) {
                        
                        estudante.setNome(rs2.getString("nome"));
                        estudante.setEmail(rs2.getString("email"));
                        estudante.setLogin(rs2.getString("login"));
                        estudante.setSenha(rs2.getString("senha"));
                        estudante.setMatricula(rs2.getString("matricula"));
                        estudante.setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();
                    
                    return estudante;
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