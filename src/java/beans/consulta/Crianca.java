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
public class Crianca {

    private String nome;
    private String sexo;
    private String dataNascimento;
    private String cartaoSUS;
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
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the cartaoSUS
     */
    public String getCartaoSUS() {
        return cartaoSUS;
    }

    /**
     * @param cartaoSUS the cartaoSUS to set
     */
    public void setCartaoSUS(String cartaoSUS) {
        this.cartaoSUS = cartaoSUS;
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