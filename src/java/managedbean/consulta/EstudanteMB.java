/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Consulta;
import beans.consulta.Historico;
import beans.consulta.Crianca;
import beans.consulta.Mae;
import beans.consulta.Estudante;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedbean.sistema.LoginMB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import persistence.consulta.ConsultaDAO;
import persistence.consulta.HistoricoDAO;
import persistence.consulta.CriancaDAO;
import persistence.consulta.MaeDAO;
import validacao.Validacoes;

/**
 *
 * @author Mayk Willians
 */
@SessionScoped
@ManagedBean (name = "estud")
public class EstudanteMB implements Serializable {

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB = new LoginMB();
    
    private Estudante estudante = new Estudante();
    private Crianca crianca;
    private Mae mae;
    private Consulta consulta;
    private Historico historico = new Historico();
    private Validacoes validacoes = new Validacoes();
    
    public void initMaeCria () {
        
        this.crianca = new Crianca();
        this.mae = new Mae();
    }
    
    public void initCons () {
        
        this.consulta = new Consulta();
    }
    
    public boolean validarCadastro() throws ParseException {
        
        boolean validou = true;
        
        String erroCNS = this.getValidacoes().validarCNS(this.getCrianca().getCartaoSUS());
        if (!erroCNS.equals("")) {
            
            mensagemDeErro("cns", erroCNS);
            validou = false;
        }
        
        String erroNome = this.getValidacoes().validarNome(this.getCrianca().getNome());
        if (!erroNome.equals("")) {
            
            mensagemDeErro("nome", erroNome);
            validou = false;
        }
        
        String erroSexo = this.getValidacoes().validarSexo(this.getCrianca().getSexo());
        if (!erroSexo.equals("")) {
            
            mensagemDeErro("sexo", erroSexo);
            validou = false;
        }
        
        String erroData = this.getValidacoes().validarData(this.getCrianca().getDataNascimento());
        if (!erroData.equals("")) {
            
            mensagemDeErro("data", erroData);
            validou = false;
        }
        
        String erroNomeMae = this.getValidacoes().validarNome(this.getMae().getNome());
        if (!erroNomeMae.equals("")) {
            
            mensagemDeErro("nomeMae", erroNomeMae);
            validou = false;
        }
        
        String erroCPF = this.getValidacoes().validarCPF(this.getMae().getCpf());
        if (!erroCPF.equals("")) {
            
            mensagemDeErro("cpf", erroCPF);
            validou = false;
        }
        
        return validou;
    }
    
    public void validarConsulta() {
        
        boolean validou = true;
        
        String erroPeso = this.getValidacoes().validarPeso(this.getConsulta().getPeso());
        if (!erroPeso.equals("")) {
            
            mensagemDeErro("peso", erroPeso);
            validou = false;
        }
        
        String erroAltura = this.getValidacoes().validarAltura(this.getConsulta().getAltura());
        if (!erroAltura.equals("")) {
            
            mensagemDeErro("altura", erroAltura);
            validou = false;
        }
        
        String erroPerc = this.getValidacoes().validarPerc(this.getConsulta().getPerimetroCefalico());
        if (!erroPerc.equals("")) {
            
            mensagemDeErro("perc", erroPerc);
            validou = false;
        }
        
        if (validou) {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('pergunta').show()");
        }
    }
    
    public void mensagemDeErro(String campo, String mensagem){
        FacesContext.getCurrentInstance().addMessage
        (campo, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem,"Erro no Login!"));
    }
    
    //Faz o tratamento de retorno de pagina correto (Feminino/Masculino)
    public String formPriConsulta() throws ParseException {
        
        if (!validarCadastro()) {
            
            return null;
        }
        
        else {
            
            if (getCrianca().getSexo().equals("F")) {

                return "primConsFemEst.xhtml?faces-redirect=true";
            }
            if (getCrianca().getSexo().equals("M")) {

                return "primConsMasEst.xhtml?faces-redirect=true";
            }
        }
        return null;
    }
    
    //Faz o tratamento de retorno de pagina correto (Feminino/Masculino)
    public String formNovaConsulta() throws ParseException {
        
        if (getCrianca().getSexo().equals("F")) {
            
            return "novaConsultaFemEst.xhtml?faces-redirect=true";
        }
        if (getCrianca().getSexo().equals("M")) {
            
            return "novaConsultaMasEst.xhtml?faces-redirect=true";
        }
        return null;
    }
    
    public boolean verificaExistencia() throws ParseException {
        
        boolean existe = false;
        List<Crianca> listaDeCriancas = listarTodasCriancas();
        for(int i = 0; i < listaDeCriancas.size(); i++) {
            if (this.getCrianca().getCartaoSUS().equals(listaDeCriancas.get(i).getCartaoSUS())) {
                existe = true;
            }
        }
        if (existe) {
            existe = true;
        }
        else {
            existe = false;
        }
        return existe;
    }
    
    //Quando cadastrar a criança, deverá também cadastrar também a mãe
    public void cadastrarCrianca() throws ParseException {
        
        //Coloca a chave do medico na crianca
        this.getCrianca().setChaveMedico(this.getLoginMB().getEstudante().getMatricula());
        
        CriancaDAO criancaDAO = new CriancaDAO();
        criancaDAO.cadastrar(this.getCrianca());
        
        //Coloca a chave do medico na mãe
        this.getMae().setChaveMedico(this.getLoginMB().getEstudante().getMatricula());
        //Coloca a chave da crianca na mãe
        this.getMae().setChaveCrianca(this.getCrianca().getCartaoSUS());
        MaeDAO maeDAO = new MaeDAO();
        maeDAO.cadastrar(this.getMae());
    }
    
    public void cadastrarConsulta() throws IOException {
        
        //Para cadastrar a data atual
        Date data = new Date();
        
        //Simulando consultas com datas alteradas como num caso real
        HistoricoDAO hist = new HistoricoDAO();
        List<Historico> qtd = hist.listarPelaChave(this.getCrianca().getCartaoSUS());
        int x = qtd.size();
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + x);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        
        this.getConsulta().setTipo(this.getLoginMB().getEstudante().getTipo());
        this.getConsulta().setNomeMedico(this.getLoginMB().getEstudante().getNome());
        this.getConsulta().setNomeCrianca(this.getCrianca().getNome());
        this.getConsulta().setChaveMedico(this.getLoginMB().getEstudante().getMatricula());
        this.getConsulta().setChaveCrianca(this.getCrianca().getCartaoSUS());
        this.getConsulta().setData(c.getTime());
        
        //Setando IMC
        //Calcula imc
        //Conversões necessárias
        double peso = this.getConsulta().getPeso();
        double altura = this.getConsulta().getAltura();
        DecimalFormat df = new DecimalFormat("0.#");
        String str = df.format(peso / ((altura * altura) / 10));
        str = str.replace(",", ".");
        Double imc = Double.parseDouble(str);
        //Seta IMC
        this.getConsulta().setImc(imc);
        
        ConsultaDAO consultaDAO = new ConsultaDAO();
        consultaDAO.cadastrar(this.getConsulta());
        
        //Aqui tbm ja cadastra o historico
        cadastrarHistorico();
        
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('sucesso').show()");
    }
    
    public void cadastrarHistorico() {
        
        this.getHistorico().setTipo(this.getConsulta().getTipo());
        this.getHistorico().setNomeMedico(this.getConsulta().getNomeMedico());
        this.getHistorico().setNomeCrianca(this.getConsulta().getNomeCrianca());
        this.getHistorico().setChaveCrianca(this.getConsulta().getChaveCrianca());
        this.getHistorico().setData(this.getConsulta().getData());
        this.getHistorico().setPeso(this.getConsulta().getPeso());
        this.getHistorico().setAltura(this.getConsulta().getAltura());
        this.getHistorico().setImc(this.getConsulta().getImc());
        this.getHistorico().setPerimetrocefalico(this.getConsulta().getPerimetroCefalico());
        this.getHistorico().setComentarios(this.getConsulta().getComentarios());
        
        HistoricoDAO historicoDAO = new HistoricoDAO();
        historicoDAO.cadastrar(this.getHistorico());
    }
    
    public List<Historico> listarConsultas() {
        
        HistoricoDAO historicoDAO = new HistoricoDAO();
        List<Historico> listaDeConsultas = historicoDAO.listarPelaChave(this.getCrianca().getCartaoSUS());

        return listaDeConsultas;
    }
    
    public List<Crianca> listarTodasCriancas() {
        
        CriancaDAO criancaDAO = new CriancaDAO();
        List<Crianca> listaCriancas = criancaDAO.listarTodos();

        return listaCriancas;
    }
    
    public List<Crianca> listarCriaPelaChave() {
        
        CriancaDAO criancaDAO = new CriancaDAO();
        List<Crianca> listaCriancas = criancaDAO.listarPelaChave(this.getLoginMB().getEstudante().getMatricula());

        return listaCriancas;
    }
    
    public void exibirPaciente(SelectEvent event) throws IOException {
        
        this.crianca.setNome(((Crianca) event.getObject()).getNome());
        this.crianca.setSexo(((Crianca) event.getObject()).getSexo());
        this.crianca.setDataNascimento(((Crianca) event.getObject()).getDataNascimento());
        this.crianca.setCartaoSUS(((Crianca) event.getObject()).getCartaoSUS());
        this.crianca.setChaveMedico(((Crianca) event.getObject()).getChaveMedico());
        
        if(this.getCrianca().getSexo().equals("F")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("historicoFemEst.xhtml");
        }
        if(this.getCrianca().getSexo().equals("M")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("historicoMasEst.xhtml");
        }
    }
    
    public void exibirHistorico(SelectEvent event) throws IOException {
        
        this.historico.setNomeMedico(((Historico) event.getObject()).getNomeMedico());
        this.historico.setNomeCrianca(((Historico) event.getObject()).getNomeCrianca());
        this.historico.setChaveCrianca(((Historico) event.getObject()).getChaveCrianca());
        this.historico.setData(((Historico) event.getObject()).getData());
        this.historico.setPeso(((Historico) event.getObject()).getPeso());
        this.historico.setAltura(((Historico) event.getObject()).getAltura());
        this.historico.setImc(((Historico) event.getObject()).getImc());
        this.historico.setPerimetrocefalico(((Historico) event.getObject()).getPerimetrocefalico());
        this.historico.setComentarios(((Historico) event.getObject()).getComentarios());
        
        if (getCrianca().getSexo().equals("F")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultaDetFemEst.xhtml");
        }
        if (getCrianca().getSexo().equals("M")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultaDetMasEst.xhtml");
        }
    }
    
    /**
     * @return the loginMB
     */
    public LoginMB getLoginMB() {
        return loginMB;
    }

    /**
     * @param loginMB the loginMB to set
     */
    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
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
}