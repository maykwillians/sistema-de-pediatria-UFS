/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Crianca;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mayk Willians
 */

@SessionScoped
@ManagedBean
public class CriancaMB implements Serializable {

    private Crianca crianca = new Crianca();
    
    /**
     * @return the crianca
     */
    public Crianca getCrianca() {
        return crianca;
    }

    /**
     * @param crianca the crianca to set
     */
    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }
}