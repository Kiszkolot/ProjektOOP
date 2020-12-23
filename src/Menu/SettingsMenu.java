package Menu;

import javax.swing.*;

public class SettingsMenu {
    public JFrame menuFrame;

    public SettingsMenu() {
        menuFrame = new JFrame("Evolucja Skurczybyki!");
        menuFrame.setSize(500,500);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
    }
    public void run(Integer[] defults){
        menuFrame.add(new SettingsPanel(defults));
        menuFrame.setVisible(true);
    }
}
