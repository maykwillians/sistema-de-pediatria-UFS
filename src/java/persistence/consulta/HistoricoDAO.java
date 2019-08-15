/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.consulta;

import beans.consulta.Historico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class HistoricoDAO {

    private Connection con = Conexao.getConnection();

    public void cadastrar(Historico historico) {

        //Converter a data para o formato aceito no banco (SqlDate)
        java.sql.Date dataConsulta = new java.sql.Date(historico.getData().getTime());

        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO HISTORICO (nomemedico, nomecrianca, chavecrianca, data, peso, altura, imc, perimetrocefalico, comentarios, tipo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);

            preparador.setString(1, historico.getNomeMedico());
            preparador.setString(2, historico.getNomeCrianca());
            preparador.setString(3, historico.getChaveCrianca());
            preparador.setDate(4, dataConsulta);
            preparador.setInt(5, historico.getPeso());
            preparador.setInt(6, historico.getAltura());
            preparador.setDouble(7, historico.getImc());
            preparador.setDouble(8, historico.getPerimetrocefalico());
            preparador.setString(9, historico.getComentarios());
            preparador.setString(10, historico.getTipo());

            preparador.execute();
            preparador.close();

            System.out.println("Histórico cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }

    public List<Historico> listarPelaChave(String chaveCrianca) {

        List<Historico> lista = new ArrayList<Historico>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from historico where chavecrianca = '" + chaveCrianca + "'";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Historico historico = new Historico();

                historico.setNomeMedico(rs.getString(1));
                historico.setTipo(rs.getString(2));
                historico.setNomeCrianca(rs.getString(3));
                historico.setChaveCrianca(rs.getString(4));
                historico.setData(rs.getDate(5));
                historico.setPeso(rs.getInt(6));
                historico.setAltura(rs.getInt(7));
                historico.setImc(rs.getDouble(8));
                historico.setPerimetrocefalico(rs.getDouble(9));
                historico.setComentarios(rs.getString(10));

                lista.add(historico);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        return lista;
    }
    
    public List<Historico> listarTodos() {

        List<Historico> lista = new ArrayList<Historico>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from historico";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Historico historico = new Historico();
                
                historico.setNomeMedico(rs.getString(1));
                historico.setTipo(rs.getString(2));
                historico.setNomeCrianca(rs.getString(3));
                historico.setChaveCrianca(rs.getString(4));
                historico.setData(rs.getDate(5));
                historico.setPeso(rs.getInt(6));
                historico.setAltura(rs.getInt(7));
                historico.setImc(rs.getDouble(8));
                historico.setPerimetrocefalico(rs.getDouble(9));
                historico.setComentarios(rs.getString(10));
                lista.add(historico);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return lista;
    }
}