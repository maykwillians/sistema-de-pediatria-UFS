/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import persistence.db.GerarTabelas;

/**
 *
 * @author Mayk Willians
 */
public class CriarTabelas {
    
    public static void main (String[] args) {
        
        new GerarTabelas().criarTabelas();
    }
}