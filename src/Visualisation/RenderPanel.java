package Visualisation;

import Classes.Animal;
import Classes.Grass;
import WorldMap.JungleStepMap;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    public JungleStepMap map;
    public MapSimulation simulation;
    
    public RenderPanel(JungleStepMap map, MapSimulation symulation) {
        this.map = map;
        this.simulation = symulation;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setSize((int)(this.simulation.frame.getWidth() * 0.6), this.simulation.frame.getHeight() - 38);
        this.setLocation((int) (0.4 * this.simulation.frame.getWidth()), 0);
        int width = this.getWidth();
        int height = this.getHeight(); //38 is toolbar size
        int widthScale = Math.round(width / this.map.getWidth());
        int heightScale = height / this.map.getHeight();


        g.setColor(new Color(150,224,80));
        g.fillRect(0,0,width,height);

        g.setColor(new Color(0, 160, 7));
        g.fillRect(this.map.getJungleLowerLeft().getX() * widthScale,
                this.map.getJungleLowerLeft().getY() * heightScale,
                this.map.getJungleWidth() * widthScale,
                this.map.grtJungleHeight() * heightScale);

        //draw Grass
        for (Grass grass : this.map.getGrass()) {
            g.setColor(grass.toColor());
            int y = map.parseDirs(grass.getPosition()).getY() * heightScale;
            int x = map.parseDirs(grass.getPosition()).getX() * widthScale;
            g.fillRect(x, y, widthScale, heightScale);
        }
        //draw Animals
        for (Animal a : map.getAnimals()) {
            g.setColor(a.toColor());
            int y = map.parseDirs(a.getPosition()).getY() * heightScale;
            int x = map.parseDirs(a.getPosition()).getX() * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }
    }
}

