/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.consulta;

/**
 *
 * @author Mayk Willians
 */
public class Mae {

    private String nome;
    private String cpf;
    private String telefone;
    private String chaveCrianca;
    private String chaveMedico;
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    /**
     * @return the chaveCrianca
     */
    public String getChaveCrianca() {
        return chaveCrianca;
    }

    /**
     * @param chaveCrianca the chaveCrianca to set
     */
    public void setChaveCrianca(String chaveCrianca) {
        this.chaveCrianca = chaveCrianca;
    }
    
    /**
     * @return the chaveMedico
     */
    public String getChaveMedico() {
        return chaveMedico;
    }

    /**
     * @param chaveMedico the chaveMedico to set
     */
    public void setChaveMedico(String chaveMedico) {
        this.chaveMedico = chaveMedico;
    }
}