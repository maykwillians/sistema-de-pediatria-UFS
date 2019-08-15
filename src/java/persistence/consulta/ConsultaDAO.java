/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.consulta;

import beans.consulta.Consulta;
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
public class ConsultaDAO {
    
    private Connection con = Conexao.getConnection();
    
    public void cadastrar(Consulta consulta) {
        
        //Converter a data para o formato aceito no banco (SqlDate)
        java.sql.Date dataConsulta = new java.sql.Date(consulta.getData().getTime());
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO CONSULTA (nomemedico, nomecrianca, chavemedico, chavecrianca, data, peso, altura, imc, perimetrocefalico, comentarios, tipo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            
            preparador.setString(1, consulta.getNomeMedico());
            preparador.setString(2, consulta.getNomeCrianca());
            preparador.setString(3, consulta.getChaveMedico());
            preparador.setString(4, consulta.getChaveCrianca());
            preparador.setDate(5, dataConsulta);
            preparador.setInt(6, consulta.getPeso());
            preparador.setInt(7, consulta.getAltura());
            preparador.setDouble(8, consulta.getImc());
            preparador.setDouble(9, consulta.getPerimetroCefalico());
            preparador.setString(10, consulta.getComentarios());
            preparador.setString(11, consulta.getTipo());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("Consulta cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public List<Consulta> listarTodos() {

        List<Consulta> lista = new ArrayList<Consulta>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from consulta";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Consulta consulta = new Consulta();
                
                consulta.setNomeMedico(rs.getString(1));
                consulta.setNomeCrianca(rs.getString(2));
                consulta.setTipo(rs.getString(3));
                consulta.setChaveMedico(rs.getString(4));
                consulta.setChaveCrianca(rs.getString(5));
                consulta.setData(rs.getDate(6));
                consulta.setPeso(rs.getInt(7));
                consulta.setAltura(rs.getInt(8));
                consulta.setImc(rs.getDouble(9));
                consulta.setPerimetroCefalico(rs.getDouble(10));
                consulta.setComentarios(rs.getString(11));
                lista.add(consulta);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return lista;
    }
}