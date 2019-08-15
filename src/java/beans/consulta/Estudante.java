/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.consulta;

import beans.sistema.UsuarioHU;

/**
 *
 * @author Mayk Willians
 */
public class Estudante extends UsuarioHU {

    private String matricula;
    
    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}