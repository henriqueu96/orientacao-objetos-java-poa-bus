/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poabus.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jxmapviewer.viewer.GeoPosition;
import poabus.domain.IParadasService;
import poabus.domain.Parada;
import poabus.services.ParadasService;

/**
 *
 * @author Sandro
 */
public class JanelaConsulta extends JFrame {

    private GerenciadorMapa gerenciador;
    
    
    // componentes
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel painelMapa;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnConsultaTodasParadas;
    
    private final IParadasService _paradasService;
    

    /**
     * Creates new form JanelaConsulta2
     */
    public JanelaConsulta() {
        initComponents();
        initMyComponents();
        _paradasService = new ParadasService();
    }

    private void initMyComponents() {
        GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);

        painelMapa.setLayout(new BorderLayout());
        painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        
    }
     
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        btnConsulta = new javax.swing.JButton();
        btnConsultaTodasParadas = new javax.swing.JButton();
        painelMapa = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnConsulta.setText("Consulta");
        btnConsulta.setName(""); // NOI18N
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        
        btnConsultaTodasParadas.setText("Todas as paradas");
        btnConsultaTodasParadas.setName(""); // NOI18N
        btnConsultaTodasParadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaTodasParadasActionPerformed(evt);
            }
        });
        

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConsultaTodasParadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConsulta)
                .addComponent(btnConsultaTodasParadas)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout painelMapaLayout = new javax.swing.GroupLayout(painelMapa);
        painelMapa.setLayout(painelMapaLayout);
        painelMapaLayout.setHorizontalGroup(
            painelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );
        painelMapaLayout.setVerticalGroup(
            painelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(painelMapa);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {                                            

        // Para obter o centro e o raio, usar como segue:
        GeoPosition centro = gerenciador.getSelecaoCentro();
//        int raio = gerenciador.getRaio();

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();

        // Exemplo:
        double valor = 250; // ex: valor da consulta (criminalidade ou dist�ncia)
        GeoPosition loc = new GeoPosition(-30.05, -51.18); // ex: localiza��o da parada
        lstPoints.add(new MyWaypoint(Color.BLUE, valor, loc));

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);

        // Informa o intervalo de valores gerados, para calcular a cor de cada ponto
        double menorValor = 15;  // exemplo
        double maiorValor = 250; // exemplo
        gerenciador.setIntervaloValores(menorValor, maiorValor);

        this.repaint();

    }


    private void btnConsultaTodasParadasActionPerformed(java.awt.event.ActionEvent evt) {
        List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
        List<Parada> todasAsParadas = _paradasService.getAllParadas();
        
        for(Parada p : todasAsParadas){
            double valor = 250; // ex: valor da consulta (criminalidade ou dist�ncia)
            GeoPosition loc = new GeoPosition(p.getLatitude(), p.getLongitude()); // ex: localiza��o da parada
            lstPoints.add(new MyWaypoint(Color.BLUE, valor, loc));            
        }
                
        GeoPosition ptCentro = gerenciador.getSelecaoCentro();        
        gerenciador.setPontos(lstPoints);                
        
        this.repaint();
    }
    
}
