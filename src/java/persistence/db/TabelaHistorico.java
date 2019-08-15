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
public class TabelaHistorico {
    
    private Connection con = Conexao.getConnection();

    public void criarTabela() {
        try {

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "historico", null);

            if (tables.next()) {


            } else {

                Statement stmt = con.createStatement();
                String sql = "CREATE TABLE historico "
                        + "(nomemedico character varying(64) NOT NULL,"
                        + " tipo character varying(1) NOT NULL, "
                        + " nomecrianca character varying(64) NOT NULL, "
                        + " chavecrianca character varying(18) NOT NULL, "
                        + " data date NOT NULL, "
                        + " peso int NOT NULL, "
                        + " altura int NOT NULL, "
                        + " imc double precision NOT NULL, "
                        + " perimetrocefalico double precision NOT NULL, "
                        + " comentarios character varying(10000))";

                stmt.executeUpdate(sql);
                stmt.close();
                con.close();
                
                System.out.println("Tabela Histórico criada");
            }
        } catch (SQLException e) {
            System.out.println("Erro - " + e.getMessage());
        }
    }
}