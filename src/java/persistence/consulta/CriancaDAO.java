/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.consulta;

/**
 *
 * @author Mayk Willians
 */

import beans.consulta.Crianca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import persistence.conexao.Conexao;

public class CriancaDAO {
    
    private Connection con = Conexao.getConnection();
    
    public void cadastrar(Crianca crianca) throws ParseException {
        
        //java.sql.Date dataNascimento = new java.sql.Date(crianca.getDataNascimento().getTime());
        
        //Converte data String para tipo date aceito no banco
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date dataNascimento = new java.sql.Date(format.parse(crianca.getDataNascimento()).getTime());
        
        //Converter a data para o formato aceito no banco (SqlDate)
        //java.sql.Date dataNascimento = new java.sql.Date(crianca.getDataNascimento().getTime());
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO CRIANCA (nome, sexo, datanascimento, cartaosus, chavemedico) values (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            
            preparador.setString(1, crianca.getNome());
            preparador.setString(2, crianca.getSexo());
            preparador.setDate(3, dataNascimento);
            preparador.setString(4, crianca.getCartaoSUS());
            preparador.setString(5, crianca.getChaveMedico());
            
            preparador.execute();
            preparador.close();
            
            System.out.println("Criança cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public List<Crianca> listarTodos() {

        List<Crianca> lista = new ArrayList<Crianca>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from crianca";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Crianca crianca = new Crianca();
                
                crianca.setNome(rs.getString(1));
                crianca.setSexo(rs.getString(2));
                crianca.setDataNascimento(rs.getString(3));
                crianca.setCartaoSUS(rs.getString(4));
                crianca.setChaveMedico(rs.getString(5));
                lista.add(crianca);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return lista;
    }
    
    public List<Crianca> listarPelaChave(String chaveMedico) {

        List<Crianca> lista = new ArrayList<Crianca>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from crianca where chavemedico = '"+chaveMedico+"'";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Crianca crianca = new Crianca();
                
                crianca.setNome(rs.getString(1));
                crianca.setSexo(rs.getString(2));
                crianca.setDataNascimento(rs.getString(3));
                crianca.setCartaoSUS(rs.getString(4));
                crianca.setChaveMedico(rs.getString(5));
                lista.add(crianca);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return lista;
    }
    
    public void alterar(String cpf) {
        
        
    }
    
    public void excluir(String cpf) {
        
        
    }
}