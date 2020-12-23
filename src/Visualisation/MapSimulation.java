package Visualisation;

import WorldMap.JungleStepMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSimulation implements ActionListener {

    public int delay;
    public JungleStepMap map;
    public int numOAnimals;
    public int numOGrass;

    public JFrame frame;
    public RenderPanel renderPanel;
    public PlotRenderPanel plotRenderPanel;
    public Timer timer;

    public MapSimulation(JungleStepMap map, int delay,int numOAnimals, int numOGrass){

        this.map = map;
        this.delay = delay;
        this.numOAnimals = numOAnimals;
        this.numOGrass = numOGrass;

        this.timer = new Timer(delay,this);

        this.frame = new JFrame("Evolution Simulator");
        this.frame.setSize(1000, 1000);
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        this.renderPanel = new RenderPanel(map, this);
        this.renderPanel.setSize(new Dimension(1, 1));

        this.plotRenderPanel = new PlotRenderPanel(map, this);
        this.plotRenderPanel.setSize(1, 1);

        this.frame.add(renderPanel);
        this.frame.add(plotRenderPanel);
    }

    public void run(){
        this.map.setTheScene();
        this.timer.start();
    }

    public void actionPerformed(ActionEvent event){
        this.plotRenderPanel.repaint();
        this.map.newDay();

    }
}
