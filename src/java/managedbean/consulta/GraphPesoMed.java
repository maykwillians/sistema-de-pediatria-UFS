/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean.consulta;

import beans.consulta.Historico;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Mayk Willians
 */

//Escolher o tipo de escopo
@ManagedBean (name = "graphPesoMed")
public class GraphPesoMed implements Serializable {

    @ManagedProperty(value="#{medics}")
    private MedicoMB medicoMB = new MedicoMB();
    
    private LineChartModel graphPesoMed;
    
    @PostConstruct
    public void init() {
        
        try {
            createZoomModel();
        } catch (ParseException ex) {
            Logger.getLogger(GraphPesoMed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineChartModel getGraphPesoMed() {
        return graphPesoMed;
    }

    private void createZoomModel() throws ParseException {
        graphPesoMed = initLinearModel();
        graphPesoMed.setTitle("Gráfico (Peso x Idade)");
        //Mudar as cores das linhas
        if(this.getMedicoMB().getCrianca().getSexo().equals("F")) {
            graphPesoMed.setSeriesColors("696969, 2E8B57, ff7cbb, ff5959, e52b2b");
        }
        if(this.getMedicoMB().getCrianca().getSexo().equals("M")) {
            graphPesoMed.setSeriesColors("696969, 2E8B57, 739fee, ff5959, e52b2b");
        }
        graphPesoMed.setZoom(true);
        graphPesoMed.setLegendPosition("e");
        Axis yAxis = graphPesoMed.getAxis(AxisType.Y);
        Axis xAxis = graphPesoMed.getAxis(AxisType.X);
        yAxis.setLabel("Peso (Kg)");
        xAxis.setLabel("Idade (meses) - Até 2 anos");
        yAxis.setMin(0);
        xAxis.setMin(0);
    }

    //Adicionando linhas no gráfico
    private LineChartModel initLinearModel() throws ParseException {
        LineChartModel model = new LineChartModel();
        model.addSeries(linhaMaisDois());
        model.addSeries(linhaMedia());
        model.addSeries(linhaCrianca());
        model.addSeries(linhaMenosDois());
        model.addSeries(linhaMenosTres());
        return model;
    }
    
    //Linha da crianca
    public LineChartSeries linhaCrianca() throws ParseException {
        
        LineChartSeries linhaDaCrianca = new LineChartSeries();
        linhaDaCrianca.setLabel("Paciente");

        //Lista de datas das consultas
        List<Historico> listaDeConsultas = this.getMedicoMB().listarConsultas();
        ArrayList <Date> datasDasConsultas = new ArrayList();
        for(int i = 0; i < listaDeConsultas.size(); i++) {
            datasDasConsultas.add(listaDeConsultas.get(i).getData());
        }
        
        //Lista das idades em meses
        ArrayList <Long> idades = new ArrayList();
        String formato = "yyyy-MM-dd";
        Date dataNascimento = new SimpleDateFormat(formato).parse(this.getMedicoMB().getCrianca().getDataNascimento());
        for(int i = 0; i < datasDasConsultas.size(); i++) {
            idades.add((datasDasConsultas.get(i).getTime() - dataNascimento.getTime()) / (1000 * 60 * 60 * 24) / 30);
        }
        
        //Adicionando no gráfico
        double peso;
        for (int i = 0; i < idades.size(); i++) {
            
            peso = listaDeConsultas.get(i).getPeso();
            linhaDaCrianca.set(idades.get(i), peso / 1000);
        }
        
        return linhaDaCrianca;
    }
    
    public LineChartSeries linhaMedia() throws ParseException {
        
        LineChartSeries linhaMedia = new LineChartSeries();
        linhaMedia.setLabel("0");
 
        //1 ano
        linhaMedia.set(0, 3.3);
        linhaMedia.set(1, 4.3);
        linhaMedia.set(2, 5.2);
        linhaMedia.set(3, 6.0);
        linhaMedia.set(4, 6.7);
        linhaMedia.set(5, 7.3);
        linhaMedia.set(6, 7.8);
        linhaMedia.set(7, 8.3);
        linhaMedia.set(8, 8.8);
        linhaMedia.set(9, 9.2);
        linhaMedia.set(10, 9.5);
        linhaMedia.set(11, 9.9);
        
        //2 anos
        linhaMedia.set(12, 10.2);
        linhaMedia.set(13, 10.4);
        linhaMedia.set(14, 10.7);
        linhaMedia.set(15, 10.9);
        linhaMedia.set(16, 11.1);
        linhaMedia.set(17, 11.3);
        linhaMedia.set(18, 11.5);
        linhaMedia.set(19, 11.7);
        linhaMedia.set(20, 11.8);
        linhaMedia.set(21, 12.0);
        linhaMedia.set(22, 12.2);
        linhaMedia.set(23, 12.4);
        linhaMedia.set(24, 12.6);
        
        return linhaMedia;
    }
    
    public LineChartSeries linhaMenosTres() throws ParseException {
        
        LineChartSeries linhaMenosTres = new LineChartSeries();
        linhaMenosTres.setLabel("-3");
 
        //1 ano
        linhaMenosTres.set(0, 2.0);
        linhaMenosTres.set(1, 2.7);
        linhaMenosTres.set(2, 3.4);
        linhaMenosTres.set(3, 4.0);
        linhaMenosTres.set(4, 4.4);
        linhaMenosTres.set(5, 4.8);
        linhaMenosTres.set(6, 5.1);
        linhaMenosTres.set(7, 5.3);
        linhaMenosTres.set(8, 5.6);
        linhaMenosTres.set(9, 5.8);
        linhaMenosTres.set(10, 5.9);
        linhaMenosTres.set(11, 6.1);
        
        //2 anos
        linhaMenosTres.set(12, 6.3);
        linhaMenosTres.set(13, 6.4);
        linhaMenosTres.set(14, 6.6);
        linhaMenosTres.set(15, 6.7);
        linhaMenosTres.set(16, 6.9);
        linhaMenosTres.set(17, 7.0);
        linhaMenosTres.set(18, 7.2);
        linhaMenosTres.set(19, 7.3);
        linhaMenosTres.set(20, 7.5);
        linhaMenosTres.set(21, 7.6);
        linhaMenosTres.set(22, 7.8);
        linhaMenosTres.set(23, 7.9);
        linhaMenosTres.set(24, 8.1);
        
        return linhaMenosTres;
    }
    
    public LineChartSeries linhaMenosDois() throws ParseException {
        
        LineChartSeries linhaMenosDois = new LineChartSeries();
        linhaMenosDois.setLabel("-2");
 
        //1 ano
        linhaMenosDois.set(0, 2.4);
        linhaMenosDois.set(1, 3.2);
        linhaMenosDois.set(2, 3.9);
        linhaMenosDois.set(3, 4.5);
        linhaMenosDois.set(4, 5.0);
        linhaMenosDois.set(5, 5.4);
        linhaMenosDois.set(6, 5.7);
        linhaMenosDois.set(7, 6.0);
        linhaMenosDois.set(8, 6.3);
        linhaMenosDois.set(9, 6.5);
        linhaMenosDois.set(10, 6.9);
        linhaMenosDois.set(11, 7.0);
        
        //2 anos
        linhaMenosDois.set(12, 7.1);
        linhaMenosDois.set(13, 7.2);
        linhaMenosDois.set(14, 7.4);
        linhaMenosDois.set(15, 7.6);
        linhaMenosDois.set(16, 7.7);
        linhaMenosDois.set(17, 7.9);
        linhaMenosDois.set(18, 8.1);
        linhaMenosDois.set(19, 8.2);
        linhaMenosDois.set(20, 8.4);
        linhaMenosDois.set(21, 8.6);
        linhaMenosDois.set(22, 8.7);
        linhaMenosDois.set(23, 8.9);
        linhaMenosDois.set(24, 9.0);
        
        return linhaMenosDois;
    }
    
    public LineChartSeries linhaMaisDois() throws ParseException {
        
        LineChartSeries linhaMaisDois = new LineChartSeries();
        linhaMaisDois.setLabel("+2");
 
        //1 ano
        linhaMaisDois.set(0, 4.2);
        linhaMaisDois.set(1, 5.5);
        linhaMaisDois.set(2, 6.6);
        linhaMaisDois.set(3, 7.5);
        linhaMaisDois.set(4, 8.2);
        linhaMaisDois.set(5, 8.8);
        linhaMaisDois.set(6, 9.3);
        linhaMaisDois.set(7, 9.8);
        linhaMaisDois.set(8, 10.2);
        linhaMaisDois.set(9, 10.5);
        linhaMaisDois.set(10, 10.9);
        linhaMaisDois.set(11, 11.2);
        
        //2 anos
        linhaMaisDois.set(12, 11.5);
        linhaMaisDois.set(13, 11.8);
        linhaMaisDois.set(14, 12.1);
        linhaMaisDois.set(15, 12.4);
        linhaMaisDois.set(16, 12.6);
        linhaMaisDois.set(17, 12.9);
        linhaMaisDois.set(18, 13.2);
        linhaMaisDois.set(19, 13.5);
        linhaMaisDois.set(20, 13.7);
        linhaMaisDois.set(21, 14.0);
        linhaMaisDois.set(22, 14.3);
        linhaMaisDois.set(23, 14.6);
        linhaMaisDois.set(24, 14.8);
        
        return linhaMaisDois;
    }
    
    /**
     * @return the medicoMB
     */
    public MedicoMB getMedicoMB() {
        return medicoMB;
    }

    /**
     * @param medicoMB the medicoMB to set
     */
    public void setMedicoMB(MedicoMB medicoMB) {
        this.medicoMB = medicoMB;
    }
}