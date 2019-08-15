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
@ManagedBean (name = "graphIMCMed")
public class GraphIMCMed implements Serializable {

    @ManagedProperty(value="#{medics}")
    private MedicoMB medicoMB = new MedicoMB();
    
    private LineChartModel graphIMCMed;
    
    @PostConstruct
    public void init() {
        
        try {
            createZoomModel();
        } catch (ParseException ex) {
            Logger.getLogger(GraphPesoMed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineChartModel getGraphIMCMed() {
        return graphIMCMed;
    }

    private void createZoomModel() throws ParseException {
        graphIMCMed = initLinearModel();
        graphIMCMed.setTitle("Gráfico (IMC x Idade)");
        //Mudar as cores das linhas
        if(this.getMedicoMB().getCrianca().getSexo().equals("F")) {
            graphIMCMed.setSeriesColors("ff7cbb, 2E8B57, 696969, 696969");
        }
        if(this.getMedicoMB().getCrianca().getSexo().equals("M")) {
            graphIMCMed.setSeriesColors("739fee, 2E8B57, 696969, 696969");
        }
        graphIMCMed.setZoom(true);
        graphIMCMed.setLegendPosition("e");
        Axis yAxis = graphIMCMed.getAxis(AxisType.Y);
        Axis xAxis = graphIMCMed.getAxis(AxisType.X);
        yAxis.setLabel("IMC (kg/cm)");
        xAxis.setLabel("Idade (meses) - Até 2 anos");
        yAxis.setMin(0);
        xAxis.setMin(0);
    }

    //Adicionando linhas no gráfico
    private LineChartModel initLinearModel() throws ParseException {
        LineChartModel model = new LineChartModel();
        model.addSeries(linhaCrianca());
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
            
            linhaDaCrianca.set(idades.get(i), listaDeConsultas.get(i).getImc());
        }
        
        return linhaDaCrianca;
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