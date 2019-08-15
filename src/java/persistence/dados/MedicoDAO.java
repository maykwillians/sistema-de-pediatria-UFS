/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dados;

import beans.consulta.Consulta;
import persistence.conexao.Conexao;
import beans.consulta.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.consulta.ConsultaDAO;

/**
 *
 * @author Mayk Willians
 */
public class MedicoDAO {
    
    private Connection con = Conexao.getConnection();
    
    public boolean verificaSeExiste (String cpf) {
        
        String sql = "select * from MEDICO where cpf = ?";

        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);

            preparador.setString(1, cpf);

            ResultSet rs = preparador.executeQuery();

            if (rs.next()) {

                preparador.close();
                rs.close();
                return true;
            }
            else {
                
                preparador.close();
                rs.close();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
        
        return false;
    }
    
    public void cadastrar(Medico medico) {
        
        //Criptografa senha
        //String senhaCriptografada = criptografaSenha(medico.getSenha());
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO MEDICO (nome, cpf, crm, uf, email, login, senha, tipo) values (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            
            preparador.setString(1, medico.getNome());
            preparador.setString(2, medico.getCpf());
            preparador.setString(3, medico.getCrm());
            preparador.setString(4, medico.getUf());
            preparador.setString(5, medico.getEmail());
            preparador.setString(6, medico.getLogin());
            preparador.setString(7, medico.getSenha());
            preparador.setString(8, "2");
            
            preparador.execute();
            preparador.close();
            
            System.out.println("Médico cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void alterar(String cpf) {
        
        
    }
    
    public boolean verifica(String cpf) {
        
        boolean ativo = false;
        
        ConsultaDAO consultaDAO = new ConsultaDAO();
        List<Consulta> lista = consultaDAO.listarTodos();
        
        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getChaveMedico().equals(cpf)) {
                
                ativo = true;
            }
        }
        
        return ativo;
    }
    
    public void excluir(String cpf) {

        String SQL = "DELETE FROM medico WHERE cpf = '" + cpf + "'";

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            System.out.println("Médico excluído com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Medico> listarTodos() {

        List<Medico> lista = new ArrayList<Medico>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from medico";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Medico medico = new Medico();
                
                medico.setNome(rs.getString(1));
                medico.setCpf(rs.getString(2));
                medico.setEmail(rs.getString(3));
                medico.setLogin(rs.getString(4));
                medico.setSenha(rs.getString(5));
                medico.setCrm(rs.getString(6));
                medico.setUf(rs.getString(7));
                medico.setTipo(rs.getString(8));
                lista.add(medico);
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