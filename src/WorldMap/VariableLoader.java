package WorldMap;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class VariableLoader {

    private int mapWidth;
    private int mapHeight;

    private int energyProfit;
    private int dailyTax;
    private int startingEnergy;
    private int copulationEnergy;
    private int numberOAnimals;
    private int numberOGrass;
    private int mapJungleHeight;
    private int mapJungleWidth;
    private Integer delay;


    static public VariableLoader loadFromFile() throws FileNotFoundException, IllegalArgumentException{
        Gson gson = new Gson();
        File f = new File("");
        System.out.println(f.getAbsolutePath());
        VariableLoader properties =  (VariableLoader)gson.fromJson(new FileReader("src\\WorldMap\\config.json"), VariableLoader.class);
        properties.validate();
        return properties;
    }

    public void validate() throws IllegalArgumentException{

        if(this.mapWidth <= 0){ throw new IllegalArgumentException("Invalid map width");}
        if(this.mapHeight <= 0){ throw new IllegalArgumentException("Invalid map height");}
        if(this.mapJungleHeight <= 0){ throw new IllegalArgumentException("Invalid jungle height");}
        if(this.mapJungleWidth <= 0){throw new IllegalArgumentException("Invalid jungle width");}
        if(this.copulationEnergy <= 0){ throw new IllegalArgumentException("Invalid copulationMinimumEnergy");}
        if(this.startingEnergy < 0){ throw new IllegalArgumentException("Invalid animalsStartEnergy");}
        if(this.numberOAnimals < 0){ throw new IllegalArgumentException("Invalid numOfSpawnedAnimals");}
        if(this.numberOGrass < 0){ throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");}
        if(this.delay <0) {throw new IllegalArgumentException("Invalid delay");}

    }
    public int getMapWidth(){
        return this.mapWidth;
    }
    public void setMapJungleWidth(int value){
        this.mapJungleWidth = value;
    }
    public int getMapJungleHeight(){ return this.mapJungleHeight;}

    public void setMapJungleHeight(int value){
        this.mapJungleHeight = value;
    }
    public int getMapJungleWidth(){
        return this.mapJungleWidth;
    }
    public void setMapWidth(int value){
        this.mapWidth = value;
    }
    public int getMapHeight(){ return this.mapHeight;}

    public void setMapHeight(int value){
        this.mapHeight = value;
    }
    public int getEnergyProfit(){
        return this.energyProfit;
    }
    public void setEnergyProfit(int value){
        this.energyProfit = value;
    }
    public int getDailyTax(){
        return this.dailyTax;
    }
    public void setDailyTax(int value){
        this.dailyTax = value;
    }
    public int getStartingEnergy(){
        return this.startingEnergy;
    }
    public void setStartingEnergy(int value){
        this.startingEnergy = value;
    }
    public int getCopulationEnergy(){
        return this.copulationEnergy;
    }
    public void setCopulationEnergy(int value){
        this.copulationEnergy = value;
    }
    public int getNumberOAnimals(){
        return this.numberOAnimals;
    }
    public void setNumberOAnimals(int value){
        this.numberOAnimals = value;
    }
    public int getNumberOGrass(){
        return this.numberOGrass;
    }
    public void setNumberOGrass(int value){
        this.numberOGrass = value;
    }

    public int getDelay() {
        return this.delay;
    }
    public void setDelay(int value){
        this.delay = value;
    }

}
