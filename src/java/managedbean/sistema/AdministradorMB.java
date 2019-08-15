/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.sistema;

import beans.consulta.Medico;
import beans.sistema.Administrador;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import persistence.dados.AdministradorDAO;
import persistence.dados.MedicoDAO;
import validacao.Validacoes;

/**
 *
 * @author Mayk Willians
 */

@SessionScoped
@ManagedBean (name = "admin")
public class AdministradorMB implements Serializable {

    private Administrador administrador = new Administrador();
    private Medico med;
    private Validacoes validacoes = new Validacoes();
    private String repetirSenha;
    private String senhaTemp;
    
    public void initMed () {
        
        this.med = new Medico();
    }
    
    public void validarCadastroMed() throws ParseException {

        boolean validou = true;
        
        String erroNome = this.getValidacoes().validarNome(this.getMed().getNome());
        if (!erroNome.equals("")) {

            mensagemDeErro("nome", erroNome);
            validou = false;
        }

        String erroCPF = this.getValidacoes().validarCPF(this.getMed().getCpf());
        if (!erroCPF.equals("")) {

            mensagemDeErro("cpf", erroCPF);
            validou = false;
        }

        String erroCRM = this.getValidacoes().validarCRM(this.getMed().getCrm());
        if (!erroCRM.equals("")) {

            mensagemDeErro("crm", erroCRM);
            validou = false;
        }

        String erroEmail = this.getValidacoes().validarEmail(this.getMed().getEmail());
        if (!erroEmail.equals("")) {

            mensagemDeErro("email", erroEmail);
            validou = false;
        }

        String erroUsuario = this.getValidacoes().validarLogin(this.getMed().getLogin());
        if (!erroUsuario.equals("")) {

            mensagemDeErro("usuario", erroUsuario);
            validou = false;
        }

        String erroSenha = this.getValidacoes().validarSenha(this.getMed().getSenha());
        if (!erroSenha.equals("")) {

            mensagemDeErro("senha", erroSenha);
            validou = false;
        }

        String erroRepetirSenha = this.getValidacoes().validarRepetirSenha(this.getMed().getSenha(), this.getRepetirSenha());
        if (!erroRepetirSenha.equals("")) {

            mensagemDeErro("repetirSenha", erroRepetirSenha);
            validou = false;
        }
        
        this.setSenhaTemp(this.getMed().getSenha());

        if (validou) {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('pergunta').show()");
        }
    }
    
    public void mensagemDeErro(String campo, String mensagem){
        FacesContext.getCurrentInstance().addMessage
        (campo, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem,"Erro no Login!"));
    }
    
    public void cadastrarMedico() {
        
        this.getMed().setSenha(this.getSenhaTemp());
        MedicoDAO medDAO = new MedicoDAO();
        medDAO.cadastrar(this.getMed());
    }
    
    public void verificar() {
        
        MedicoDAO medDAO = new MedicoDAO();
        boolean ativo = medDAO.verifica(this.getMed().getCpf());
        
        if (ativo) {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('semSucesso').show()");
        }
        else {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('pergunta').show()");
        }
    }
    
    public void excluirMedico() {
        
        MedicoDAO medDAO = new MedicoDAO();
        medDAO.excluir(this.getMed().getCpf());
    }
    
    public List<Medico> listarMedicos() {
        
        MedicoDAO medicoDAO = new MedicoDAO();
        List<Medico> listaDeMedicos = medicoDAO.listarTodos();

        return listaDeMedicos;
    }
    
    public void exibirMedico(SelectEvent event) throws IOException {
        
        this.getMed().setNome(((Medico) event.getObject()).getNome());
        this.getMed().setCpf(((Medico) event.getObject()).getCpf());
        this.getMed().setEmail(((Medico) event.getObject()).getEmail());
        this.getMed().setLogin(((Medico) event.getObject()).getLogin());
        this.getMed().setSenha(((Medico) event.getObject()).getSenha());
        this.getMed().setCrm(((Medico) event.getObject()).getCrm());
        this.getMed().setUf(((Medico) event.getObject()).getUf());
        this.getMed().setTipo(((Medico) event.getObject()).getTipo());
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("exibirMedico.xhtml");
    }
    
    public void alterarADM() {
        
        AdministradorDAO admDAO = new AdministradorDAO();
        admDAO.alterarADM(this.getAdministrador());
    }
    
    public void gerarADMdefault() {
        
        AdministradorDAO admDAO = new AdministradorDAO();
        admDAO.gerarADMdefault();
    }
    
    /**
     * @return the administrador
     */
    public Administrador getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
    
    /**
     * @return the med
     */
    public Medico getMed() {
        return med;
    }

    /**
     * @param med the med to set
     */
    public void setMed(Medico med) {
        this.med = med;
    }
    
    /**
     * @return the validacoes
     */
    public Validacoes getValidacoes() {
        return validacoes;
    }

    /**
     * @param validacoes the validacoes to set
     */
    public void setValidacoes(Validacoes validacoes) {
        this.validacoes = validacoes;
    }
    
    /**
     * @return the repetirSenha
     */
    public String getRepetirSenha() {
        return repetirSenha;
    }

    /**
     * @param repetirSenha the repetirSenha to set
     */
    public void setRepetirSenha(String repetirSenha) {
        this.repetirSenha = repetirSenha;
    }
    
    /**
     * @return the senhaTemp
     */
    public String getSenhaTemp() {
        return senhaTemp;
    }

    /**
     * @param senhaTemp the senhaTemp to set
     */
    public void setSenhaTemp(String senhaTemp) {
        this.senhaTemp = senhaTemp;
    }
}