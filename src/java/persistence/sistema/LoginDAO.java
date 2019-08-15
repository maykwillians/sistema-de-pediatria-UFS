/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.sistema;

import beans.sistema.UsuarioHU;

/**
 *
 * @author Mayk Willians
 */

public class LoginDAO {
    
    public UsuarioHU verificaCredenciaisDB(String login, String senha) {
        
        //String senhaCriptografada = criptografaSenha(senha);
        
        UsuarioHU usuarioHU;
        
        LoginAdministradorDAO loginAdministradorDAO = new LoginAdministradorDAO();
        LoginMedicoDAO loginMedicoDAO = new LoginMedicoDAO();
        LoginEstudanteDAO loginEstudanteDAO = new LoginEstudanteDAO();
        
        usuarioHU = loginAdministradorDAO.verifica(login, senha);
        if (usuarioHU != null) {
            
            return usuarioHU;
        }
        
        usuarioHU = loginMedicoDAO.verifica(login, senha);
        if (usuarioHU != null) {
            
            return usuarioHU;
        }
        
        usuarioHU = loginEstudanteDAO.verifica(login, senha);
        if (usuarioHU != null) {
            
            return usuarioHU;
        }
        
        return null;
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