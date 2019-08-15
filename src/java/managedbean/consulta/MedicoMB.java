/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Consulta;
import beans.consulta.Historico;
import beans.consulta.Crianca;
import beans.consulta.Estudante;
import beans.consulta.Mae;
import beans.consulta.Medico;
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
import persistence.dados.EstudanteDAO;
import validacao.Validacoes;

/**
 *
 * @author Mayk Willians
 */
@SessionScoped
@ManagedBean (name = "medics")
public class MedicoMB implements Serializable {

    @ManagedProperty(value="#{loginMB}")
    private LoginMB loginMB = new LoginMB();
    
    private Medico med = new Medico();
    private Crianca crianca;
    private Mae mae;
    private Consulta consulta;
    private Historico historico = new Historico();
    private Validacoes validacoes = new Validacoes();
    private Estudante estudante;
    private String repetirSenha;
    private String senhaTemp;
    
    public void initEst () {
        
        this.estudante = new Estudante();
    }
    
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
    
    public void validarCadastroEst() throws ParseException {
        
        boolean validou = true;

        String erroNome = this.getValidacoes().validarNome(this.getEstudante().getNome());
        if (!erroNome.equals("")) {

            mensagemDeErro("nome", erroNome);
            validou = false;
        }

        String erroEmail = this.getValidacoes().validarEmail(this.getEstudante().getEmail());
        if (!erroEmail.equals("")) {

            mensagemDeErro("email", erroEmail);
            validou = false;
        }
        
        String erroMatricula = this.getValidacoes().validarMatricula(this.getEstudante().getMatricula());
        if (!erroMatricula.equals("")) {

            mensagemDeErro("matricula", erroMatricula);
            validou = false;
        }

        String erroUsuario = this.getValidacoes().validarLogin(this.getEstudante().getLogin());
        if (!erroUsuario.equals("")) {

            mensagemDeErro("usuario", erroUsuario);
            validou = false;
        }

        String erroSenha = this.getValidacoes().validarSenha(this.getEstudante().getSenha());
        if (!erroSenha.equals("")) {

            mensagemDeErro("senha", erroSenha);
            validou = false;
        }

        String erroRepetirSenha = this.getValidacoes().validarRepetirSenha(this.getEstudante().getSenha(), this.getRepetirSenha());
        if (!erroRepetirSenha.equals("")) {

            mensagemDeErro("repetirSenha", erroRepetirSenha);
            validou = false;
        }
        
        this.setSenhaTemp(this.getEstudante().getSenha());

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

                return "primConsFemMed.xhtml?faces-redirect=true";
            }
            if (getCrianca().getSexo().equals("M")) {

                return "primConsMasMed.xhtml?faces-redirect=true";
            }
        }
        return null;
    }
    
    //Faz o tratamento de retorno de pagina correto (Feminino/Masculino)
    public String formNovaConsulta() throws ParseException {
        
        if (getCrianca().getSexo().equals("F")) {
            
            return "novaConsultaFemMed.xhtml?faces-redirect=true";
        }
        if (getCrianca().getSexo().equals("M")) {
            
            return "novaConsultaMasMed.xhtml?faces-redirect=true";
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
    
    public void cadastrarEstudante() {
        
        this.getEstudante().setSenha(this.getSenhaTemp());
        EstudanteDAO estudanteDAO = new EstudanteDAO();
        estudanteDAO.cadastrar(this.getEstudante());
    }
    
    public void verificarEstudante() {
        
        EstudanteDAO estDAO = new EstudanteDAO();
        boolean ativo = estDAO.verifica(this.getEstudante().getMatricula());
        
        if (ativo) {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('semSucesso').show()");
        }
        else {
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('pergunta').show()");
        }
    }
    
    public void excluirEstudante() {
        
        EstudanteDAO estDAO = new EstudanteDAO();
        estDAO.excluir(this.getEstudante().getMatricula());
    }
    
    //Quando cadastrar a criança, deverá também cadastrar também a mãe
    public void cadastrarCrianca() throws ParseException {
        
        //Coloca a chave do medico na crianca
        this.getCrianca().setChaveMedico(this.getLoginMB().getMedico().getCpf());
        
        CriancaDAO criancaDAO = new CriancaDAO();
        criancaDAO.cadastrar(this.getCrianca());
        
        //Coloca a chave do medico na mãe
        this.getMae().setChaveMedico(this.getLoginMB().getMedico().getCpf());
        //Coloca a chave da crianca na mãe
        this.getMae().setChaveCrianca(this.getCrianca().getCartaoSUS());
        MaeDAO maeDAO = new MaeDAO();
        maeDAO.cadastrar(this.getMae());
    }
    
    public void cadastrarConsulta() {
        
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
        
        this.getConsulta().setTipo(this.getLoginMB().getMedico().getTipo());
        this.getConsulta().setNomeMedico(this.getLoginMB().getMedico().getNome());
        this.getConsulta().setNomeCrianca(this.getCrianca().getNome());
        this.getConsulta().setChaveMedico(this.getLoginMB().getMedico().getCpf());
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
    
    public List<Estudante> listarEstudantes() {
        
        EstudanteDAO estudanteDAO = new EstudanteDAO();
        List<Estudante> listaDeEstudantes = estudanteDAO.listarTodos();

        return listaDeEstudantes;
    }
    
    public List<Crianca> listarCriaPelaChave() {
        
        CriancaDAO criancaDAO = new CriancaDAO();
        List<Crianca> listaCriancas = criancaDAO.listarPelaChave(this.getLoginMB().getMedico().getCpf());

        return listaCriancas;
    }
    
    public void exibirPaciente(SelectEvent event) throws IOException {
        
        this.getCrianca().setNome(((Crianca) event.getObject()).getNome());
        this.getCrianca().setSexo(((Crianca) event.getObject()).getSexo());
        this.getCrianca().setDataNascimento(((Crianca) event.getObject()).getDataNascimento());
        this.getCrianca().setCartaoSUS(((Crianca) event.getObject()).getCartaoSUS());
        this.getCrianca().setChaveMedico(((Crianca) event.getObject()).getChaveMedico());
        
        if(this.getCrianca().getSexo().equals("F")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("historicoFemMed.xhtml");
        }
        if(this.getCrianca().getSexo().equals("M")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("historicoMasMed.xhtml");
        }
    }
    
    public void exibirHistorico(SelectEvent event) throws IOException {
        
        this.getHistorico().setNomeMedico(((Historico) event.getObject()).getNomeMedico());
        this.getHistorico().setNomeCrianca(((Historico) event.getObject()).getNomeCrianca());
        this.getHistorico().setChaveCrianca(((Historico) event.getObject()).getChaveCrianca());
        this.getHistorico().setData(((Historico) event.getObject()).getData());
        this.getHistorico().setPeso(((Historico) event.getObject()).getPeso());
        this.getHistorico().setAltura(((Historico) event.getObject()).getAltura());
        this.getHistorico().setImc(((Historico) event.getObject()).getImc());
        this.getHistorico().setPerimetrocefalico(((Historico) event.getObject()).getPerimetrocefalico());
        this.getHistorico().setComentarios(((Historico) event.getObject()).getComentarios());
        
        if (getCrianca().getSexo().equals("F")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultaDetFemMed.xhtml");
        }
        if (getCrianca().getSexo().equals("M")) {
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultaDetMasMed.xhtml");
        }
    }
    
    public void exibirEstudante(SelectEvent event) throws IOException {
        
        this.getEstudante().setNome(((Estudante) event.getObject()).getNome());
        this.getEstudante().setEmail(((Estudante) event.getObject()).getEmail());
        this.getEstudante().setLogin(((Estudante) event.getObject()).getLogin());
        this.getEstudante().setSenha(((Estudante) event.getObject()).getSenha());
        this.getEstudante().setMatricula(((Estudante) event.getObject()).getMatricula());
        this.getEstudante().setTipo(((Estudante) event.getObject()).getTipo());
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("exibirEstudante.xhtml");
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