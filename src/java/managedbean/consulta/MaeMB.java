/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Mae;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mayk Willians
 */

@SessionScoped
@ManagedBean
public class MaeMB implements Serializable {

     private Mae mae = new Mae();
    
    /**
     * @return the mae
     */
    public Mae getMae() {
        return mae;
    }

    /**
     * @param mae the mae to set
     */
    public void setMae(Mae mae) {
        this.mae = mae;
    }
}