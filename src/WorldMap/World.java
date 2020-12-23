package WorldMap;

import Menu.SettingsMenu;

import java.io.FileNotFoundException;

public class World {

    public static void main(String[] args){
        try{
            VariableLoader variables = VariableLoader.loadFromFile();
            Integer[] defoultMap = {
                    variables.getMapWidth(),
                    variables.getMapHeight(),
                    variables.getMapJungleWidth(),
                    variables.getMapJungleHeight(),
                    variables.getEnergyProfit(),
                    variables.getDailyTax(),
                    variables.getCopulationEnergy(),
                    variables.getStartingEnergy(),
                    variables.getNumberOAnimals(),
                    variables.getDelay(),
                    variables.getNumberOGrass()
            };
            SettingsMenu menu = new SettingsMenu();
            menu.run(defoultMap);
        }
        catch(IllegalArgumentException ex ) {
            System.out.println(ex);
            return;
        }
        catch (FileNotFoundException ex){
            System.out.println(ex);
            return;
        }

    }
}
