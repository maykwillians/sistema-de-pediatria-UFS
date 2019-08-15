/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Consulta;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import persistence.consulta.ConsultaDAO;

/**
 *
 * @author Mayk Willians
 */

@SessionScoped
@ManagedBean
public class ConsultaMB implements Serializable {

    private Consulta consulta = new Consulta();
    
    /**
     * @return the consulta
     */
    public Consulta getConsulta() {
        return consulta;
    }

    /**
     * @param consulta the consulta to set
     */
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    
    public void cadastrar() {
        
        ConsultaDAO consultaDAO = new ConsultaDAO();
        consultaDAO.cadastrar(this.getConsulta());
    }
}