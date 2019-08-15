/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dados;

import beans.sistema.Administrador;
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
public class AdministradorDAO {
 
    private Connection con = Conexao.getConnection();
    
    public void alterarADM(Administrador adm) {
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sqlADM = "INSERT INTO ADMINISTRADOR (nome, email, login, senha, tipo) values (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sqlADM);
            
            preparador.setString(1, adm.getNome());
            preparador.setString(2, adm.getEmail());
            preparador.setString(3, adm.getLogin());
            preparador.setString(4, adm.getSenha());
            preparador.setString(5, "1"); // Adm tem tipo 1
            
            preparador.execute();
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void gerarADMdefault() {
        
        //Criptografa senha
        //String senhaCriptografada = criptografaSenha("admin");
        
        String sqlADM = "select nome from administrador";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sqlADM);
            
            ResultSet rs = preparador.executeQuery();
            
            if (!rs.next()) {
                
                Administrador adm = new Administrador();

                adm.setNome("Adicinéia Aparecida de Oliveira");
                adm.setEmail("adicineia@gmail.com");
                adm.setLogin("adicineia");
                adm.setSenha("admin");
                adm.setTipo("1");

                alterarADM(adm);
                
                System.out.println("UM NOVO ADMINISTRADOR FOI CADASTRADO NO SISTEMA: " + adm.getNome());
                
                System.out.println();
                System.out.println();
                System.out.println();
            }
            
            preparador.close();
            
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public List<Administrador> listarTodos() {

        List<Administrador> lista = new ArrayList<Administrador>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from administrador";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Administrador adm = new Administrador();
                
                adm.setNome(rs.getString(1));
                adm.setEmail(rs.getString(2));
                adm.setLogin(rs.getString(3));
                adm.setSenha(rs.getString(4));
                adm.setTipo(rs.getString(5));
                lista.add(adm);
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
    public String criptografaSenha(String senhaOriginal) {

        String senhaCriptografada = null;
        MessageDigest algoritmo;
        byte messageDigest[];
        StringBuilder hexString;
        try {
            algoritmo = MessageDigest.getInstance("SHA-256");// 64 letras
            //algoritmo = MessageDigest.getInstance("MD5");  // 32 letras
            messageDigest = algoritmo.digest(senhaOriginal.getBytes("UTF-8"));
            hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            senhaCriptografada = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return senhaCriptografada;
    }
    **/
}