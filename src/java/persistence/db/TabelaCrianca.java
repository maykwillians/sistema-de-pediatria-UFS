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
public class TabelaCrianca {
    
    private Connection con = Conexao.getConnection();

    public void criarTabela() {
        try {

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "crianca", null);

            if (tables.next()) {


            } else {

                Statement stmt = con.createStatement();
                String sql = "CREATE TABLE crianca "
                        + "(nome character varying(64) NOT NULL,"
                        + " sexo char(1) NOT NULL, "
                        + " datanascimento date NOT NULL, "
                        + " cartaosus character varying(18) PRIMARY KEY NOT NULL, "
                        + " chavemedico character varying(14) NOT NULL)";

                stmt.executeUpdate(sql);
                stmt.close();
                con.close();
                
                System.out.println("Tabela Criança criada");
            }
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
}