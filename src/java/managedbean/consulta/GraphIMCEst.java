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
@ManagedBean (name = "graphIMCEst")
public class GraphIMCEst implements Serializable {

    @ManagedProperty(value = "#{estud}")
    private EstudanteMB estudanteMB = new EstudanteMB();
    
    private LineChartModel graphIMCEst;
    
    @PostConstruct
    public void init() {
        
        try {
            createZoomModel();
        } catch (ParseException ex) {
            Logger.getLogger(GraphIMCEst.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineChartModel getGraphIMCEst() {
        return graphIMCEst;
    }

    private void createZoomModel() throws ParseException {
        graphIMCEst = initLinearModel();
        graphIMCEst.setTitle("Gráfico (IMC x Idade)");
        //Mudar as cores das linhas
        if(this.getEstudanteMB().getCrianca().getSexo().equals("F")) {
            graphIMCEst.setSeriesColors("ff7cbb, 2E8B57, 696969, 696969");
        }
        if(this.getEstudanteMB().getCrianca().getSexo().equals("M")) {
            graphIMCEst.setSeriesColors("739fee, 2E8B57, 696969, 696969");
        }
        graphIMCEst.setZoom(true);
        graphIMCEst.setLegendPosition("e");
        Axis yAxis = graphIMCEst.getAxis(AxisType.Y);
        Axis xAxis = graphIMCEst.getAxis(AxisType.X);
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
        List<Historico> listaDeConsultas = this.getEstudanteMB().listarConsultas();
        ArrayList <Date> datasDasConsultas = new ArrayList();
        for(int i = 0; i < listaDeConsultas.size(); i++) {
            datasDasConsultas.add(listaDeConsultas.get(i).getData());
        }
        
        //Lista das idades em meses
        ArrayList <Long> idades = new ArrayList();
        String formato = "yyyy-MM-dd";
        Date dataNascimento = new SimpleDateFormat(formato).parse(this.getEstudanteMB().getCrianca().getDataNascimento());
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
     * @return the estudanteMB
     */
    public EstudanteMB getEstudanteMB() {
        return estudanteMB;
    }

    /**
     * @param estudanteMB the estudanteMB to set
     */
    public void setEstudanteMB(EstudanteMB estudanteMB) {
        this.estudanteMB = estudanteMB;
    }
}