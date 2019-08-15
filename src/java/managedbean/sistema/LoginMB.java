/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.sistema;

import beans.consulta.Estudante;
import beans.consulta.Medico;
import beans.sistema.Administrador;
import beans.sistema.UsuarioHU;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import persistence.sistema.LoginDAO;

/**
 *
 * @author Mayk Willians
 */
@SessionScoped
@ManagedBean (name = "loginMB")
public class LoginMB implements Serializable {

    private String login;
    private String senha;
    private Administrador administrador;
    private Medico medico;
    private Estudante estudante;
    private UsuarioHU usuarioHU;

    //Faz logoff do usuario no sistema
    public String logout() {
        
        this.setUsuarioHU(null);
        
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessao.invalidate();
        return "login.xhtml?faces-redirect=true";
    }
    
    public String efetuarLogin() {
        
        if (this.login.equals("") || this.senha.equals("")) {
            mensagemDeErro("Usuário ou senha não podem ser vazios");
            return null;
        }
        
        if (this.login.trim().isEmpty() || this.senha.trim().isEmpty()) {
            mensagemDeErro("Usuário ou senha não podem ser vazios");
            return null;
        }
        
        LoginDAO loginDAO = new LoginDAO();
        this.setUsuarioHU(loginDAO.verificaCredenciaisDB(this.login, this.senha));
        
        if (this.getUsuarioHU() == null) {
            
            FacesContext.getCurrentInstance().validationFailed();
            
            System.out.println("login ou senha incorretos");
            
            System.out.println();
            System.out.println();
            System.out.println();
            
            mensagemDeErro("Usuário ou senha incorretos!");
            
            return null;
        }
        
        else  {
            
            Object obj = this.getUsuarioHU();
            
            if (this.getUsuarioHU().getTipo().equals("1")) {
                
                this.setAdministrador((Administrador) obj);
                
                return "administradorHome.xhtml?faces-redirect=true";
            }

            if (this.getUsuarioHU().getTipo().equals("2")) {

                this.setMedico((Medico) obj);
                
                return "medicoHome.xhtml?faces-redirect=true";
            }

            if (this.getUsuarioHU().getTipo().equals("3")) {

                this.setEstudante((Estudante) obj);
                
                return "estudanteHome.xhtml?faces-redirect=true";
            }
        }
        return null;
    }
    
    public void mensagemDeErro(String mensagem){
        FacesContext.getCurrentInstance().addMessage
        (null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem,"Erro no Login!"));
    }
    
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
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
     * @return the medico
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    /**
     * @return the estudante
     */
    public Estudante getEstudante() {
        return estudante;
    }

    /**
     * @param estudante the estudante to set
     */
    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }
    
    /**
     * @return the usuarioHU
     */
    public UsuarioHU getUsuarioHU() {
        return usuarioHU;
    }

    /**
     * @param usuarioHU the usuarioHU to set
     */
    public void setUsuarioHU(UsuarioHU usuarioHU) {
        this.usuarioHU = usuarioHU;
    }
}