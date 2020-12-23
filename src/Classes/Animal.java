package Classes;

import EnumClasses.MapDirection;
import Interfaces.IMapElement;
import Interfaces.IPositonChangedObserver;
import Interfaces.IWorldMap;

import java.awt.*;
import java.util.ArrayList;

public class Animal implements IMapElement, IPositonChangedObserver {
    private Vector2D position;
    private MapDirection facing;
    private Gene genotype;
    private IWorldMap map;
    private int energy;
    private int startEnergy;
    private int birthdate;
    private int deathdate;

    private int numOKids;
    private ArrayList<Animal> parents = new ArrayList<Animal>();
    private ArrayList<Animal> children = new ArrayList<Animal>();
    private ArrayList<IPositonChangedObserver> observers = new ArrayList<>();


    public Animal(Vector2D position, MapDirection facing, Gene genotype, IWorldMap map, int energy){
        this.position = position;
        this.facing = facing;
        this.genotype = genotype;
        this.map = map;
        this.energy = energy;
        this.startEnergy = energy;
        this.numOKids = 0;
    }

    public void setBirthdate(int Val){
        this.birthdate = Val;
    }

    public int getBirthdate(){
        return this.birthdate;
    }

    public void setDeathdate(int value){
        this.deathdate = value;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Color toColor() {
        if (this.energy == 0) return new Color(222, 221, 224);
        if (this.energy < 0.2 * this.startEnergy) return new Color(224, 179, 173);
        if (this.energy < 0.4 * this.startEnergy) return new Color(224, 142, 127);
        if (this.energy < 0.6 * this.startEnergy) return new Color(201, 124, 110);
        if (this.energy < 0.8 * this.startEnergy) return new Color(182, 105, 91);
        if (this.energy < this.startEnergy) return new Color(164, 92, 82);
        if (this.energy < 2 * this.startEnergy) return new Color(146, 82, 73);
        if (this.energy < 4 * this.startEnergy) return new Color(128, 72, 64);
        if (this.energy < 6 * this.startEnergy) return new Color(119, 67, 59);
        if (this.energy < 8 * this.startEnergy) return new Color(88, 50, 44);
        if (this.energy < 10 * this.startEnergy) return new Color(74, 42, 37);
        return new Color(55, 31, 027);
    }
    @Override
    public String toString(){
        return this.energy<=0 ? "X":this.getFacing().toString();
    }

    public MapDirection getFacing() {
        return this.facing;
    }

    public int getEnergy(){ return this.energy; }

    public Gene getGenotype() {return this.genotype; }

    public void turn(int num){
        while(num>0){
            this.facing.next();
            num-=1;
        }
    }

    public void move(){
        Vector2D whereTo = this.facing.toUnitVector();
        Vector2D newpos = whereTo.add(this.getPosition());
        if(this.map.canMoveTo(newpos)){
            this.positionChanged(this.getPosition(),newpos,this);
            this.position = newpos;
        }
    }

    public boolean positionChanged(Vector2D position, Vector2D newpos, Animal animal){
        for(IPositonChangedObserver watcher:observers){
            watcher.positionChanged(position,newpos,animal);
        }
        return true;
    }

    public boolean isDead(){
        return this.energy<=0;
    }

    public void changeEnergy(int value){
        this.energy+=value;
    }

    public Animal makeAChild(Animal other){
        int energy = this.energy/4+other.energy/4;
        this.changeEnergy((int)-1*this.energy/4);
        other.changeEnergy((int)-1*other.energy/4);
        Vector2D placement = this.getPosition();
        placement = this.map.goodSpot(placement);
        Gene genotype = new Gene(this.genotype,other.genotype);
        MapDirection facing = null;
        facing = facing.randomDir();
        Animal child = new Animal(placement,facing,genotype,this.map,energy);
        child.startEnergy = this.startEnergy;
        this.numOKids++;
        other.numOKids++;
        return child;
    }
    @Override
    public void addObserver(IPositonChangedObserver watcher){
        this.observers.add(watcher);
    }

    @Override
    public void removeObserver(IPositonChangedObserver watcher){
        this.observers.remove(watcher);
    }

    public int getChildness(){
        return this.numOKids;
    }


}
