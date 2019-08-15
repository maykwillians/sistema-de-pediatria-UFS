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
public class TabelaMae {
    
    private Connection con = Conexao.getConnection();

    public void criarTabela() {
        try {

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "mae", null);

            if (tables.next()) {


            } else {

                Statement stmt = con.createStatement();
                String sql = "CREATE TABLE mae "
                        + "(nome character varying(64) NOT NULL,"
                        + " cpf character varying(14), "
                        + " telefone character varying(15), "
                        + " chavemedico character varying(14) NOT NULL, "
                        + " chavecrianca character varying(18) NOT NULL)";

                stmt.executeUpdate(sql);
                stmt.close();
                con.close();
                
                System.out.println("Tabela Mãe criada");
            }
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
}