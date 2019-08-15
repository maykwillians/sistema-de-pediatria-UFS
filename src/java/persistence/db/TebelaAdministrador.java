/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistence.conexao.Conexao;

/**
 *
 * @author Mayk Willians
 */
public class TebelaAdministrador {
    
    private Connection con = Conexao.getConnection();

    public void criarTabela() {
        try {

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "administrador", null);

            if (tables.next()) {


            } else {

                Statement stmt = con.createStatement();
                String sql = "CREATE TABLE administrador "
                        + "(nome character varying(64) NOT NULL,"
                        + " email character varying(64) NOT NULL, "
                        + " login character varying(10) NOT NULL, "
                        + " senha character varying(10) NOT NULL, "
                        + " tipo character varying(1) NOT NULL)";

                stmt.executeUpdate(sql);
                stmt.close();
                con.close();
                
                System.out.println("Tabela Administrador criada");
            }
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
}