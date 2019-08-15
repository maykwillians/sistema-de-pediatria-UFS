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
@ManagedBean (name = "graphAlturaEst")
public class GraphAlturaEst implements Serializable {

    @ManagedProperty(value = "#{estud}")
    private EstudanteMB estudanteMB = new EstudanteMB();
    
    private LineChartModel graphAlturaEst;
    
    @PostConstruct
    public void init() {
        try {
            createZoomModel();
        } catch (ParseException ex) {
            Logger.getLogger(GraphAlturaEst.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LineChartModel getGraphAlturaEst() {
        return graphAlturaEst;
    }

    private void createZoomModel() throws ParseException {
        graphAlturaEst = initLinearModel();
        graphAlturaEst.setTitle("Gráfico (Altura x Idade)");
        //Mudar as cores das linhas
        if(this.getEstudanteMB().getCrianca().getSexo().equals("F")) {
            graphAlturaEst.setSeriesColors("696969, 2E8B57, ff7cbb, e52b2b");
        }
        if(this.getEstudanteMB().getCrianca().getSexo().equals("M")) {
            graphAlturaEst.setSeriesColors("696969, 2E8B57, 739fee, e52b2b");
        }
        graphAlturaEst.setZoom(true);
        graphAlturaEst.setLegendPosition("e");
        Axis yAxis = graphAlturaEst.getAxis(AxisType.Y);
        Axis xAxis = graphAlturaEst.getAxis(AxisType.X);
        yAxis.setLabel("Altura (cm)");
        xAxis.setLabel("Idade (meses) - Até 2 anos");
        yAxis.setMin(30);
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
            
            linhaDaCrianca.set(idades.get(i), listaDeConsultas.get(i).getAltura());
        }
        
        return linhaDaCrianca;
    }
    
    public LineChartSeries linhaMedia() throws ParseException {
        
        LineChartSeries linhaMedia = new LineChartSeries();
        linhaMedia.setLabel("0");
 
        //1 ano
        linhaMedia.set(0, 50);
        linhaMedia.set(1, 54);
        linhaMedia.set(2, 58);
        linhaMedia.set(3, 61);
        linhaMedia.set(4, 63);
        linhaMedia.set(5, 65);
        linhaMedia.set(6, 67);
        linhaMedia.set(7, 69);
        linhaMedia.set(8, 71);
        linhaMedia.set(9, 72);
        linhaMedia.set(10, 73);
        linhaMedia.set(11, 74);
        
        //2 anos
        linhaMedia.set(12, 76);
        linhaMedia.set(13, 77);
        linhaMedia.set(14, 78);
        linhaMedia.set(15, 79);
        linhaMedia.set(16, 80);
        linhaMedia.set(17, 81);
        linhaMedia.set(18, 82);
        linhaMedia.set(19, 83);
        linhaMedia.set(20, 84);
        linhaMedia.set(21, 85);
        linhaMedia.set(22, 86);
        linhaMedia.set(23, 87);
        linhaMedia.set(24, 88);
        
        return linhaMedia;
    }
    
    public LineChartSeries linhaMenosDois() throws ParseException {
        
        LineChartSeries linhaMenosDois = new LineChartSeries();
        linhaMenosDois.setLabel("-2");
 
        //1 ano
        linhaMenosDois.set(0, 44);
        linhaMenosDois.set(1, 48);
        linhaMenosDois.set(2, 52);
        linhaMenosDois.set(3, 53);
        linhaMenosDois.set(4, 57);
        linhaMenosDois.set(5, 59);
        linhaMenosDois.set(6, 61);
        linhaMenosDois.set(7, 62);
        linhaMenosDois.set(8, 64);
        linhaMenosDois.set(9, 65);
        linhaMenosDois.set(10, 66);
        linhaMenosDois.set(11, 67);
        
        //2 anos
        linhaMenosDois.set(12, 68);
        linhaMenosDois.set(13, 69);
        linhaMenosDois.set(14, 70);
        linhaMenosDois.set(15, 71);
        linhaMenosDois.set(16, 72);
        linhaMenosDois.set(17, 73);
        linhaMenosDois.set(18, 74);
        linhaMenosDois.set(19, 74);
        linhaMenosDois.set(20, 75);
        linhaMenosDois.set(21, 76);
        linhaMenosDois.set(22, 77);
        linhaMenosDois.set(23, 78);
        linhaMenosDois.set(24, 78);
        
        return linhaMenosDois;
    }
    
    public LineChartSeries linhaMaisDois() throws ParseException {
        
        LineChartSeries linhaMaisDois = new LineChartSeries();
        linhaMaisDois.setLabel("+2");
 
        //1 ano
        linhaMaisDois.set(0, 53);
        linhaMaisDois.set(1, 56);
        linhaMaisDois.set(2, 60);
        linhaMaisDois.set(3, 64);
        linhaMaisDois.set(4, 66);
        linhaMaisDois.set(5, 69);
        linhaMaisDois.set(6, 70);
        linhaMaisDois.set(7, 72);
        linhaMaisDois.set(8, 73);
        linhaMaisDois.set(9, 75);
        linhaMaisDois.set(10, 76);
        linhaMaisDois.set(11, 78);
        
        //2 anos
        linhaMaisDois.set(12, 79);
        linhaMaisDois.set(13, 81);
        linhaMaisDois.set(14, 82);
        linhaMaisDois.set(15, 83);
        linhaMaisDois.set(16, 84);
        linhaMaisDois.set(17, 85);
        linhaMaisDois.set(18, 86);
        linhaMaisDois.set(19, 88);
        linhaMaisDois.set(20, 89);
        linhaMaisDois.set(21, 91);
        linhaMaisDois.set(22, 92);
        linhaMaisDois.set(23, 93);
        linhaMaisDois.set(24, 94);
        
        return linhaMaisDois;
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