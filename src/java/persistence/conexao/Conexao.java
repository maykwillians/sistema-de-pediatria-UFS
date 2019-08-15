/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mayk Willians
 */
public class Conexao {
    
    public static Connection getConnection() {
        
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Pediatria", "postgres", "siufs");
        } catch (SQLException e) {
            System.out.println("Erro - Conexao" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erro - Driver" + e.getMessage());
        }
        return con;
    }
}