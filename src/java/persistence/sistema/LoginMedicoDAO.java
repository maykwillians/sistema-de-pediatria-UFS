/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.sistema;

import beans.consulta.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class LoginMedicoDAO {

    private Connection con = Conexao.getConnection();

    private Medico medico = new Medico();

    /**
     * @return the medico
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Medico verifica(String login, String senha) {

        //Para Logar Como Medico pelo email e senha
        if (login.contains("@")) {

            String sqlMedico = "select * from medico where email = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlMedico);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {

                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlMedico2 = "select nome, cpf, email, login, senha, crm, uf, tipo from medico where email = '" + login + "'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlMedico2);
                    ResultSet rs2 = preparador2.executeQuery();

                    while (rs2.next()) {

                        getMedico().setNome(rs2.getString("nome"));
                        getMedico().setCpf(rs2.getString("cpf"));
                        getMedico().setEmail(rs2.getString("email"));
                        getMedico().setLogin(rs2.getString("login"));
                        getMedico().setSenha(rs2.getString("senha"));
                        getMedico().setCrm(rs2.getString("crm"));
                        getMedico().setUf(rs2.getString("uf"));
                        getMedico().setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();

                    return getMedico();
                }

                preparador.close();
                rs.close();

            } catch (SQLException e) {
                System.out.println("Erro - " + e.getMessage());
            }
            return null;
        }

        //Para Logar Como Medico pelo usuario e senha
        else {

            String sqlMedico = "select * from medico where login = ? and senha = ? ";

            try {
                PreparedStatement preparador = this.con.prepareStatement(sqlMedico);

                preparador.setString(1, login);
                preparador.setString(2, senha);

                ResultSet rs = preparador.executeQuery();

                if (rs.next()) {

                    //Faz o acesso ao DB e carrega os dados do usuário
                    String sqlMedico2 = "select nome, cpf, email, login, senha, crm, uf, tipo from medico where login = '" + login + "'";
                    PreparedStatement preparador2 = this.con.prepareStatement(sqlMedico2);
                    ResultSet rs2 = preparador2.executeQuery();

                    while (rs2.next()) {

                        getMedico().setNome(rs2.getString("nome"));
                        getMedico().setCpf(rs2.getString("cpf"));
                        getMedico().setEmail(rs2.getString("email"));
                        getMedico().setLogin(rs2.getString("login"));
                        getMedico().setSenha(rs2.getString("senha"));
                        getMedico().setCrm(rs2.getString("crm"));
                        getMedico().setUf(rs2.getString("uf"));
                        getMedico().setTipo(rs2.getString("tipo"));
                    }
                    
                    preparador2.close();
                    rs2.close();

                    return getMedico();
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