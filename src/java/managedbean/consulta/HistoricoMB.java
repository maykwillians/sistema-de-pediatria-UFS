/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Historico;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import persistence.consulta.HistoricoDAO;

/**
 *
 * @author Mayk Willians
 */
@SessionScoped
@ManagedBean (name = "hist")
public class HistoricoMB {

    private Historico historico = new Historico();
    
    /**
     * @return the historico
     */
    public Historico getHistorico() {
        return historico;
    }

    /**
     * @param historico the historico to set
     */
    public void setHistorico(Historico historico) {
        this.historico = historico;
    }
    
    public void cadastrar() {
        
        HistoricoDAO historicoDAO = new HistoricoDAO();
        historicoDAO.cadastrar(this.getHistorico());
    }
}