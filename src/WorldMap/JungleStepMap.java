package WorldMap;

import Classes.Animal;
import Classes.Gene;
import Classes.Grass;
import Classes.Vector2D;
import EnumClasses.MapDirection;
import Interfaces.IMapElement;
import Interfaces.IPositonChangedObserver;
import Interfaces.IWorldMap;

import java.util.*;
import java.util.stream.Collectors;

public class JungleStepMap implements IWorldMap, IPositonChangedObserver {
    private final Vector2D upperRight;
    private final Vector2D lowerLeft;
    private final Vector2D upperRightJungle;
    private final Vector2D lowerLeftJungle;
    private final int mapWidth;
    private final int mapHeight;
    private final int mapWidthJungle;
    private final int mapHeightJungle;

    private final int energyProfit;
    private final int dailytax;
    private final int startingEnergy;
    private final int copulatonEnergy;
    private final int startingGrass;
    private final int startingAnimals;
    private final int grassPerDay;

    public LinkedList<Animal> animalsList;
    public LinkedList<Grass> grassList;
    public ArrayList<Animal> morque;

    public Map<Vector2D, LinkedList<Animal>> animals = new HashMap<>();
    public Map<Vector2D, Grass> grassPiles = new HashMap<>();

    private int numODeadAnimals;
    public float avarageLifespan;
    public int age;


    //Inicjator
    public JungleStepMap(int mapWidth, int mapHeight, int mapWidthJungle, int mapHeightJungle, int startingEnergy, int dailytax, int energyProfit, int copulatonEnergy, int startingAnimals,int grassPerDay) {
        this.animalsList = new LinkedList<>();
        this.grassList = new LinkedList<>();

        this.energyProfit = energyProfit;
        this.dailytax = dailytax;
        this.startingEnergy = startingEnergy;
        this.copulatonEnergy = copulatonEnergy;
        this.startingAnimals = startingAnimals;
        this.startingGrass = 2*startingAnimals;
        this.grassPerDay = grassPerDay;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapHeightJungle = mapHeightJungle;
        this.mapWidthJungle = mapWidthJungle;


        this.upperRight = new Vector2D(mapWidth-1,mapHeight-1);
        this.lowerLeft = new Vector2D(0,0);

        this.avarageLifespan = 0;
        this.numODeadAnimals = 0;
        this.age = 0;

        //lokalizujemy dżunglę

        int llx=0;
        int lly =0;
        int urx=mapWidth-1;
        int ury = mapHeight-1;

        for(int i=0; i<(mapWidth-mapWidthJungle);i++){
            if(i%2==0){
                llx+=1;
            }
            else{
                urx-=1;
            }
        }

        for(int i=0; i<(mapHeight-mapHeightJungle);i++){
            if(i%2==0){
                lly+=1;
            }
            else{
                ury-=1;
            }
        }
        this.lowerLeftJungle= new Vector2D(llx,lly);
        this.upperRightJungle = new Vector2D(urx,ury);
    }

    //Funkcje z IWorldMap
    @Override
    public boolean canMoveTo(Vector2D position) {
        return(position.follows(this.lowerLeft) && position.precedes(this.upperRight));
    }

    @Override
    public boolean place(IMapElement el) {
        Vector2D position = el.getPosition();
        position = this.parseDirs(position);
        if(!canPlace(position)){
            throw new IllegalArgumentException("Field "+position.toString()+" is unplacable");
        }
        else{
            if(el instanceof Animal){
                addAnimal((Animal) el, position);
                animalsList.add((Animal) el);
                //el.addObserver.this;
            }
            if(el instanceof Grass){
                if(grassPiles.get(position)==null) {
                    grassPiles.put(position, (Grass) el);
                    grassList.add((Grass)el);
                }
            }
            return true;
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return objectAt(position)!=null;
    }

    @Override
    public Object objectAt(Vector2D position) {
        position = this.parseDirs(position);
        LinkedList<Animal> l = animals.get(position);
        if (l == null) return grassPiles.get(position);
        else if (l.size() == 0) return grassPiles.get(position);
        else return l.getFirst();
    }

    //Funkcje z IPositionChangedObserver
    @Override
    public boolean positionChanged(Vector2D oldpos,Vector2D newpos,Animal pet){
        newpos = this.parseDirs(newpos);
        if(canMoveTo(newpos)){
            removeAnimal(pet,oldpos);
            addAnimal(pet,newpos);
            return true;
        }
        return false;
    }

    @Override
    public boolean canPlace(Vector2D position){
        position = this.parseDirs(position);
        if(objectAt(position)==null){
            return true;
        }
        return false;
    }

    //Zawijanie
    public Vector2D parseDirs(Vector2D position){
        int x = position.getX();
        int y = position.getY();
        x = Math.max(x,-1*x);
        y = Math.max(y,-1*y);
        x %= this.mapWidth;
        y %= this.mapHeight;
        return new Vector2D(x,y);
    }

    //pomocnicze do umieszczania dziecka na planszy
    public ArrayList<Vector2D> directionParser(Vector2D curr){
        ArrayList<Vector2D> result = new ArrayList<>();
        MapDirection facing = MapDirection.NORTH;
        for(int i=0;i<8;i++){
            Vector2D vec = facing.toUnitVector();
            Vector2D nextVec = vec.add(curr);
            nextVec = this.parseDirs(nextVec);
            if(this.canPlace(nextVec)){
                result.add(nextVec);
            }
            facing = facing.next();
        }
        return result;
    }

    @Override
    public Vector2D goodSpot(Vector2D parentPosition){
        ArrayList<Vector2D> available = this.directionParser(parentPosition);
        int n = available.size();
        if(n==0){
            return parentPosition;
        }
        int k = new Random().nextInt(n);
        return available.get(k);

    }


    //Pomocnicze do przemieszczania i usuwania zwierząt
    public boolean addAnimal(Animal animal, Vector2D position){
        if(animal==null){
            return false;
        }
        else{
            position = this.parseDirs(position);
            LinkedList<Animal> list = animals.get(position);
            if (list==null){
                LinkedList<Animal> temp = new LinkedList<>();
                temp.add(animal);
                animals.put(position,temp);
            }
            else if(list!=null){
                list.add(animal);
            }
            return true;
        }
    }

    public boolean removeAnimal(Animal animal, Vector2D position){
        position = this.parseDirs(position);
        LinkedList<Animal> l = animals.get(position);
        if (l == null)
            throw new IllegalArgumentException("Animal" + animal.getPosition() + " sitting on " + position + " is gone from the map");
        else if (l.size() == 0)
            throw new IllegalArgumentException("Animal" + animal.getPosition() + " already not exist in the map empty list");
        else {
            l.remove(animal);
            if (l.size() == 0) {
                animals.remove(position);
            }
        }
        return true;
    }

    //randomy
    public Vector2D getRandomVector(){
        int x = new Random().nextInt(this.mapWidth);
        int y = new Random().nextInt(this.mapHeight);
        return new Vector2D(x,y);
    }

    public MapDirection getRandomDirection(){
        MapDirection elem = MapDirection.NORTH;
        elem.randomDir();
        return elem;
    }

    public Vector2D nextUnoccupied(Vector2D beg){
        for(int i=0;i<this.mapWidth;i++){
            for(int j=0;j<this.mapHeight;j++){
                Vector2D newVector = beg.add(new Vector2D(i,j));
                newVector = this.parseDirs(newVector);
                if(this.canPlace(newVector)){
                    return newVector;
                }
            }
        }
        return beg;
    }


    //Codzienność zwierząt
    public void eating(){
        LinkedList<Grass> eaten = new LinkedList<>();
        for (Grass food : grassPiles.values()){
            LinkedList<Animal> hungry = animals.get(food.getPosition());
            if(hungry != null){
                if(hungry.size()>0){
                    LinkedList<Animal> strongest = new LinkedList<>();
                    int energy = 0;
                    for(Animal pet: hungry){
                        if(strongest.size()==0){
                            strongest.add(pet);
                            energy = pet.getEnergy();
                        }
                        else if(pet.getEnergy()==energy){
                            strongest.add(pet);
                        }
                        else if(pet.getEnergy()>energy){
                            LinkedList<Animal> stronger = new LinkedList<>();
                            stronger.add(pet);
                            strongest = stronger;
                        }
                    }
                    for(Animal pet:strongest){
                        pet.changeEnergy(energyProfit/strongest.size());
                    }
                    eaten.add(food);
                }
            }
        }
        for(Grass food:eaten){
            grassList.remove(food);
            grassPiles.remove(food.getPosition());
        }
    }
    public void rotateAll(){
        for(Animal pet:animalsList){
            pet.turn(pet.getGenotype().getRandomGene());
        }
    }

    public void moveAll(){
        for(Animal pet:animalsList){
            pet.move();
        }
    }



    public void reproduce(){
        for(LinkedList<Animal> pets:animals.values()){
            if(pets!=null){
                if(pets.size()>2){
                    List<Animal> ordered = pets.stream().sorted(Comparator.comparing(Animal::getEnergy)).collect(Collectors.toList());
                    Animal parent1 = ordered.get(0);
                    Animal parent2 = ordered.get(1);
                    if(parent1.getEnergy()>=this.copulatonEnergy && parent2.getEnergy()>=this.copulatonEnergy) {
                        Animal child = parent1.makeAChild(parent2);
                        addAnimal(child, child.getPosition());
                    }
                }
            }
        }
    }

    public void updateAvgLifespan(int age){
        this.avarageLifespan = this.avarageLifespan*(this.numODeadAnimals-1)+age;
        this.avarageLifespan/=this.numODeadAnimals;
    }

    public void removeBodies() {
        for (Animal pet : animalsList) {
            if(pet.isDead()){
                this.removeAnimal(pet,pet.getPosition());
                this.numODeadAnimals+=1;
                pet.setDeathdate(this.age);
                this.updateAvgLifespan(age-pet.getBirthdate());
            }
        }
    }

    public void payForTheDay(){
        for(Animal pet: animalsList){
            pet.changeEnergy(-1*this.dailytax);
        }
    }

    public void growPlants(){
        for(int i=0;i<this.grassPerDay;i++){
            Vector2D vec = this.getRandomVector();
            if(this.isOccupied(vec)){
                vec = this.nextUnoccupied(vec);
            }
            Grass newPile = new Grass(vec);
            this.grassPiles.put(vec,newPile);
            this.grassList.add(newPile);
        }
    }

    public void newDay(){
        this.removeBodies();
        this.rotateAll();
        this.moveAll();
        this.eating();
        this.reproduce();
        this.growPlants();
        this.age+=1;
        //jeszcze coś do liczenia wieku dodać
    }

    //Funkcje zapełniające miejsca na mapie na początek


    public void setAnimalsRandomly(){
        for(int i=0;i<this.startingAnimals;i++){
            Vector2D vec = this.getRandomVector();
            if(this.isOccupied(vec)){
                vec = this.nextUnoccupied(vec);
            }
            Animal pet = new Animal(vec,this.getRandomDirection(),new Gene(8,32),this,this.startingEnergy);
            this.addAnimal(pet,vec);
            pet.setBirthdate(this.age);
        }
    }

    public void fillWithGrass(){
        for(int i=0;i<this.startingGrass;i++){
            Vector2D vec = this.getRandomVector();
            if(this.isOccupied(vec)){
                vec = this.nextUnoccupied(vec);
            }
            Grass newpile = new Grass(vec);
            this.grassPiles.put(vec,newpile);
            this.grassList.add(newpile);
        }
    }

    public void setTheScene(){
        this.fillWithGrass();
        this.setAnimalsRandomly();
    }

    public int getWidth() {
        return this.mapWidth;
    }

    public int getHeight() {
        return this.mapHeight;
    }

    public Vector2D getJungleLowerLeft() {
        return this.lowerLeftJungle;
    }

    public int getJungleWidth() {
        return this.mapWidthJungle;
    }

    public int grtJungleHeight() {
        return this.mapHeightJungle;
    }

    public LinkedList<Grass> getGrass() {
        return this.grassList;
    }

    public LinkedList< Animal> getAnimals() {
        return this.animalsList;
    }

    public float getAvarageEnergy(){
        int avg = 0;
        for(Animal pet: this.animalsList){
            avg+=pet.getEnergy();
        }
        return avg/this.animalsList.size();
    }

    public float getAvarageChildness(){
        int avg =0;
        for(Animal pet: this.animalsList){
            avg+=pet.getChildness();
        }
        return avg/this.animalsList.size();
    }
}