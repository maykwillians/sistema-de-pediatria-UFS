/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.consulta;

import java.util.Date;

/**
 *
 * @author Mayk Willians
 */
public class Historico {

    private String tipo;
    private String nomeMedico;
    private String nomeCrianca;
    private String chaveCrianca;
    private Date data;
    private int peso;
    private int altura;
    private double imc;
    private double perimetrocefalico;
    private String comentarios;
    
    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @return the nomeMedico
     */
    public String getNomeMedico() {
        return nomeMedico;
    }

    /**
     * @param nomeMedico the nomeMedico to set
     */
    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    /**
     * @return the nomeCrianca
     */
    public String getNomeCrianca() {
        return nomeCrianca;
    }

    /**
     * @param nomeCrianca the nomeCrianca to set
     */
    public void setNomeCrianca(String nomeCrianca) {
        this.nomeCrianca = nomeCrianca;
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
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * @return the altura
     */
    public int getAltura() {
        return altura;
    }

    /**
     * @param altura the altura to set
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * @return the imc
     */
    public double getImc() {
        return imc;
    }

    /**
     * @param imc the imc to set
     */
    public void setImc(double imc) {
        this.imc = imc;
    }

    /**
     * @return the perimetrocefalico
     */
    public double getPerimetrocefalico() {
        return perimetrocefalico;
    }

    /**
     * @param perimetrocefalico the perimetrocefalico to set
     */
    public void setPerimetrocefalico(double perimetrocefalico) {
        this.perimetrocefalico = perimetrocefalico;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}