/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.dados;

import beans.consulta.Consulta;
import persistence.conexao.Conexao;
import beans.consulta.Estudante;
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
public class EstudanteDAO {
    
    private Connection con = Conexao.getConnection();
    
    public boolean verificaSeExiste (String matricula) {
        
        String sql = "select * from ESTUDANTE where matricula = ?";

        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);

            preparador.setString(1, matricula);

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
    
    public void cadastrar(Estudante aluno) {
        
        //Criptografa senha
        //String senhaCriptografada = criptografaSenha(aluno.getSenha());
        
        //O nome da tabela no banco precisa estar tudo em minúsculo
        String sql = "INSERT INTO ESTUDANTE (nome, email, login, senha, matricula, tipo) values (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparador = this.con.prepareStatement(sql);
            
            preparador.setString(1, aluno.getNome());
            preparador.setString(2, aluno.getEmail());
            preparador.setString(3, aluno.getLogin());
            preparador.setString(4, aluno.getSenha());
            preparador.setString(5, aluno.getMatricula());
            preparador.setString(6, "3");
            
            preparador.execute();
            preparador.close();
            
            System.out.println("Estudante cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
    
    public void alterar(String matricula) {
        
        
    }
    
    public boolean verifica(String matricula) {
        
        boolean ativo = false;
        
        ConsultaDAO consultaDAO = new ConsultaDAO();
        List<Consulta> lista = consultaDAO.listarTodos();
        
        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getChaveMedico().equals(matricula)) {
                
                ativo = true;
            }
        }
        
        return ativo;
    }
    
    public void excluir(String matricula) {

        String SQL = "DELETE FROM estudante WHERE matricula = '" + matricula + "'";

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            System.out.println("Estudante excluído com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(EstudanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Estudante> listarTodos() {

        List<Estudante> lista = new ArrayList<Estudante>();

        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            String sql = "select * from estudante";
            rs = st.executeQuery(sql);

            while (rs.next()) {

                Estudante estudante = new Estudante();
                
                estudante.setNome(rs.getString(1));
                estudante.setEmail(rs.getString(2));
                estudante.setLogin(rs.getString(3));
                estudante.setSenha(rs.getString(4));
                estudante.setMatricula(rs.getString(5));
                estudante.setTipo(rs.getString(6));
                lista.add(estudante);
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