/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validacao;

import beans.consulta.Crianca;
import beans.consulta.Estudante;
import beans.consulta.Medico;
import beans.sistema.Administrador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import persistence.consulta.CriancaDAO;
import persistence.dados.AdministradorDAO;
import persistence.dados.EstudanteDAO;
import persistence.dados.MedicoDAO;

/**
 *
 * @author Mayk Willians
 */
public class Validacoes {
    
    public String validarCNS(String cns) {
        
        CriancaDAO criaDAO = new CriancaDAO();
        List<Crianca> listaDeCria = criaDAO.listarTodos();
        
        if (cns.equals("")) {
            return "(Erro: O CNS não pode ser vazio!)";
        }
        
        for (int i = 0; i < listaDeCria.size(); i++) {
            
            if (listaDeCria.get(i).getCartaoSUS().equals(cns)) {
                
                return "(Erro: Já existe um(a) paciente cadastrado(a) com este cns!)";
            }
        }
        return "";
    }
    
    public String validarNome(String nome) {
        
        if (nome.equals("")) {
            return "(Erro: O nome não pode ser vazio!)";
        }
        if (nome.trim().isEmpty()) {
            return "(Erro: O nome não pode ser vazio!)";
        }
        if (nome.substring(nome.length() - 1, nome.length()).equals(" ")) {
            return "(Erro: Não utilize espaços no final do nome!)";
        }
        if (nome.contains("  ")) {
            return "(Erro: Não utilize dois ou mais espaços consecutivos!)";
        }
        if(nome.indexOf(" ") == -1) {
            
            return "(Erro: O nome deve conter um sobrenome!)";
        }
        if(nome.substring(0,nome.indexOf(" ")).length() < 2 || nome.substring(nome.lastIndexOf(" ") + 1).length() < 2) {
            
            return "(Erro: Nome e/ou sobrenome muito pequenos!)";
        }
        if(nome.substring(0,nome.indexOf(" ")).length() > 15 || nome.substring(nome.lastIndexOf(" ") + 1).length() > 15) {
            
            return "(Erro: Nome e/ou sobrenome muito grandes!)";
        }
        
        for (int i = 0; i < nome.length() - 2; i++) {
            
            if (nome.substring(i, i + 1).equals(nome.substring(i + 1, i + 2)) && nome.substring(i, i + 1).equals(nome.substring(i + 2, i + 3))) {
                
                return "(Erro: Nome inválido!)";
            }
        }
        return "";
    }
    
    public String validarSexo(String sexo) {
        
        if (sexo.equals("")) {
            return "(Erro: Você deve escolher um sexo)";
        }
        return "";
    }
    
    public String validarData(String data) {
        
        if (data.equals("")) {
            
            return "(Erro: A data não pode ser vazia!)";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        
        try {
            Date dataNascimento = sdf.parse(data);
            Date dataAtual = new Date();

            if (dataNascimento.compareTo(dataAtual) > 0) {

                return "(Erro: Data de nascimento maior que data atual!)";
            }

            int anoNasc = Integer.parseInt(data.substring(6, data.length()));
            if (anoNasc < 2015) {

                return "(Erro: Ano de nascimento inválido!)";
            }
        } catch (ParseException ex) {
            return "(Erro: Data inválida!)";
        }
        
        return "";
    }
    
    //
    public String validarPeso(double peso) {
        
        if (peso == 0) {
            
            return "(Erro: O peso não pode ser zero!)";
        }
        if (peso < 2500) {
            
            return "(Erro: Peso muito baixo!)";
        }
        if (peso > 150000) {
            
            return "(Erro: Peso muito alto!)";
        }
        return "";
    }
    
    public String validarAltura(int altura) {
        
        if (altura == 0) {
            
            return "(Erro: A altura não pode ser zero!)";
        }
        if (altura < 30) {
            
            return "(Erro: Valor para altura muito baixo!)";
        }
        if (altura > 210) {
            
            return "(Erro: Valor para altura muito alto!)";
        }
        return "";
    }
    
    public String validarPerc(double perc) {
        
       if (perc == 0) {
            
            return "(Erro: O perímetro não pode ser zero!)";
        }
        if (perc < 25) {
            
            return "(Erro: Perímetro muito baixo!)";
        }
        if (perc > 55) {
            
            return "(Erro: Perímetro muito alto!)";
        }
        return "";
    }
    
    public String validarCPF(String cpf) {
        
        MedicoDAO medDAO = new MedicoDAO();
        boolean existe = medDAO.verificaSeExiste(cpf);
        
        if (cpf.equals("")) {
            return "(Erro: O CPF não pode ser vazio!)";
        }
        if (existe) {
            return "(Erro: Já existe um(a) médico(a) cadastrado(a) com este cpf!)";
        }
        return "";
    }
    
    public String validarCRM(String crm) {
        
        if (crm.equals("")) {
            return "(Erro: O CRM não pode ser vazio!)";
        }
        return "";
    }
    
    public String validarEmail(String email) {
        
        MedicoDAO medDAO = new MedicoDAO();
        List<Medico> listaMed = medDAO.listarTodos();
        
        EstudanteDAO estDAO = new EstudanteDAO();
        List<Estudante> listaEst = estDAO.listarTodos();
        
        AdministradorDAO admDAO = new AdministradorDAO();
        List<Administrador> listaAdm = admDAO.listarTodos();
        
        if (email.equals("")) {
            return "(Erro: O e-mail não pode ser vazio!)";
        }
        
        String padraoDeEmail = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
        Pattern padrao = Pattern.compile(padraoDeEmail, Pattern.CASE_INSENSITIVE);
        Matcher m = padrao.matcher(email);
        
        if (!m.matches()) {
            
            return "(Erro: E-mail inválido!)";
        }
        
        for (int i = 0; i < listaMed.size(); i++) {
            
            if (listaMed.get(i).getEmail().equals(email)) {
                
                return "(Erro: E-mail já cadastrado no sistema!)";
            }
        }
        for (int i = 0; i < listaEst.size(); i++) {
            
            if (listaEst.get(i).getEmail().equals(email)) {
                
                return "(Erro: E-mail já cadastrado no sistema!)";
            }
        }
        for (int i = 0; i < listaAdm.size(); i++) {
            
            if (listaAdm.get(i).getEmail().equals(email)) {
                
                return "(Erro: E-mail já cadastrado no sistema!)";
            }
        }
        
        return "";
    }
    
    public String validarLogin(String login) {
        
        MedicoDAO medDAO = new MedicoDAO();
        List<Medico> listaMed = medDAO.listarTodos();
        
        EstudanteDAO estDAO = new EstudanteDAO();
        List<Estudante> listaEst = estDAO.listarTodos();
        
        AdministradorDAO admDAO = new AdministradorDAO();
        List<Administrador> listaAdm = admDAO.listarTodos();
        
        if (login.equals("")) {
            return "(Erro: O usuário não pode ser vazio!)";
        }
        
        if (login.trim().isEmpty()) {
            return "(Erro: O usuário não pode ser vazio!)";
        }
        
        if (login.length() < 6) {
            return "(Erro: Utilize no mínimo 6 caracteres!)";
        }
        
        for (int i = 0; i < listaMed.size(); i++) {
            
            if (listaMed.get(i).getLogin().equals(login)) {
                
                return "(Erro: Nome de usuário já utilizado!)";
            }
        }
        for (int i = 0; i < listaEst.size(); i++) {
            
            if (listaEst.get(i).getLogin().equals(login)) {
                
                return "(Erro: Nome de usuário já utilizado!)";
            }
        }
        for (int i = 0; i < listaAdm.size(); i++) {
            
            if (listaAdm.get(i).getLogin().equals(login)) {
                
                return "(Erro: Nome de usuário já utilizado!)";
            }
        }
        return "";
    }
    
    public String validarSenha(String senha) {
        
        if (senha.equals("")) {
            return "(Erro: A senha não pode ser vazia!)";
        }
        if (senha.trim().isEmpty()) {
            return "(Erro: A senha não pode ser vazia!)";
        }
        if (senha.length() < 6) {
            return "(Erro: Utilize no mínimo 6 caracteres!)";
        }
        return "";
    }
    
    public String validarRepetirSenha(String senha1, String senha2) {
        
        if (senha2.equals("")) {
            return "(Erro: A senha não pode ser vazia!)";
        }
        if (senha2.trim().isEmpty()) {
            return "(Erro: A senha não pode ser vazia!)";
        }
        if (!senha1.equals(senha2)) {
            return "(Erro: As senhas não são iguais!)";
        }
        return "";
    }
    
    public String validarMatricula(String matricula) {
        
        EstudanteDAO estDAO = new EstudanteDAO();
        boolean existe = estDAO.verificaSeExiste(matricula);
        
        if (matricula.equals("")) {
            return "(Erro: A matrícula não pode ser vazia!)";
        }
        if (existe) {
            return "(Erro: Já existe um(a) estudante cadastrado(a) com esta matrícula!)";
        }
        return "";
    }
}