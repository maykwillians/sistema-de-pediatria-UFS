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
@ManagedBean (name = "graphPCMed")
public class GraphPCMed implements Serializable {

    @ManagedProperty(value="#{medics}")
    private MedicoMB medicoMB = new MedicoMB();
    
    private LineChartModel graphPCMed;
    
    @PostConstruct
    public void init() {
        
        try {
            createZoomModel();
        } catch (ParseException ex) {
            Logger.getLogger(GraphPesoMed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineChartModel getGraphPCMed() {
        return graphPCMed;
    }

    private void createZoomModel() throws ParseException {
        graphPCMed = initLinearModel();
        graphPCMed.setTitle("Gráfico (Perímetro Cefálico x Idade)");
        //Mudar as cores das linhas
        if(this.getMedicoMB().getCrianca().getSexo().equals("F")) {
            graphPCMed.setSeriesColors("696969, 2E8B57, ff7cbb, e52b2b");
        }
        if(this.getMedicoMB().getCrianca().getSexo().equals("M")) {
            graphPCMed.setSeriesColors("696969, 2E8B57, 739fee, e52b2b");
        }
        graphPCMed.setZoom(true);
        graphPCMed.setLegendPosition("e");
        Axis yAxis = graphPCMed.getAxis(AxisType.Y);
        Axis xAxis = graphPCMed.getAxis(AxisType.X);
        yAxis.setLabel("Perímetro Cefálico (cm)");
        xAxis.setLabel("Idade (meses) - Até 2 anos");
        yAxis.setMin(25);
        yAxis.setMax(55);
        xAxis.setMin(0);
    }

    //Adicionando linhas no gráfico
    private LineChartModel initLinearModel() throws ParseException {
        LineChartModel model = new LineChartModel();
        model.addSeries(linhaMaisDois());
        model.addSeries(linhaMedia());
        model.addSeries(linhaCrianca());
        model.addSeries(linhaMenosDois());
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
        for (int i = 0; i < idades.size(); i++) {
            
            linhaDaCrianca.set(idades.get(i), listaDeConsultas.get(i).getPerimetrocefalico());
        }
        
        return linhaDaCrianca;
    }
    
    public LineChartSeries linhaMedia() throws ParseException {
        
        LineChartSeries linhaMedia = new LineChartSeries();
        linhaMedia.setLabel("0");
 
        //1 ano
        linhaMedia.set(0, 34.4);
        linhaMedia.set(1, 37.2);
        linhaMedia.set(2, 39.1);
        linhaMedia.set(3, 40.5);
        linhaMedia.set(4, 41.6);
        linhaMedia.set(5, 42.5);
        linhaMedia.set(6, 43.3);
        linhaMedia.set(7, 43.9);
        linhaMedia.set(8, 44.5);
        linhaMedia.set(9, 45.0);
        linhaMedia.set(10, 45.4);
        linhaMedia.set(11, 45.7);
        
        //2 anos
        linhaMedia.set(12, 46.0);
        linhaMedia.set(13, 46.3);
        linhaMedia.set(14, 46.6);
        linhaMedia.set(15, 46.8);
        linhaMedia.set(16, 47.0);
        linhaMedia.set(17, 47.2);
        linhaMedia.set(18, 47.3);
        linhaMedia.set(19, 47.5);
        linhaMedia.set(20, 47.8);
        linhaMedia.set(21, 47.9);
        linhaMedia.set(22, 48.0);
        linhaMedia.set(23, 48.1);
        linhaMedia.set(24, 48.2);
        
        return linhaMedia;
    }
    
    public LineChartSeries linhaMenosDois() throws ParseException {
        
        LineChartSeries linhaMenosDois = new LineChartSeries();
        linhaMenosDois.setLabel("-2");
 
        //1 ano
        linhaMenosDois.set(0, 31.9);
        linhaMenosDois.set(1, 34.9);
        linhaMenosDois.set(2, 36.8);
        linhaMenosDois.set(3, 38.1);
        linhaMenosDois.set(4, 39.2);
        linhaMenosDois.set(5, 40.1);
        linhaMenosDois.set(6, 40.9);
        linhaMenosDois.set(7, 41.5);
        linhaMenosDois.set(8, 42.0);
        linhaMenosDois.set(9, 42.5);
        linhaMenosDois.set(10, 42.9);
        linhaMenosDois.set(11, 43.2);
        
        //2 anos
        linhaMenosDois.set(12, 43.5);
        linhaMenosDois.set(13, 43.8);
        linhaMenosDois.set(14, 44.2);
        linhaMenosDois.set(15, 44.4);
        linhaMenosDois.set(16, 44.6);
        linhaMenosDois.set(17, 44.7);
        linhaMenosDois.set(18, 44.9);
        linhaMenosDois.set(19, 45.0);
        linhaMenosDois.set(20, 45.2);
        linhaMenosDois.set(21, 45.3);
        linhaMenosDois.set(22, 45.4);
        linhaMenosDois.set(23, 45.5);
        linhaMenosDois.set(24, 45.7);
        
        return linhaMenosDois;
    }
    
    public LineChartSeries linhaMaisDois() throws ParseException {
        
        LineChartSeries linhaMaisDois = new LineChartSeries();
        linhaMaisDois.setLabel("+2");
 
        //1 ano
        linhaMaisDois.set(0, 37.0);
        linhaMaisDois.set(1, 39.6);
        linhaMaisDois.set(2, 41.5);
        linhaMaisDois.set(3, 42.9);
        linhaMaisDois.set(4, 44.0);
        linhaMaisDois.set(5, 45.0);
        linhaMaisDois.set(6, 45.8);
        linhaMaisDois.set(7, 46.4);
        linhaMaisDois.set(8, 47.0);
        linhaMaisDois.set(9, 47.5);
        linhaMaisDois.set(10, 47.9);
        linhaMaisDois.set(11, 48.3);
        
        //2 anos
        linhaMaisDois.set(12, 48.6);
        linhaMaisDois.set(13, 48.9);
        linhaMaisDois.set(14, 49.2);
        linhaMaisDois.set(15, 49.4);
        linhaMaisDois.set(16, 49.6);
        linhaMaisDois.set(17, 49.8);
        linhaMaisDois.set(18, 50.0);
        linhaMaisDois.set(19, 50.2);
        linhaMaisDois.set(20, 50.4);
        linhaMaisDois.set(21, 50.5);
        linhaMaisDois.set(22, 50.7);
        linhaMaisDois.set(23, 50.8);
        linhaMaisDois.set(24, 51.0);
        
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